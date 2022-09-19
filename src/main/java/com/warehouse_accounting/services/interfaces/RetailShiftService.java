package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.RetailShiftDto;

import java.util.List;

public interface RetailShiftService {
    List <RetailShiftDto> getAll();

    RetailShiftDto getById(Long Id);

    void create(RetailShiftDto retailShiftDto);

    void update(RetailShiftDto retailShiftDto);

    void deleteById(Long Id);
}
