package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.TechnologicalMapDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.TechnologicalMapService;
import com.warehouse_accounting.services.interfaces.api.TechnologicalMapApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Service
@Log4j2
public class TechnologicalMapServiceImpl implements TechnologicalMapService {

    private final TechnologicalMapApi api;
    private final String url;

    public TechnologicalMapServiceImpl(@Value("${retrofit.restServices.technological_map_url}")
                                       String url, Retrofit retrofit) {
        this.api = retrofit.create(TechnologicalMapApi.class);
        this.url = url;
    }

    @Override
    public List<TechnologicalMapDto> getAll() {
        Call<List<TechnologicalMapDto>> call = api.getAll(url);
        return new ServiceUtils<>(TechnologicalMapDto.class).getAll(call);
    }

    @Override
    public TechnologicalMapDto getById(Long id) {
        Call<TechnologicalMapDto> call = api.getById(url, id);
        return new ServiceUtils<>(TechnologicalMapDto.class).getById(call, id);
    }

    @Override
    public void create(TechnologicalMapDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(TechnologicalMapDto.class).create(call);
    }

    @Override
    public void update(TechnologicalMapDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(TechnologicalMapDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(TechnologicalMapDto.class).delete(call);
    }
}
