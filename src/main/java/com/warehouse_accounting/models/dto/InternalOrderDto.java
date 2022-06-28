package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class InternalOrderDto {

    Long id;

    String number;

    LocalDateTime localDateTime;

    CompanyDto organization = new CompanyDto();
    BigDecimal sum;

    BigDecimal shipped;

    ProjectDto projectDto = new ProjectDto();

    Boolean isSharedAccess;

    WarehouseDto warehouseDto = new WarehouseDto();

    DepartmentDto ownerDepartment = new DepartmentDto();

    EmployeeDto ownerEmployee = new EmployeeDto();

    Boolean sent;

    Boolean print;

    String comment;

    EmployeeDto whoChanged = new EmployeeDto();

    LocalDateTime whenChanged;

    List<TaskDto> tasksDto = new ArrayList<>();

    List<FileDto> filesDto = new ArrayList<>();

    public InternalOrderDto(Long id, String number, LocalDateTime localDateTime, Long organizationId,
                            BigDecimal sum, BigDecimal shipped, Long projectId, Boolean isSharedAccess,
                            Long warehouseId, Long ownerDepId, Long ownerEmpId, Boolean sent,
                            Boolean print, String comment, Long whoChangedId, LocalDateTime whenChanged) {
        this.id = id;
        this.number = number;
        this.localDateTime = localDateTime;
        this.organization.setId(organizationId);
        this.sum = sum;
        this.shipped = shipped;
        this.projectDto.setId(projectId);
        this.isSharedAccess = isSharedAccess;
        this.warehouseDto.setId(warehouseId);
        this.ownerDepartment.setId(ownerDepId);
        this.ownerEmployee.setId(ownerEmpId);
        this.sent = sent;
        this.print = print;
        this.comment = comment;
        this.whoChanged.setId(whoChangedId);
        this.whenChanged = whenChanged;
    }
}
