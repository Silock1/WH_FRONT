package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.ShipmentsDto;
import com.warehouse_accounting.services.interfaces.ShipmentsService;
import com.warehouse_accounting.services.interfaces.api.ShipmentsApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class ShipmentsServiceImpl implements ShipmentsService {

    private final ShipmentsApi api;
    private final String url;

    public ShipmentsServiceImpl(Retrofit retrofit, @Value("${retrofit.restServices.shipments_url}") String url) {
        this.api = retrofit.create(ShipmentsApi.class);
        this.url = url;
    }

    @Override
    public List<ShipmentsDto> getAll() {
        List<ShipmentsDto> shipmentsDtos = new ArrayList<>(0);
        Call<List<ShipmentsDto>> listCall = api.getAll(url);
        try {
            Response<List<ShipmentsDto>> response = listCall.execute();
            if (response.isSuccessful()) {
                shipmentsDtos = response.body();
                log.info("Успешно выполнен запрос на получение списка ShipmentsDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка ShipmentsDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка ShipmentsDto", e);
        }
        return shipmentsDtos;
    }

    @Override
    public ShipmentsDto getById(Long id) {
        ShipmentsDto dto = new ShipmentsDto();
        Call<ShipmentsDto> call = api.getById(url, id);
        try {
            Response<ShipmentsDto> response = call.execute();
            if (response.isSuccessful()) {
                dto = response.body();
                log.info("Успешно выполнен запрос на получение ShipmentsDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение ShipmentsDto по id {}", response.code(), id);
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение ShipmentsDto", e);
        }
        return dto;
    }

    @Override
    public void create(ShipmentsDto dto) {
        Call<Void> call = api.create(url, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание ShipmentsDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создание ShipmentsDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создание ShipmentsDto", e);
        }
    }

    @Override
    public void update(ShipmentsDto dto) {
        Call<Void> call = api.update(url, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на обновление ShipmentsDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на обновление ShipmentsDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на обновление ShipmentsDto", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление ShipmentsDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление ShipmentsDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление ShipmentsDto", e);
        }
    }
}
