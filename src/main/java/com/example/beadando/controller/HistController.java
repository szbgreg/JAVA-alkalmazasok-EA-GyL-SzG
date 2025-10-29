package com.example.beadando.controller;

import com.example.beadando.model.CandleData;
import com.example.beadando.model.Granularity;
import com.example.beadando.Service.HistService;
import jakarta.servlet.ServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/forex")
public class HistController {

    private final HistService histService;
    private final ServletResponse servletResponse;

    @GetMapping("/historic-price")
    public String showHistPage(@RequestParam(required = false) String instrument,
                               @RequestParam(required = false) Granularity granularity,
                               Model model) {

        model.addAttribute("activePage", "forex-histar"); // <-- a menü ehhez igazodik

        if (instrument == null) instrument = "EUR_USD";
        if (granularity == null) granularity = Granularity.H1;

        List<CandleData> candles = histService.getLastCandles(instrument, granularity.name());

        String items = candles.stream()
                .map(c -> "Candle(time=" + c.getTime() + ", close=" + c.getClose() + ")")
                .collect(Collectors.joining(", "));
        String candleStr = "Candles(instrument=" + instrument
                + ", granularity=" + granularity.name()
                + ", count=" + candles.size()
                + ", items=[" + items + "])";

        model.addAttribute("instruments", List.of("EUR_USD","GBP_USD","USD_JPY","AUD_USD","USD_CAD"));
        model.addAttribute("granularities", Granularity.values());
        model.addAttribute("instrument", instrument);
        model.addAttribute("granularity", granularity);
        model.addAttribute("candles", candles);
        model.addAttribute("candleStr", candleStr);

        return "forex/hist"; // templates/forex/hist.html
    }
}
