package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.ShipmentsDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.ShipmentsService;
import com.warehouse_accounting.services.interfaces.api.ShipmentsApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class ShipmentsServiceImpl implements ShipmentsService {

    private final ShipmentsApi api;
    private final String url;

    public ShipmentsServiceImpl(Retrofit retrofit, @Value("${retrofit.restServices.shipments_url}") String url) {
        this.api = retrofit.create(ShipmentsApi.class);
        this.url = url;
    }

    @Override
    public List<ShipmentsDto> getAll() {
        Call<List<ShipmentsDto>> call = api.getAll(url);
        return new ServiceUtils<>(ShipmentsDto.class).getAll(call);
    }

    @Override
    public ShipmentsDto getById(Long id) {
        Call<ShipmentsDto> call = api.getById(url, id);
        return new ServiceUtils<>(ShipmentsDto.class).getById(call, id);
    }

    @Override
    public void create(ShipmentsDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(ShipmentsDto.class).create(call);
    }

    @Override
    public void update(ShipmentsDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(ShipmentsDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(ShipmentsDto.class).delete(call);
    }
}
