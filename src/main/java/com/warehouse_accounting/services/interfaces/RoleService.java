package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.RoleDto;

import java.util.List;

public interface RoleService {

    List<RoleDto> getAll();

    RoleDto getById(Long id);

    void create(RoleDto dto);

    void update(RoleDto dto);

    void deleteById(Long id);
}
