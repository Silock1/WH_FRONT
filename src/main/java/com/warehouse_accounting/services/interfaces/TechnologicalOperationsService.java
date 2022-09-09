package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.TechnologicalOperationDto;

import java.util.List;

public interface TechnologicalOperationsService {
    List <TechnologicalOperationDto> getAll();
    TechnologicalOperationDto getById(Long id);
    void create (TechnologicalOperationDto technologicalOperationDto);
    void update (TechnologicalOperationDto technologicalOperationDto);
    void deleteById(Long id);
}
