package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequisitesDto {

    private Long id;

    private String organization;

    private AddressDto legalAddress;

    private Integer INN;

    private Integer KPP;

    private Integer BIK;

    private Integer checkingAccount;
}
