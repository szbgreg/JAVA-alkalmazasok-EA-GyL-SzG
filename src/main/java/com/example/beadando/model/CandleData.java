package com.example.beadando.model;

public class CandleData {
    private String time;
    private String close;

    public CandleData(String time, String close) {
        this.time = time;
        this.close = close;
    }

    public String getTime() {
        return time;
    }

    public String getClose() {
        return close;
    }
}
