package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.ShipmentsDto;
import com.warehouse_accounting.models.dto.SubscriptionDto;

import java.util.List;

public interface ShipmentsService {

    List<ShipmentsDto> getAll();

    ShipmentsDto getById(Long id);

    void create(ShipmentsDto shipmentsDto);

    void update(ShipmentsDto shipmentsDto);

    void deleteById(Long id);
}
