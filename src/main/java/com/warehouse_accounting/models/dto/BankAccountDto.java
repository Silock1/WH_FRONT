package com.warehouse_accounting.models.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.warehouse_accounting.models.dto.dadataDto.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator= ObjectIdGenerators.UUIDGenerator.class, property="@id")
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
