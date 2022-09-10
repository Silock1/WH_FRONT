package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
//@Builder
//@AllArgsConstructor
@NoArgsConstructor
public class TurnsDto {
    private Long id;

    private String name;

    private Double sum;

    private Long article;

    private String unit;

    private Double amountBegin;
    private Double sumBegin; //начало периода

    private Double amountComing;
    private Double sumComing; // приход

    private Double amountConsumption; //расход
    private Double sumConsumption;

    private Double amountEnd;
    private Double sumEnd; //конец периода

    public TurnsDto(Long id, String name, Double sum,
                      Long article, Double amountBegin, String unit, Double sumBegin, Double amountComing, Double sumComing,
                    Double amountConsumption, Double sumConsumption, Double amountEnd, Double sumEnd) {
        this.id = id;
        this.name = name;
        this.sum = sum;
        this.article = article;
        this.amountBegin = amountBegin;
        this.sumBegin = sumBegin;
        this.unit = unit;
        this.amountComing = amountComing;
        this.sumComing = sumComing;
        this.amountEnd = amountEnd;
        this.sumEnd = sumEnd;
        this.amountConsumption = amountConsumption;
        this.sumConsumption = sumConsumption;
    }
}
