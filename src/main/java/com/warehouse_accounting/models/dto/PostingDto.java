package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostingDto {
    private Long id;
    private LocalDateTime dateOfCreation;

    private WarehouseDto warehouseTo = new WarehouseDto();

    private CompanyDto company = new CompanyDto();

    private BigDecimal sum = BigDecimal.valueOf(0);

    private boolean moved ;

    private boolean printed ;

    private String comment;


}