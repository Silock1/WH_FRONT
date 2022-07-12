package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.TechnologicalMapDto;

import java.util.List;

public interface TechnologicalMapService {
    List<TechnologicalMapDto> getAll();

    TechnologicalMapDto getById(Long id);

    void create(TechnologicalMapDto dto);

    void update(TechnologicalMapDto dto);

    void deleteById(Long id);
}
