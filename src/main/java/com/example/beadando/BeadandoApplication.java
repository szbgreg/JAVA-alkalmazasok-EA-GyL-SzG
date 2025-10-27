package com.example.beadando;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class BeadandoApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeadandoApplication.class, args);
    }

    @GetMapping("/")
    public String showHomePage(){
        return "index";
    }
}
