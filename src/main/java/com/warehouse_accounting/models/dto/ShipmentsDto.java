package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShipmentsDto {

    private Long id;

    private LocalDateTime data;

    private WarehouseDto warehouse = new WarehouseDto();

    private ContractorDto contractor = new ContractorDto();

    private CompanyDto company = new CompanyDto();

    private CompanyDto consignee = new CompanyDto();

    private BigDecimal sum;

    private BigDecimal paid;

    private Boolean sent = false;

    private Boolean printed = false;

    private String comment;

    List<TaskDto> tasksDto = new ArrayList<>();

    List<FileDto> filesDto = new ArrayList<>();

}
