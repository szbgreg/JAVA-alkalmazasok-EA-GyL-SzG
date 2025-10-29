package com.example.beadando.model;

/**
 * OANDA REST v20 API granularitások (gyertyák időlépcsői)
 * Forrás: https://developer.oanda.com/rest-live-v20/instrument-df/
 */
public enum Granularity {
    S5("5 sec"),
    S10("10 sec"),
    S15("15 sec"),
    S30("30 sec"),
    M1("1 min"),
    M2("2 min"),
    M4("4 min"),
    M5("5 min"),
    M10("10 min"),
    M15("15 min"),
    M30("30 min"),
    H1("1 hour"),
    H2("2 hour"),
    H3("3 hour"),
    H4("4 hour"),
    H6("6 hour"),
    H8("8 hour"),
    H12("12 hour"),
    D("1 day"),
    W("1 week"),
    M("1 month");

    private final String label;

    Granularity(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
