package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.AddressDto;

import java.util.List;

public interface AddressService {

    List<AddressDto> getAll();

    AddressDto getById(Long id);

    void create(AddressDto addressDto);

    void update(AddressDto addressDto);

    void deleteById(Long id);

    AddressDto getByFullAddress(String fullAddress);
}
