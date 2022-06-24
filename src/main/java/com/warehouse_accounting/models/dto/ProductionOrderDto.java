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
public class ProductionOrderDto {
    private Long id;

    private String number;

    private LocalDateTime dateTime;

    private Long companyId;

    private String companyName;

    private Long technologicalMapId;

    private String technologicalMapName;

    private BigDecimal volumeOfProduction;

    private Long warehouseId;

    private String warehouseName;

    private LocalDate planDate;

    private Long projectId;

    private String projectName;

    private String comment;

}
