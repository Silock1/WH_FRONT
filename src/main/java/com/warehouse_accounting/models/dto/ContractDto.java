package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContractDto {

    private Long id;

    private String number;

    private LocalDate contractDate;

    private CompanyDto companyDto = new CompanyDto();

    private BankAccountDto bankAccountDto = new BankAccountDto();

    private ContractorDto contractorDto = new ContractorDto();

    private BigDecimal amount = BigDecimal.valueOf(0);

    private Boolean archive = false;

    private String comment;

    private LegalDetailDto legalDetailDto = new LegalDetailDto();

    public ContractDto(
            Long id,
            String number,
            LocalDate contractDate,
            BigDecimal amount,
            Boolean archive,
            String comment,
            Long companyDtoId,
            Long bankAccountDtoId,
            Long contractorDtoId,
            Long legalDetailDtoId
            ) {
        this.id = id;
        this.number = number;
        this.contractDate = contractDate;
        this.amount = amount;
        this.archive = archive;
        this.comment = comment;
        this.companyDto.setId(companyDtoId);
        this.bankAccountDto.setId(bankAccountDtoId);
        this.contractorDto.setId(contractorDtoId);
        this.legalDetailDto.setId(legalDetailDtoId);
    }
}
