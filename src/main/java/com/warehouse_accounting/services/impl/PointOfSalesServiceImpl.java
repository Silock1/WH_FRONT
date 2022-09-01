package com.warehouse_accounting.services.impl;


import com.warehouse_accounting.models.dto.PointOfSalesDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.PointOfSalesService;
import com.warehouse_accounting.services.interfaces.api.RetailApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class PointOfSalesServiceImpl implements PointOfSalesService {

    private final RetailApi api;
    private final String url;

    public PointOfSalesServiceImpl(@Value("${retrofit.restServices.retail_url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(RetailApi.class);
    }

    @Override
    public List<PointOfSalesDto> getAll() {
        Call<List<PointOfSalesDto>> call = api.getAll(url);
        return new ServiceUtils<>(PointOfSalesDto.class).getAll(call);
    }

    @Override
    public PointOfSalesDto getById(Long id) {
        Call<PointOfSalesDto> call = api.getById(url, id);
        return new ServiceUtils<>(PointOfSalesDto.class).getById(call, id);
    }

    @Override
    public void create(PointOfSalesDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(PointOfSalesDto.class).create(call);
    }

    @Override
    public void update(PointOfSalesDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(PointOfSalesDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(PointOfSalesDto.class).delete(call);
    }
}
