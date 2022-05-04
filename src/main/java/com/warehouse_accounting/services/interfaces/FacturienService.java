package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.FacturienDto;

import java.util.List;

public interface FacturienService {
    List<FacturienDto> getAll();

    FacturienDto getById(Long id);

    void create(FacturienDto dto);

    void update(FacturienDto dto);

    void deleteById(Long id);
}
