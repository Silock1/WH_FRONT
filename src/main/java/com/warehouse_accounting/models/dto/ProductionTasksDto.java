package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductionTasksDto {

    private Long id;
    private Long taskId;
    private LocalDate dateOfCreate;
    private String organization;
    private LocalDate plannedDate;
    private Long materialWarehouseId;
    private String materialWarehouseName;
    private Long productionWarehouseId;
    private String productionWarehouseName;
    private LocalDate dateOfStart;
    private LocalDate dateOfEnd;
    private Boolean isAccessed;
    private Long ownerDepartmentId;
    private String ownerDepartmentName;
    private Long ownerEmployeeId;
    private String ownerEmployeeName;
    private LocalDate dateOfSend;
    private LocalDate dateOfPrint;
    private String description;
    private LocalDate dateOfEdit;
    private Long editEmployeeId;
    private String editEmployeeName;
    private List<Long> additionalFieldsIds;
    private List<String> additionalFieldsNames;

}
