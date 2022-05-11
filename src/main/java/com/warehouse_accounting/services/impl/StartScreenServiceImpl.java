package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.StartScreenDto;
import com.warehouse_accounting.services.interfaces.StartScreenService;
import com.warehouse_accounting.services.interfaces.api.StartScreenApi;
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
public class StartScreenServiceImpl implements StartScreenService {

    private final String URL;

    private final StartScreenApi service;

    public StartScreenServiceImpl(@Value("${retrofit.restServices.start_screen_url}") String url, Retrofit retrofit) {
        this.URL = url;
        this.service = retrofit.create(StartScreenApi.class);
    }

    @Override
    public List<StartScreenDto> getAll() {
        List<StartScreenDto> result = new ArrayList<>();
        try {
            Response<List<StartScreenDto>> listResponse = service.getAll(URL).execute();
            if (listResponse.isSuccessful()) {
                result = listResponse.body();
                log.info("Успешно выполнен запрос на получение списка StartScreen");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка StartScreen", listResponse.code());
            }
        } catch (IOException ex) {
            log.error("Произошла ошибка при выполнении запроса на получение списка StartScreen", ex);
        }
        return result;
    }

    @Override
    public StartScreenDto getById(Long id) {
        StartScreenDto result = new StartScreenDto();
        try {
            Response<StartScreenDto> response = service.getById(URL, id).execute();
            if (response.isSuccessful()) {
                result = response.body();
                log.info("Успешно выполнен запрос на получение StartScreen");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение StartScreen по id {}", response.code(), id);
            }
        } catch (IOException ex) {
            log.error("Произошла ошибка при выполнении запроса на получение StartScreen по id {}", ex, id);
        }
        return result;
    }

    @Override
    public void create(StartScreenDto dto) {
        try {
            Response<Void> response = service.create(URL, dto).execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание StartScreen");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создание StartScreen", response.code());
            }
        } catch (IOException ex) {
            log.error("Произошла ошибка при выполнении запроса на создание StartScreen", ex);
        }
    }

    @Override
    public void update(StartScreenDto dto) {
        try {
            Response<Void> response = service.update(URL, dto).execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на изменение StartScreen");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на изменение StartScreen", response.code());
            }
        } catch (IOException ex) {
            log.error("Произошла ошибка при выполнении запроса на изменение StartScreen", ex);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            Response<Void> response = service.deleteById(URL, id).execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление StartScreen");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление StartScreen с id {}", response.code(), id);
            }
        } catch (IOException ex) {
            log.error("Произошла ошибка при выполнении запроса на удаление StartScreen с id {}", ex, id);
        }
    }
}
