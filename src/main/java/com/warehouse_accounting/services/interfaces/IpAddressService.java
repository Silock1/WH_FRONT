package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.IpAddressDto;

import java.util.List;

public interface IpAddressService {

    List<IpAddressDto> getAll();

    IpAddressDto getById(Long id);

    void create(IpAddressDto dto);

    void update(IpAddressDto dto);

    void deleteById(Long id);
}
