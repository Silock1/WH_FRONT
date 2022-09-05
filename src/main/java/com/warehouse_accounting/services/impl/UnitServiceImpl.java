package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.UnitDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.UnitService;
import com.warehouse_accounting.services.interfaces.api.UnitApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class UnitServiceImpl implements UnitService {

    private final UnitApi api;
    private final String url;

    public UnitServiceImpl(@Value("${retrofit.restServices.unit_url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(UnitApi.class);
    }

    @Override
    public List<UnitDto> getAll() {
        Call<List<UnitDto>> call = api.getAll(url);
        return new ServiceUtils<>(UnitDto.class).getAll(call);
    }

    @Override
    public UnitDto getById(Long id) {
        Call<UnitDto> call = api.getById(url, id);
        return new ServiceUtils<>(UnitDto.class).getById(call, id);
    }

    @Override
    public void create(UnitDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(UnitDto.class).create(call);
    }

    @Override
    public void update(UnitDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(UnitDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(UnitDto.class).delete(call);
    }
}
