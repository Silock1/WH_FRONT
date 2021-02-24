package com.warehouse_accounting.services.impl;


import com.warehouse_accounting.models.dto.WarehouseDto;
import com.warehouse_accounting.services.interfaces.WarehouseService;
import com.warehouse_accounting.services.interfaces.api.WarehouseApi;
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
public class WarehouseServiceImpl implements WarehouseService {
    private final WarehouseApi warehouseService;
    private final String warehouseUrl;

    public WarehouseServiceImpl(@Value("${retrofit.restServices.warehouse_url}") String warehouseUrl, Retrofit retrofit) {
        this.warehouseService = retrofit.create(WarehouseApi.class);
        this.warehouseUrl = warehouseUrl;
    }


    public List<WarehouseDto> getAll() {
        List<WarehouseDto> listWarehouseDto = Collections.emptyList();
        Call<List<WarehouseDto>> call = warehouseService.getAll(warehouseUrl);
        try {
            Response<List<WarehouseDto>> response = call.execute();
            if (response.isSuccessful()) {
                listWarehouseDto = response.body();
                log.info("Успешно выполнен запрос на получение списка WarehouseDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка WarehouseDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка WarehouseDto", e);
        }
        return listWarehouseDto;
    }

    @Override
    public WarehouseDto getById(Long id) {
        WarehouseDto warehouseDto = null;
        Call<WarehouseDto> call = warehouseService.getById(warehouseUrl, id);
        try {
            Response<WarehouseDto> response = call.execute();
            if (response.isSuccessful()) {
                warehouseDto = response.body();
                log.info("Успешно выполнен запрос на получение WarehouseDto по id: {}", id);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение  WarehouseDto по id: {}", response.code(), id);
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение  WarehouseDto по id", e);
        }
        return warehouseDto;
    }

    @Override
    public void create(WarehouseDto warehouseDto) {
        Call<Void> call = warehouseService.create(warehouseUrl, warehouseDto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание WarehouseDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создание WarehouseDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создание WarehouseDto", e);
        }
    }

    @Override
    public void update(WarehouseDto warehouseDto) {
        Call<Void> call = warehouseService.update(warehouseUrl, warehouseDto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на изменение WarehouseDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на изменение WarehouseDto",response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на изменение WarehouseDto", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = warehouseService.deleteById(warehouseUrl, id);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление WarehouseDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление WarehouseDto по id: {}",response.code(), id);
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление WarehouseDto по id", e);
        }
    }
}
