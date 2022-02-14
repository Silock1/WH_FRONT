package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.ComissionerReportsDto;
import com.warehouse_accounting.models.dto.SalesChannelDto;
import com.warehouse_accounting.services.interfaces.ComissionerReportsService;
import com.warehouse_accounting.services.interfaces.api.ComissionerReportsApi;
import com.warehouse_accounting.services.interfaces.api.SalesChannelsApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Log4j2
@Service
public class ComissionerReportsServiceImpl implements ComissionerReportsService {

    private final String reportsUrl = "/api/sales_channels";
    private final ComissionerReportsApi reportsApi = buildRetrofit().create(ComissionerReportsApi.class);

    Retrofit buildRetrofit() {
        return new Retrofit.Builder()
            .baseUrl("http://localhost:4446")
            .addConverterFactory(GsonConverterFactory.create()).build();
    }

    @Override
    public List<ComissionerReportsDto> getAll() {
        List<ComissionerReportsDto> reportsDtoList = Collections.emptyList();
        Call<List<ComissionerReportsDto>> reportsGetAllCall = reportsApi.getAll(reportsUrl);
        try {
            Response<List<ComissionerReportsDto>> response = reportsGetAllCall.execute();
            if (response.isSuccessful()) {
                reportsDtoList = response.body();
                log.info("Успешно выполнен запрос на получение списка ComissionerReportsDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка ComissionerReportsDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка ComissionerReportsDto", e);
        }
        return reportsDtoList;
    }

    @Override
    public ComissionerReportsDto getById(Long id) {
        ComissionerReportsDto channelDto = null;
        Call<ComissionerReportsDto> callSync = reportsApi.getById(reportsUrl, id);
        try {
            Response<ComissionerReportsDto> response = callSync.execute();
            if (response.isSuccessful()) {
                channelDto = response.body();
                log.info("Успешно выполнен запрос на получение ComissionerReportsDto по id: {}", id);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение ComissionerReportsDto по id {}",response.code(), id);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение ComissionerReportsDto по id", e);
        }
        return channelDto;
    }

    @Override
    public void create(ComissionerReportsDto dto) {
        try {
            Call<Void> voidCall = reportsApi.create(reportsUrl, dto);
            Response<Void> response = voidCall.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание ComissionerReportsDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создание ComissionerReportsDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создание ComissionerReportsDto", e);
        }
    }

    @Override
    public void update(ComissionerReportsDto dto) {
        Call<Void> call = reportsApi.update(reportsUrl, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на изменение ComissionerReportsDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на изменение ComissionerReportsDto",response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на изменение ComissionerReportsDto", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = reportsApi.deleteById(reportsUrl, id);
        try {
            Response<Void> response = call.execute();
            if(response.isSuccessful()){
                log.info("Успешно выполнен запрос на удаление ComissionerReportsDto");
            }else{
                log.error("Произошла ошибка {} при выполнении запроса на удаление ComissionerReportsDto по id",response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление ComissionerReportsDto по id", e);
        }

    }
}
