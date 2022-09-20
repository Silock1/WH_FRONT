package com.warehouse_accounting.services.interfaces;


import com.warehouse_accounting.models.dto.TurnsDto;

import java.util.List;

public interface TurnsService {

    List<TurnsDto> getAll();

    TurnsDto getById(Long id);

    void create(TurnsDto dto);

    void update(TurnsDto dto);

    void deleteById(Long id);
}
