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
            unitDtoList = unitGetAllCall.execute().body();
            log.info("Успешно выполнен запрос на получение списка UnitDto");
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка UnitDto");
        }
        return unitDtoList;
    }

    @Override
    public UnitDto getById(Long id) {
        UnitDto unitDto = null;
        Call<UnitDto> callSync = unitApi.getById(unitUrl, id);
        try {
            Response<UnitDto> response = callSync.execute();
            unitDto = response.body();
            log.info("Успешно выполнен запрос на получение UnitDto по id: {}", id);
        } catch (Exception ex) {
            log.error("Произошла ошибка при выполнении запроса на получение UnitDto по id: {}", id);
        }
        return unitDto;
    }

    @Override
    public void create(UnitDto dto) {
        Call<Void> call = unitApi.create(unitUrl, dto);
        try {
            call.execute();
            log.info("Успешно выполнен запрос на создание UnitDto");
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создании UnitDto");
        }
    }

    @Override
    public void update(UnitDto dto) {
        Call<Void> call = unitApi.update(unitUrl, dto);
        try {
            call.execute();
            log.info("Успешно выполнен запрос на изменении UnitDto");
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на изменении UnitDto");
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = unitApi.deleteById(unitUrl, id);
        try {
            call.execute();
            log.info("Успешно выполнен запрос на удаление UnitDto");
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление UnitDto по id: {}", id);
        }
    }
}
