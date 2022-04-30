package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductionProcessTechnologyDto {

    private Long id;
    private String name;
    private String description;
    private boolean generalAccess;
    private boolean archived;
    private String sortNumber;
    private Long ownerDepartmentId;
    private Long ownerEmployeeId;
    private Date dateOfEdit;
    private Long editorEmployeeId;
    private Set<Long> usedProductionStageId;

}
