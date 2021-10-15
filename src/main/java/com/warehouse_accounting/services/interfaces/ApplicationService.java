package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.ApplicationDto;

import java.util.List;

public interface ApplicationService {

    List<ApplicationDto> getAll();

    ApplicationDto getById(Long id);

    void create(ApplicationDto dto);

    void update(ApplicationDto dto);

    void deleteById(Long id);
}
