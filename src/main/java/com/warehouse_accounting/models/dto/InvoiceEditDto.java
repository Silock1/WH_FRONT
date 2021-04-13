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
public class InvoiceEditDto {
    private Long id;
    private EmployeeDto editAuthorDto = new EmployeeDto();
    private LocalDateTime dateTime;
    private String field;
    private String before;
    private String after;


}
