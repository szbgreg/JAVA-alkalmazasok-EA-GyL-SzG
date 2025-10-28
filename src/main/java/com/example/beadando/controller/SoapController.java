package com.example.beadando.controller;

import com.example.beadando.Service.SoapService;
import com.example.beadando.model.ExchangeRatesResponse;
import com.example.beadando.model.SoapRequestObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.xml.sax.SAXException;
import soapclient.MNBArfolyamServiceSoapGetCurrenciesStringFaultFaultMessage;
import soapclient.MNBArfolyamServiceSoapGetExchangeRatesStringFaultFaultMessage;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

@Controller
public class SoapController {
    @Autowired
    SoapService soapService;

    @GetMapping("/soap")
    public String showPage(Model model) throws MNBArfolyamServiceSoapGetCurrenciesStringFaultFaultMessage {
        model.addAttribute("requestObj", new SoapRequestObj());
        model.addAttribute("currencies", soapService.getCurrencies());

        return "soap";
    }

    @PostMapping("/soap")
    public String processingData(@ModelAttribute SoapRequestObj requestObj, Model model) throws MNBArfolyamServiceSoapGetExchangeRatesStringFaultFaultMessage, MNBArfolyamServiceSoapGetCurrenciesStringFaultFaultMessage {
        ExchangeRatesResponse response = soapService.getExchangeRates(requestObj);

        model.addAttribute("currencies", soapService.getCurrencies());
        model.addAttribute("requestObj", requestObj);
        model.addAttribute("xml", response.getXml());
        model.addAttribute("exchangeRates", response.getExchangeRates());
        return "soap";
    }
}
