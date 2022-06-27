package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.StreetDto;

import java.util.List;

public interface StreetService {

    List<StreetDto> getAll();

    StreetDto getById(Long id);

    void create(StreetDto streetDto);

    void update(StreetDto streetDto);

    void deleteById(Long id);
}
