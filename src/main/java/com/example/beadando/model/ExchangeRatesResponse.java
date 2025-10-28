package com.example.beadando.model;

import java.util.List;
import java.util.Map;

public class ExchangeRatesResponse {
    private String xml;
    private Map<String, String> exchangeRates;

    public ExchangeRatesResponse(String xml, Map<String, String> exchangeRates) {
        this.xml = xml;
        this.exchangeRates = exchangeRates;
    }

    public String getXml() {
        return xml;
    }

    public Map<String, String> getExchangeRates() {
        return exchangeRates;
    }
}
