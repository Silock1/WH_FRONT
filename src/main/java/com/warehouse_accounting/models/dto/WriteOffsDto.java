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
public class WriteOffsDto {
    private Long id;

    private LocalDateTime dateTime;

    private CompanyDto company = new CompanyDto();

    private String warehouseName;

    private String status;

    private BigDecimal sum = BigDecimal.valueOf(0);

    private boolean moved;

    private boolean printed;

    private String comment;

    public WriteOffsDto(long l, LocalDateTime now, CompanyDto companyDto, boolean b, boolean b1, String comment) {
    }
}
