package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.BuildingDto;
import com.warehouse_accounting.models.dto.CityDto;

import java.util.List;

public interface BuildingService {

    List<BuildingDto> getAll(String streetCode);

    List<BuildingDto> getSlice(int offset, int limit, String name, String regionCityStreetCode);

    int getCount(String name, String regionCityStreetCode);

    BuildingDto getById(Long id);
}
