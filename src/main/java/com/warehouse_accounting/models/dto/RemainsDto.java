package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
//@AllArgsConstructor
@NoArgsConstructor
public class RemainsDto {
    private Long id;

    private String name;

    private Long sum;

    private Long article;

    private Long remainder;

    private Long NonRemainder;

    private Long reserve;

    private Long wait;

    private Long available;

    private String unit;

    private Long days;

    private Double price;
    private Double salesPrice;
    private Double buyPrice;

    public RemainsDto(Long id, String name, Long sum,
                      Long article, Long remainder, Long nonRemainder,
                      Long reserve, Long wait, Long available, String unit, Long days, Double price,
                      Double salesPrice, Double buyPrice) {
        this.id = id;
        this.name = name;
        this.sum = sum;
        this.article = article;
        this.remainder = remainder;
        this.NonRemainder = nonRemainder;
        this.reserve = reserve;
        this.wait = wait;
        this.available = available;
        this.unit = unit;
        this.days = days;
        this.price = price;
        this.salesPrice = salesPrice;
        this.buyPrice = buyPrice;
    }
}
