package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryDto {

    private Long id;

    private LocalDateTime dateTime;

    private WarehouseDto warehouseFrom = new WarehouseDto();

    private CompanyDto company = new CompanyDto();

    private boolean moved;

    private boolean printed;

    private String comment;


}

