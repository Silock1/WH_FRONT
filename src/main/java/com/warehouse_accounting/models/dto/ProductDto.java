package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Long id;

    private String name;

    private BigDecimal weight;

    private BigDecimal volume;

    private BigDecimal purchasePrice;

    private String description;

    private UnitDto unit;

    private Boolean archive = false;

    private ContractorDto contractor;

    private TaxSystemDto taxSystem;

    private List<ImageDto> images;

    private ProductGroupDto productGroup;

    private AttributeOfCalculationObjectDto attributeOfCalculationObject;
}
