package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.BuildingDto;
import com.warehouse_accounting.services.interfaces.BuildingService;
import com.warehouse_accounting.services.interfaces.api.BuildingApi;
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
public class BuildingServiceImpl implements BuildingService {

    private final BuildingApi api;
    private final String url;

    public BuildingServiceImpl(@Value("${retrofit.restServices.building_url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(BuildingApi.class);
    }

    @Override
    public List<BuildingDto> getAll(String streetCode) {
        List<BuildingDto> dtoList = Collections.emptyList();
        Call<List<BuildingDto>> apiAll = api.getAll(url, streetCode);
        try {
            Response<List<BuildingDto>> response = apiAll.execute();
            if (response.isSuccessful()) {
                dtoList = response.body();
                log.info("Успешно выполнен запрос на получение списка BuildingDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка BuildingDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка BuildingDto", e);
        }
        return dtoList;
    }

    @Override
    public BuildingDto getById(Long id) {
        BuildingDto dto = null;
        Call<BuildingDto> callSync = api.getById(url, id);
        try {
            Response<BuildingDto> response = callSync.execute();
            if (response.isSuccessful()) {
                dto = response.body();
                log.info("Успешно выполнен запрос на получение BuildingDto по id: {}", id);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение BuildingDto по id {}", response.code(), id);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение BuildingDto по id", e);
        }
        return dto;
    }
}
