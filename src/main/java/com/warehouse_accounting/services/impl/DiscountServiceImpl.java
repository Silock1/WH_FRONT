package com.warehouse_accounting.services.impl;


import com.warehouse_accounting.models.dto.DiscountDto;
import com.warehouse_accounting.services.interfaces.DiscountService;
import com.warehouse_accounting.services.interfaces.api.DiscountApi;
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
public class DiscountServiceImpl implements DiscountService {

    private final DiscountApi discountApi;

    private final String discountUrl;

    public DiscountServiceImpl(@Value("api/discount") String discountUrl, Retrofit retrofit) {
        this.discountUrl = discountUrl;
        this.discountApi = retrofit.create(DiscountApi.class);
    }

    @Override
    public List<DiscountDto> getAll() {
        List<DiscountDto> discountDtos = Collections.emptyList();
        Call<List<DiscountDto>> discountApiAll = discountApi.getAll(discountUrl);
        try {
            Response<List<DiscountDto>> response = discountApiAll.execute();
            if (response.isSuccessful()) {
                discountDtos = response.body();
                log.info("Успешно выполнен запрос на получение списка DiscountDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка DiscountDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка DiscountDto", e);
        }
        return discountDtos;
    }

    @Override
    public DiscountDto getById(Long id) {
        DiscountDto discountDto = null;
        Call<DiscountDto> discountDtoCall = discountApi.getById(discountUrl, id);
        try {
            Response<DiscountDto> response = discountDtoCall.execute();
            if (response.isSuccessful()) {
                discountDto = response.body();
                log.info("Успешно выполнен запрос на получение DiscountDto по id: {}", id);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение DiscountDto по id {}", response.code(), id);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение DiscountDto по id", e);
        }
        return discountDto;
    }

    @Override
    public void create(DiscountDto dto) {
        Call<Void> call = discountApi.create(discountUrl, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание DiscountDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создание DiscountDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создание DiscountDto", e);
        }
    }

    @Override
    public void update(DiscountDto dto) {
        Call<Void> call = discountApi.update(discountUrl, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на изменение DiscountDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на изменение DiscountDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на изменение DiscountDto", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = discountApi.deleteById(discountUrl, id);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление DiscountDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление DiscountDto по id", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление DiscountDto по id", e);
        }
    }
}
