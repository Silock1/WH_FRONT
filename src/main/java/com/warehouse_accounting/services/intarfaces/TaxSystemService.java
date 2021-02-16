package com.warehouse_accounting.services.intarfaces;

import com.warehouse_accounting.models.dto.TaxSystemDto;

import java.util.List;

public interface TaxSystemService {
    public List<TaxSystemDto> getAll();

    public TaxSystemDto getById(Long id);

    public void update(TaxSystemDto taxSystemDto);

    public void create(TaxSystemDto taxSystemDto);

    public void deleteById(Long id);
}
