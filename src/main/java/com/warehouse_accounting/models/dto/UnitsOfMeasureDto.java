package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UnitsOfMeasureDto { // единица количества товара

    private Long id;

    private String type; // ?

    private String name; // шт.

    private  String fullName; // Штука

    private String code;

}
