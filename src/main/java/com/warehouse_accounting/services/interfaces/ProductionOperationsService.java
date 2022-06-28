package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.ProductionOperationsDto;

import java.util.List;

public interface ProductionOperationsService {
    List <ProductionOperationsDto> getAll();
    ProductionOperationsDto getById(Long id);
    void create (ProductionOperationsDto productionOperationsDto);
    void update (ProductionOperationsDto productionOperationsDto);
    void deleteById(Long id);
}
