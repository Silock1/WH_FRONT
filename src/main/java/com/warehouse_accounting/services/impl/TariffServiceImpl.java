package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.TariffDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.api.TariffApi;
import com.warehouse_accounting.services.interfaces.TariffService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class TariffServiceImpl implements TariffService {
    private final TariffApi api;
    private final String url;

    public TariffServiceImpl(Retrofit retrofit, @Value("${retrofit.restServices.tariff_url") String url) {
        this.api = retrofit.create(TariffApi.class);
        this.url = url;
    }


    @Override
    public List<TariffDto> getAll() {
        Call<List<TariffDto>> call = api.getAll(url);
        return new ServiceUtils<>(TariffDto.class).getAll(call);
    }

    @Override
    public TariffDto getById(Long id) {
        Call<TariffDto> call = api.getById(url, id);
        return new ServiceUtils<>(TariffDto.class).getById(call, id);
    }

    @Override
    public void create(TariffDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(TariffDto.class).create(call);
    }

    @Override
    public void update(TariffDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(TariffDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(TariffDto.class).delete(call);
    }
}
