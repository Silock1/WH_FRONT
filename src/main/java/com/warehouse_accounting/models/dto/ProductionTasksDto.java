package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductionTasksDto {

    private Long id;
    private Long taskId;
    private Date dateOfCreate;
    private String organization;
    private Date plannedDate;
    private Long materialWarehouseId;
    private String materialWarehouseName;
    private Long productionWarehouseId;
    private String productionWarehouseName;
    private Date dateOfStart;
    private Date dateOfEnd;
    private Boolean isAccessed;
    private Long ownerDepartmentId;
    private String ownerDepartmentName;
    private Long ownerEmployeeId;
    private String ownerEmployeeName;
    private Date dateOfSend;
    private Date dateOfPrint;
    private String description;
    private Date dateOfEdit;
    private Long editEmployeeId;
    private String editEmployeeName;

}
