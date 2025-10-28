package com.example.beadando.Service;

import com.example.beadando.Config;
import com.oanda.v20.Context;
import com.oanda.v20.ExecuteException;
import com.oanda.v20.RequestException;
import com.oanda.v20.account.AccountInstrumentsRequest;
import com.oanda.v20.account.AccountInstrumentsResponse;
import com.oanda.v20.account.AccountSummary;
import com.oanda.v20.primitives.Instrument;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class TradeService {

    private Context ctx = new Context(Config.URL, Config.TOKEN);

    public AccountSummary getAccountSummary() throws ExecuteException, RequestException {
        AccountSummary summary = ctx.account.summary(Config.ACCOUNTID).getAccount();
        OffsetDateTime time = OffsetDateTime.parse(summary.getCreatedTime());
        String formatted = time.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        summary.setCreatedTime(formatted);
        return summary;
    }

    public List<Instrument> getInstruments() throws ExecuteException, RequestException {
        AccountInstrumentsRequest request = new AccountInstrumentsRequest(Config.ACCOUNTID);
        AccountInstrumentsResponse response = ctx.account.instruments(request);
        return response.getInstruments();
    }

}
