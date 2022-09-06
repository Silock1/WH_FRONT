package com.warehouse_accounting.services.impl;


import com.warehouse_accounting.models.dto.SubscriptionDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.SubscriptionService;
import com.warehouse_accounting.services.interfaces.api.SubscriptionApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionApi api;
    private final String url;

    public SubscriptionServiceImpl(@Value("${retrofit.restServices.subscription_url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(SubscriptionApi.class);
    }

    @Override
    public List<SubscriptionDto> getAll() {
        Call<List<SubscriptionDto>> call = api.getAll(url);
        return new ServiceUtils<>(SubscriptionDto.class).getAll(call);
    }

    @Override
    public SubscriptionDto getById(Long id) {
        Call<SubscriptionDto> call = api.getById(url, id);
        return new ServiceUtils<>(SubscriptionDto.class).getById(call, id);
    }

    @Override
    public void create(SubscriptionDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(SubscriptionDto.class).create(call);
    }

    @Override
    public void update(SubscriptionDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(SubscriptionDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(SubscriptionDto.class).delete(call);
    }
}
