package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.ProductionStageDto;

import java.util.List;

public interface ProductionStageService {
    List<ProductionStageDto> getAll();
    void create(ProductionStageDto productionStageDto);
    void update(ProductionStageDto productionStageDto);
}
