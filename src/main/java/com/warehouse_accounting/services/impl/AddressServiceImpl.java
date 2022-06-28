package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.AddressDto;
import com.warehouse_accounting.services.interfaces.AddressService;
import com.warehouse_accounting.services.interfaces.api.AddressApi;
import lombok.extern.log4j.Log4j2;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Log4j2
@Service
public class AddressServiceImpl implements AddressService {

    private final AddressApi api;
    private final String url;

    public AddressServiceImpl(@Value("${retrofit.restServices.address_url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(AddressApi.class);
    }

    @Override
    public List<AddressDto> getAll() {
        List<AddressDto> dtoList = Collections.emptyList();
        Call<List<AddressDto>> apiAll = api.getAll(url);
        try {
            Response<List<AddressDto>> response = apiAll.execute();
            if (response.isSuccessful()) {
                dtoList = response.body();
                log.info("Успешно выполнен запрос на получение списка AddressDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка AddressDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка AddressDto", e);
        }
        return dtoList;
    }

    @Override
    public AddressDto getById(Long id) {
        AddressDto dto = null;
        Call<AddressDto> callSync = api.getById(url, id);
        try {
            Response<AddressDto> response = callSync.execute();
            if (response.isSuccessful()) {
                dto = response.body();
                log.info("Успешно выполнен запрос на получение AddressDto по id: {}", id);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение AddressDto по id {}", response.code(), id);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение AddressDto по id", e);
        }
        return dto;
    }

    @Override
    public void create(AddressDto dto) {
        Call<Void> call = api.create(url, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание AddressDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создании AddressDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создании AddressDto", e);
        }
    }

    @Override
    public void update(AddressDto dto) {
        Call<Void> call = api.update(url, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на изменение AddressDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на изменение AddressDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на изменение AddressDto", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление AddressDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление AddressDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление AddressDto по id", e);
        }
    }
}
