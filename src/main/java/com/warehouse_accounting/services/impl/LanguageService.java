package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.LanguageDto;
import com.warehouse_accounting.services.interfaces.api.LanguageApi;
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
public class LanguageService implements com.warehouse_accounting.services.interfaces.LanguageService {

    private final String URL;

    private final LanguageApi service;

    public LanguageService(@Value("${retrofit.restServices.language_url}") String url, Retrofit retrofit) {
        this.URL = url;
        this.service = retrofit.create(LanguageApi.class);
    }

    @Override
    public List<LanguageDto> getAll() {
        List<LanguageDto> result = new ArrayList<>();
        try {
            Response<List<LanguageDto>> listResponse = service.getAll(URL).execute();
            if (listResponse.isSuccessful()) {
                result = listResponse.body();
                log.info("Успешно выполнен запрос на получение списка Language");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка Language", listResponse.code());
            }
        } catch (IOException ex) {
            log.error("Произошла ошибка при выполнении запроса на получение списка Language", ex);
        }
        return result;
    }

    @Override
    public LanguageDto getById(Long id) {
        LanguageDto result = new LanguageDto();
        try {
            Response<LanguageDto> response = service.getById(URL, id).execute();
            if (response.isSuccessful()) {
                result = response.body();
                log.info("Успешно выполнен запрос на получение Language");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение Language по id {}", response.code(), id);
            }
        } catch (IOException ex) {
            log.error("Произошла ошибка при выполнении запроса на получение Language по id {}", ex, id);
        }
        return result;
    }

    @Override
    public void create(LanguageDto dto) {
        try {
            Response<Void> response = service.create(URL, dto).execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание Language");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создание Language", response.code());
            }
        } catch (IOException ex) {
            log.error("Произошла ошибка при выполнении запроса на создание Language", ex);
        }
    }

    @Override
    public void update(LanguageDto dto) {
        try {
            Response<Void> response = service.update(URL, dto).execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на изменение Language");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на изменение Language", response.code());
            }
        } catch (IOException ex) {
            log.error("Произошла ошибка при выполнении запроса на изменение Language", ex);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            Response<Void> response = service.deleteById(URL, id).execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление Language");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление Language с id {}", response.code(), id);
            }
        } catch (IOException ex) {
            log.error("Произошла ошибка при выполнении запроса на удаление Language с id {}", ex, id);
        }
    }
}
