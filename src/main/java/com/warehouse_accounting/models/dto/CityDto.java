package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CityDto {
    private Long id;

    private String name;

    private String socr;

    private String code;

    private String index;

    private String gninmb;

    private String uno;

    private String ocatd;

    private String status;
}
