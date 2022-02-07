package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {

    List<EmployeeDto> getAll();

    EmployeeDto getById(Long id);

    void create(EmployeeDto employeeDto);

    void update(EmployeeDto employeeDto);

    void deleteById(Long id);

    EmployeeDto getPrincipal();
}
