package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SupplierInvoiceDto {
    private Long id;

    private String  invoiceNumber;

    private String dateInvoiceNumber;

    private Boolean checkboxProd;

    private String organization;

    private String warehouse;

    private String contrAgent;

    private String contract;

    private String datePay;

    private String project;

    private String incomingNumber;

    private String dateIncomingNumber;

    private Boolean checkboxName;

    private Boolean checkboxNDS;

    private Boolean checkboxOnNDS;

    private String addPosition;

    private String addComment;
}
