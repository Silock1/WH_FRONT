package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.StreetDto;
import com.warehouse_accounting.services.ServiceUtils;
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
        Call<List<StreetDto>> call = api.getAll(url, regionCityCode);
        return new ServiceUtils<>(StreetDto.class).getAll(call);
    }

    @Override
    public List<StreetDto> getSlice(int offset, int limit, String name, String regionCityCode) {
        List<StreetDto> dtoList = Collections.emptyList();
        Call<List<StreetDto>> request = api.getSlice(url, offset, limit, name, regionCityCode);
        try {
            Response<List<StreetDto>> response = request.execute();
            if (response.isSuccessful()) {
                dtoList = response.body();
                log.info("Успешно выполнен запрос на получение slice StreetDto, по offset: {}, limit: {}, name: {}, code {}", offset, limit, name, regionCityCode);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение slice StreetDto по offset: {}, limit: {}, name: {}, code {}", response.code(), offset, limit, name, regionCityCode);
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение slice StreetDto по offset: {}, limit: {}, name: {}, code {}", offset, limit, name, regionCityCode, e);
        }
        return dtoList;
    }

    @Override
    public int getCount(String name, String regionCityCode) {
        int count = 0;
        Call<Integer> callSync = api.getCount(url, name, regionCityCode);
        try {
            Response<Integer> response = callSync.execute();
            if (response.isSuccessful()) {
                count = response.body();
                log.info("Успешно выполнен запрос на получение count Street по name: {}, code {}", name, regionCityCode);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение count Street по name {}, code {}", response.code(), name, regionCityCode);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение count Street по name", e);
        }
        return count;
    }

    @Override
    public StreetDto getById(Long id) {
        Call<StreetDto> call = api.getById(url, id);
        return new ServiceUtils<>(StreetDto.class).getById(call, id);
    }
}
