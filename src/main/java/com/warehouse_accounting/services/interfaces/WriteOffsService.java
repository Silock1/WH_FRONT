package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.WriteOffsDto;

import java.util.List;

public interface WriteOffsService {

    List<WriteOffsDto> getAll();

    WriteOffsDto getById(Long id);

    void create(WriteOffsDto dto);

    void update(WriteOffsDto dto);

    void deleteById(Long id);
}
