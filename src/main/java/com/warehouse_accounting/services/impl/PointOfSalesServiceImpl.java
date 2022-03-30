package com.warehouse_accounting.services.impl;


import com.warehouse_accounting.models.dto.PointOfSalesDto;
import com.warehouse_accounting.services.interfaces.PointOfSalesService;
import com.warehouse_accounting.services.interfaces.api.RetailApi;
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
public class PointOfSalesServiceImpl implements PointOfSalesService {
    private final RetailApi retailApi;
    private final String retailUrl;

    public PointOfSalesServiceImpl(@Value("${retrofit.restServices.retail_url}") String retailUrl, Retrofit retrofit) {
        this.retailUrl = retailUrl;
        this.retailApi = retrofit.create(RetailApi.class);
    }

    @Override
    public List<PointOfSalesDto> getAll() {
        List<PointOfSalesDto> pointOfSalesDtoList = Collections.emptyList();
        Call<List<PointOfSalesDto>> pointOfSalesApiAll = retailApi.getAll(retailUrl);
        try {
            Response<List<PointOfSalesDto>> response = pointOfSalesApiAll.execute();
            if (response.isSuccessful()) {
                pointOfSalesDtoList = response.body();
                log.info("Успешно выполнен запрос на получение списка PointOfSalesDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка PointOfSalesDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка PointOfSalesDto", e);
        }
        return pointOfSalesDtoList;
    }

    @Override
    public PointOfSalesDto getById(Long id) {
        PointOfSalesDto pointOfSalesDto = null;
        Call<PointOfSalesDto> callSync = retailApi.getById(retailUrl, id);
        try {
            Response<PointOfSalesDto> response = callSync.execute();
            if (response.isSuccessful()) {
                pointOfSalesDto = response.body();
                log.info("Успешно выполнен запрос на получение PointOfSalesDto по id: {}", id);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение PointOfSalesDto по id {}", response.code(), id);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение PointOfSalesDto по id", e);
        }
        return pointOfSalesDto;
    }

    @Override
    public void create(PointOfSalesDto pointOfSalesDto) {
        Call<Void> call = retailApi.create(retailUrl, pointOfSalesDto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание PointOfSalesDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создании PointOfSalesDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создании PointOfSalesDto", e);
        }
    }

    @Override
    public void update(PointOfSalesDto pointOfSalesDto) {
        Call<Void> call = retailApi.update(retailUrl, pointOfSalesDto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на изменение PointOfSalesDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на изменение PointOfSalesDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на изменение PointOfSalesDto", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = retailApi.deleteById(retailUrl, id);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление PointOfSalesDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление PointOfSalesDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление PointOfSalesDto по id", e);
        }
    }
}
