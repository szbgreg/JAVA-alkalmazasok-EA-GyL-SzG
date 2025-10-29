package com.example.beadando.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OpenOrderForm {
    @NotBlank
    private String instrument;

    @NotNull
    private Integer units; // + = Long, - = Short, 0 tiltva
}
