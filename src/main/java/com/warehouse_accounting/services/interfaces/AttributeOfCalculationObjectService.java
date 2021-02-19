package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.AttributeOfCalculationObjectDto;

import java.util.List;

public interface AttributeOfCalculationObjectService {

    List<AttributeOfCalculationObjectDto> getAll();

    AttributeOfCalculationObjectDto getById(Long id);

    void create(AttributeOfCalculationObjectDto attributeOfCalculationObjectDto);

    void update(AttributeOfCalculationObjectDto attributeOfCalculationObjectDto);

    void deleteById(Long id);
}
