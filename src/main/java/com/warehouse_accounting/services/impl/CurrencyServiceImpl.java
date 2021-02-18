package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.CurrencyDto;
import com.warehouse_accounting.services.interfaces.CurrencyService;
import com.warehouse_accounting.services.interfaces.api.CurrencyApi;
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
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyApi api;
    private final String url;

    public CurrencyServiceImpl(Retrofit retrofit, @Value("${retrofit.restServices.currency_url}") String url) {
        this.api = retrofit.create(CurrencyApi.class);
        this.url = url;
    }


    @Override
    public List<CurrencyDto> getAll() {
        List<CurrencyDto> list = new ArrayList<>();
        Call<List<CurrencyDto>> listCall = api.getAll(url);
        try {
            Response<List<CurrencyDto>> response = listCall.execute();
            if (response.isSuccessful()){
                list = response.body();
                log.info("Успешно выполнен запрос на получение списка CurrencyDto");
            }else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка CurrencyDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка CurrencyDto " + e);
        }
        return list;
    }

    @Override
    public CurrencyDto getById(Long id) {
        CurrencyDto dto = new CurrencyDto();
        Call<CurrencyDto> call = api.getById(url, id);
        try {
            Response<CurrencyDto> response = call.execute();
            if (response.isSuccessful()){
                dto = response.body();
                log.info("Успешно выполнен запрос на получение CurrencyDto");
            }else {
                log.error("Произошла ошибка {} при выполнении запроса на получение CurrencyDto c id {}", response.code(), id);
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение CurrencyDto " + e);
        }
        return dto;
    }

    @Override
    public void create(CurrencyDto dto) {
        Call<Void> call = api.create(url, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()){
                log.info("Успешно выполнен запрос на создание CurrencyDto");
            }else {
                log.error("Произошла ошибка {} при выполнении запроса на создание CurrencyDto c id {}", response.code(), dto.getId());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создание CurrencyDto " + e);
        }
    }

    @Override
    public void update(CurrencyDto dto) {
        Call<Void> call = api.update(url, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()){
                log.info("Успешно выполнен запрос на обновление CurrencyDto");
            }else {
                log.error("Произошла ошибка {} при выполнении запроса на обновление CurrencyDto c id {}", response.code(), dto.getId());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на обновление CurrencyDto" + e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()){
                log.info("Успешно выполнен запрос на удаление CurrencyDto");
            }else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление CurrencyDto c id {}", response.code(), id);
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление CurrencyDto" + e);
        }
    }
}
