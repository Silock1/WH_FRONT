package com.warehouse_accounting.security;

import com.warehouse_accounting.models.dto.EmployeeDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserPrincipal implements UserDetails {

    private EmployeeDto employeeDto;

    public UserPrincipal(EmployeeDto employeeDto) {
        this.employeeDto = employeeDto;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return employeeDto.getRoles();
    }

    @Override
    public String getPassword() {
        return employeeDto.getPassword();
    }

    @Override
    public String getUsername() {
        return employeeDto.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public EmployeeDto getEmployeeDto() {
        return this.employeeDto;
    }
}
