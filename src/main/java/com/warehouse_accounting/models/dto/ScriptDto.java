package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScriptDto {

    private Long id;

    private String name;

    private Boolean activity;

    private String comment;

    private Long ownerId;
    private String firstNameOwner;

    private String event;

    private String action;

    private String conditions;
}
