package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankAccountDto {

    private Long id;

    private String rcbic;

    private String bank;


    private String address;
//    public Address address;

    private String correspondentAccount;

    private String account;

    private Boolean mainAccount;

    private String sortNumber;

}
