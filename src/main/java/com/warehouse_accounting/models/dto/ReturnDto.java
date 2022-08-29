package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReturnDto {
    private Long id;
    private String number;
    private LocalDateTime returnDateTime;
    private String comment;
    private boolean isPosted;
    private boolean isSent;
    private BigDecimal sum;
    private List<ProductDto> products;
    private List<TaskDto> tasks;
    private ProjectDto project = new ProjectDto();
    private CompanyDto company = new CompanyDto();
    private WarehouseDto warehouse = new WarehouseDto();
    private ContractorDto contractor = new ContractorDto();
    private ContractDto contract = new ContractDto();



}
