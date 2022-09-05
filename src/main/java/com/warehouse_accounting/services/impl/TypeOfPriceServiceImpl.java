package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.TypeOfPriceDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.TypeOfPriceService;
import com.warehouse_accounting.services.interfaces.api.TypeOfPriceApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class TypeOfPriceServiceImpl implements TypeOfPriceService {
    private final TypeOfPriceApi api;
    private final String url;

    public TypeOfPriceServiceImpl(@Value("${retrofit.restServices.type_of_price_url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(TypeOfPriceApi.class);
    }

    @Override
    public List<TypeOfPriceDto> getAll() {
        Call<List<TypeOfPriceDto>> call = api.getAll(url);
        return new ServiceUtils<>(TypeOfPriceDto.class).getAll(call);
    }

    @Override
    public TypeOfPriceDto getById(Long id) {
        Call<TypeOfPriceDto> call = api.getById(url, id);
        return new ServiceUtils<>(TypeOfPriceDto.class).getById(call, id);
    }

    @Override
    public void create(TypeOfPriceDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(TypeOfPriceDto.class).create(call);
    }

    @Override
    public void update(TypeOfPriceDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(TypeOfPriceDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(TypeOfPriceDto.class).delete(call);
    }
}
