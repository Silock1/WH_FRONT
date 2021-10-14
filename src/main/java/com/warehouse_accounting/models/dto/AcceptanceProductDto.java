package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AcceptanceProductDto {

    private Long id;

    private AcceptancesDto acceptancesDto;

    private ProductDto productDto;

    private BigDecimal sumPaid = BigDecimal.valueOf(0);

    private BigDecimal count = BigDecimal.valueOf(0);

    private BigDecimal price = BigDecimal.valueOf(0);

    private BigDecimal sum = count.multiply(price);

    //for the payment
    private LocalDateTime incomingDate;
}
