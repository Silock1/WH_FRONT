package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdjustmentsDto {

    private String number;
    private LocalDateTime dateTimeAdjustment;
    private CompanyDto company;
    private ContractDto contractor;
    private String currentBalance;
    private String totalBalance;
    private String adjustmentAmount;
    private String comment;
    private LocalDateTime whenChanged;
    private String type;
}
