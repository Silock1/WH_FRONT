package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.CustomerOrderDto;
import com.warehouse_accounting.models.dto.InvoiceDto;

import java.util.List;

public interface InvoiceService {

    List<InvoiceDto> getAll();

    InvoiceDto getById(Long id);

    void create(CustomerOrderDto invoiceDto);

    void update(CustomerOrderDto invoiceDto);

    void deleteById(Long id);
}

