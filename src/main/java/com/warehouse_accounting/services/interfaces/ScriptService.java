package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.ScriptDto;

import java.util.List;

public interface ScriptService {

    List<ScriptDto> getAll();

    ScriptDto getById(Long id);

    void create(ScriptDto dto);

    void update(ScriptDto dto);

    void deleteById(Long id);
}
