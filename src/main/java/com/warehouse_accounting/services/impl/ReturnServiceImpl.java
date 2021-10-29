package com.warehouse_accounting.services.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import com.warehouse_accounting.models.dto.ReturnDto;
import com.warehouse_accounting.services.interfaces.ReturnService;
import com.warehouse_accounting.services.interfaces.api.ReturnApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

@Log4j2
@Service
public class ReturnServiceImpl implements ReturnService {
    private final ReturnApi returnApi;
    private final String returnUrl;

    public ReturnServiceImpl(@Value("${retrofit.restServices.return_url}") String returnUrl, Retrofit retrofit) {
        this.returnUrl = returnUrl;
        this.returnApi = retrofit.create(ReturnApi.class);
    }

    @Override
    public List<ReturnDto> getAll() {
        List<ReturnDto> returnDtoList = Collections.emptyList();
        Call<List<ReturnDto>> invoiceApiAll = returnApi.getAll(returnUrl);
        try {
            Response<List<ReturnDto>> response = invoiceApiAll.execute();
            if (response.isSuccessful()) {
                returnDtoList = response.body();
                log.info("Успешно выполнен запрос на получение списка ReturnDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка ReturnDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка ReturnDto", e);
        }
        return returnDtoList;
    }

    @Override
    public ReturnDto getById(Long id) {
        ReturnDto returnDto = null;
        Call<ReturnDto> callSync = returnApi.getById(returnUrl, id);
        try {
            Response<ReturnDto> response = callSync.execute();
            if (response.isSuccessful()) {
                returnDto = response.body();
                log.info("Успешно выполнен запрос на получение ReturnDto по id: {}", id);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение ReturnDto по id {}", response.code(), id);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение ReturnDto по id", e);
        }
        return returnDto;
    }

    @Override
    public void create(ReturnDto acceptancesDto) {
        Call<Void> call = returnApi.create(returnUrl, acceptancesDto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание ReturnDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создании ReturnDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создании ReturnDto", e);
        }
    }

    @Override
    public void update(ReturnDto acceptancesDto) {
        Call<Void> call = returnApi.update(returnUrl, acceptancesDto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на изменение ReturnDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на изменение ReturnDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на изменение ReturnDto", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = returnApi.deleteById(returnUrl, id);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление ReturnDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление ReturnDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление ReturnDto по id", e);
        }
    }
}

