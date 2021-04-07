package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CountryDto {

    private Long id;

    private String shortName;

    private String longName;

    private BigInteger code;

    private String codeOne;

    private String codeTwo;
}
