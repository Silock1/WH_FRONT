package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.SettingsDto;

import java.util.List;

public interface SettingsService {

    List<SettingsDto> getAll();

    SettingsDto getById(Long id);

    void create(SettingsDto dto);

    void update(SettingsDto dto);

    void deleteById(Long id);
}
