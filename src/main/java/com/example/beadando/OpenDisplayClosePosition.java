package com.example.beadando;

import com.oanda.v20.Context;
import com.oanda.v20.ContextBuilder;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.order.MarketOrderRequest;
import com.oanda.v20.order.OrderCreateRequest;
import com.oanda.v20.order.OrderCreateResponse;
import com.oanda.v20.primitives.InstrumentName;

public abstract class OpenDisplayClosePosition {
    static Context ctx;
    static AccountID accountId;

    public static void main(String[] args) {
        ctx = new ContextBuilder(Config.URL)
                .setToken(Config.TOKEN)
                .setApplication("StepByStepOrder")
                .build();
        accountId = Config.ACCOUNTID;

        // Minta nyitás (NZD_USD, -10 = SHORT)
        Nyitas();
        System.out.println("Done");
    }

    static void Nyitas() {
        System.out.println("Place a Market Order");
        InstrumentName instrument = new InstrumentName("NZD_USD");
        try {
            OrderCreateRequest request = new OrderCreateRequest(accountId);
            MarketOrderRequest marketorderrequest = new MarketOrderRequest();
            marketorderrequest.setInstrument(instrument);

            // Ha pozitív -> LONG, ha negatív -> SHORT:
            marketorderrequest.setUnits(-10);

            request.setOrder(marketorderrequest);
            OrderCreateResponse response = ctx.order.create(request);

            // tradeId (= Ticket Number a webes felületen)
            System.out.println("tradeId: " + response.getOrderFillTransaction().getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}