package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.BuildingDto;
import com.warehouse_accounting.services.ServiceUtils;
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
        Call<List<BuildingDto>> call = api.getAll(url, streetCode);
        return new ServiceUtils<>(BuildingDto.class).getAll(call);
    }

    @Override
    public List<BuildingDto> getSlice(int offset, int limit, String name, String regionCityStreetCode) {
        List<BuildingDto> dtoList = Collections.emptyList();
        Call<List<BuildingDto>> request = api.getSlice(url, offset, limit, name, regionCityStreetCode);
        try {
            Response<List<BuildingDto>> response = request.execute();
            if (response.isSuccessful()) {
                dtoList = response.body();
                log.info("Успешно выполнен запрос на получение slice BuildingDto по offset: {}, limit: {}, name: {}, code {}", offset, limit, name, regionCityStreetCode);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение slice BuildingDto по offset: {}, limit: {}, name: {}, code {}", response.code(), offset, limit, name, regionCityStreetCode);
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение slice BuildingDto по offset: {}, limit: {}, name: {}, code {}", offset, limit, name, regionCityStreetCode, e);
        }
        return dtoList;
    }

    @Override
    public int getCount(String name, String regionCityStreetCode) {
        int count = 0;
        Call<Integer> callSync = api.getCount(url, name, regionCityStreetCode);
        try {
            Response<Integer> response = callSync.execute();
            if (response.isSuccessful()) {
                count = response.body();
                log.info("Успешно выполнен запрос на получение count Building по name: {}, code {}", name, regionCityStreetCode);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение count Building по name {}, code {}", response.code(), name, regionCityStreetCode);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение count Building по name", e);
        }
        return count;
    }

    @Override
    public BuildingDto getById(Long id) {
        Call<BuildingDto> call = api.getById(url, id);
        return new ServiceUtils<>(BuildingDto.class).getById(call, id);
    }
}
