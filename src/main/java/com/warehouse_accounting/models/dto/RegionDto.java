package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegionDto {
    private Long id;

    private String name;

    private String socr;

    private String code;

    public String getNameSocr() {
        if (socr == null || socr.isEmpty()) {
            return name;
        } else {
            return new StringBuilder()
                    .append(name)
                    .append(" ")
                    .append(socr)
                    .toString();
        }
    }
}
