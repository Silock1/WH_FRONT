package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.LegalDetailDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.LegalDetailService;
import com.warehouse_accounting.services.interfaces.api.LegalDetailApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class LegalDetailServiceImpl implements LegalDetailService {

    private final LegalDetailApi api;
    private final String url;

    public LegalDetailServiceImpl(@Value("${retrofit.restServices.legal_detail_url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(LegalDetailApi.class);
    }

    @Override
    public List<LegalDetailDto> getAll() {
        Call<List<LegalDetailDto>> call = api.getAll(url);
        return new ServiceUtils<>(LegalDetailDto.class).getAll(call);
    }

    @Override
    public LegalDetailDto getById(Long id) {
        Call<LegalDetailDto> call = api.getById(url, id);
        return new ServiceUtils<>(LegalDetailDto.class).getById(call, id);
    }

    @Override
    public void create(LegalDetailDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(LegalDetailDto.class).create(call);
    }

    @Override
    public void update(LegalDetailDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(LegalDetailDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(LegalDetailDto.class).delete(call);
    }
}