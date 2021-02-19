package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.TypeOfPriceDto;
import com.warehouse_accounting.services.interfaces.TypeOfPriceService;
import com.warehouse_accounting.services.interfaces.api.TypeOfPriceApi;
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
public class TypeOfPriceServiceImpl implements TypeOfPriceService {
    private final TypeOfPriceApi typeOfPriceApi;
    private final String typeOfPriceUrl;

    public TypeOfPriceServiceImpl(@Value("${retrofit.restServices.type_of_price_url}") String typeOfPriceUrl, Retrofit retrofit) {
        this.typeOfPriceUrl = typeOfPriceUrl;
        this.typeOfPriceApi = retrofit.create(TypeOfPriceApi.class);
    }

    @Override
    public List<TypeOfPriceDto> getAll() {
        List<TypeOfPriceDto> typeOfPriceDtos = Collections.emptyList();
        Call<List<TypeOfPriceDto>> typeOfPriceApiAll = typeOfPriceApi.getAll(typeOfPriceUrl);
        try {
            Response<List<TypeOfPriceDto>> response = typeOfPriceApiAll.execute();
            if (response.isSuccessful()) {
                typeOfPriceDtos = response.body();
                log.info("Успешно выполнен запрос на получение списка TypeOfPriceDto");
            } else {
                log.error("Произошла ошибка при выполнении запроса на получение списка TypeOfPriceDto");
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка TypeOfPriceDto", e);
        }
        return typeOfPriceDtos;
    }

    @Override
    public TypeOfPriceDto getById(Long id) {
        TypeOfPriceDto typeOfPriceDto = null;
        Call<TypeOfPriceDto> callSync = typeOfPriceApi.getById(typeOfPriceUrl, id);
        try {
            Response<TypeOfPriceDto> response = callSync.execute();
            if (response.isSuccessful()) {
                typeOfPriceDto = response.body();
                log.info("Успешно выполнен запрос на получение TypeOfPriceDto по id: {}", id);
            } else {
                log.error("Произошла ошибка при выполнении запроса на получение TypeOfPriceDto по id: {}", id);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение TypeOfPriceDto по id", e);
        }
        return typeOfPriceDto;
    }

    @Override
    public void create(TypeOfPriceDto dto) {
        Call<Void> call = typeOfPriceApi.create(typeOfPriceUrl, dto);
        try {
            if (call.execute().isSuccessful()) {
                log.info("Успешно выполнен запрос на создание TypeOfPriceDto");
            } else {
                log.error("Произошла ошибка при выполнении запроса на создании TypeOfPriceDto");
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создании TypeOfPriceDto", e);
        }
    }

    @Override
    public void update(TypeOfPriceDto dto) {
        Call<Void> call = typeOfPriceApi.update(typeOfPriceUrl, dto);
        try {
            if (call.execute().isSuccessful()) {
                log.info("Успешно выполнен запрос на изменении TypeOfPriceDto");
            } else {
                log.error("Произошла ошибка при выполнении запроса на изменении TypeOfPriceDto");
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на изменении TypeOfPriceDto", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = typeOfPriceApi.deleteById(typeOfPriceUrl, id);
        try {
            if (call.execute().isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление TypeOfPriceDto");
            } else {
                log.error("Произошла ошибка при выполнении запроса на удаление TypeOfPriceDto по id: {}", id);
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление TypeOfPriceDto по id", e);
        }
    }
}
