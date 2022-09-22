package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TypeOfPriceDto {

    private Long id;

    private String name;

    private String sortNumber;

    @Override
    public String toString() {
        return name;
    }
}


