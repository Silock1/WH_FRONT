package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SelectorForViewDto {

    private Long id;

    private boolean activate;

    private String description;

    private boolean post;

    private boolean phone;

    private String label;
}
