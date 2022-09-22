package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.StatusDto;

import java.util.List;

public interface StatusService {

    List<StatusDto> getAllByNameOfClass(String nameOfClass);

    List<StatusDto> getAll();

    StatusDto getById(Long id);

    void create(StatusDto dto);

    void update(StatusDto dto);

    void deleteById(Long id);
}
