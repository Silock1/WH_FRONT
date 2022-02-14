package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.ComissionerReportsDto;

import java.util.List;

public interface ComissionerReportsService {

    List<ComissionerReportsDto> getAll();

    ComissionerReportsDto getById(Long id);

    void create(ComissionerReportsDto dto);

    void update(ComissionerReportsDto dto);

    void deleteById(Long id);
}
