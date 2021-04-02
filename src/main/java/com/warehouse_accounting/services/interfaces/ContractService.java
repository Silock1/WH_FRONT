package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.ContractDto;

import java.util.List;

public interface ContractService {

    List<ContractDto> getAll();

    ContractDto getById(Long id);

    void create(ContractDto dto);

    void update(ContractDto dto);

    void deleteById(Long id);
}
