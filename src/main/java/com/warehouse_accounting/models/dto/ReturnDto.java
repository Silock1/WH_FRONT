package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private List<ProductDto> productDtoList;

    private Long contractorId;
    private String contractorName;

    private Long contractId;
    private String contractNumber;

    private Long warehouseId;
    private String warehouseName;

    private Long companyId;
    private String companyName;

    private Long projectId;
    private String projectName;

    // контрагент
    private Long authorId;
    private String authorLastName;
    private String authorFirstName;


}
