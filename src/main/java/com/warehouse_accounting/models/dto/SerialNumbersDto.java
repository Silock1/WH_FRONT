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
public class SerialNumbersDto {
    private Long sn;

    private Long code;

    private Double article;

    private String product;

    private String fabric;
    private String typeOfDoc;

    private Long number;

    private String description;

    public SerialNumbersDto(Long sn, Long code, Double article,
                     String product, String fabric, String typeOfDoc, Long number, String description) {
        this.sn = sn;
        this.code = code;
        this.article = article;
        this.product = product;
        this.fabric = fabric;
        this.typeOfDoc = typeOfDoc;
        this.number = number;
        this.description = description;
    }
}
