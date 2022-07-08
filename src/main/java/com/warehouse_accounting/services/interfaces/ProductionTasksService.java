package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.ProductionTasksDto;

import java.util.List;

public interface ProductionTasksService {
    List<ProductionTasksDto> getAll();
    void create(ProductionTasksDto productionTasksDto);
    void update(ProductionTasksDto productionTasksDto);
    void delete(Long id);

}
