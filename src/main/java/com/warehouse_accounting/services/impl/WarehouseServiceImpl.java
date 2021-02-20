package com.warehouse_accounting.services.impl;


import com.warehouse_accounting.models.dto.WarehouseDto;
import com.warehouse_accounting.services.interfaces.WarehouseService;
import com.warehouse_accounting.services.interfaces.api.WarehouseApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Log4j2
@Service
public class WarehouseServiceImpl implements WarehouseService {
    WarehouseApi warehouseService;
    String warehouseUrl;

    public WarehouseServiceImpl(@Value("${retrofit.restServices.warehouse_url}") String warehouseUrl, Retrofit retrofit) {
        this.warehouseService = retrofit.create(WarehouseApi.class);
        this.warehouseUrl = warehouseUrl;
    }


    public List<WarehouseDto> getAll() {
        List<WarehouseDto> listWarehouseDto = Collections.emptyList();
        Call<List<WarehouseDto>> call = warehouseService.getAll(warehouseUrl);
        try {
            listWarehouseDto = call.execute().body();
            log.info("Успешно выполнен запрос на получение списка WarehouseDto");
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса : {}", e.getMessage());
            e.printStackTrace();
        }
        return listWarehouseDto;
    }

    @Override
    public WarehouseDto getById(Long id) {
        WarehouseDto warehouseDto = null;
        Call<WarehouseDto> call = warehouseService.getById(warehouseUrl, id);
        try {
            if (call.execute().isSuccessful()) {
                warehouseDto = call.clone().execute().body();
                log.info("Успешно выполнен запрос на получение WarehouseDto по id: {}", id);
            } else {
                log.error("Произошла ошибка при выполнении запроса на получение  WarehouseDto по id: {}", id);
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса : {}", e.getMessage());
            e.printStackTrace();
        }
        return warehouseDto;
    }

    @Override
    public void create(WarehouseDto warehouseDto) {
        Call<Void> call = warehouseService.create(warehouseUrl, warehouseDto);
        try {
            call.execute();
            log.info("Успешно выполнен запрос на создание WarehouseDto");
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void update(WarehouseDto warehouseDto) {
        Call<Void> call = warehouseService.update(warehouseUrl, warehouseDto);
        try {
            if (call.execute().isSuccessful()) {
                log.info("Успешно выполнен запрос на изменении WarehouseDto");
            } else {
                log.error("Произошла ошибка при выполнении запроса на изменении WarehouseDto");
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса : {}", e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = warehouseService.deleteById(warehouseUrl, id);
        try {
            if (call.execute().isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление WarehouseDto");
            } else {
                log.error("Произошла ошибка при выполнении запроса на удаление WarehouseDto по id: {}", id);
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса : {}", e.getMessage());
            e.printStackTrace();
        }
    }
}
