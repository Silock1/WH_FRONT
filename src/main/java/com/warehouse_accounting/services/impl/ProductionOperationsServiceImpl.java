package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.TechnologicalOperationDto;
import com.warehouse_accounting.services.interfaces.ProductionOperationsService;
import com.warehouse_accounting.services.interfaces.api.ProductionOperationsApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
@Log4j2
public class ProductionOperationsServiceImpl implements ProductionOperationsService {
    private ProductionOperationsApi api;
    private String url;

    public ProductionOperationsServiceImpl(Retrofit retrofit, @Value("api/technological_operation") String url) {
        this.api = retrofit.create(ProductionOperationsApi.class);
        this.url = url;
    }

    @Override
    public List<TechnologicalOperationDto> getAll() {
        List <TechnologicalOperationDto> technologicalOperationDtoList = Collections.emptyList();
        Call <List<TechnologicalOperationDto>> listCall = api.getAll(url);
        try {
            Response <List<TechnologicalOperationDto>> response = listCall.execute();
            if (response.isSuccessful()) {
                technologicalOperationDtoList = response.body();
                log.info("Успешно выполнен запроc на получение списка");
                log.info(technologicalOperationDtoList);
            } else  {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка" + e);
        }
        return technologicalOperationDtoList;
    }

    @Override
    public TechnologicalOperationDto getById(Long id) {
        TechnologicalOperationDto technologicalOperationDto = new TechnologicalOperationDto();
        Call <TechnologicalOperationDto> call = api.getById(url, id);
        try {
            Response <TechnologicalOperationDto> response = call.execute();
            if (response.isSuccessful()) {
                technologicalOperationDto = call.execute().body();
                log.info("Успешно выполнен запрон на получение");
            } else  {
                log.error("Произошла ошибка {} при выполнении запроса при получение c id {}", response.code(), id);
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса при получение" + e);
        }
        return technologicalOperationDto;
    }

    @Override
    public void create(TechnologicalOperationDto technologicalOperationDto) {
        Call <Void> call = api.create(url, technologicalOperationDto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("успешно создан");
            } else {
                log.error("Произошла ошибка {} при создании id {}", response.code(), technologicalOperationDto.getId());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при создании" + e);
        }


    }

    @Override
    public void update(TechnologicalOperationDto technologicalOperationDto) {
        Call <Void> call = api.update(url, technologicalOperationDto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("успешно обновлен");
            } else {
                log.error("Произошла ошибка {} при обновлении c id {}", response.code(), technologicalOperationDto.getId());
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
