package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.ProductionOrderDto;
import com.warehouse_accounting.services.interfaces.ProductionOrderService;
import com.warehouse_accounting.services.interfaces.api.ProductionOrderApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class ProductionOrderServiceImpl implements ProductionOrderService {

    private final ProductionOrderApi api;
    private final String url;

    public ProductionOrderServiceImpl(Retrofit retrofit, @Value("${retrofit.restServices.production_order_url}") String url) {
        this.api = retrofit.create(ProductionOrderApi.class);
        this.url = url;
    }

    @Override
    public List<ProductionOrderDto> getAll() {

        List<ProductionOrderDto> productionOrderDtoList = new ArrayList<>();
        Call<List<ProductionOrderDto>> listCall = api.getAll(url);
        try {
            Response<List<ProductionOrderDto>> response = listCall.execute();
            if (response.isSuccessful()) {
                productionOrderDtoList = listCall.execute().body();
                log.info("Успешно выполнен запрон на получение списка");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка" + e);
        }
        return productionOrderDtoList;
    }


    @Override
    public ProductionOrderDto getById(Long id) {
        ProductionOrderDto productionOrderDto = new ProductionOrderDto();
        Call<ProductionOrderDto> call = api.getById(url, id);
        try {
            Response<ProductionOrderDto> response = call.execute();
            if (response.isSuccessful()) {
                productionOrderDto = response.body();
                log.info("Успешно выполнен запрон на получение");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса при получение c id {}", response.code(), id);
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса при получение" + e);
        }
        return productionOrderDto;
    }

    @Override
    public void create(ProductionOrderDto productionOrderDto) {
        Call<Void> call = api.create(url, productionOrderDto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("успешно создан");
            } else {
                log.error("Произошла ошибка {} при созданииc id {}", response.code(), productionOrderDto.getId());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при создании" + e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("успешно удален");
            } else {
                log.error("Произошла ошибка {} при удалении c id {}", response.code(), id);
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при удалении" + e);
        }
    }

    @Override
    public void update(ProductionOrderDto productionOrderDto) {
        Call<Void> call = api.update(url, productionOrderDto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("успешно обновлен");
            } else {
                log.error("Произошла ошибка {} при обновлении c id {}", response.code(), productionOrderDto.getId());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при обновлении" + e);
        }
    }
}
