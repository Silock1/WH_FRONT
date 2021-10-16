package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.AcceptancesDto;

import java.util.List;

public interface AcceptanceService {
    List<AcceptancesDto> getAll();

    AcceptancesDto getById(Long id);

    void create(AcceptancesDto invoiceDto);

    void update(AcceptancesDto invoiceDto);

    void deleteById(Long id);
}
