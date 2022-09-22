package com.warehouse_accounting.services.interfaces;


import com.warehouse_accounting.models.dto.IncomingVoicesDto;
import com.warehouse_accounting.models.dto.SerialNumbersDto;

import java.util.List;

public interface IncomingVoicesService {

    List<IncomingVoicesDto> getAll();

    IncomingVoicesDto getById(Long id);

    void create(IncomingVoicesDto dto);

    void update(IncomingVoicesDto dto);

    void deleteById(Long id);
}
