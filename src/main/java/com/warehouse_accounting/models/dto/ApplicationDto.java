package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationDto {
    private Long id;

    private String name;

    private String description;

    private String developer;

    private String devSite;

    private String devMail;

    private Boolean isFree;

    private Long logoId;
}
