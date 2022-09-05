package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.CityDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.CityService;
import com.warehouse_accounting.services.interfaces.api.CityApi;
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
public class CityServiceImpl implements CityService {

    private final CityApi api;
    private final String url;

    public CityServiceImpl(@Value("${retrofit.restServices.city_url}") String url, Retrofit retrofit) {

        this.url = url;
        this.api = retrofit.create(CityApi.class);
    }

    @Override
    public List<CityDto> getAll(String regionCode) {
        Call<List<CityDto>> call = api.getAll(url, regionCode);
        return new ServiceUtils<>(CityDto.class).getAll(call);
    }

    @Override
    public CityDto getById(Long id) {
        Call<CityDto> call = api.getById(url, id);
        return new ServiceUtils<>(CityDto.class).getById(call, id);
    }

    @Override
    public List<CityDto> getSlice(int offset, int limit, String name, String regionCode) {
        Call<List<CityDto>> call = api.getSlice(url, offset, limit, name, regionCode);
        return new ServiceUtils<>(CityDto.class).getSlice(call, offset, limit, name, regionCode);
    }

    @Override
    public int getCount(String name, String regionCode) {
        Call<Integer> call = api.getCount(url, name, regionCode);
        return new ServiceUtils<>(CityDto.class).getCount(call, name, regionCode);
    }

    @Override
    public CityDto getByCode(String code) {
        Call<CityDto> call = api.getByCode(url, code);
        return new ServiceUtils<>(CityDto.class).getByCode(call, code);
    }
}
