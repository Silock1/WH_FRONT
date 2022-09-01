package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.LegalDetailDto;
import com.warehouse_accounting.models.dto.ProductionStageDto;
import com.warehouse_accounting.services.interfaces.ProductionStageService;
import com.warehouse_accounting.services.interfaces.api.LegalDetailApi;
import com.warehouse_accounting.services.interfaces.api.ProductionStageApi;
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
public class ProductionStageServiceImpl implements ProductionStageService {
    private final ProductionStageApi productionStageApi;
    private final String productionStageUrl;

    public ProductionStageServiceImpl(@Value("api/production_stage") String productionStageUrl, Retrofit retrofit) {
        this.productionStageApi = retrofit.create(ProductionStageApi.class);
        this.productionStageUrl = productionStageUrl;
    }

    @Override
    public List<ProductionStageDto> getAll() {
        List<ProductionStageDto> productionStageDtoList = Collections.emptyList();
        Call<List<ProductionStageDto>> call = productionStageApi.getAll(productionStageUrl);
        try {
            Response<List<ProductionStageDto>> response = call.execute();
            if (response.isSuccessful()) {
                productionStageDtoList = response.body();
                log.info("Успешно выполнен запрос на получение списка ProductionStageDto");
                log.info(productionStageDtoList);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка ProductionStageDto", response.code());
                log.error("[eq");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productionStageDtoList;
    }
    @Override
    public ProductionStageDto getById(Long id) {
        ProductionStageDto productionStageDto = null;
        Call<ProductionStageDto> call = productionStageApi.getById(productionStageUrl, id);
        try {
            Response<ProductionStageDto> response = call.execute();
            if (response.isSuccessful()) {
                productionStageDto = response.body();
                log.info("Успешно выполнен запрос на получение ProductionStageDto по id");
                log.info(productionStageDto);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение  ProductionStageDto по id", response.code());
                log.error("[eq");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productionStageDto;
    }


    @Override
    public void create(ProductionStageDto productionStageDto) {
        Call<Void> request = productionStageApi.create(productionStageUrl, productionStageDto);
        try {
            Response<Void> response = request.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание ProductionStageDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создании ProductionStageDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создании ProductionStageDto", e);
        }
    }

    @Override
    public void update(ProductionStageDto productionStageDto) {
        Call<Void> request = productionStageApi.update(productionStageUrl, productionStageDto);
        try {
            Response<Void> response = request.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на обновление ProductionStageDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на обновление ProductionStageDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на обновление ProductionStageDto", e);
        }
    }


    @Override
    public void delete(Long id) {
        Call<Void> request = productionStageApi.deleteById(productionStageUrl, id);
        try {
            Response<Void> response = request.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление ProductionStageDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление ProductionStageDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление ProductionStageDto", e);
        }
    }
}
