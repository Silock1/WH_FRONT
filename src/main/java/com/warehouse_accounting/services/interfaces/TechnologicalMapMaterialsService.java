package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.TechnologicalMapMaterialDto;

import java.util.List;

public interface TechnologicalMapMaterialsService {

    List<TechnologicalMapMaterialDto> getAll();

    TechnologicalMapMaterialDto getById(Long id);

    void create(TechnologicalMapMaterialDto dto);

    void update(TechnologicalMapMaterialDto dto);

    void deleteById(Long id);
}
