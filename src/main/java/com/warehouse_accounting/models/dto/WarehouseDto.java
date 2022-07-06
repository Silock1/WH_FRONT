package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public String getName() {
        if(name == null) return "ERROR: warehouse name ID=" + id; // todo: фикс на беке
        return name;
    }

    @Override
    public String toString() {
        return name == null ? "Warehouse number: " + id : name;
    }
}
