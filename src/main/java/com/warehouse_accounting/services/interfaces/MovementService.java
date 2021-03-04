package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.MovementDto;

import java.util.List;

public interface MovementService {

    List<MovementDto> getAll();

    MovementDto getById(Long id);

    void create(MovementDto dto);

    void update(MovementDto dto);

    void deleteById(Long id);
}
