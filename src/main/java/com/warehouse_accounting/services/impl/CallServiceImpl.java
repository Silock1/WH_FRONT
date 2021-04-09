package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.CallDto;
import com.warehouse_accounting.services.interfaces.CallService;
import com.warehouse_accounting.services.interfaces.api.CallApi;
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
public class CallServiceImpl implements CallService {

    private final CallApi callApi;
    private final String callUrl;

    public CallServiceImpl(@Value("${retrofit.restServices.call_url}") String callUrl, Retrofit retrofit) {
        this.callUrl = callUrl;
        this.callApi = retrofit.create(CallApi.class);
    }

    @Override
    public List<CallDto> getAll() {
        List<CallDto> callDtos = Collections.emptyList();
        Call<List<CallDto>> callApiAll = callApi.getAll(callUrl);
        try {
            Response<List<CallDto>> response = callApiAll.execute();
            if (response.isSuccessful()) {
                callDtos = response.body();
                log.info("Успешно выполнен запрос на получение списка CallDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка CallDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка CallDto", e);
        }
        return callDtos;
    }

    @Override
    public CallDto getById(Long id) {
        CallDto callDto = null;
        Call<CallDto> callSync = callApi.getById(callUrl, id);
        try {
            Response<CallDto> response = callSync.execute();
            if (response.isSuccessful()) {
                callDto = response.body();
                log.info("Успешно выполнен запрос на получение CallDto по id: {}", id);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение CallDto по id {}", response.code(), id);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение CallDto по id", e);
        }
        return callDto;
    }

    @Override
    public void create(CallDto dto) {
        Call<Void> call = callApi.create(callUrl, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание CallDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создании CallDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создании CallDto", e);
        }
    }

    @Override
    public void update(CallDto dto) {
        Call<Void> call = callApi.update(callUrl, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на изменение CallDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на изменение CallDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на изменение CallDto", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = callApi.deleteById(callUrl, id);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление CallDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление CallDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление CallDto по id", e);
        }
    }
}
