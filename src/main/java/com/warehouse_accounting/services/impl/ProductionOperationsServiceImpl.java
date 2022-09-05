package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.TechnologicalOperationDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.ProductionOperationsService;
import com.warehouse_accounting.services.interfaces.api.ProductionOperationsApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Service
@Log4j2
public class ProductionOperationsServiceImpl implements ProductionOperationsService {
    private ProductionOperationsApi api;
    private String url;

    public ProductionOperationsServiceImpl(Retrofit retrofit, @Value("api/technological_operation") String url) {
        this.api = retrofit.create(ProductionOperationsApi.class);
        this.url = url;
    }

    @Override
    public List<TechnologicalOperationDto> getAll() {
        Call <List<TechnologicalOperationDto>> call = api.getAll(url);
        return new ServiceUtils<>(TechnologicalOperationDto.class).getAll(call);
    }

    @Override
    public TechnologicalOperationDto getById(Long id) {
        Call <TechnologicalOperationDto> call = api.getById(url, id);
        return new ServiceUtils<>(TechnologicalOperationDto.class).getById(call, id);
    }

    @Override
    public void create(TechnologicalOperationDto dto) {
        Call <Void> call = api.create(url, dto);
        new ServiceUtils<>(TechnologicalOperationDto.class).create(call);
    }

    @Override
    public void update(TechnologicalOperationDto dto) {
        Call <Void> call = api.update(url, dto);
        new ServiceUtils<>(TechnologicalOperationDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call <Void> call = api.deleteById(url, id);
        new ServiceUtils<>(TechnologicalOperationDto.class).delete(call);
    }
}
