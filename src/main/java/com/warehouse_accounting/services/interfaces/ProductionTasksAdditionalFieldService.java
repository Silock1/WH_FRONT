package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.ProductionTasksAdditionalFieldDto;

import java.util.List;

public interface ProductionTasksAdditionalFieldService {

    List<ProductionTasksAdditionalFieldDto> getAll();

    ProductionTasksAdditionalFieldDto create(ProductionTasksAdditionalFieldDto productionTasksAdditionalFieldDto);

    ProductionTasksAdditionalFieldDto update(ProductionTasksAdditionalFieldDto productionTasksAdditionalFieldDto);

    void delete(Long id);

}
