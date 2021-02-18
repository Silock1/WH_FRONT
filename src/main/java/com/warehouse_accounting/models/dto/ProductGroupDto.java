package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductGroupDto {

    private Long id;

    private String name;

    private String sortNumber;

    private ProductGroupDto productGroupDto = new ProductGroupDto();

    public ProductGroupDto(Long id, String name, String sortNumber, Long productGroupDtoId) {
        this.id = id;
        this.name = name;
        this.sortNumber = sortNumber;
        this.productGroupDto.setId(productGroupDtoId);
    }
}
