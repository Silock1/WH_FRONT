package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RemainsDto {
    private Long id;

    private String name;

    private BigDecimal sum = BigDecimal.valueOf(0);

    private Long article;

    private Long remainder;

    private Long NonRemainder;

    private Long reserve;

    private Long wait;

    private Long available;

//    private String unit;
//
//    private Long days;
//
//    private Double price;
//    private Double salesPrice;
//    private Double buyPrice;
//
//    private Boolean access;

    public RemainsDto(long l) {
    }
}
