package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.TechnologicalMapGroupDto;

import java.util.List;

public interface TechnologicalMapGroupService {

    List<TechnologicalMapGroupDto> getAll();

    TechnologicalMapGroupDto getById(Long id);

    void create(TechnologicalMapGroupDto dto);

    void update(TechnologicalMapGroupDto dto);

    void deleteById(Long id);
}
