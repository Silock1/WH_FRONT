package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyDto {
    private Long id;
    private String numcode;
    private String charcode;
    private int nominal;
    private String name;
    private String _Value;
    private double value;
}

