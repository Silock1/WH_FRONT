package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.AddressDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.AddressService;
import com.warehouse_accounting.services.interfaces.api.AddressApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class AddressServiceImpl implements AddressService {

    private final AddressApi api;
    private final String url;

    public AddressServiceImpl(@Value("${retrofit.restServices.address_url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(AddressApi.class);
    }

    @Override
    public List<AddressDto> getAll() {
        Call<List<AddressDto>> call = api.getAll(url);
        return new ServiceUtils<>(AddressDto.class).getAll(call);
    }

    @Override
    public AddressDto getById(Long id) {
        Call<AddressDto> call = api.getById(url, id);
        return new ServiceUtils<>(AddressDto.class).getById(call, id);
    }

    @Override
    public void create(AddressDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(AddressDto.class).create(call);
    }

    @Override
    public void update(AddressDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(AddressDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(AddressDto.class).delete(call);
    }

    @Override
    public AddressDto getByFullAddress(String fullAddress) {
        Call<AddressDto> call = api.getByFullAddress(url, fullAddress);
        return new ServiceUtils<>(AddressDto.class).getByFullAddress(call, fullAddress);
    }

}
