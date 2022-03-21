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
    private String documentType;
    private String number;
    private LocalDateTime date;
    private BigDecimal sum  = BigDecimal.valueOf(0);
    private WarehouseDto warehouse = new WarehouseDto();
    private String warehouseFrom;
    private CompanyDto companyName = new CompanyDto();
    private ContractorGroupDto contractorName = new ContractorGroupDto();
    private String status;
    private ProjectDto project = new ProjectDto();
    private String shipped;
    private String printed;
    private String comment;

  /*  public RecycleBinDto(Long id,
                         String documentType,
                         String number,
                         LocalDateTime date,
                         BigDecimal sum,
                         Long warehouseDto,
                         String warehouseFrom,
                         Long companyNameDto,
                         Long contractorNameDto,
                         String status,
                         Long projectDto,
                         String shipped,
                         String printed,
                         String comment) {
        this.id = id;
        this.documentType = documentType;
        this.number = number;
        this.date = date;
        this.sum = sum;
        this.warehouse.setId(warehouseDto);
        this.warehouseFrom = warehouseFrom;
        this.companyName.setId(companyNameDto);
        this.contractorName.setId(contractorNameDto);
        this.status = status;
        this.project.setId(projectDto);
        this.shipped = shipped;
        this.printed = printed;
        this.comment = comment;
    }*/
}
