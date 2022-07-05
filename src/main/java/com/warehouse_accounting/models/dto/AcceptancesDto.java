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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AcceptancesDto {

    private Long id;
    private String number;
    private LocalDateTime time;

    private Long warehouseId;
    private String warehouseName;

    private Long contractorId;
    private String contractorName;

    private Long companyId;
    private String companyName;

    private BigDecimal sum;
    private BigDecimal paid;
    private BigDecimal noPaid;

    private LocalDateTime dateIncomingNumber;
    private String incomingNumber;

    private Long projectId;
    private String projectName;

    private Long contractId;
    private String contractNumber;

    private BigDecimal overHeadCost;
    private BigDecimal returnSum;

    private Boolean isSharedAccess;

    private Long ownerEmployeeId;
    private String ownerEmployeeName;

    private Long ownerDepartmentId;
    private String ownerDepartmentName;

    private Boolean send;
    private Boolean print;
    private String comment;

    private LocalDateTime whenChanged;

    private Long idWhoChanged;
    private String nameWhoChanget;


    private List<TaskDto> tasksDto = new ArrayList<>();
    private List<FileDto> filesDto = new ArrayList<>();
    private List<ProductDto> productDtos = new ArrayList<>();
}
