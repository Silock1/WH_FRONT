package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankAccountDto {

    private Long id;

    private String rcbic;

    private String bank;

    private String correspondentAccount;

    private String account;

    private Boolean mainAccount;

    private String sortNumber;

    public String bankAddress;

    private ContractorDto contractor;
}
