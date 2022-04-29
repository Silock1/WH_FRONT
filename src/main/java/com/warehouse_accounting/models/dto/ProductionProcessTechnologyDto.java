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
    private Boolean generalAccess;
    private boolean archived;
    private String sortNumber;
    private Long ownerDepartmentId;
    private Long ownerEmployeeId;
    private Date dateOfEdit;
    private Long editorEmployeeId;
    private Set<Long> usedProductionStageId;





//    public ProductionProcessTechnologyDto( Long id,
//                                           String name,
//                                           String description,
//                                           Boolean generalAccess,
//                                           String sortNumber,
//                                           Long department ,
//                                           Long employee
//    ) {
//        this.id = id;
//        this.name = name;
//        this.description = description;
//        this.generalAccess = generalAccess;
//        this.sortNumber = sortNumber;
//        this.department.setId(department);
//        this.employee.setId(employee);
//    }
}
