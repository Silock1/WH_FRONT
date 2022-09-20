package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.RemainsDto;

import java.util.List;

public interface RemainsService {

    List<RemainsDto> getAll();

    RemainsDto getById(Long id);

    void create(RemainsDto dto);

    void update(RemainsDto dto);

    void deleteById(Long id);
}
