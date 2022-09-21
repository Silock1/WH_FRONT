package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
//@Builder
//@AllArgsConstructor
@NoArgsConstructor
public class IncomingVoicesDto {
    private Long sn;

    private LocalDate date;

    private String otpr;

    private String poluch;
    private Long sum;

    private String stat;

    public IncomingVoicesDto(Long sn, LocalDate date, String otpr,
                             String poluch, Long sum, String stat) {
        this.sn = sn;
        this.date = date;
        this.otpr = otpr;
        this.poluch = poluch;
        this.sum = sum;
        this.stat = stat;

    }
}
