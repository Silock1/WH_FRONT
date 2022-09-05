package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.ReturnDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.ReturnService;
import com.warehouse_accounting.services.interfaces.api.ReturnApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class ReturnServiceImpl implements ReturnService {
    private final ReturnApi api;
    private final String url;

    public ReturnServiceImpl(@Value("${retrofit.restServices.return_url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(ReturnApi.class);
    }

    @Override
    public List<ReturnDto> getAll() {
        Call<List<ReturnDto>> call = api.getAll(url);
        return new ServiceUtils<>(ReturnDto.class).getAll(call);
    }

    @Override
    public ReturnDto getById(Long id) {
        Call<ReturnDto> call = api.getById(url, id);
        return new ServiceUtils<>(ReturnDto.class).getById(call, id);
    }

    @Override
    public void create(ReturnDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(ReturnDto.class).create(call);
    }

    @Override
    public void update(ReturnDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(ReturnDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(ReturnDto.class).delete(call);
    }
}

