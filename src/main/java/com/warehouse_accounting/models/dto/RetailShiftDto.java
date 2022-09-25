package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RetailShiftDto {
    private Long id;
    private LocalDate dateOfOpen;
    private LocalDate dateOfClose;
    private PointOfSalesDto pointOfSales = new PointOfSalesDto();
    private WarehouseDto warehouse = new WarehouseDto();
    private CompanyDto company = new CompanyDto();
    private BankAccountDto bank = new BankAccountDto();
    private BigDecimal cashlessShiftRevenue;
    private BigDecimal  cashShiftRevenue;
    private BigDecimal  shiftRevenue;
    private BigDecimal recevied;
    private BigDecimal sale;
    private BigDecimal comission;
    private boolean isAccessed;
    private DepartmentDto ownerDepartment = new DepartmentDto();
    private EmployeeDto ownerEmployee = new EmployeeDto();
    private boolean isSent;
    private boolean isPrinted;
    private String description;
    private LocalDate dateOfEdit;
    private EmployeeDto editEmployee = new EmployeeDto();
}
