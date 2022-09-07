package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.ProductionOrderDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.ProductionOrderService;
import com.warehouse_accounting.services.interfaces.api.ProductionOrderApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Service
@Log4j2
public class ProductionOrderServiceImpl implements ProductionOrderService {

    private final ProductionOrderApi api;
    private final String url;

    public ProductionOrderServiceImpl(Retrofit retrofit, @Value("api/production_order") String url) {
        this.api = retrofit.create(ProductionOrderApi.class);
        this.url = url;
    }

    @Override
    public List<ProductionOrderDto> getAll() {
        Call<List<ProductionOrderDto>> call = api.getAll(url);
        return new ServiceUtils<>(ProductionOrderDto.class).getAll(call);
    }


    @Override
    public ProductionOrderDto getById(Long id) {
        Call<ProductionOrderDto> call = api.getById(url, id);
        return new ServiceUtils<>(ProductionOrderDto.class).getById(call, id);
    }

    @Override
    public void create(ProductionOrderDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(ProductionOrderDto.class).create(call);
    }

    @Override
    public void update(ProductionOrderDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(ProductionOrderDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(ProductionOrderDto.class).delete(call);
    }
}
