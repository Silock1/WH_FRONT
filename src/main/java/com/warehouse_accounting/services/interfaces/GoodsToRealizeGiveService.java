package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.GoodsToRealizeGiveDto;

import java.util.List;

public interface GoodsToRealizeGiveService {

    List<GoodsToRealizeGiveDto> getAll();

    GoodsToRealizeGiveDto getById(Long id);

    void create(GoodsToRealizeGiveDto dto);

    void update(GoodsToRealizeGiveDto dto);

    void deleteById(Long id);
}
