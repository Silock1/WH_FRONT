package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConnectorSettingsDto {

    private Long id;

    private String shops;

    private String type;

    private String orders;

    private String leftovers;

}
