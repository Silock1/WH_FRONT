package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.CityDto;
import com.warehouse_accounting.services.interfaces.CityService;
import com.warehouse_accounting.services.interfaces.api.CityApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Query;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Log4j2
@Service
public class CityServiceImpl implements CityService {

    private final CityApi api;
    private final String url;

    public CityServiceImpl(@Value("${retrofit.restServices.city_url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(CityApi.class);
    }

    @Override
    public List<CityDto> getAll(String regionCode) {
        List<CityDto> dtoList = Collections.emptyList();
        Call<List<CityDto>> apiAll = api.getAll(url, regionCode);
        try {
            Response<List<CityDto>> response = apiAll.execute();
            if (response.isSuccessful()) {
                dtoList = response.body();
                log.info("Успешно выполнен запрос на получение списка CityDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка CityDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка CityDto", e);
        }
        return dtoList;
    }

    @Override
    public List<CityDto> getSlice(int offset, int limit, String name, String regionCode) {
        List<CityDto> dtoList = Collections.emptyList();
        Call<List<CityDto>> request = api.getSlice(url, offset, limit, name, regionCode);
        try {
            Response<List<CityDto>> response = request.execute();
            if (response.isSuccessful()) {
                dtoList = response.body();
                log.info("Успешно выполнен запрос на получение slice CityDto по offset: {}, limit: {}, name: {}, code {}", offset, limit, name, regionCode);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение slice CityDto по offset: {}, limit: {}, name: {}, code {}", response.code(), offset, limit, name, regionCode);
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение slice CityDto по offset: {}, limit: {}, name: {}, code {}", offset, limit, name, regionCode, e);
        }
        return dtoList;
    }

    @Override
    public int getCount(String name, String regionCode) {
        int count = 0;
        Call<Integer> callSync = api.getCount(url, name, regionCode);
        try {
            Response<Integer> response = callSync.execute();
            if (response.isSuccessful()) {
                count = response.body();
                log.info("Успешно выполнен запрос на получение count City по name: {}, code {}", name, regionCode);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение count City по name {}, code {}", response.code(), name, regionCode);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение count City по name", e);
        }
        return count;
    }

    @Override
    public CityDto getById(Long id) {
        CityDto dto = null;
        Call<CityDto> callSync = api.getById(url, id);
        try {
            Response<CityDto> response = callSync.execute();
            if (response.isSuccessful()) {
                dto = response.body();
                log.info("Успешно выполнен запрос на получение CityDto по id: {}", id);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение CityDto по id {}", response.code(), id);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение CityDto по id", e);
        }
        return dto;
    }

    @Override
    public CityDto getByCode(String code) {
        CityDto dto = null;
        Call<CityDto> callSync = api.getByCode(url, code);
        try {
            Response<CityDto> response = callSync.execute();
            if (response.isSuccessful()) {
                dto = response.body();
                log.info("Успешно выполнен запрос на получение CityDto по code: {}", code);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение CityDto по code {}", response.code(), code);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение CityDto по code", e);
        }
        return dto;
    }
}
