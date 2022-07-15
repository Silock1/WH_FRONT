package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocumentDto {

    private Long id;

    private String type;

    private String docNumber;

    private BigDecimal sum;

    private Long warehouseFromId;
    private String warehouseFromName;

    private Long warehouseToId;
    private String warehouseToName;

    private Long companyId;
    private String companyName;

    private Long contrAgentId;
    private String contrAgentName;

    private Long projectId;
    private String projectName;

    private Long salesChannelId;
    private String salesChannelName;

    private Long contractId;
    private String contractNumber;

    private Boolean isSharedAccess;

    private Long departmentId;
    private String departmentName;

    private Long employeeId;
    private String employeeFirstname;

    private Boolean sent;

    private Boolean print;

    private String comments;

    private Long updatedFromEmployeeId;
    private String updatedFromEmployeeFirstname;

    private List<TaskDto> tasks = new ArrayList<>();

    private List<FileDto> files = new ArrayList<>();
}