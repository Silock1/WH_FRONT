package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.CityDto;
import com.warehouse_accounting.models.dto.StreetDto;
import com.warehouse_accounting.services.interfaces.StreetService;
import com.warehouse_accounting.services.interfaces.api.StreetApi;
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
public class StreetServiceImpl implements StreetService {

    private final StreetApi api;
    private final String url;

    public StreetServiceImpl(@Value("${retrofit.restServices.street_url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(StreetApi.class);
    }

    @Override
    public List<StreetDto> getAll(String regionCityCode) {
        List<StreetDto> dtoList = Collections.emptyList();
        Call<List<StreetDto>> apiAll = api.getAll(url, regionCityCode);
        try {
            Response<List<StreetDto>> response = apiAll.execute();
            if (response.isSuccessful()) {
                dtoList = response.body();
                log.info("Успешно выполнен запрос на получение списка StreetDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка StreetDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка StreetDto", e);
        }
        return dtoList;
    }

    @Override
    public List<StreetDto> getSlice(int offset, int limit, String name) {
        List<StreetDto> dtoList = Collections.emptyList();
        Call<List<StreetDto>> request = api.getSlice(url, offset, limit, name);
        try {
            Response<List<StreetDto>> response = request.execute();
            if (response.isSuccessful()) {
                dtoList = response.body();
                log.info("Успешно выполнен запрос на получение slice StreetDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение slice StreetDto по offset: {}, limit: {}, name: {}", response.code(), offset, limit, name);
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение slice StreetDto по offset: {}, limit: {}, name: {}", offset, limit, name, e);
        }
        return dtoList;
    }

    @Override
    public int getCount(String name) {
        int count = 0;
        Call<Integer> callSync = api.getCount(url, name);
        try {
            Response<Integer> response = callSync.execute();
            if (response.isSuccessful()) {
                count = response.body();
                log.info("Успешно выполнен запрос на получение count Street по name: {}", name);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение count Street по name {}", response.code(), name);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение count Street по name", e);
        }
        return count;
    }

    @Override
    public StreetDto getById(Long id) {
        StreetDto dto = null;
        Call<StreetDto> callSync = api.getById(url, id);
        try {
            Response<StreetDto> response = callSync.execute();
            if (response.isSuccessful()) {
                dto = response.body();
                log.info("Успешно выполнен запрос на получение StreetDto по id: {}", id);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение StreetDto по id {}", response.code(), id);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение StreetDto по id", e);
        }
        return dto;
    }
}
