package com.example.beadando.Service;

import com.example.beadando.Config;
import com.example.beadando.model.ActualPriceResponse;
import com.example.beadando.model.InstrumentRequest;
import com.example.beadando.model.PriceView;
import com.oanda.v20.Context;
import com.oanda.v20.ExecuteException;
import com.oanda.v20.RequestException;
import com.oanda.v20.account.AccountInstrumentsRequest;
import com.oanda.v20.account.AccountInstrumentsResponse;
import com.oanda.v20.account.AccountSummary;
import com.oanda.v20.pricing.ClientPrice;
import com.oanda.v20.pricing.PricingGetRequest;
import com.oanda.v20.pricing.PricingGetResponse;
import com.oanda.v20.primitives.Instrument;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class TradeService {

    private Context ctx = new Context(Config.URL, Config.TOKEN);

    public AccountSummary getAccountSummary() throws ExecuteException, RequestException {
        AccountSummary summary = ctx.account.summary(Config.ACCOUNTID).getAccount();
        String formattedTime = formatTime(summary.getCreatedTime().toString());
        summary.setCreatedTime(formattedTime);
        return summary;
    }

    public List<Instrument> getInstruments() throws ExecuteException, RequestException {
        AccountInstrumentsRequest request = new AccountInstrumentsRequest(Config.ACCOUNTID);
        AccountInstrumentsResponse response = ctx.account.instruments(request);
        return response.getInstruments();
    }

    public ActualPriceResponse getActualPrice(InstrumentRequest instrumentRequest) {
        ActualPriceResponse actualPriceResponse = new ActualPriceResponse();
        List<String> instruments = new ArrayList<>();
        String strOut = "";

        PriceView priceView;

        instruments.add(instrumentRequest.getInstrument());

        try {
            PricingGetRequest request = new PricingGetRequest(Config.ACCOUNTID, instruments);
            PricingGetResponse response = ctx.pricing.get(request);
            ClientPrice price = response.getPrices().get(0);

            strOut += price;
            actualPriceResponse.setStrOut(strOut);

            priceView = new PriceView(price);
            String formattedTime = formatTime(priceView.getTime());
            priceView.setTime(formattedTime);
            actualPriceResponse.setPriceView(priceView);

            return actualPriceResponse;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String formatTime(String time) {
        OffsetDateTime t = OffsetDateTime.parse(time);
        return t.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    }
}
