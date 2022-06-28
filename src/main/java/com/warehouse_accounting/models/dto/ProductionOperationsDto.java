package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductionOperationsDto {
    private long id;
    private String number;
    private boolean isArchive = false;
    private LocalDateTime technologicalOperationDateTime;
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

    private List<TaskDto> tasks = new ArrayList<>();

    public ProductionOperationsDto (Long id,
                                    String number,
                                    boolean isArchive,
                                    LocalDateTime dateTime,
                                    BigDecimal volumeOfProduction,
                                    String comments,
                                    Long warehouseForMaterialsId,
                                    String warehouseForMaterialsName,
                                    Long warehouseForProductId,
                                    String warehouseForProductName,
                                    Long companyId,
                                    String companyName,
                                    Long projectId,
                                    String projectName,
                                    Long technologicalMapDtoId,
                                    String technologicalMapDtoName) {
        this.id = id;
        this.number = number;
        this.isArchive = isArchive;
        this.technologicalOperationDateTime = dateTime;
        this.volumeOfProduction = volumeOfProduction;
        this.comments = comments;
        this.warehouseForMaterialsId = warehouseForMaterialsId;
        this.warehouseForMaterialsName = warehouseForMaterialsName;
        this.warehouseForProductId = warehouseForProductId;
        this.warehouseForProductName = warehouseForProductName;
        this.companyId = companyId;
        this.companyName = companyName;
        this.projectId = projectId;
        this.projectName = projectName;
        this.technologicalMapId = technologicalMapDtoId;
        this.technologicalMapName = technologicalMapDtoName;

    }

}
