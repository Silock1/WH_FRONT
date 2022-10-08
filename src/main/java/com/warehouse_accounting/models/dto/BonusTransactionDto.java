package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BonusTransactionDto {

    private Long id;

    private LocalDateTime created;

    private TransactionType transactionType;

    private Long bonusValue;

    private TransactionStatus transactionStatus;

    private LocalDateTime executionDate;

    private BonusProgramDto bonusProgramDto;

    private ContractorDto contragent = new ContractorDto();

    private String comment;

    private EmployeeDto owner = new EmployeeDto();

    public enum TransactionType {
        EARNING, SPENDING
    }

    public enum TransactionStatus {
        WAIT_PROCESSING, COMPLETED, CANCELED
    }


}