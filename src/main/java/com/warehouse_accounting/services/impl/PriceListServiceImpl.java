package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.PriceListDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.PriceListService;
import com.warehouse_accounting.services.interfaces.api.PriceListApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class PriceListServiceImpl implements PriceListService {
    private final PriceListApi api;
    private final String url;

    public PriceListServiceImpl(@Value("${retrofit.restServices.priceList_url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(PriceListApi.class);
    }

    @Override
    public List<PriceListDto> getAll() {
        Call<List<PriceListDto>> call = api.getAll(url);
        return new ServiceUtils<>(PriceListDto.class).getAll(call);
    }

    @Override
    public PriceListDto getById(Long id) {
        Call<PriceListDto> call = api.getById(url, id);
        return new ServiceUtils<>(PriceListDto.class).getById(call, id);
    }

    @Override
    public void create(PriceListDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(PriceListDto.class).create(call);
    }

    @Override
    public void update(PriceListDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(PriceListDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(PriceListDto.class).delete(call);
    }
}

