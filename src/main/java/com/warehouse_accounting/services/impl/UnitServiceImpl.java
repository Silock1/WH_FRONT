package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.UnitDto;
import com.warehouse_accounting.services.interfaces.UnitService;
import com.warehouse_accounting.services.interfaces.api.UnitApi;
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
public class UnitServiceImpl implements UnitService {

    private final UnitApi unitApi;
    private final String unitUrl;

    public UnitServiceImpl(@Value("${retrofit.restServices.unit_url}") String unitUrl, Retrofit retrofit) {
        this.unitUrl = unitUrl;
        this.unitApi = retrofit.create(UnitApi.class);
    }

    @Override
    public List<UnitDto> getAll() {
        List<UnitDto> unitDtoList = Collections.emptyList();
        Call<List<UnitDto>> unitGetAllCall = unitApi.getAll(unitUrl);
        try {
            Response<List<UnitDto>> response = unitGetAllCall.execute();
            if (response.isSuccessful()) {
                unitDtoList = response.body();
                log.info("Успешно выполнен запрос на получение списка UnitDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка UnitDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка UnitDto", e);
        }
        return unitDtoList;
    }

    @Override
    public UnitDto getById(Long id) {
        UnitDto unitDto = null;
        Call<UnitDto> call = unitApi.getById(unitUrl, id);
        try {
            Response<UnitDto> execute = call.execute();
            if (execute.isSuccessful()) {
                unitDto = execute.body();
                log.info("Успешно выполнен запрос на получение UnitDto по id: {}", id);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение UnitDto по id: {}", execute.code(), id);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение UnitDto по id", e);
        }
        return unitDto;
    }

    @Override
    public void create(UnitDto dto) {
        Call<Void> call = unitApi.create(unitUrl, dto);
        try {
            Response<Void> response = call.execute();

            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание UnitDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создание UnitDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создание UnitDto", e);
        }
    }

    @Override
    public void update(UnitDto dto) {
        Call<Void> call = unitApi.update(unitUrl, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на изменение UnitDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на изменение UnitDto",response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на изменение UnitDto", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = unitApi.deleteById(unitUrl, id);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление UnitDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление UnitDto по id: {}",response.code(), id);
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление UnitDto по id", e);
        }
    }
}
