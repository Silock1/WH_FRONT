package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.InvoicesReceivedDto;

import java.util.List;

public interface InvoicesReceivedService {
    List<InvoicesReceivedDto> getAll();

    InvoicesReceivedDto getById(Long id);

    void create(InvoicesReceivedDto dto);

    void update(InvoicesReceivedDto dto);

    void deleteById(Long id);
}
