package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.ProductionProcessTechnologyDto;
import com.warehouse_accounting.models.dto.ProductionStageDto;

import java.util.List;

public interface ProductionProcessTechnologyService {
    List<ProductionProcessTechnologyDto> getAll();
    void create(ProductionProcessTechnologyDto productionProcessTechnologyDto);
    void update(ProductionProcessTechnologyDto productionProcessTechnologyDto);
    void delete(Long id);

}
