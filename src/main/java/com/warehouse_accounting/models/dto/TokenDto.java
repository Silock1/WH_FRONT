package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {
    private Long id;

    private String lastName;

    private String firstName;

    private String middleName;

    private String sortNumber;

    private DepartmentDto department;

    private PositionDto position;

    private Set<RoleDto> roles;

    private ImageDto image;
}