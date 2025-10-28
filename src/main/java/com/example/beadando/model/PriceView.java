package com.example.beadando.model;

import com.oanda.v20.pricing.ClientPrice;

public class PriceView {
    private String instrument;
    private String time;
    private boolean tradeable;
    private double bid;
    private double ask;

    public PriceView(ClientPrice price) {
        this.instrument = price.getInstrument().toString();
        this.time = price.getTime().toString();
        this.tradeable = price.getTradeable();
        this.bid = price.getBids().get(0).getPrice().doubleValue();
        this.ask = price.getAsks().get(0).getPrice().doubleValue();
    }

    public String getInstrument() {
        return instrument;
    }

    public String getTime() {
        return time;
    }

    public boolean isTradeable() {
        return tradeable;
    }

    public double getBid() {
        return bid;
    }

    public double getAsk() {
        return ask;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTradeable(boolean tradeable) {
        this.tradeable = tradeable;
    }

    public void setBid(double bid) {
        this.bid = bid;
    }

    public void setAsk(double ask) {
        this.ask = ask;
    }
}