package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TechnologicalMapMaterialDto {
    private Long id;

    private Long materialId;
    private String materialName;

    private BigDecimal count = BigDecimal.valueOf(1);

    private TechnologicalMapDto technologicalMapDto = new TechnologicalMapDto();
}
