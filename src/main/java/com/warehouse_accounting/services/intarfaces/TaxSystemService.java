package com.warehouse_accounting.services.intarfaces;

import com.warehouse_accounting.models.dto.TaxSystemDto;

import java.util.List;

public interface TaxSystemService {
    List<TaxSystemDto> getAll();

    TaxSystemDto getById(Long id);

    void update(TaxSystemDto taxSystemDto);

    void create(TaxSystemDto taxSystemDto);

    void deleteById(Long id);
}
