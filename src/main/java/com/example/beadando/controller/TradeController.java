package com.example.beadando.controller;

import com.example.beadando.Config;
import com.oanda.v20.Context;
import com.oanda.v20.account.AccountSummary;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class TradeController {

    @GetMapping("/forex/account")
    public String f1(Model model) {
        Context ctx = new Context(Config.URL, Config.TOKEN);

        model.addAttribute("activePage", "forex-account");

        try {
            AccountSummary summary = ctx.account.summary(Config.ACCOUNTID).getAccount();
            OffsetDateTime time = OffsetDateTime.parse(summary.getCreatedTime());
            String formatted = time.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
            summary.setCreatedTime(formatted);
            model.addAttribute("summary", summary);
            return "forex/account";
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "forex/account";
    }

}
