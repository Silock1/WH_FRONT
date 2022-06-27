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
public class ContractDto {

    private Long id;

    private String number;

    private LocalDate contractDate;

    private CompanyDto companyDto;

    private BankAccountDto bankAccountDto;

    private ContractorDto contractorDto;

    private BigDecimal amount;

    private BigDecimal paid;

    private BigDecimal done;

    private Boolean isSharedAccess;

    private DepartmentDto ownerDepartment;

    private EmployeeDto ownerEmployee;

    private Boolean isSend;

    private Boolean isPrint;

    private Boolean archive;

    private String comment;

    private LocalDate whenChange;

    private EmployeeDto whoChange;

    private LegalDetailDto legalDetailDto;
}
