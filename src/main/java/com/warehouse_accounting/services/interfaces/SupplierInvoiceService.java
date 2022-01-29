package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.SupplierInvoiceDto;

import java.util.List;

public interface SupplierInvoiceService {

    List<SupplierInvoiceDto> getAll();

    SupplierInvoiceDto getById(Long id);

    void create(SupplierInvoiceDto dto);

    void update(SupplierInvoiceDto dto);

    void deleteById(Long id);
}
