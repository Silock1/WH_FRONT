package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.PointOfSalesDto;


import java.util.List;

public interface PointOfSalesService {

    List<PointOfSalesDto> getAll();

    PointOfSalesDto getById(Long id);

    void create(PointOfSalesDto dto);

    void update(PointOfSalesDto dto);

    void deleteById(Long id);
}
