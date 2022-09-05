package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.StartScreenDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.StartScreenService;
import com.warehouse_accounting.services.interfaces.api.StartScreenApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class StartScreenServiceImpl implements StartScreenService {

    private final String url;
    private final StartScreenApi api;

    public StartScreenServiceImpl(@Value("${retrofit.restServices.start_screen_url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(StartScreenApi.class);
    }

    @Override
    public List<StartScreenDto> getAll() {
        Call<List<StartScreenDto>> call = api.getAll(url);
        return new ServiceUtils<>(StartScreenDto.class).getAll(call);
    }

    @Override
    public StartScreenDto getById(Long id) {
        Call<StartScreenDto> call = api.getById(url, id);
        return new ServiceUtils<>(StartScreenDto.class).getById(call, id);
    }

    @Override
    public void create(StartScreenDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(StartScreenDto.class).create(call);
    }

    @Override
    public void update(StartScreenDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(StartScreenDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(StartScreenDto.class).delete(call);
    }
}
