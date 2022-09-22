package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.PointOfSalesDto;
import com.warehouse_accounting.models.dto.RetailShiftDto;
import com.warehouse_accounting.services.interfaces.RetailShiftService;
import com.warehouse_accounting.services.interfaces.api.RetailShiftApi;
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
public class RetailShiftServiceImpl implements RetailShiftService {

    private final RetailShiftApi retailShiftApi;

    private final String retailUrl;

    public RetailShiftServiceImpl(@Value("/api/retail_shift") String retailUrl, Retrofit retrofit) {
        this.retailShiftApi = retrofit.create(RetailShiftApi.class);
        this.retailUrl = retailUrl;
    }


    @Override
    public List<RetailShiftDto> getAll() {
        List<RetailShiftDto> retailShiftDtoList = Collections.emptyList();
        Call<List<RetailShiftDto>> retailShiftApiAll = retailShiftApi.getAll(retailUrl);
        try {
            Response<List<RetailShiftDto>> response = retailShiftApiAll.execute();
            if (response.isSuccessful()) {
                retailShiftDtoList = response.body();
                log.info("Успешно выполнен запрос на получение списка RetailShiftDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка RetailShiftDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка RetailShiftDto", e);
        }
        return retailShiftDtoList;
    }

    @Override
    public RetailShiftDto getById(Long Id) {
        RetailShiftDto retailShiftDto = null;
        Call<RetailShiftDto> callSync = retailShiftApi.getById(retailUrl, Id);
        try {
            Response<RetailShiftDto> response = callSync.execute();
            if (response.isSuccessful()) {
                retailShiftDto = response.body();
                log.info("Успешно выполнен запрос на получение RetailShiftDto по id: {}", Id);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение RetailShiftDto по id {}", response.code(), Id);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение RetailShiftDto по id", e);
        }
        return retailShiftDto;
    }

    @Override
    public void create(RetailShiftDto retailShiftDto) {
        Call<Void> call = retailShiftApi.create(retailUrl, retailShiftDto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание RetailShiftDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создании RetailShiftDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создании RetailShiftDto", e);
        }
    }

    @Override
    public void update(RetailShiftDto retailShiftDto) {
        Call<Void> call = retailShiftApi.update(retailUrl, retailShiftDto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на изменение RetailShiftDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на изменение RetailShiftDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на изменение RetailShiftDto", e);
        }
    }

    @Override
    public void deleteById(Long Id) {
        Call<Void> call = retailShiftApi.deleteById(retailUrl, Id);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление RetailShiftDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление RetailShiftDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление RetailShiftDto по id", e);
        }
    }
}
