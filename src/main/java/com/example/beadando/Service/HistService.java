package com.example.beadando.Service;

import com.example.beadando.Config;
import com.example.beadando.model.CandleData;
import com.oanda.v20.Context;
import com.oanda.v20.ContextBuilder;
import com.oanda.v20.instrument.Candlestick;
import com.oanda.v20.instrument.InstrumentCandlesRequest;
import com.oanda.v20.instrument.InstrumentCandlesResponse;
import com.oanda.v20.primitives.InstrumentName;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Historikus árfolyamadatok lekérdezése OANDA v20 API-val
 */
@Service
public class HistService {

    public List<CandleData> getLastCandles(String instrument, String granularity) {
        List<CandleData> result = new ArrayList<>();

        try {
            Context ctx = new ContextBuilder(Config.URL)
                    .setToken(Config.TOKEN)
                    .setApplication("HistorikusAdatok")
                    .build();

            InstrumentCandlesRequest request =
                    new InstrumentCandlesRequest(new InstrumentName(instrument));
            request.setCount(10L);
            request.setGranularity(
                    com.oanda.v20.instrument.CandlestickGranularity.valueOf(granularity)
            );

            InstrumentCandlesResponse resp = ctx.instrument.candles(request);

            for (Candlestick candle : resp.getCandles()) {
                String time = candle.getTime().toString();
                String close = candle.getMid().getC().toString();
                result.add(new CandleData(time, close));
            }

        } catch (Exception e) {
            throw new RuntimeException("Hiba a historikus adatok lekérésekor", e);
        }

        return result;
    }
}
