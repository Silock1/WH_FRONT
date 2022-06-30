package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.InternalOrderDto;
import com.warehouse_accounting.services.interfaces.InternalOrderService;
import com.warehouse_accounting.services.interfaces.api.InternalOrderApi;
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
public class InternalOrderServiceImpl implements InternalOrderService {

    private final InternalOrderApi internalOrderApi;
    private final String internalOrderUrl;

    public InternalOrderServiceImpl(@Value("${retrofit.restServices.internal_order-url}") String internUrl, Retrofit retrofit) {
        this.internalOrderUrl = internUrl;
        this.internalOrderApi = retrofit.create(InternalOrderApi.class);
    }

    @Override
    public List<InternalOrderDto> getAll() {
        List<InternalOrderDto> internalOrderDtos = Collections.emptyList();
        Call<List<InternalOrderDto>> listCall = internalOrderApi.getAll(internalOrderUrl);
        try {
            Response<List<InternalOrderDto>> response = listCall.execute();
            if (response.isSuccessful()) {
                internalOrderDtos = response.body();
                log.info("Успешно выполнен запрос на получение списка InternalOrderDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка InternalOrderDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка InternalOrderDto", e);
        }
        return internalOrderDtos;
    }

    @Override
    public InternalOrderDto getById(Long id) {
        InternalOrderDto internalOrderDto = null;
        Call<InternalOrderDto> callSync = internalOrderApi.getById(internalOrderUrl, id);
        try {
            Response<InternalOrderDto> response = callSync.execute();
            if (response.isSuccessful()) {
                internalOrderDto = response.body();
                log.info("Успешно выполнен запрос на получение InternalOrderDto по id: {}", id);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение InternalOrderDto по id {}", response.code(), id);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение InternalOrderDto по id", e);
        }
        return internalOrderDto;
    }

    @Override
    public void create(InternalOrderDto internalOrderDto) {
        Call<Void> call = internalOrderApi.create(internalOrderUrl, internalOrderDto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание InternalOrderDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создании InternalOrderDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создании InternalOrderDto", e);
        }
    }

    @Override
    public void update(InternalOrderDto internalOrderDto) {
        Call<Void> call = internalOrderApi.update(internalOrderUrl, internalOrderDto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на изменение InternalOrderDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на изменение InternalOrderDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на изменениие InternalOrderDto", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = internalOrderApi.deleteById(internalOrderUrl, id);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление InternalOrderDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление InternalOrderDto по id", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление InternalOrderDto по id", e);
        }
    }
}
