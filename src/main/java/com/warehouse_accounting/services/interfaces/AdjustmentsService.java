package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.AdjustmentsDto;

import java.util.List;

public interface AdjustmentsService {

    List<AdjustmentsDto> getAll();

    AdjustmentsDto getById(Long id);

    void create(AdjustmentsDto adjustmentsDto);

    void update(AdjustmentsDto adjustmentsDto);

    void deleteById(Long id);

}
