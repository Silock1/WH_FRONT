package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.CityDto;
import com.warehouse_accounting.models.dto.StreetDto;

import java.util.List;

public interface StreetService {

    List<StreetDto> getAll(String regionCityCode);

    List<StreetDto> getSlice(int offset, int limit, String name);

    int getCount(String name);

    StreetDto getById(Long id);
}
