package com.warehouse_accounting.services.interfaces;


import com.warehouse_accounting.models.dto.SerialNumbersDto;

import java.util.List;

public interface SerialNumbersService {

    List<SerialNumbersDto> getAll();

    SerialNumbersDto getById(Long id);

    void create(SerialNumbersDto dto);

    void update(SerialNumbersDto dto);

    void deleteById(Long id);
}
