package com.example.beadando.controller;

import com.example.beadando.dto.OpenOrderForm;
import com.example.beadando.Service.V20TradingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TradingController {

    private final V20TradingService tradingService;

    private static final List<String> INSTRUMENTS = List.of(
            "EUR_USD","GBP_USD","USD_JPY","USD_CHF","AUD_USD","USD_CAD","NZD_USD","XAU_USD"
    );

    @GetMapping("/forex/open")
    public String showOpenForm(Model model) {
        model.addAttribute("activePage", "forex-nyit");
        if (!model.containsAttribute("form")) {
            OpenOrderForm form = new OpenOrderForm();
            form.setInstrument("EUR_USD");
            form.setUnits(100); // alapértelmezett Long
            model.addAttribute("form", form);
        }
        model.addAttribute("instruments", INSTRUMENTS);
        return "forex/open";
    }

    @PostMapping("/forex/open")
    public String submitOpen(@Valid OpenOrderForm form,
                             BindingResult br,
                             RedirectAttributes ra) {

        if (form.getUnits() == null || form.getUnits() == 0) {
            br.rejectValue("units", "units.zero", "A mennyiség nem lehet 0.");
        }

        if (br.hasErrors()) {
            ra.addFlashAttribute("org.springframework.validation.BindingResult.form", br);
            ra.addFlashAttribute("form", form);
            return "redirect:/forex/open";
        }

        try {
            var result = tradingService.placeMarketOrder(form.getInstrument(), form.getUnits());
            ra.addFlashAttribute("success",
                    "Piaci megbízás elküldve • Instrumentum: %s • Mennyiség: %d • Ticket/TradeID: %s"
                            .formatted(result.instrument(), result.units(), result.tradeId()));
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Sikertelen megbízás: " + e.getMessage());
            ra.addFlashAttribute("form", form);
        }
        return "redirect:/forex/open";
    }
}
