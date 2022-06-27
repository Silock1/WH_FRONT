package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.CityDto;

import java.util.List;

public interface CityService {

    List<CityDto> getAll();

    CityDto getById(Long id);

    void create(CityDto cityDto);

    void update(CityDto cityDto);

    void deleteById(Long id);
}
