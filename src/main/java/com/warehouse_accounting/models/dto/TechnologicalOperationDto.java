package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TechnologicalOperationDto {
    private long id;
    private String number;
    private boolean isArchive = false;
    private boolean isPosted;
    private LocalDate technologicalOperationDateTime;
    private BigDecimal volumeOfProduction;
    private String comments;

    private Long warehouseForMaterialsId;
    private String warehouseForMaterialsName;

    private Long warehouseForProductId;
    private String warehouseForProductName;

    private Long companyId;
    private String companyName;

    private Long projectId;
    private String projectName;

    private Long technologicalMapId;
    private String technologicalMapName;

//    private List<TaskDto> tasks = new ArrayList<>();

}
