package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.IpAddressDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.IpAddressService;
import com.warehouse_accounting.services.interfaces.api.IpAddressApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class IpAddressServiceImpl implements IpAddressService {

    private final String url;

    private final IpAddressApi api;

    public IpAddressServiceImpl(@Value("${retrofit.restServices.ip_address_url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(IpAddressApi.class);
    }

    @Override
    public List<IpAddressDto> getAll() {
        Call<List<IpAddressDto>> call = api.getAll(url);
        return new ServiceUtils<>(IpAddressDto.class).getAll(call);
    }

    @Override
    public IpAddressDto getById(Long id) {
        Call<IpAddressDto> call = api.getById(url, id);
        return new ServiceUtils<>(IpAddressDto.class).getById(call, id);
    }

    @Override
    public void create(IpAddressDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(IpAddressDto.class).create(call);
    }

    @Override
    public void update(IpAddressDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(IpAddressDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(IpAddressDto.class).delete(call);
    }
}
