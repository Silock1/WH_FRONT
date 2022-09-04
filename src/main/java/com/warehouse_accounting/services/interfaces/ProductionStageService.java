package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.ProductionStageDto;

import java.util.List;

public interface ProductionStageService {
    List<ProductionStageDto> getAll();

    ProductionStageDto getById(Long id);

    void create(ProductionStageDto productionStageDto);

    void update(ProductionStageDto productionStageDto);

    void delete(Long id);
}
