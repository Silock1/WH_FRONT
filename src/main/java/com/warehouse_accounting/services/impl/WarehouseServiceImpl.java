package com.warehouse_accounting.services.impl;


import com.warehouse_accounting.models.dto.WarehouseDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.WarehouseService;
import com.warehouse_accounting.services.interfaces.api.WarehouseApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class WarehouseServiceImpl implements WarehouseService {
    private final WarehouseApi api;
    private final String url;

    public WarehouseServiceImpl(@Value("${retrofit.restServices.warehouse_url}") String url, Retrofit retrofit) {
        this.api = retrofit.create(WarehouseApi.class);
        this.url = url;
    }

    public List<WarehouseDto> getAll() {
        Call<List<WarehouseDto>> call = api.getAll(url);
        return new ServiceUtils<>(WarehouseDto.class).getAll(call);
    }

    @Override
    public WarehouseDto getById(Long id) {
        Call<WarehouseDto> call = api.getById(url, id);
        return new ServiceUtils<>(WarehouseDto.class).getById(call, id);
    }

    @Override
    public void create(WarehouseDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(WarehouseDto.class).create(call);
    }

    @Override
    public void update(WarehouseDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(WarehouseDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(WarehouseDto.class).delete(call);
    }
}
