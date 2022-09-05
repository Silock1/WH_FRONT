package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.ProductionStageDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.ProductionStageService;
import com.warehouse_accounting.services.interfaces.api.ProductionStageApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class ProductionStageServiceImpl implements ProductionStageService {

    private final ProductionStageApi api;
    private final String url;

    public ProductionStageServiceImpl(@Value("api/production_stage") String url, Retrofit retrofit) {
        this.api = retrofit.create(ProductionStageApi.class);
        this.url = url;
    }

    @Override
    public List<ProductionStageDto> getAll() {
        Call<List<ProductionStageDto>> call = api.getAll(url);
        return new ServiceUtils<>(ProductionStageDto.class).getAll(call);
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
    public void create(ProductionStageDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(ProductionStageDto.class).create(call);
    }

    @Override
    public void update(ProductionStageDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(ProductionStageDto.class).update(call);
    }

    @Override
    public void delete(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(ProductionStageDto.class).delete(call);
    }
}
