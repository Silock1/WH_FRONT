package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerOrdersDto {

    private Long id;

    private String customer;

    private BigDecimal summa;

    private BigDecimal bill;

    private BigDecimal summaPaid;

    private BigDecimal shipped;

    private BigDecimal reserved;

    private String status;

    private String sent;

    private String print;

    private String comment;


}
