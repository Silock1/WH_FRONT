package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.CustomerOrderDto;

import java.util.List;

public interface CustomerOrderService {

    List<CustomerOrderDto> getAll();

    CustomerOrderDto getById(Long id);

    void create(CustomerOrderDto dto);

    void update(CustomerOrderDto dto);

    void deleteById(Long id);
}
