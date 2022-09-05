package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.ApplicationDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.ApplicationService;
import com.warehouse_accounting.services.interfaces.api.ApplicationApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final String url;
    private final ApplicationApi api;

    public ApplicationServiceImpl(@Value("${retrofit.restServices.application_url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(ApplicationApi.class);
    }

    @Override
    public List<ApplicationDto> getAll() {
        Call<List<ApplicationDto>> call = api.getAll(url);
        return new ServiceUtils<>(ApplicationDto.class).getAll(call);
    }

    @Override
    public ApplicationDto getById(Long id) {
        Call<ApplicationDto> call = api.getById(url, id);
        return new ServiceUtils<>(ApplicationDto.class).getById(call, id);
    }

    @Override
    public void create(ApplicationDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(ApplicationDto.class).create(call);
    }

    @Override
    public void update(ApplicationDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(ApplicationDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.delete(url, id);
        new ServiceUtils<>(ApplicationDto.class).delete(call);
    }
}
