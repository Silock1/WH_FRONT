package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.RegionDto;

import java.util.List;

public interface RegionService {

    List<RegionDto> getAll();

    RegionDto getById(Long id);

    RegionDto getByCode(String code);
}
