package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.AdjustmentsDto;
import com.warehouse_accounting.services.interfaces.AdjustmentsService;
import com.warehouse_accounting.services.interfaces.api.AdjustmentsApi;

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
public class AdjustmentsServiceImpl implements AdjustmentsService {

    private final AdjustmentsApi adjustmentsApi;
    private final String adjustmentsUrl;

    public AdjustmentsServiceImpl(@Value("${retrofit.restServices.adjustments_url}") String adjustmentsUrl, Retrofit retrofit) {
        this.adjustmentsUrl = adjustmentsUrl;
        this.adjustmentsApi = retrofit.create(AdjustmentsApi.class);
    }

    @Override
    public List<AdjustmentsDto> getAll() {
        List<AdjustmentsDto> adjustmentsDtoList = Collections.emptyList();
        Call<List<AdjustmentsDto>> adjustmentsAllCall = adjustmentsApi.getAll(adjustmentsUrl);
        try {
            Response<List<AdjustmentsDto>> respons = adjustmentsAllCall.execute();
            if (respons.isSuccessful()) {
                adjustmentsDtoList = respons.body();
                log.info("Успешно выполнен запрос на получение списка AdjustmentsDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка AdjustmentsDto", respons.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка AdjustmentsDto", e);
        }
        return adjustmentsDtoList;
    }

    @Override
    public AdjustmentsDto getById(Long id) {
        AdjustmentsDto adjustmentsDto = null;
        Call<AdjustmentsDto> callSync = adjustmentsApi.getById(adjustmentsUrl, id);
        try {
            Response<AdjustmentsDto> respons = callSync.execute();
            if (respons.isSuccessful()) {
                adjustmentsDto = respons.body();
                log.info("Успешно выполнен запрос на получение AdjustmentsDto по id: {}", id);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение AdjustmentsDto по id {}", respons.code(), id);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение AdjustmentsDto по id", e);
        }
        return adjustmentsDto;
    }

    @Override
    public void create(AdjustmentsDto adjustmentsDto) {
        Call<Void> call = adjustmentsApi.create(adjustmentsUrl, adjustmentsDto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание AdjustmentsDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создании AdjustmentsDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создании AdjustmentsDto", e);
        }
    }

    @Override
    public void update(AdjustmentsDto adjustmentsDto) {
        Call<Void> call = adjustmentsApi.update(adjustmentsUrl, adjustmentsDto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на изменение AdjustmentsDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на изменение AdjustmentsDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на изменениие AdjustmentsDto", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = adjustmentsApi.deleteById(adjustmentsUrl, id);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление AdjustmentsDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление AdjustmentsDto по id", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление AdjustmentsDto по id", e);
        }
    }
}
