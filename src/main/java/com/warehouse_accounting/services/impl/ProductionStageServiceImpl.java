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
                log.error("Произошла ошибка {} при выполнении запроса на получение списка ProductionStageDto",response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productionStageDtoList;
    }
}
