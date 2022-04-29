package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.PriceListDto;

import java.util.List;

public interface PriceListService {

    List<PriceListDto> getAll();

    PriceListDto getById(Long id);

    void create(PriceListDto dto);

    void update(PriceListDto dto);

    void deleteById(Long id);

}
