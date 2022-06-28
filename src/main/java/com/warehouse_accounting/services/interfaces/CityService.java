package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.CityDto;

import java.util.List;

public interface CityService {

    List<CityDto> getAll(String regionCode);

    List<CityDto> getSlice(int offset, int limit, String name);

    int getCount(String name);

    CityDto getById(Long id);

    CityDto getByCode(String code);
}
