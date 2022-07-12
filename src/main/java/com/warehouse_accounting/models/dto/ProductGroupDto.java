package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductGroupDto { // Возможно здесь нужно поле TaxSystemDto

    private Long id;

    private String name;

    private String sortNumber;

    private ProductGroupDto productGroupDto;
}
