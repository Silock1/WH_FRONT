package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyDto {

    private Long id;

    private String shortName;

    private String fullName;

    private String digitalCode;

    private String letterCode;

    private String sortNumber;
}


