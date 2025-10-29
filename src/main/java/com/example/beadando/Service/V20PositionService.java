package com.example.beadando.Service;

import com.example.beadando.Config;
import com.example.beadando.exception.TestFailureException;

import com.oanda.v20.Context;
import com.oanda.v20.ContextBuilder;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.position.Position;
import com.oanda.v20.position.PositionListOpenResponse;
import com.oanda.v20.position.PositionSide;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class V20PositionService {

    /** Nézetbarát sor DTO. Rakhatod külön package-be is (model), ha szeretnéd. */
    public record PositionRow(
            String instrument,
            String longUnits,  String longAvgPrice,  String longUPL,
            String shortUnits, String shortAvgPrice, String shortUPL
    ) {}

    private Context buildContext(String appName) {
        return new ContextBuilder(Config.URL)
                .setToken(Config.TOKEN)
                .setApplication(appName)
                .build();
    }

    private final AccountID accountId = Config.ACCOUNTID;

    /** Nyitott pozíciók OANDA v20 SDK-val (request objektum NÉLKÜL, overloaddal). */
    public List<PositionRow> listOpenPositions() {
        Context ctx = buildContext("ForexPositions");
        try {
            PositionListOpenResponse resp = ctx.position.listOpen(accountId);

            List<PositionRow> rows = new ArrayList<>();
            if (resp == null || resp.getPositions() == null) return rows;

            for (Position p : resp.getPositions()) {
                String instrument = p.getInstrument() != null ? p.getInstrument().toString() : "-";

                PositionSide L = p.getLong();
                String lUnits    = (L != null && L.getUnits() != null)         ? L.getUnits().toString()         : "0";
                String lAvgPrice = (L != null && L.getAveragePrice() != null)  ? L.getAveragePrice().toString()  : "-";
                String lUPL      = (L != null && L.getUnrealizedPL() != null)  ? L.getUnrealizedPL().toString()  : "-";

                PositionSide S = p.getShort();
                String sUnits    = (S != null && S.getUnits() != null)         ? S.getUnits().toString()         : "0";
                String sAvgPrice = (S != null && S.getAveragePrice() != null)  ? S.getAveragePrice().toString()  : "-";
                String sUPL      = (S != null && S.getUnrealizedPL() != null)  ? S.getUnrealizedPL().toString()  : "-";

                rows.add(new PositionRow(instrument, lUnits, lAvgPrice, lUPL, sUnits, sAvgPrice, sUPL));
            }
            return rows;

        } catch (Exception e) {
            throw new TestFailureException(e);
        }
    }

    /** Ha kell a nyers OANDA-típusokhoz. */
    public List<Position> listOpenPositionsRaw() {
        Context ctx = buildContext("ForexPositionsRaw");
        try {
            PositionListOpenResponse resp = ctx.position.listOpen(accountId);
            return (resp == null || resp.getPositions() == null) ? List.of() : resp.getPositions();
        } catch (Exception e) {
            throw new TestFailureException(e);
        }
    }
}
