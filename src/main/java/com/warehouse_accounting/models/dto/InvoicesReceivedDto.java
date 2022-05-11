package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoicesReceivedDto {

    private Long id;

    private LocalDateTime data;

    private ContractorDto contractor = new ContractorDto();

    private CompanyDto company = new CompanyDto();

    private BigDecimal sum = BigDecimal.valueOf(0);

    private BigDecimal incomingNumber = BigDecimal.valueOf(0);

    private LocalDateTime dateIncomingNumber;

    private Boolean sent;

    private Boolean printed;

    private String comment;

}
