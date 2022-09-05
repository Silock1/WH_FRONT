package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.InventoryDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.InventoryService;
import com.warehouse_accounting.services.interfaces.api.InventoryApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class InventoryServiceImpl implements InventoryService {
    private final InventoryApi api;
    private final String url;

    public InventoryServiceImpl(@Value("api/inventory") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(InventoryApi.class);
    }

    @Override
    public List<InventoryDto> getAll() {
        Call<List<InventoryDto>> call = api.getAll(url);
        return new ServiceUtils<>(InventoryDto.class).getAll(call);
    }

    @Override
    public InventoryDto getById(Long id) {
        Call<InventoryDto> call = api.getById(url, id);
        return new ServiceUtils<>(InventoryDto.class).getById(call, id);
    }

    @Override
    public void create(InventoryDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(InventoryDto.class).create(call);
    }

    @Override
    public void update(InventoryDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(InventoryDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(InventoryDto.class).delete(call);
    }

}