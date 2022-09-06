package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.PositionDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.PositionService;
import com.warehouse_accounting.services.interfaces.api.PositionApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class PositionServiceImpl implements PositionService {

    private final PositionApi api;
    private final String url;

    public PositionServiceImpl(Retrofit retrofit, @Value("${retrofit.restServices.position_url}") String url) {
        this.api = retrofit.create(PositionApi.class);
        this.url = url;
    }

    @Override
    public List<PositionDto> getAll() {
        Call<List<PositionDto>> call = api.getAll(url);
        return new ServiceUtils<>(PositionDto.class).getAll(call);
    }

    @Override
    public PositionDto getById(Long id) {
        Call<PositionDto> call = api.getById(url, id);
        return new ServiceUtils<>(PositionDto.class).getById(call, id);
    }

    @Override
    public void create(PositionDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(PositionDto.class).create(call);
    }

    @Override
    public void update(PositionDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(PositionDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(PositionDto.class).delete(call);
    }
}
