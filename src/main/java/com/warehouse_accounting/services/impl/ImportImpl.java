package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.ConnectorSettingsDto;
import com.warehouse_accounting.services.interfaces.ImportService;

import java.io.IOException;
import java.util.List;

public class ImportImpl implements ImportService {
    @Override
    public List<ConnectorSettingsDto> getAll() throws IOException {
        return null;
    }

    @Override
    public ConnectorSettingsDto getById(long id) throws IOException {
        return null;
    }

    @Override
    public void create(ConnectorSettingsDto companyDto) throws IOException {

    }

    @Override
    public void update(ConnectorSettingsDto companyDto) throws IOException {

    }

    @Override
    public void deleteById(Long id) throws IOException {

    }
}


