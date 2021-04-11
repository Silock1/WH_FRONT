package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.InvoiceEditDto;

import java.util.List;

public interface InvoiceEditService {

    List<InvoiceEditDto> getAll();

    InvoiceEditDto getById(Long id);

    void create(InvoiceEditDto invoiceEditDto);

    void update(InvoiceEditDto invoiceEditDto);

    void deleteById(Long id);
}

