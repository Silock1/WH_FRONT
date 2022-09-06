package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.ScriptDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.ScriptService;
import com.warehouse_accounting.services.interfaces.api.ScriptApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class ScriptServiceImpl implements ScriptService {

    private final ScriptApi api;
    private final String url;

    public ScriptServiceImpl(@Value("${retrofit.restServices.scripts_url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(ScriptApi.class);
    }

    @Override
    public List<ScriptDto> getAll() {
        Call<List<ScriptDto>> call = api.getAll(url);
        return new ServiceUtils<>(ScriptDto.class).getAll(call);
    }

    @Override
    public ScriptDto getById(Long id) {
        Call<ScriptDto> call = api.getById(url, id);
        return new ServiceUtils<>(ScriptDto.class).getById(call, id);
    }

    @Override
    public void create(ScriptDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(ScriptDto.class).create(call);
    }

    @Override
    public void update(ScriptDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(ScriptDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(ScriptDto.class).delete(call);
    }
}
