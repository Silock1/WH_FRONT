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
public class EmployeeDto {
    private Long id;

    private String lastName;

    private String firstName;

    private String middleName;

    private String sortNumber;

    private String phone;

    private String inn;

    private String description;

    private String email;

    private String password;

    private DepartmentDto department;

    private PositionDto position;

    private Set<RoleDto> roles;

    private ImageDto image;

    private Set<TariffDto> tariff;

}
