package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductionTasksAdditionalFieldDto {
    private Long id;
    private String name;
    private Boolean required;
    private Boolean hide;
    private String description;
    private Map<String, Object> property;
}
