package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductionStageDto {

    private Long id;
    private String name;
    private String description;
    private boolean generalAccess;
    private boolean archived;
    private String sortNumber;
    private Long ownerDepartmentId;
    private String ownerDepartmentName;
    private Long ownerEmployeeId;
    private String ownerEmployeeName;
    private Date dateOfEdit;
    private Long editorEmployeeId;
    private String editorEmployeeName;
}

