package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.CountryDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.CountryService;
import com.warehouse_accounting.services.interfaces.api.CountryApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class CountryServiceImpl implements CountryService {

    private final CountryApi api;
    private final String url;

    public CountryServiceImpl(@Value("${retrofit.restServices.country_url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(CountryApi.class);
    }

    @Override
    public List<CountryDto> getAll() {
        Call<List<CountryDto>> call = api.getAll(url);
        return new ServiceUtils<>(CountryDto.class).getAll(call);
    }

    @Override
    public CountryDto getById(Long id) {
        Call<CountryDto> call = api.getById(url, id);
        return new ServiceUtils<>(CountryDto.class).getById(call, id);
    }

    @Override
    public void create(CountryDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(CountryDto.class).create(call);
    }

    @Override
    public void update(CountryDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(CountryDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(CountryDto.class).delete(call);
    }
}

