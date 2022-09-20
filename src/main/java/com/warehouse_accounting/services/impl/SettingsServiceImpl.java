package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.SettingsDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.SettingsService;
import com.warehouse_accounting.services.interfaces.api.SettingsApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class SettingsServiceImpl implements SettingsService {

    private final String url;
    private final SettingsApi api;

    public SettingsServiceImpl(@Value("${retrofit.restServices.settings_url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(SettingsApi.class);
    }

    @Override
    public List<SettingsDto> getAll() {
        Call<List<SettingsDto>> call = api.getAll(url);
        return new ServiceUtils<>(SettingsDto.class).getAll(call);
    }

    @Override
    public SettingsDto getById(Long id) {
        Call<SettingsDto> call = api.getById(url, id);
        return new ServiceUtils<>(SettingsDto.class).getById(call, id);
    }

    @Override
    public void create(SettingsDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(SettingsDto.class).create(call);
    }

    @Override
    public void update(SettingsDto dto) {
        System.out.println(dto);
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(SettingsDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(SettingsDto.class).delete(call);
    }
}
