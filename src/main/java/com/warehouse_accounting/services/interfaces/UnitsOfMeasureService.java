package com.warehouse_accounting.services.interfaces;


import com.warehouse_accounting.models.dto.UnitsOfMeasureDto;

import java.util.List;

public interface UnitsOfMeasureService {

    List<UnitsOfMeasureDto> getAll();

    UnitsOfMeasureDto getById(Long id);

    void create(UnitsOfMeasureDto dto);

    void update(UnitsOfMeasureDto dto);

    void deleteById(Long id);
}
