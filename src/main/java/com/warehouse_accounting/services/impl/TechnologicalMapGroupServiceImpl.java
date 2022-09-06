package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.ProductionStageDto;
import com.warehouse_accounting.models.dto.TechnologicalMapGroupDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.TechnologicalMapGroupService;
import com.warehouse_accounting.services.interfaces.api.TechnologicalMapGroupApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
@Log4j2
public class TechnologicalMapGroupServiceImpl implements TechnologicalMapGroupService {
    private final TechnologicalMapGroupApi api;
    private final String url;

    public TechnologicalMapGroupServiceImpl(@Value("${retrofit.restServices.technological_map_group_url}")
                                            String url, Retrofit retrofit) {
        this.api = retrofit.create(TechnologicalMapGroupApi.class);
        this.url = url;
    }

    @Override
    public List<TechnologicalMapGroupDto> getAll() {
        Call<List<TechnologicalMapGroupDto>> call = api.getAll(url);
        return new ServiceUtils<>(TechnologicalMapGroupDto.class).getAll(call);
    }

    @Override
    public TechnologicalMapGroupDto getById(Long id) {
        Call<TechnologicalMapGroupDto> call = api.getById(url, id);
        return new ServiceUtils<>(TechnologicalMapGroupDto.class).getById(call, id);
    }

    @Override
    public void create(TechnologicalMapGroupDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(TechnologicalMapGroupDto.class).create(call);
    }

    @Override
    public void update(TechnologicalMapGroupDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(TechnologicalMapGroupDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(TechnologicalMapGroupDto.class).delete(call);
    }
}
