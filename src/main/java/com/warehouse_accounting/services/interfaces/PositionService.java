package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.PositionDto;

import java.util.List;

public interface PositionService {

    List<PositionDto> getAll();

    PositionDto getById(Long id);

    void create(PositionDto dto);

    void update(PositionDto dto);

    void deleteById(Long id);
}
