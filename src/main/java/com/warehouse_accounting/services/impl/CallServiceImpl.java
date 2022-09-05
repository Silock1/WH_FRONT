package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.CallDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.CallService;
import com.warehouse_accounting.services.interfaces.api.CallApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class CallServiceImpl implements CallService {

    private final CallApi api;
    private final String url;

    public CallServiceImpl(@Value("${retrofit.restServices.call_url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(CallApi.class);
    }

    @Override
    public List<CallDto> getAll() {
        Call<List<CallDto>> call = api.getAll(url);
        return new ServiceUtils<>(CallDto.class).getAll(call);
    }

    @Override
    public CallDto getById(Long id) {
        Call<CallDto> call = api.getById(url, id);
        return new ServiceUtils<>(CallDto.class).getById(call, id);
    }

    @Override
    public void create(CallDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(CallDto.class).create(call);
    }

    @Override
    public void update(CallDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(CallDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(CallDto.class).delete(call);
    }
}
