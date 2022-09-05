package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.ProductionProcessTechnologyDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.ProductionProcessTechnologyService;
import com.warehouse_accounting.services.interfaces.api.ProductionProcessTechnologyApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class ProductionProcessTechnologyServiceImpl implements ProductionProcessTechnologyService {

    private final ProductionProcessTechnologyApi api;
    private final String url;

    public ProductionProcessTechnologyServiceImpl(@Value("api/production_process_technology") String url, Retrofit retrofit) {
        this.api = retrofit.create(ProductionProcessTechnologyApi.class);
        this.url = url;
    }

    @Override
    public List<ProductionProcessTechnologyDto> getAll() {
        Call<List<ProductionProcessTechnologyDto>> call = api.getAll(url);
        return new ServiceUtils<>(ProductionProcessTechnologyDto.class).getAll(call);
    }

    @Override
    public void create(ProductionProcessTechnologyDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(ProductionProcessTechnologyDto.class).create(call);
    }

    @Override
    public void update(ProductionProcessTechnologyDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(ProductionProcessTechnologyDto.class).update(call);
    }

    @Override
    public void delete(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(ProductionProcessTechnologyDto.class).delete(call);
    }
}
