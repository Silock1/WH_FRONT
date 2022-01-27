package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.GoodsToRealizeGetDto;

import java.util.List;

public interface GoodsToRealizeGetService {

    List<GoodsToRealizeGetDto> getAll();

    GoodsToRealizeGetDto getById(Long id);

    void create(GoodsToRealizeGetDto dto);

    void update(GoodsToRealizeGetDto dto);

    void deleteById(Long id);
}
