package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecycleBinDto {

    private Long id;
    private String name;
    private LocalDateTime dateTime;
    private WarehouseDto warehouse = new WarehouseDto();
    private CompanyDto company = new CompanyDto();
    private BigDecimal sum;
    private ContractorGroupDto contractor = new ContractorGroupDto();
    private BigDecimal status;
    private ProjectDto project = new ProjectDto();
    private BigDecimal comment;

}