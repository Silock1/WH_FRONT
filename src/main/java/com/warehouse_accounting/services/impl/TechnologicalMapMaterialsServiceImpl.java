package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.TechnologicalMapDto;
import com.warehouse_accounting.models.dto.TechnologicalMapGroupDto;
import com.warehouse_accounting.models.dto.TechnologicalMapMaterialDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.TechnologicalMapMaterialsService;
import com.warehouse_accounting.services.interfaces.api.TechnologicalMapApi;
import com.warehouse_accounting.services.interfaces.api.TechnologicalMapMaterialsApi;
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
public class TechnologicalMapMaterialsServiceImpl implements TechnologicalMapMaterialsService {
    private final TechnologicalMapMaterialsApi api;
    private final String url;

    public TechnologicalMapMaterialsServiceImpl(@Value("${retrofit.restServices.technological_map_material_url}")
                                                String url, Retrofit retrofit) {
        this.api = retrofit.create(TechnologicalMapMaterialsApi.class);
        this.url = url;
    }

    @Override
    public List<TechnologicalMapMaterialDto> getAll() {
        Call<List<TechnologicalMapMaterialDto>> call = api.getAll(url);
        return new ServiceUtils<>(TechnologicalMapMaterialDto.class).getAll(call);
    }

    @Override
    public TechnologicalMapMaterialDto getById(Long id) {
        Call<TechnologicalMapMaterialDto> call = api.getById(url, id);
        return new ServiceUtils<>(TechnologicalMapMaterialDto.class).getById(call, id);
    }

    @Override
    public void create(TechnologicalMapMaterialDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(TechnologicalMapMaterialDto.class).create(call);
    }

    @Override
    public void update(TechnologicalMapMaterialDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(TechnologicalMapMaterialDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(TechnologicalMapMaterialDto.class).delete(call);
    }
}
