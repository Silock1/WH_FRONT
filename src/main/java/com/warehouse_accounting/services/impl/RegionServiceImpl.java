package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.RegionDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.RegionService;
import com.warehouse_accounting.services.interfaces.api.RegionApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

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
        Call<List<RegionDto>> call = api.getAll(url);
        return new ServiceUtils<>(RegionDto.class).getAll(call);
    }

    @Override
    public RegionDto getById(Long id) {
        Call<RegionDto> call = api.getById(url, id);
        return new ServiceUtils<>(RegionDto.class).getById(call, id);
    }

    @Override
    public RegionDto getByCode(String code) {
        Call<RegionDto> call = api.getByCode(url, code);
        return new ServiceUtils<>(RegionDto.class).getByCode(call, code);
    }
}
