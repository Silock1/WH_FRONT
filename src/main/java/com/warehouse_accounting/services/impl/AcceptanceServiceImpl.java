package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.AcceptancesDto;
import com.warehouse_accounting.services.interfaces.AcceptanceService;
import com.warehouse_accounting.services.interfaces.api.AcceptanceApi;
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
public class AcceptanceServiceImpl implements AcceptanceService {

    private final AcceptanceApi acceptanceApi;
    private final String acceptanceUrl;

    public AcceptanceServiceImpl(@Value("${retrofit.restServices.acceptance_url}") String acceptanceUrl, Retrofit retrofit) {
        this.acceptanceUrl = acceptanceUrl;
        this.acceptanceApi = retrofit.create(AcceptanceApi.class);
    }

    @Override
    public List<AcceptancesDto> getAll() {
        List<AcceptancesDto> acceptancesDtoList = Collections.emptyList();
        Call<List<AcceptancesDto>> invoiceApiAll = acceptanceApi.getAll(acceptanceUrl);
        try {
            Response<List<AcceptancesDto>> response = invoiceApiAll.execute();
            if (response.isSuccessful()) {
                acceptancesDtoList = response.body();
                log.info("Успешно выполнен запрос на получение списка AcceptancesDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка AcceptancesDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка AcceptancesDto", e);
        }
        return acceptancesDtoList;
    }

    @Override
    public AcceptancesDto getById(Long id) {
        AcceptancesDto acceptancesDto = null;
        Call<AcceptancesDto> callSync = acceptanceApi.getById(acceptanceUrl, id);
        try {
            Response<AcceptancesDto> response = callSync.execute();
            if (response.isSuccessful()) {
                acceptancesDto = response.body();
                log.info("Успешно выполнен запрос на получение AcceptancesDto по id: {}", id);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение AcceptancesDto по id {}", response.code(), id);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение AcceptancesDto по id", e);
        }
        return acceptancesDto;
    }

    @Override
    public void create(AcceptancesDto acceptancesDto) {
        Call<Void> call = acceptanceApi.create(acceptanceUrl, acceptancesDto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание AcceptancesDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создании AcceptancesDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создании AcceptancesDto", e);
        }
    }

    @Override
    public void update(AcceptancesDto acceptancesDto) {
        Call<Void> call = acceptanceApi.update(acceptanceUrl, acceptancesDto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на изменение AcceptancesDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на изменение AcceptancesDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на изменение AcceptancesDto", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = acceptanceApi.deleteById(acceptanceUrl, id);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление AcceptancesDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление AcceptancesDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление AcceptancesDto по id", e);
        }
    }

}
