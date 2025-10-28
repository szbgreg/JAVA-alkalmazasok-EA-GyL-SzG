package com.example.beadando.model;

public class ActualPriceResponse {
    private PriceView priceView;
    private String strOut;

    public PriceView getPriceView() {
        return priceView;
    }

    public void setPriceView(PriceView priceView) {
        this.priceView = priceView;
    }

    public String getStrOut() {
        return strOut;
    }

    public void setStrOut(String strOut) {
        this.strOut = strOut;
    }
}
