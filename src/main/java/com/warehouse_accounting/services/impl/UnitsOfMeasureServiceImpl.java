package com.warehouse_accounting.services.impl;


import com.warehouse_accounting.models.dto.RoleDto;
import com.warehouse_accounting.models.dto.UnitsOfMeasureDto;
import com.warehouse_accounting.services.interfaces.UnitsOfMeasureService;
import com.warehouse_accounting.services.interfaces.api.UnitsOfMeasureApi;

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
public class UnitsOfMeasureServiceImpl implements UnitsOfMeasureService {
    private final UnitsOfMeasureApi measureApi;
    private final String measureUrl;

    public UnitsOfMeasureServiceImpl(@Value("${retrofit.restServices.units_of_measure_url}") String measureUrl, Retrofit retrofit) {
        this.measureApi = retrofit.create(UnitsOfMeasureApi.class);
        this.measureUrl = measureUrl;
    }


    @Override
    public List<UnitsOfMeasureDto> getAll() {
        List<UnitsOfMeasureDto> measureDtoList = Collections.emptyList();
        Call<List<UnitsOfMeasureDto>> measureGetAllCall = measureApi.getAll(measureUrl);
        try {
            Response<List<UnitsOfMeasureDto>> response = measureGetAllCall.execute();
            if (response.isSuccessful()) {
                measureDtoList = response.body();
                log.info("Успешно выполнен запрос на получение списка UnitsOfMeasureDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка UnitsOfMeasureDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка UnitsOfMeasureDto", e);
        }
        return measureDtoList;
    }

    @Override
    public UnitsOfMeasureDto getById(Long id) {
        UnitsOfMeasureDto measureDto = null;
        Call<UnitsOfMeasureDto> callSync = measureApi.getById(measureUrl, id);
        try {
            Response<UnitsOfMeasureDto> response = callSync.execute();
            if (response.isSuccessful()) {
                measureDto = response.body();
                log.info("Успешно выполнен запрос на получение UnitsOfMeasureDto по id: {}", id);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение UnitsOfMeasureDto по id {}",response.code(), id);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение UnitsOfMeasureDto по id", e);
        }
        return measureDto;
    }

    @Override
    public void create(UnitsOfMeasureDto dto) {
        Call<Void> call = measureApi.create(measureUrl, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание UnitsOfMeasureDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создание UnitsOfMeasureDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создание UnitsOfMeasureDto", e);
        }
    }

    @Override
    public void update(UnitsOfMeasureDto dto) {
        Call<Void> call = measureApi.update(measureUrl, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на изменение UnitsOfMeasureDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на изменение UnitsOfMeasureDto",response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на изменение UnitsOfMeasureDto", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = measureApi.deleteById(measureUrl, id);
        try {
            Response<Void> response = call.execute();
            if(response.isSuccessful()){
                log.info("Успешно выполнен запрос на удаление UnitsOfMeasureDto");
            }else{
                log.error("Произошла ошибка {} при выполнении запроса на удаление UnitsOfMeasureDto по id",response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление UnitsOfMeasureDto по id", e);
        }

    }
}
