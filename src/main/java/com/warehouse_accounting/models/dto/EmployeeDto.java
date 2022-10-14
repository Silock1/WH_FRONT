package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
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

    @NotNull
    private Set<TariffDto> tariff;

    private IpNetworkDto ipNetwork = new IpNetworkDto();

    private Set<IpAddressDto> ipAddress = new HashSet<>();

    @Override
    public String toString() {
        return "EmployeeDto{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", sortNumber='" + sortNumber + '\'' +
                ", phone='" + phone + '\'' +
                ", inn='" + inn + '\'' +
                ", description='" + description + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", department=" + department +
                ", position=" + position +
                ", roles=" + roles +
                ", image=" + image +
                ", tariff=" + tariff +
                ", ipNetwork=" + ipNetwork +
                ", ipAddress=" + ipAddress +
                '}';
    }
}
