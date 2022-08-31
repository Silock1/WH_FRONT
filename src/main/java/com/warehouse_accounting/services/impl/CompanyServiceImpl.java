package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.CompanyDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.CompanyService;
import com.warehouse_accounting.services.interfaces.api.CompanyApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class CompanyServiceImpl implements CompanyService {

    private final String url;

    private final CompanyApi api;

    public CompanyServiceImpl(@Value("${retrofit.restServices.company_url}") String url, Retrofit retrofit) {
        api = retrofit.create(CompanyApi.class);
        this.url = url;
    }

    @Override
    public List<CompanyDto> getAll() {
        Call<List<CompanyDto>> call = api.getAll(url);
        return new ServiceUtils<>(CompanyDto.class).getAll(call);
    }

    @Override
    public CompanyDto getById(long id) {
        Call<CompanyDto> call = api.getById(url, id);
        return new ServiceUtils<>(CompanyDto.class).getById(call, id);
    }

    @Override
    public void create(CompanyDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(CompanyDto.class).create(call);
    }

    @Override
    public void update(CompanyDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(CompanyDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(CompanyDto.class).delete(call);
    }
}