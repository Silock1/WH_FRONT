package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseDto {
    private Long id;
    private String name;
    private String sortNumber;
    private String address;
    private String commentToAddress;
    private String comment;

    public String getName(WarehouseDto warehouseDto) {
        return warehouseDto.name == null ? "Warehouse number: " + warehouseDto.id : warehouseDto.name;
    }

    @Override
    public String toString() {
        return name == null ? "Company number: " + id : name;
    }
}
