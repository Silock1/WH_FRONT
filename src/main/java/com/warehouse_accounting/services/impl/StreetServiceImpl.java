package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.StreetDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.StreetService;
import com.warehouse_accounting.services.interfaces.api.StreetApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

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
        Call<List<StreetDto>> call = api.getSlice(url, offset, limit, name, regionCityCode);
        return new ServiceUtils<>(StreetDto.class).getSlice(call, offset, limit, name, regionCityCode);
    }

    @Override
    public int getCount(String name, String regionCityCode) {
        Call<Integer> call = api.getCount(url, name, regionCityCode);
        return new ServiceUtils<>(StreetDto.class).getCount(call, name, regionCityCode);
    }

    @Override
    public StreetDto getById(Long id) {
        Call<StreetDto> call = api.getById(url, id);
        return new ServiceUtils<>(StreetDto.class).getById(call, id);
    }
}
