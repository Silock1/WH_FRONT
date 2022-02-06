package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SalesChannelDto {
    private Long id;
    private String name;
    private String type;
    private String description;
    private String generalAccessC;
    private String ownerDepartment;
    private String ownerEmployee;
    private String whenChanged;
    private String whoChanged;

}
