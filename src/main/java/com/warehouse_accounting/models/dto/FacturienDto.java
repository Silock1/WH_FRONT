package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacturienDto {

    private Long id;

    private String contrAgent;

    private String organization;

    private String invoiceNumber;

    private String incomingNumber;

    private String incomingDate;

    private LocalDateTime incomingTime;

    private String dateInvoiceNumber;

    private String addComment;

    private BigDecimal count = BigDecimal.valueOf(0);

    private BigDecimal price = BigDecimal.valueOf(0);

    private BigDecimal sum = count.multiply(price);

    private Boolean sent;

    private Boolean printed;

}
