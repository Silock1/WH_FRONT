package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.ReturnDto;

import java.util.List;

public interface ReturnService {
    List<ReturnDto> getAll();

    ReturnDto getById(Long id);

    void create(ReturnDto returnDto);

    void update(ReturnDto returnDto);

    void deleteById(Long id);
}