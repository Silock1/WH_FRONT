package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.LanguageDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.api.LanguageApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class LanguageService implements com.warehouse_accounting.services.interfaces.LanguageService {

    private final String url;
    private final LanguageApi api;

    public LanguageService(@Value("${retrofit.restServices.language_url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(LanguageApi.class);
    }

    @Override
    public List<LanguageDto> getAll() {
        Call<List<LanguageDto>> call = api.getAll(url);
        return new ServiceUtils<>(LanguageDto.class).getAll(call);
    }

    @Override
    public LanguageDto getById(Long id) {
        Call<LanguageDto> call = api.getById(url, id);
        return new ServiceUtils<>(LanguageDto.class).getById(call, id);
    }

    @Override
    public void create(LanguageDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(LanguageDto.class).create(call);
    }

    @Override
    public void update(LanguageDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(LanguageDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(LanguageDto.class).delete(call);
    }
}
