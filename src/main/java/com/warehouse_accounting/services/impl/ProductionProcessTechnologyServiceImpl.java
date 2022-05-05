package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.ProductionProcessTechnologyDto;
import com.warehouse_accounting.services.interfaces.ProductionProcessTechnologyService;
import com.warehouse_accounting.services.interfaces.api.ProductionProcessTechnologyApi;
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
public class ProductionProcessTechnologyServiceImpl implements ProductionProcessTechnologyService {

    private final ProductionProcessTechnologyApi productionProcessTechnologyApi;
    private final String productionProcessTechnologyUrl;

    public ProductionProcessTechnologyServiceImpl(@Value("api/production_process_technology") String productionStageUrl, Retrofit retrofit) {
        this.productionProcessTechnologyApi = retrofit.create(ProductionProcessTechnologyApi.class);
        this.productionProcessTechnologyUrl = productionStageUrl;
    }

    @Override
    public List<ProductionProcessTechnologyDto> getAll() {
        List<ProductionProcessTechnologyDto> productionProcessTechnologyDtoList = Collections.emptyList();
        Call<List<ProductionProcessTechnologyDto>> call = productionProcessTechnologyApi.getAll(productionProcessTechnologyUrl);
        try {
            Response<List<ProductionProcessTechnologyDto>> response = call.execute();
            if (response.isSuccessful()) {
                productionProcessTechnologyDtoList = response.body();
                log.info("Успешно выполнен запрос на получение списка ProductionProcessTechnologyDto");
                log.info(productionProcessTechnologyDtoList);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка ProductionProcessTechnologyDto", response.code());
                log.error("[eq");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productionProcessTechnologyDtoList;
    }

    @Override
    public void create(ProductionProcessTechnologyDto productionProcessTechnologyDto) {
        Call<Void> request = productionProcessTechnologyApi.create(productionProcessTechnologyUrl, productionProcessTechnologyDto);
        try {
            Response<Void> response = request.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание ProductionProcessTechnologyDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создании ProductionProcessTechnologyDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создании ProductionProcessTechnologyDto", e);
        }
    }

    @Override
    public void update(ProductionProcessTechnologyDto ProductionProcessTechnologyDto) {

    }

    @Override
    public void delete(Long id) {

    }
}
