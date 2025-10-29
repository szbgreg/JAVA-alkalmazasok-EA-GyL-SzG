package com.example.beadando.dto;

import jakarta.validation.constraints.NotNull;

public class CloseTradeForm {
    @NotNull
    private Long tradeId;

    public Long getTradeId() { return tradeId; }
    public void setTradeId(Long tradeId) { this.tradeId = tradeId; }
}
