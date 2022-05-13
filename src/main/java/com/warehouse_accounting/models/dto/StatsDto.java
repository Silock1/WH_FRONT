package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatsDto {
    private int paymentCount;
    private BigDecimal paymentAmount;
    private int expiredOrdersCount;
    private BigDecimal expiredOrdersAmount;
    private String company;
    private BigDecimal companyBalance;
}
