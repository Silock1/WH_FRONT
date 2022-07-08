package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.RegionDto;
import com.warehouse_accounting.services.interfaces.RegionService;
import com.warehouse_accounting.services.interfaces.api.RegionApi;
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
public class RegionServiceImpl implements RegionService {

    private final RegionApi api;
    private final String url;

    public RegionServiceImpl(@Value("${retrofit.restServices.region_url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(RegionApi.class);
    }

    @Override
    public List<RegionDto> getAll() {
        List<RegionDto> dtoList = Collections.emptyList();
        Call<List<RegionDto>> apiAll = api.getAll(url);
        try {
            Response<List<RegionDto>> response = apiAll.execute();
            if (response.isSuccessful()) {
                dtoList = response.body();
                log.info("Успешно выполнен запрос на получение списка RegionDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка RegionDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка RegionDto", e);
        }
        return dtoList;
    }

    @Override
    public RegionDto getById(Long id) {
        RegionDto dto = null;
        Call<RegionDto> callSync = api.getById(url, id);
        try {
            Response<RegionDto> response = callSync.execute();
            if (response.isSuccessful()) {
                dto = response.body();
                log.info("Успешно выполнен запрос на получение RegionDto по id: {}", id);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение RegionDto по id {}", response.code(), id);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение RegionDto по id", e);
        }
        return dto;
    }

    @Override
    public RegionDto getByCode(String code) {
        RegionDto dto = null;
        Call<RegionDto> callSync = api.getByCode(url, code);
        try {
            Response<RegionDto> response = callSync.execute();
            if (response.isSuccessful()) {
                dto = response.body();
                log.info("Успешно выполнен запрос на получение RegionDto по code: {}", code);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение RegionDto по code {}", response.code(), code);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение RegionDto по code", e);
        }
        return dto;
    }
}