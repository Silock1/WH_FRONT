package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.CurrencyDto;
import com.warehouse_accounting.services.interfaces.CurrencyService;
import com.warehouse_accounting.services.interfaces.api.CurrencyApi;
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
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyApi api;
    private final String url;

    public CurrencyServiceImpl(Retrofit retrofit, @Value("${retrofit.restServices.position_url}") String url) {
        this.api = retrofit.create(CurrencyApi.class);
        this.url = url;
    }


    @Override
    public List<CurrencyDto> getAll() {
        List<CurrencyDto> currencyDtoList = new ArrayList<>();
        Call<List<CurrencyDto>> listCall = api.getAll(url);
        try {
            listCall.execute().body();
            log.info("Успешно выполнен запрос на получение списка CurrencyDto");
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка CurrencyDto");
        }
        return currencyDtoList;
    }

    @Override
    public CurrencyDto getById(Long id) {
        CurrencyDto currencyDto = new CurrencyDto();
        Call<CurrencyDto> call = api.getById(url, id);
        try {
            call.execute().body();
            log.info("Успешно выполнен запрос на получение CurrencyDto");
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение CurrencyDto");
        }
        return currencyDto;
    }

    @Override
    public void create(CurrencyDto dto) {
        Call<Void> call = api.create(url, dto);
        try {
            call.execute();
            log.info("Успешно выполнен запрос на создание CurrencyDto");
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создание CurrencyDto");
        }

    }

    @Override
    public void update(CurrencyDto dto) {
        Call<Void> call = api.update(url, dto);
        try {
            call.execute();
            log.info("Успешно выполнен запрос на обновление CurrencyDto");
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на обновление CurrencyDto");
        }

    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        try {
            call.execute();
            log.info("Успешно выполнен запрос на удаление CurrencyDto");
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление CurrencyDto");
        }
    }
}
