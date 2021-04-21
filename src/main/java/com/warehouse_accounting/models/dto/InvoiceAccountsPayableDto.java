package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/*
This entity represents the invoice for the goods or services that company received but not paid yet, in other words
goods and services bought on credit, which will be paid on some particular date in the future
*/

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceAccountsPayableDto {

    private Long id;

    private InvoiceDto invoiceDto;

    private ProductDto productDto;

    private BigDecimal count = BigDecimal.valueOf(0);

    private BigDecimal price = BigDecimal.valueOf(0);

    private BigDecimal sum = count.multiply(price);
//the scheduled date for the payment
    private LocalDateTime dueDate;
}
