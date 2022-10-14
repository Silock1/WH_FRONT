package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.IpNetworkDto;

import java.util.List;

public interface IpNetworkService {
    List<IpNetworkDto> getAll();

    IpNetworkDto getById(Long id);

    void create(IpNetworkDto dto);

    void update(IpNetworkDto dto);

    void deleteById(Long id);
}
