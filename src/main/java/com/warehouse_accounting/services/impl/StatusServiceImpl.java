package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.StatusDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.StatusService;
import com.warehouse_accounting.services.interfaces.api.StatusApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;
@Service
public class StatusServiceImpl implements StatusService {

    private final StatusApi api;
    private final String url;

    public StatusServiceImpl(@Value("api/statuses") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(StatusApi.class);
    }

    @Override
    public List<StatusDto> getAllByNameOfClass(String nameOfClass) {
        Call<List<StatusDto>> call = api.getAllByNameOfClass(url, nameOfClass);
        return new ServiceUtils<>(StatusDto.class).getAll(call);
    }

    @Override
    public List<StatusDto> getAll() {
        Call<List<StatusDto>> call = api.getAll(url);
        return new ServiceUtils<>(StatusDto.class).getAll(call);
    }

    @Override
    public StatusDto getById(Long id) {
        Call<StatusDto> call = api.getById(url, id);
        return new ServiceUtils<>(StatusDto.class).getById(call, id);
    }

    @Override
    public void create(StatusDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(StatusDto.class).create(call);
    }

    @Override
    public void update(StatusDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(StatusDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(StatusDto.class).delete(call);
    }
}

