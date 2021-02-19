package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.AttributeOfCalculationObjectDto;
import com.warehouse_accounting.services.interfaces.AttributeOfCalculationObjectService;
import com.warehouse_accounting.services.interfaces.api.AttributeOfCalculationObjectApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class AttributeOfCalculationObjectServiceImpl implements AttributeOfCalculationObjectService {

    private final AttributeOfCalculationObjectApi api;
    private final String url;

    public AttributeOfCalculationObjectServiceImpl(Retrofit retrofit, @Value("${retrofit.restServices.attribute_of_calculation_object_url}") String url) {
        this.api = retrofit.create(AttributeOfCalculationObjectApi.class);
        this.url = url;
    }

    @Override
    public List<AttributeOfCalculationObjectDto> getAll() {
        List<AttributeOfCalculationObjectDto> attributeOfCalculationObjectDtoList = new ArrayList<>(0);
        Call<List<AttributeOfCalculationObjectDto>> listCall = api.getAll(url);
        try {
            attributeOfCalculationObjectDtoList = listCall.execute().body();
            log.info("Успешно выполнен запрос на получение списка AttributeOfCalculationObjectDto");
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка AttributeOfCalculationObjectDto");
        }
        return attributeOfCalculationObjectDtoList;
    }

    @Override
    public AttributeOfCalculationObjectDto getById(Long id) {
        AttributeOfCalculationObjectDto attributeOfCalculationObjectDto = new AttributeOfCalculationObjectDto();
        Call<AttributeOfCalculationObjectDto> call = api.getById(url,id);
        try {
            attributeOfCalculationObjectDto = call.execute().body();
            log.info("Успешно выполнен запрос на получение AttributeOfCalculationObjectDto");
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение AttributeOfCalculationObjectDto");
        }
        return attributeOfCalculationObjectDto;
    }

    @Override
    public void create(AttributeOfCalculationObjectDto attributeOfCalculationObjectDto) {
        Call<Void> call = api.create(url, attributeOfCalculationObjectDto);
        try {
            call.execute();
            log.info("Успешно выполнен запрос на создание AttributeOfCalculationObjectDto");
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создание AttributeOfCalculationObjectDto");
        }
    }

    @Override
    public void update(AttributeOfCalculationObjectDto attributeOfCalculationObjectDto) {
        Call<Void> call = api.update(url, attributeOfCalculationObjectDto);
        try {
            call.execute();
            log.info("Успешно выполнен запрос на обновление AttributeOfCalculationObjectDto");
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на обновление AttributeOfCalculationObjectDto");
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        try {
            call.execute();
            log.info("Успешно выполнен запрос на удаление AttributeOfCalculationObjectDto");
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление AttributeOfCalculationObjectDto");
        }
    }
}
