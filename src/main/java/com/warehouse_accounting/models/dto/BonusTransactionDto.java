package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BonusTransactionDto {

    private Long id;

    private LocalDate created;

    private TransactionType transactionType;

    private Long bonusValue;

    private TransactionStatus transactionStatus;

    private LocalDate executionDate;

    private BonusProgramDto bonusProgramDto;

    private LocalDate changedDate = LocalDate.now();

    private EmployeeDto changedEmployee;

    private boolean generalAccess = false;

    private ContractorDto contragent;

    private String comment;

    private EmployeeDto owner;

    public enum TransactionType {
        EARNING, SPENDING
    }

    public enum TransactionStatus {
        WAIT_PROCESSING, COMPLETED, CANCELED
    }

}