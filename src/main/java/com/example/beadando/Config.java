package com.example.beadando;

import com.oanda.v20.account.AccountID;

public class Config {
    private Config() {
    }

    public static final String URL = "https://api-fxpractice.oanda.com";
    public static final AccountID ACCOUNTID = new AccountID("<ACCOUNT-ID>");
    public static final String TOKEN = "<TOKEN>";
}
