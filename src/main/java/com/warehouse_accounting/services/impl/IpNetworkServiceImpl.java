package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.IpNetworkDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.IpNetworkService;
import com.warehouse_accounting.services.interfaces.api.IpNetworkApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class IpNetworkServiceImpl implements IpNetworkService {

    private final String url;

    private final IpNetworkApi api;

    public IpNetworkServiceImpl(@Value("${retrofit.restServices.ip_network_url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(IpNetworkApi.class);
    }

    @Override
    public List<IpNetworkDto> getAll() {
        Call<List<IpNetworkDto>> call = api.getAll(url);
        return new ServiceUtils<>(IpNetworkDto.class).getAll(call);
    }

    @Override
    public IpNetworkDto getById(Long id) {
        Call<IpNetworkDto> call = api.getById(url, id);
        return new ServiceUtils<>(IpNetworkDto.class).getById(call, id);
    }

    @Override
    public void create(IpNetworkDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(IpNetworkDto.class).create(call);
    }

    @Override
    public void update(IpNetworkDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(IpNetworkDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(IpNetworkDto.class).delete(call);
    }

}
