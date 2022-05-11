package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.SettingsDto;
import com.warehouse_accounting.services.interfaces.SettingsService;
import com.warehouse_accounting.services.interfaces.api.SettingsApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class SettingsServiceImpl implements SettingsService {

    private final String URL;

    private final SettingsApi service;

    public SettingsServiceImpl(@Value("${retrofit.restServices.settings_url}") String url, Retrofit retrofit) {
        this.URL = url;
        this.service = retrofit.create(SettingsApi.class);
    }

    @Override
    public List<SettingsDto> getAll() {
        List<SettingsDto> result = new ArrayList<>();
        try {
            Response<List<SettingsDto>> listResponse = service.getAll(URL).execute();
            if (listResponse.isSuccessful()) {
                result = listResponse.body();
                log.info("Успешно выполнен запрос на получение списка Settings");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка Settings", listResponse.code());
            }
        } catch (IOException ex) {
            log.error("Произошла ошибка при выполнении запроса на получение списка Settings", ex);
        }
        return result;
    }

    @Override
    public SettingsDto getById(Long id) {
        SettingsDto result = new SettingsDto();
        try {
            Response<SettingsDto> response = service.getById(URL, id).execute();
            if (response.isSuccessful()) {
                result = response.body();
                log.info("Успешно выполнен запрос на получение Settings");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение Settings по id {}", response.code(), id);
            }
        } catch (IOException ex) {
            log.error("Произошла ошибка при выполнении запроса на получение Settings по id {}", ex, id);
        }
        return result;
    }

    @Override
    public void create(SettingsDto dto) {
        try {
            Response<Void> response = service.create(URL, dto).execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание Settings");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создание Settings", response.code());
            }
        } catch (IOException ex) {
            log.error("Произошла ошибка при выполнении запроса на создание Settings", ex);
        }
    }

    @Override
    public void update(SettingsDto dto) {
        try {
            Response<Void> response = service.update(URL, dto).execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на изменение Settings");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на изменение Settings", response.code());
            }
        } catch (IOException ex) {
            log.error("Произошла ошибка при выполнении запроса на изменение Settings", ex);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            Response<Void> response = service.deleteById(URL, id).execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление Settings");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление Settings с id {}", response.code(), id);
            }
        } catch (IOException ex) {
            log.error("Произошла ошибка при выполнении запроса на удаление SupplierInvoice с id {}", ex, id);
        }
    }
}
