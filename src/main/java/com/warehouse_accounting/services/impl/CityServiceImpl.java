package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.CityDto;
import com.warehouse_accounting.services.interfaces.CityService;
import com.warehouse_accounting.services.interfaces.api.CityApi;
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
public class CityServiceImpl implements CityService {

    private final CityApi api;
    private final String url;

    public CityServiceImpl(@Value("${retrofit.restServices.city_url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(CityApi.class);
    }

    @Override
    public List<CityDto> getAll() {
        List<CityDto> dtoList = Collections.emptyList();
        Call<List<CityDto>> apiAll = api.getAll(url);
        try {
            Response<List<CityDto>> response = apiAll.execute();
            if (response.isSuccessful()) {
                dtoList = response.body();
                log.info("Успешно выполнен запрос на получение списка CityDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка CityDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка CityDto", e);
        }
        return dtoList;
    }

    @Override
    public CityDto getById(Long id) {
        CityDto dto = null;
        Call<CityDto> callSync = api.getById(url, id);
        try {
            Response<CityDto> response = callSync.execute();
            if (response.isSuccessful()) {
                dto = response.body();
                log.info("Успешно выполнен запрос на получение CityDto по id: {}", id);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение CityDto по id {}", response.code(), id);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение CityDto по id", e);
        }
        return dto;
    }

    @Override
    public void create(CityDto dto) {
        Call<Void> call = api.create(url, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание CityDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создании CityDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создании CityDto", e);
        }
    }

    @Override
    public void update(CityDto dto) {
        Call<Void> call = api.update(url, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на изменение CityDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на изменение CityDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на изменение CityDto", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление CityDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление CityDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление CityDto по id", e);
        }
    }
}
