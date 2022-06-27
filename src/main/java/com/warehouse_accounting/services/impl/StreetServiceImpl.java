package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.StreetDto;
import com.warehouse_accounting.services.interfaces.StreetService;
import com.warehouse_accounting.services.interfaces.api.StreetApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Log4j2
@Service
public class StreetServiceImpl implements StreetService {

    private final StreetApi api;
    private final String url;

    public StreetServiceImpl(@Value("${retrofit.restServices.street_url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(StreetApi.class);
    }

    @Override
    public List<StreetDto> getAll() {
        List<StreetDto> dtoList = Collections.emptyList();
        Call<List<StreetDto>> apiAll = api.getAll(url);
        try {
            Response<List<StreetDto>> response = apiAll.execute();
            if (response.isSuccessful()) {
                dtoList = response.body();
                log.info("Успешно выполнен запрос на получение списка StreetDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка StreetDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка StreetDto", e);
        }
        return dtoList;
    }

    @Override
    public StreetDto getById(Long id) {
        StreetDto dto = null;
        Call<StreetDto> callSync = api.getById(url, id);
        try {
            Response<StreetDto> response = callSync.execute();
            if (response.isSuccessful()) {
                dto = response.body();
                log.info("Успешно выполнен запрос на получение StreetDto по id: {}", id);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение StreetDto по id {}", response.code(), id);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение StreetDto по id", e);
        }
        return dto;
    }

    @Override
    public void create(StreetDto dto) {
        Call<Void> call = api.create(url, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание StreetDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создании StreetDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создании StreetDto", e);
        }
    }

    @Override
    public void update(StreetDto dto) {
        Call<Void> call = api.update(url, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на изменение StreetDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на изменение StreetDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на изменение StreetDto", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление StreetDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление StreetDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление StreetDto по id", e);
        }
    }
}
