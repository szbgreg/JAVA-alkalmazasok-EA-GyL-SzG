package com.example.beadando.controller;

import com.example.beadando.Service.V20TradeCloseService;
import com.example.beadando.dto.CloseTradeForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/forex")
public class CloseController {

    private final V20TradeCloseService closeService;

    @ModelAttribute("form")
    public CloseTradeForm form() {
        return new CloseTradeForm(); // osztály getter/setterrel (ne record)
    }

    @GetMapping("/close")
    public String showForm(Model model) {
        model.addAttribute("activePage", "forex-zar");
        model.addAttribute("form", new CloseTradeForm());
        return "forex/close"; // templates/forex/close.html
    }

    @PostMapping("/close")
    public String doClose(
            @ModelAttribute("form") @Valid CloseTradeForm form,
            BindingResult br,
            Model model
    ) {
        model.addAttribute("activePage", "forex-zar");

        if (br.hasErrors()) {
            return "forex/close"; // a "form" már a modellben van, nem lesz Whitelabel
        }

        try {
            String msg = closeService.closeAllUnits(form.getTradeId());
            model.addAttribute("success", msg);
            // új üres form az űrlaphoz
            model.addAttribute("form", new CloseTradeForm());
        } catch (Exception e) {
            model.addAttribute("error", "Zárás sikertelen: " +
                    (e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName()));
            // form marad a modellben a @ModelAttribute miatt
        }
        return "forex/close";
    }
}
