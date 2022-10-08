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