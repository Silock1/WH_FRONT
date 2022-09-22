package com.warehouse_accounting.models.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TechnologicalMapDto {
    private Long id;
    private String name;
    private String comment;
    private boolean isArchived = false;
    private boolean isDeleted;
    private BigDecimal productionCost = BigDecimal.valueOf(0);

    private Long technologicalMapGroupId;
    private String technologicalMapGroupName;

    private List<TechnologicalMapProductDto> finishedProducts = new ArrayList<>();
    private List<TechnologicalMapMaterialDto> materials = new ArrayList<>();
}
