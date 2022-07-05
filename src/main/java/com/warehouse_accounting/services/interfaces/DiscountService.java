package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.DiscountDto;

import java.util.List;

public interface DiscountService {
    List<DiscountDto> getAll();

    DiscountDto getById(Long id);

    void create(DiscountDto discountDto);

    void update(DiscountDto discountDto);

    void deleteById(Long id);
}
