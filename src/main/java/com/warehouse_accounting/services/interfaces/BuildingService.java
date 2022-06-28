package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.BuildingDto;

import java.util.List;

public interface BuildingService {

    List<BuildingDto> getAll(String streetCode);

    BuildingDto getById(Long id);
}
