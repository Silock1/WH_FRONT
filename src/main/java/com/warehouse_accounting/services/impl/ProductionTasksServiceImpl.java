package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.ProductionTasksDto;
import com.warehouse_accounting.services.interfaces.ProductionTasksService;
import com.warehouse_accounting.services.interfaces.api.ProductionTasksApi;
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
public class ProductionTasksServiceImpl implements ProductionTasksService {

    private final ProductionTasksApi productionTasksApi;
    private final String productionTasksUrl;

    public ProductionTasksServiceImpl(@Value("${retrofit.restServices.production_tasks_url}") String productionTasksUrl, Retrofit retrofit) {
        this.productionTasksApi = retrofit.create(ProductionTasksApi.class);
        this.productionTasksUrl = productionTasksUrl;
    }

    @Override
    public List<ProductionTasksDto> getAll() {
        List<ProductionTasksDto> productionTasksDtoList = Collections.emptyList();
        Call<List<ProductionTasksDto>> call = productionTasksApi.getAll(productionTasksUrl);
        try {
            Response<List<ProductionTasksDto>> response = call.execute();
            if (response.isSuccessful()) {
                productionTasksDtoList = response.body();
                log.info("Успешно выполнен запрос на получение списка ProductionTasksDto");
                log.info(productionTasksDtoList);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка ProductionTasksDto", response.code());
                log.error("[eq");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productionTasksDtoList;
    }

    @Override
    public ProductionTasksDto getById(Long id) {
        ProductionTasksDto productionTasksDto = new ProductionTasksDto();
        Call<ProductionTasksDto> call = productionTasksApi.getById(productionTasksUrl, id);
        try {
            Response<ProductionTasksDto> response = call.execute();
            if (response.isSuccessful()) {
                productionTasksDto = response.body();
                log.info("Успешно выполнен запрос на получение ProductionTasksDto");
                log.info(productionTasksDto);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение ProductionTasksDto"
                        , response.code());
                log.error("[eq");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productionTasksDto;
    }

    @Override
    public void create(ProductionTasksDto productionTasksDto) {
        Call<Void> request = productionTasksApi.create(productionTasksUrl, productionTasksDto);
        try {
            Response<Void> response = request.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание ProductionTasksDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создании ProductionTasksDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создании ProductionTasksDto", e);
        }
    }

    @Override
    public void update(ProductionTasksDto productionTasksDto) {
        Call<Void> request = productionTasksApi.update(productionTasksUrl, productionTasksDto);
        try {
            Response<Void> response = request.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на обновление ProductionTasksDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на обновление ProductionTasksDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на обновление ProductionTasksDto", e);
        }
    }

    @Override
    public void delete(Long id) {
        Call<Void> request = productionTasksApi.deleteById(productionTasksUrl, id);
        try {
            Response<Void> response = request.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление ProductionTasksDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление ProductionTasksDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление ProductionTasksDto", e);
        }
    }
}
