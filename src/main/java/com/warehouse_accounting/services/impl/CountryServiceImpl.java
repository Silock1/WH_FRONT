package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.CountryDto;
import com.warehouse_accounting.services.interfaces.CountryService;
import com.warehouse_accounting.services.interfaces.api.CountryApi;
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
public class CountryServiceImpl implements CountryService {

    private final CountryApi countryApi;
    private final String countryUrl;

    public CountryServiceImpl(@Value("${retrofit.restServices.country_url}") String countryUrl, Retrofit retrofit) {
        this.countryUrl = countryUrl;
        this.countryApi = retrofit.create(CountryApi.class);
    }

    @Override
    public List<CountryDto> getAll() {
        List<CountryDto> countryDtoList = Collections.emptyList();
        Call<List<CountryDto>> countryApiAll = countryApi.getAll(countryUrl);
        try {
            Response<List<CountryDto>> response = countryApiAll.execute();
            if (response.isSuccessful()) {
                countryDtoList = response.body();
                log.info("Успешно выполнен запрос на получение списка CountryDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка CountryDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка CountryDto", e);
        }
        return countryDtoList;
    }

    @Override
    public CountryDto getById(Long id) {
        CountryDto countryDto = null;
        Call<CountryDto> callSync = countryApi.getById(countryUrl, id);
        try {
            Response<CountryDto> response = callSync.execute();
            if (response.isSuccessful()) {
                countryDto = response.body();
                log.info("Успешно выполнен запрос на получение CountryDto по id: {}", id);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение CountryDto по id {}", response.code(), id);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение CountryDto по id", e);
        }
        return countryDto;
    }

    @Override
    public void create(CountryDto countryDto) {
        Call<Void> call = countryApi.create(countryUrl, countryDto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание CountryDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создании CountryDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создании CountryDto", e);
        }
    }

    @Override
    public void update(CountryDto countryDto) {
        Call<Void> call = countryApi.update(countryUrl, countryDto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на изменение CountryDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на изменение CountryDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на изменение CountryDto", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = countryApi.deleteById(countryUrl, id);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление CountryDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление CountryDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление CountryDto по id", e);
        }
    }
}

