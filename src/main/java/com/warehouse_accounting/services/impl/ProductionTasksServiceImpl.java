package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.ProductionTasksDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.ProductionTasksService;
import com.warehouse_accounting.services.interfaces.api.ProductionTasksApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class ProductionTasksServiceImpl implements ProductionTasksService {

    private final ProductionTasksApi api;
    private final String url;

    public ProductionTasksServiceImpl(@Value("${retrofit.restServices.production_tasks_url}") String url, Retrofit retrofit) {
        this.api = retrofit.create(ProductionTasksApi.class);
        this.url = url;
    }

    @Override
    public List<ProductionTasksDto> getAll() {
        Call<List<ProductionTasksDto>> call = api.getAll(url);
        return new ServiceUtils<>(ProductionTasksDto.class).getAll(call);
    }

    @Override
    public ProductionTasksDto getById(Long id) {
        Call<ProductionTasksDto> call = api.getById(url, id);
        return new ServiceUtils<>(ProductionTasksDto.class).getById(call, id);
    }

    @Override
    public void create(ProductionTasksDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(ProductionTasksDto.class).create(call);
    }

    @Override
    public void update(ProductionTasksDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(ProductionTasksDto.class).update(call);
    }

    @Override
    public void delete(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(ProductionTasksDto.class).delete(call);
    }
}
