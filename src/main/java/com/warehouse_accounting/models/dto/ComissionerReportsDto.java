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
public class ComissionerReportsDto {

    private Long id;
    private String docType;
    private Long number;
    private LocalDateTime time;
    private String organization;
    private Long organizationAccount;
    private String contractor;
    private Long contractorAccount;
    private BigDecimal sum;
    private String project;
    private String contract;
    private BigDecimal sumOfReward;
    private BigDecimal sumOfCommittent;
    private BigDecimal paid;
    private BigDecimal remainsToPay;
    private String startOfPeriod;
    private String endOfPeriod;
    private Long incomingNumber;
    private String incomingDate;
    private String salesChannel;
    private String generalAccess;
    private String ownerDepartment;
    private String ownerEmployee;
    private String status;
    private String sent;
    private String printed;
    private String commentary;
    private String whenChanged;
    private String whoChanged;
}


















