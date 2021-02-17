package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.CurrencyDto;

import java.util.List;

public interface CurrencyService {

    List<CurrencyDto> getAll();

    CurrencyDto getById(Long id);

    void create(CurrencyDto dto);

    void update(CurrencyDto dto);

    void deleteById(Long id);
}
