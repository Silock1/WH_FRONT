package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.CustomerOrderDto;
import com.warehouse_accounting.services.interfaces.CustomerOrderService;
import com.warehouse_accounting.services.interfaces.api.CustomerOrderApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class CustomerOrderServiceImpl implements CustomerOrderService {

    private final CustomerOrderApi api;
    private final String url;

    public CustomerOrderServiceImpl(Retrofit retrofit, @Value("${retrofit.restServices.customer_order_url}") String url) {
        this.api = retrofit.create(CustomerOrderApi.class);
        this.url = url;
    }

    @Override
    public List<CustomerOrderDto> getAll() {
        List<CustomerOrderDto> customerOrderDtoList = new ArrayList<>(0);
        Call<List<CustomerOrderDto>> listCall = api.getAll(url);
        try {
            Response<List<CustomerOrderDto>> response = listCall.execute();
            if (response.isSuccessful()) {
                customerOrderDtoList = response.body();
                log.info("Успешно выполнен запрос на получение списка CustomerOrderDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка CustomerOrderDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка CustomerOrderDto", e);
        }
        return customerOrderDtoList;
    }

    @Override
    public CustomerOrderDto getById(Long id) {
        CustomerOrderDto dto = new CustomerOrderDto();
        Call<CustomerOrderDto> call = api.getById(url, id);
        try {
            Response<CustomerOrderDto> response = call.execute();
            if (response.isSuccessful()) {
                dto = response.body();
                log.info("Успешно выполнен запрос на получение CustomerOrderDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение CustomerOrderDto по id {}", response.code(), id);
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение CustomerOrderDto", e);
        }
        return dto;
    }

    @Override
    public void create(CustomerOrderDto dto) {
        Call<Void> call = api.create(url, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание CustomerOrderDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создание CustomerOrderDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создание CustomerOrderDto", e);
        }
    }

    @Override
    public void update(CustomerOrderDto dto) {
        Call<Void> call = api.update(url, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на обновление CustomerOrderDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на обновление CustomerOrderDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на обновление CustomerOrderDto", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление CustomerOrderDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление CustomerOrderDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление CustomerOrderDto", e);
        }
    }
}
