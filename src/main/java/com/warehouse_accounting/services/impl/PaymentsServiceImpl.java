package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.PaymentsDto;
import com.warehouse_accounting.services.interfaces.PaymentsService;
import com.warehouse_accounting.services.interfaces.api.PaymentsApi;

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
public class PaymentsServiceImpl implements PaymentsService {

    private final PaymentsApi paymentsApi;
    private final String paymentsUrl;

    public PaymentsServiceImpl(@Value("${retrofit.restServices.payments_url}") String paymentsUrl, Retrofit retrofit) {
        this.paymentsUrl = paymentsUrl;
        this.paymentsApi = retrofit.create(PaymentsApi.class);
    }

    @Override
    public List<PaymentsDto> getAll() {
        List<PaymentsDto> paymentsDtoList = Collections.emptyList();
        Call<List<PaymentsDto>> paymentsApiAll = paymentsApi.getAll(paymentsUrl);
        try {
            Response<List<PaymentsDto>> response = paymentsApiAll.execute();
            if (response.isSuccessful()) {
                paymentsDtoList = response.body();
                log.info("Успешно выполнен запрос на получение списка PaymentsDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка PaymentsDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка PaymentsDto", e);
        }
        return paymentsDtoList;
    }

    @Override
    public PaymentsDto getById(Long id) {
        PaymentsDto paymentsDto = null;
        Call<PaymentsDto> callSync = paymentsApi.getById(paymentsUrl, id);
        try {
            Response<PaymentsDto> response = callSync.execute();
            if (response.isSuccessful()) {
                paymentsDto = response.body();
                log.info("Успешно выполнен запрос на получение PaymentsDto по id: {}", id);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение PaymentsDto по id {}", response.code(), id);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение PaymentsDto по id", e);
        }
        return paymentsDto;
    }

    @Override
    public void create(PaymentsDto paymentsDto) {
        Call<Void> call = paymentsApi.create(paymentsUrl, paymentsDto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание PaymentsDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создании PaymentsDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создании PaymentsDto", e);
        }
    }

    @Override
    public void update(PaymentsDto paymentsDto) {
        Call<Void> call = paymentsApi.update(paymentsUrl, paymentsDto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на изменение PaymentsDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на изменение PaymentsDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на изменение PaymentsDto", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = paymentsApi.deleteById(paymentsUrl, id);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление PaymentsDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление PaymentsDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление PaymentsDto по id", e);
        }
    }

}
