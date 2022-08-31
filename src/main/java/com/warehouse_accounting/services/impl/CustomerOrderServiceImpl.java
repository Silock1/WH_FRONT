package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.CustomerOrderDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.CustomerOrderService;
import com.warehouse_accounting.services.interfaces.api.CustomerOrderApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class CustomerOrderServiceImpl implements CustomerOrderService {

    private final CustomerOrderApi api;
    private final String url;

    public CustomerOrderServiceImpl(Retrofit retrofit, @Value("${retrofit.restServices.customer_order_url}") String url) {
        this.api = retrofit.create(CustomerOrderApi.class);
        this.url = url;
    }

    @Override
    public List<CustomerOrderDto> getAll() {
        Call<List<CustomerOrderDto>> call = api.getAll(url);
        return new ServiceUtils<>(CustomerOrderDto.class).getAll(call);
    }

    @Override
    public CustomerOrderDto getById(Long id) {
        Call<CustomerOrderDto> call = api.getById(url, id);
        return new ServiceUtils<>(CustomerOrderDto.class).getById(call, id);
    }

    @Override
    public void create(CustomerOrderDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(CustomerOrderDto.class).create(call);
    }

    @Override
    public void update(CustomerOrderDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(CustomerOrderDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(CustomerOrderDto.class).delete(call);
    }
}
