package com.example.beadando.controller;

import com.example.beadando.Service.V20PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/forex")
public class PositionsController {

    private final V20PositionService positionsService;

    @GetMapping("/position")
    public String positions(Model model) {
        model.addAttribute("activePage", "forex-poz");
        model.addAttribute("positions", positionsService.listOpenPositions());
        return "forex/position";
    }
}
