package com.example.beadando.Service;

import com.example.beadando.Config;
import com.example.beadando.exception.TestFailureException;
import com.oanda.v20.Context;
import com.oanda.v20.ContextBuilder;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.trade.TradeCloseRequest;
import com.oanda.v20.trade.TradeCloseResponse;
import com.oanda.v20.trade.TradeSpecifier;
import org.springframework.stereotype.Service;

@Service
public class V20TradeCloseService {

    private Context ctx(String app) {
        return new ContextBuilder(Config.URL)
                .setToken(Config.TOKEN)
                .setApplication(app)
                .build();
    }

    private final AccountID accountId = Config.ACCOUNTID;

    /** Piaci áras zárás: az adott tradeId összes egységét lezárja. */
    public String closeAllUnits(long tradeId) {
        try {
            TradeSpecifier spec = new TradeSpecifier(String.valueOf(tradeId));
            TradeCloseRequest req = new TradeCloseRequest(accountId, spec);
            req.setUnits("ALL");                 // v20: "ALL" / "NONE" / szám is lehet
            TradeCloseResponse resp = ctx("ForexClose").trade.close(req);

            // Visszajelzéshez összerakunk egy rövid üzenetet
            var fill = resp.getOrderFillTransaction();
            String pl   = fill != null && fill.getPl() != null ? fill.getPl().toString() : "-";
            String ins  = fill != null && fill.getInstrument() != null ? fill.getInstrument().toString() : "-";
            String px   = fill != null && fill.getPrice() != null ? fill.getPrice().toString() : "-";
            return "Trade #" + tradeId + " lezárva. Instrument: " + ins + ", ár: " + px + ", P/L: " + pl;

        } catch (Exception e) {
            throw new TestFailureException(e);
        }
    }
}
