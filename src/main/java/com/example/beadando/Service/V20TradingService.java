package com.example.beadando.Service;

import com.oanda.v20.Context;
import com.oanda.v20.ContextBuilder;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.order.MarketOrderRequest;
import com.oanda.v20.order.OrderCreateRequest;
import com.oanda.v20.order.OrderCreateResponse;
import com.oanda.v20.primitives.InstrumentName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.beadando.Config;
import com.example.beadando.exception.TestFailureException;

@Service
@RequiredArgsConstructor
public class V20TradingService {

    public record Result(String tradeId, String instrument, int units) {}

    public Result placeMarketOrder(String instrument, int units) {
        if (units == 0) throw new IllegalArgumentException("A mennyiség nem lehet 0.");

        // Config osztály a korábbi feladatból (ugyanazt használjuk!)
        Context ctx = new ContextBuilder(Config.URL)
                .setToken(Config.TOKEN)
                .setApplication("ForexOpenForm")
                .build();

        AccountID accountId = Config.ACCOUNTID;

        try {
            OrderCreateRequest request = new OrderCreateRequest(accountId);
            MarketOrderRequest mo = new MarketOrderRequest();
            mo.setInstrument(new InstrumentName(instrument));
            // OANDA: + = LONG, - = SHORT
            mo.setUnits(units);
            request.setOrder(mo);

            OrderCreateResponse resp = ctx.order.create(request);
            String tradeId = resp.getOrderFillTransaction().getId().toString();
            return new Result(tradeId, instrument, units);

        } catch (Exception e) {
            throw new TestFailureException(e);
        }
    }
}