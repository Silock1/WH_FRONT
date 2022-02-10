package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.ConnectorSettingsDto;

import java.io.IOException;
import java.util.List;

public interface ConnectorSettingsService {
    List<ConnectorSettingsDto> getAll() throws IOException;

    ConnectorSettingsDto getById(long id) throws IOException;

    void create(ConnectorSettingsDto companyDto) throws IOException;

    void update(ConnectorSettingsDto companyDto) throws IOException;

    void deleteById(Long id) throws IOException;
}

