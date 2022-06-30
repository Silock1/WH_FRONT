package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.InventoryDto;

import java.util.List;

public interface InventoryService {

    List<InventoryDto> getAll();

    InventoryDto getById(Long id);

    void create(InventoryDto dto);

    void update(InventoryDto dto);

    void deleteById(Long id);
}
