package com.example.beadando.controller;

import com.example.beadando.Service.TradeService;
import com.example.beadando.model.ActualPriceResponse;
import com.example.beadando.model.InstrumentRequest;
import com.example.beadando.model.PriceView;
import com.oanda.v20.ExecuteException;
import com.oanda.v20.RequestException;
import com.oanda.v20.account.AccountSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
        model.addAttribute("instrumentRequest", new InstrumentRequest());
        model.addAttribute("activePage", "forex-aktar");
        return "forex/actual_prices";
    }

    @PostMapping("/forex/actual-prices")
    public String postPrices(@ModelAttribute InstrumentRequest instrumentRequest, Model model) throws ExecuteException, RequestException {
        ActualPriceResponse actualPriceResponse = tradeService.getActualPrice(instrumentRequest);

        model.addAttribute("instruments", tradeService.getInstruments());
        model.addAttribute("selectedInstrument", instrumentRequest.getInstrument());
        model.addAttribute("price", actualPriceResponse.getPriceView());
        model.addAttribute("priceStr", actualPriceResponse.getStrOut());
        return "forex/actual_prices";
    }
}
