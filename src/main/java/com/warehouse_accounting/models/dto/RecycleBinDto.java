package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecycleBinDto {

    private Long id;
    private String documentType;
    private String number;
    // private LocalDate date; ошибка
    private BigDecimal sum  = BigDecimal.valueOf(0);
    private Long warehouseID;
    private String warehouseName;
    private String warehouseFrom;
    private Long companyID;//
    private String companyName;
    private Long contractorID;
    private String contractorName;
    private String status;
    private Long projectID ;
    private String projectName;
    private String shipped;
    private String printed;
    private String comment;

}
