package com.warehouse_accounting.services.interfaces;
import com.warehouse_accounting.models.dto.CountryDto;

import java.util.List;

public interface CountryService {

    List<CountryDto> getAll();

    CountryDto getById(Long id);

    void create(CountryDto countryDto);

    void update(CountryDto countryDto);

    void deleteById(Long id);
}

