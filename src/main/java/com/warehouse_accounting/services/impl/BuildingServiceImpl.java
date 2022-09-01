package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.BuildingDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.BuildingService;
import com.warehouse_accounting.services.interfaces.api.BuildingApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

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
    public BuildingDto getById(Long id) {
        Call<BuildingDto> call = api.getById(url, id);
        return new ServiceUtils<>(BuildingDto.class).getById(call, id);
    }

    @Override
    public List<BuildingDto> getSlice(int offset, int limit, String name, String regionCityStreetCode) {
        Call<List<BuildingDto>> call = api.getSlice(url, offset, limit, name, regionCityStreetCode);
        return new ServiceUtils<>(BuildingDto.class).getSlice(call, offset, limit, name, regionCityStreetCode);
    }

    @Override
    public int getCount(String name, String regionCityStreetCode) {
        Call<Integer> call = api.getCount(url, name, regionCityStreetCode);
        return new ServiceUtils<>(BuildingDto.class).getCount(call, name, regionCityStreetCode);
    }
}
