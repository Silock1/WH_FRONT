package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.InventoryDto;
import com.warehouse_accounting.services.interfaces.InventoryService;
import com.warehouse_accounting.services.interfaces.api.InventoryApi;
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
public class InventoryServiceImpl implements InventoryService {
    private final InventoryApi inventoryApi;
    private final String inventoryUrl;

    public InventoryServiceImpl(@Value("api/inventory") String inventoryUrl, Retrofit retrofit) {
        this.inventoryUrl = inventoryUrl;
        this.inventoryApi = retrofit.create(InventoryApi.class);
    }

    @Override
    public List<InventoryDto> getAll() {
        List<InventoryDto> inventoryDtoList = Collections.emptyList();
        Call<List<InventoryDto>> roleGetAllCall = inventoryApi.getAll(inventoryUrl);
        try {
            Response<List<InventoryDto>> response = roleGetAllCall.execute();
            if (response.isSuccessful()) {
                inventoryDtoList = response.body();
                log.info("Успешно выполнен запрос на получение списка InventoryDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка InventoryDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка InventoryDto", e);
        }
        return inventoryDtoList;
    }

    @Override
    public InventoryDto getById(Long id) {
        InventoryDto inventoryDto = null;
        Call<InventoryDto> callSync = inventoryApi.getById(inventoryUrl, id);
        try {
            Response<InventoryDto> response = callSync.execute();
            if (response.isSuccessful()) {
                inventoryDto = response.body();
                log.info("Успешно выполнен запрос на получение InventoryDto по id: {}", id);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение InventoryDto по id {}", response.code(), id);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение InventoryDto по id", e);
        }
        return inventoryDto;
    }

    @Override
    public void create(InventoryDto dto) {
        Call<Void> call = inventoryApi.create(inventoryUrl, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание InventoryDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создании InventoryDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создании InventoryDto", e);
        }
    }

    @Override
    public void update(InventoryDto dto) {
        Call<Void> call = inventoryApi.update(inventoryUrl, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на изменение InventoryDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на изменение InventoryDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на изменениие InventoryDto", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = inventoryApi.deleteById(inventoryUrl, id);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление InventoryDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление InventoryDto по id", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление InventoryDto по id", e);
        }
    }

}