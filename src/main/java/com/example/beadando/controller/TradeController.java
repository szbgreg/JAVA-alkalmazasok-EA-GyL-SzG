package com.example.beadando.controller;

import com.example.beadando.Config;
import com.example.beadando.Service.TradeService;
import com.example.beadando.model.InstrumentRequest;
import com.oanda.v20.Context;
import com.oanda.v20.ExecuteException;
import com.oanda.v20.RequestException;
import com.oanda.v20.account.AccountSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @GetMapping("/forex/account")
    public String f1(Model model) {

        model.addAttribute("activePage", "forex-account");

        try {
            AccountSummary summary = tradeService.getAccountSummary();
            model.addAttribute("summary", summary);
            return "forex/account";
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "forex/account";
    }

    @GetMapping("/forex/actual-prices")
    public String actualPrices(Model model) throws ExecuteException, RequestException {

        model.addAttribute("instruments", tradeService.getInstruments());
        model.addAttribute("pair", new InstrumentRequest());
        model.addAttribute("activePage", "forex-aktar");
        return "forex/actual_prices";
    }
}
