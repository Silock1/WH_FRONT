package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.CurrencyDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.CurrencyService;
import com.warehouse_accounting.services.interfaces.api.CurrencyApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyApi api;
    private final String url;

    public CurrencyServiceImpl(Retrofit retrofit, @Value("${retrofit.restServices.currency_url}") String url) {
        this.api = retrofit.create(CurrencyApi.class);
        this.url = url;
    }


    @Override
    public List<CurrencyDto> getAll() {
        Call<List<CurrencyDto>> call = api.getAll(url);
        return new ServiceUtils<>(CurrencyDto.class).getAll(call);
    }

    @Override
    public CurrencyDto getById(Long id) {
        Call<CurrencyDto> call = api.getById(url, id);
        return new ServiceUtils<>(CurrencyDto.class).getById(call, id);
    }

    @Override
    public void create(CurrencyDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(CurrencyDto.class).create(call);
    }

    @Override
    public void update(CurrencyDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(CurrencyDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(CurrencyDto.class).delete(call);
    }
}
