package com.warehouse_accounting.services.impl;


import com.warehouse_accounting.models.dto.DiscountDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.DiscountService;
import com.warehouse_accounting.services.interfaces.api.DiscountApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class DiscountServiceImpl implements DiscountService {

    private final DiscountApi api;
    private final String url;

    public DiscountServiceImpl(@Value("${retrofit.restServices.discount_url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(DiscountApi.class);
    }

    @Override
    public List<DiscountDto> getAll() {
        Call<List<DiscountDto>> call = api.getAll(url);
        return new ServiceUtils<>(DiscountDto.class).getAll(call);
    }

    @Override
    public DiscountDto getById(Long id) {
        Call<DiscountDto> call = api.getById(url, id);
        return new ServiceUtils<>(DiscountDto.class).getById(call, id);
    }

    @Override
    public void create(DiscountDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(DiscountDto.class).create(call);
    }

    @Override
    public void update(DiscountDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(DiscountDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(DiscountDto.class).delete(call);
    }
}
