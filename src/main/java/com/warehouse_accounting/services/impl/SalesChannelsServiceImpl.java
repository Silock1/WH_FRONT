package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.SalesChannelDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.SalesChannelsService;
import com.warehouse_accounting.services.interfaces.api.SalesChannelsApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class SalesChannelsServiceImpl implements SalesChannelsService {

    private final String url;
    private final SalesChannelsApi api;

    public SalesChannelsServiceImpl(@Value("${retrofit.restServices.sales_channels_url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(SalesChannelsApi.class);
    }

    @Override
    public List<SalesChannelDto> getAll() {
        Call<List<SalesChannelDto>> call = api.getAll(url);
        return new ServiceUtils<>(SalesChannelDto.class).getAll(call);
    }

    @Override
    public SalesChannelDto getById(Long id) {
        Call<SalesChannelDto> call = api.getById(url, id);
        return new ServiceUtils<>(SalesChannelDto.class).getById(call, id);
    }

    @Override
    public void create(SalesChannelDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(SalesChannelDto.class).create(call);
    }

    @Override
    public void update(SalesChannelDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(SalesChannelDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(SalesChannelDto.class).delete(call);
    }
}

