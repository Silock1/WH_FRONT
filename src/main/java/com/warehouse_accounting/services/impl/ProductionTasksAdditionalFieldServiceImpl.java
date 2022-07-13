package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.ProductionTasksAdditionalFieldDto;
import com.warehouse_accounting.services.interfaces.ProductionTasksAdditionalFieldService;
import com.warehouse_accounting.services.interfaces.api.ProductionTasksAdditionalFieldApi;
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
public class ProductionTasksAdditionalFieldServiceImpl implements ProductionTasksAdditionalFieldService {

    private final ProductionTasksAdditionalFieldApi api;
    private final String url;

    public ProductionTasksAdditionalFieldServiceImpl(
            @Value("${retrofit.restServices.production_tasks_additional_field_url}") String url, Retrofit retrofit) {
        this.api = retrofit.create(ProductionTasksAdditionalFieldApi.class);
        this.url = url;
    }

    @Override
    public List<ProductionTasksAdditionalFieldDto> getAll() {
        List<ProductionTasksAdditionalFieldDto> dtoList = Collections.emptyList();
        Call<List<ProductionTasksAdditionalFieldDto>> call = api.getAll(url);
        try {
            Response<List<ProductionTasksAdditionalFieldDto>> response = call.execute();
            if (response.isSuccessful()) {
                dtoList = response.body();
                log.info("Успешно выполнен запрос на получение списка ProductionTasksAdditionalFieldDto");
                log.info(dtoList);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка ProductionTasksAdditionalFieldDto"
                        , response.code());
                log.error("[eq");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dtoList;
    }

    @Override
    public ProductionTasksAdditionalFieldDto getById(Long id) {
        Call<ProductionTasksAdditionalFieldDto> call = api.getById(url, id);
        ProductionTasksAdditionalFieldDto responseDto = new ProductionTasksAdditionalFieldDto();
        try {
            Response<ProductionTasksAdditionalFieldDto> response = call.execute();
            if (response.isSuccessful()) {
                responseDto = response.body();
                log.info("Успешно выполнен запрос на получение ProductionTasksAdditionalFieldDto");
                log.info(responseDto);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение ProductionTasksAdditionalFieldDto"
                        , response.code());
                log.error("[eq");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseDto;
    }

    @Override
    public ProductionTasksAdditionalFieldDto create(ProductionTasksAdditionalFieldDto dto) {
        Call<ProductionTasksAdditionalFieldDto> request = api.create(url, dto);
        ProductionTasksAdditionalFieldDto responseDto = new ProductionTasksAdditionalFieldDto();
        try {
            Response<ProductionTasksAdditionalFieldDto> response = request.execute();
            if (response.isSuccessful()) {
                responseDto = response.body();
                log.info("Успешно выполнен запрос на создание ProductionTasksAdditionalFieldDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создании ProductionTasksAdditionalFieldDto"
                        , response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создании ProductionTasksAdditionalFieldDto", e);
        }
        return responseDto;
    }

    @Override
    public ProductionTasksAdditionalFieldDto update(ProductionTasksAdditionalFieldDto dto) {
        Call<ProductionTasksAdditionalFieldDto> request = api.update(url, dto);
        ProductionTasksAdditionalFieldDto responseDto = new ProductionTasksAdditionalFieldDto();
        try {
            Response<ProductionTasksAdditionalFieldDto> response = request.execute();

            if (response.isSuccessful()) {
                responseDto = response.body();
                log.info("Успешно выполнен запрос на обновление ProductionTasksAdditionalFieldDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на обновление ProductionTasksAdditionalFieldDto"
                        , response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на обновление ProductionTasksAdditionalFieldDto", e);
        }
        return responseDto;
    }

    @Override
    public void delete(Long id) {
        Call<Void> request = api.deleteById(url, id);
        try {
            Response<Void> response = request.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление ProductionTasksAdditionalFieldDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление ProductionTasksAdditionalFieldDto"
                        , response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление ProductionTasksAdditionalFieldDto", e);
        }
    }
}
