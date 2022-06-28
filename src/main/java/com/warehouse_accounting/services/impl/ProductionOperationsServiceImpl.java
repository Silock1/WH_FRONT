package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.ProductionOperationsDto;
import com.warehouse_accounting.services.interfaces.ProductionOperationsService;
import com.warehouse_accounting.services.interfaces.api.ProductionOperationsApi;
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
public class ProductionOperationsServiceImpl implements ProductionOperationsService {
    private ProductionOperationsApi api;
    private String url;

    public ProductionOperationsServiceImpl(Retrofit retrofit, @Value("${retrofit.restServices.production_operations_url}") String url) {
        this.api = retrofit.create(ProductionOperationsApi.class);
        this.url = url;
    }

    @Override
    public List<ProductionOperationsDto> getAll() {
        List <ProductionOperationsDto> productionOperationsDtoList = new ArrayList<>();
        Call <List<ProductionOperationsDto>> listCall = api.getAll(url);
        try {
            Response <List<ProductionOperationsDto>> response = listCall.execute();
            if (response.isSuccessful()) {
                productionOperationsDtoList = listCall.execute().body();
                log.info("Успешно выполнен запрон на получение списка");
            } else  {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка" + e);
        }
        return productionOperationsDtoList;
    }

    @Override
    public ProductionOperationsDto getById(Long id) {
        ProductionOperationsDto productionOperationsDto = new ProductionOperationsDto();
        Call <ProductionOperationsDto> call = api.getById(url, id);
        try {
            Response <ProductionOperationsDto> response = call.execute();
            if (response.isSuccessful()) {
                productionOperationsDto = call.execute().body();
                log.info("Успешно выполнен запрон на получение");
            } else  {
                log.error("Произошла ошибка {} при выполнении запроса при получение c id {}", response.code(), id);
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса при получение" + e);
        }
        return productionOperationsDto;
    }

    @Override
    public void create(ProductionOperationsDto productionOperationsDto) {
        Call <Void> call = api.create(url, productionOperationsDto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("успешно создан");
            } else {
                log.error("Произошла ошибка {} при создании id {}", response.code(), productionOperationsDto.getId());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при создании" + e);
        }


    }

    @Override
    public void update(ProductionOperationsDto productionOperationsDto) {
        Call <Void> call = api.update(url, productionOperationsDto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("успешно обновлен");
            } else {
                log.error("Произошла ошибка {} при обновлении c id {}", response.code(), productionOperationsDto.getId());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при обновлении" + e);
        }


    }

    @Override
    public void deleteById(Long id) {
        Call <Void> call = api.deleteById(url, id);
        try {
            Response <Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("успешно удален");
            } else  {
                log.error("Произошла ошибка {} при удалении c id {}", response.code(), id);
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при удалении" + e);
        }

    }
}
