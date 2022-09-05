package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.InternalOrderDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.InternalOrderService;
import com.warehouse_accounting.services.interfaces.api.InternalOrderApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class InternalOrderServiceImpl implements InternalOrderService {

    private final InternalOrderApi api;
    private final String url;

    public InternalOrderServiceImpl(@Value("${retrofit.restServices.internal_order-url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(InternalOrderApi.class);
    }

    @Override
    public List<InternalOrderDto> getAll() {
        Call<List<InternalOrderDto>> call = api.getAll(url);
        return new ServiceUtils<>(InternalOrderDto.class).getAll(call);
    }

    @Override
    public InternalOrderDto getById(Long id) {
        Call<InternalOrderDto> call = api.getById(url, id);
        return new ServiceUtils<>(InternalOrderDto.class).getById(call, id);
    }

    @Override
    public void create(InternalOrderDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(InternalOrderDto.class).create(call);
    }

    @Override
    public void update(InternalOrderDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(InternalOrderDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(InternalOrderDto.class).delete(call);
    }
}
