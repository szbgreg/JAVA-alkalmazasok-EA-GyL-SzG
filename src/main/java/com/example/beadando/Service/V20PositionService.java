package com.example.beadando.Service;

import com.example.beadando.Config;
import com.example.beadando.exception.TestFailureException;

import com.oanda.v20.Context;
import com.oanda.v20.ContextBuilder;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.trade.Trade;
import com.oanda.v20.trade.TradeID;
import com.oanda.v20.trade.TradeListOpenResponse;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class V20PositionService {

    /** A táblázat sorai – a mintában szereplő mezőkkel. */
    public record PositionRow(
            long id,
            String instrument,
            String openTime,
            String currentUnits,
            String price,
            String unrealizedPL
    ) {}

    // Segédfüggvény: TradeID -> long
    private static long toLong(TradeID tradeId) {
        if (tradeId == null) return 0L;
        String s = tradeId.toString();           // v20-ban ez adja vissza az ID-t
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException e) {
            // Ha valaha nem numerikus, jelezzük tisztán
            throw new IllegalArgumentException("TradeID nem numerikus: " + s, e);
        }
    }

    private Context ctx(String app) {
        return new ContextBuilder(Config.URL)
                .setToken(Config.TOKEN)
                .setApplication(app)
                .build();
    }

    private final AccountID accountId = Config.ACCOUNTID;

    /** A controller változtatása nélkül hagyjuk a nevet: listOpenPositions(). */
    public List<PositionRow> listOpenPositions() {
        try {
            TradeListOpenResponse resp = ctx("ForexOpenTrades").trade.listOpen(accountId);

            List<PositionRow> rows = new ArrayList<>();
            if (resp == null || resp.getTrades() == null) return rows;

            for (Trade t : resp.getTrades()) {
                long id           =  toLong(t.getId());
                String instrument = t.getInstrument() != null ? t.getInstrument().toString() : "-";
                String openTime   = t.getOpenTime() != null ? t.getOpenTime().toString() : "-";
                String units      = t.getCurrentUnits() != null ? t.getCurrentUnits().toString() : "0";
                String price      = t.getPrice() != null ? t.getPrice().toString() : "-";
                String upl        = t.getUnrealizedPL() != null ? t.getUnrealizedPL().toString() : "-";

                rows.add(new PositionRow(id, instrument, openTime, units, price, upl));
            }
            return rows;

        } catch (Exception e) {
            throw new TestFailureException(e);
        }
    }

    /** Ha kell nyers lista a konzolos kiíráshoz. */
    public List<Trade> listOpenPositionsRaw() {
        try {
            TradeListOpenResponse resp = ctx("ForexOpenTradesRaw").trade.listOpen(accountId);
            return (resp == null || resp.getTrades() == null) ? List.of() : resp.getTrades();
        } catch (Exception e) {
            throw new TestFailureException(e);
        }
    }
}
