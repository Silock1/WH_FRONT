package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnitDto { // валюта, файл "Валюта.xlsx" не содержит единицы валют, это копия UnitsOfMeasure.xls

    private Long id;

    private String shortName;

    private String fullName;

    private String sortNumber;

}
