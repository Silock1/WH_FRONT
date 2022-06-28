package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.InternalOrderDto;

import java.util.List;

public interface InternalOrderService {

    List<InternalOrderDto> getAll();

    InternalOrderDto getById(Long id);

    void create(InternalOrderDto internalOrderDto);

    void update(InternalOrderDto internalOrderDto);

    void deleteById(Long id);
}
