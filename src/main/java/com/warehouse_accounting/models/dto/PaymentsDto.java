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
public class PaymentsDto {
    private Long id;
    private String number;
    private LocalDateTime date;
    private String amount;
    private String purpose;
    private String tax;
    private String isDone;
    private String comment;
    private String typeOfPayment;
    private CompanyDto company;
    private ContractDto contract;
    private ProjectDto project;
    private ContractorDto contractor;
    private String paymentExpenditure;
}
