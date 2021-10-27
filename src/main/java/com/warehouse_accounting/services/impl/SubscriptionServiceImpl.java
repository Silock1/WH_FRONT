package com.warehouse_accounting.services.impl;


import com.warehouse_accounting.models.dto.SubscriptionDto;
import com.warehouse_accounting.services.interfaces.SubscriptionService;
import com.warehouse_accounting.services.interfaces.api.SubscriptionApi;
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
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionApi subscriptionApi;
    private final String subscriptionUrl;

    public SubscriptionServiceImpl(@Value("${retrofit.restServices.subscription_url}") String subscriptionUrl, Retrofit retrofit) {
        this.subscriptionUrl = subscriptionUrl;
        this.subscriptionApi = retrofit.create(SubscriptionApi.class);
    }

    @Override
    public List<SubscriptionDto> getAll() {
        List<SubscriptionDto> subscriptionDtos = Collections.emptyList();
        Call<List<SubscriptionDto>> subscriptionApiAll = subscriptionApi.getAll(subscriptionUrl);
        try {
            Response<List<SubscriptionDto>> response = subscriptionApiAll.execute();
            if (response.isSuccessful()) {
                subscriptionDtos = response.body();
                log.info("Успешно выполнен запрос на получение списка SubscriptionDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка SubscriptionDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка SubscriptionDto", e);
        }
        return subscriptionDtos;
    }

    @Override
    public SubscriptionDto getById(Long id) {
        SubscriptionDto subscriptionDto = null;
        Call<SubscriptionDto> callSync = subscriptionApi.getById(subscriptionUrl, id);
        try {
            Response<SubscriptionDto> response = callSync.execute();
            if (response.isSuccessful()) {
                subscriptionDto = response.body();
                log.info("Успешно выполнен запрос на получение SubscriptionDto по id: {}", id);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение SubscriptionDto по id {}", response.code(), id);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение SubscriptionDto по id", e);
        }
        return subscriptionDto;
    }

    @Override
    public void create(SubscriptionDto subscriptionDto) {
        Call<Void> call = subscriptionApi.create(subscriptionUrl, subscriptionDto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание SubscriptionDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создании SubscriptionDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создании SubscriptionDto", e);
        }
    }

    @Override
    public void update(SubscriptionDto subscriptionDto) {
        Call<Void> call = subscriptionApi.update(subscriptionUrl, subscriptionDto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на изменение SubscriptionDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на изменение SubscriptionDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на изменение SubscriptionDto", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = subscriptionApi.deleteById(subscriptionUrl, id);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление CountryDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление SubscriptionDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление SubscriptionDto по id", e);
        }
    }
}
