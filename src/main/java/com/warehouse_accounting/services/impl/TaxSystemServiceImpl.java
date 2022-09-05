package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.TaxSystemDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.TaxSystemService;
import com.warehouse_accounting.services.interfaces.api.TaxSystemApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class TaxSystemServiceImpl implements TaxSystemService {

    private final TaxSystemApi api;
    private final String url;

    public TaxSystemServiceImpl(Retrofit retrofit, @Value("${retrofit.restServices.tax_system_url}") String url) {
        this.api = retrofit.create(TaxSystemApi.class);
        this.url = url;
    }

    @Override
    public List<TaxSystemDto> getAll() {
        Call<List<TaxSystemDto>> call = api.getAll(url);
        return new ServiceUtils<>(TaxSystemDto.class).getAll(call);
    }

    @Override
    public TaxSystemDto getById(Long id) {
        Call<TaxSystemDto> call = api.getById(url, id);
        return new ServiceUtils<>(TaxSystemDto.class).getById(call, id);
    }

    @Override
    public void create(TaxSystemDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(TaxSystemDto.class).create(call);
    }

    @Override
    public void update(TaxSystemDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(TaxSystemDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(TaxSystemDto.class).delete(call);
    }
}
