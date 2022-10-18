package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class FileDto {
    private Long id;
    private String name;
    private double size;
    private LocalDate createdDate;
    private EmployeeDto employeeDto;
}
