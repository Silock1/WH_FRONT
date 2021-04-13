package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceProductDto {

    private Long id;

    private InvoiceDto invoiceDto = new InvoiceDto();

    private ProductDto productDto = new ProductDto();

    private BigDecimal count = BigDecimal.valueOf(0);

    private BigDecimal price = BigDecimal.valueOf(0);

    private BigDecimal sum = count.multiply(price);
}
