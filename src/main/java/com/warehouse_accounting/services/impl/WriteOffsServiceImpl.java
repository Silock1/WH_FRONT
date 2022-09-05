package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.WriteOffsDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.WriteOffsService;
import com.warehouse_accounting.services.interfaces.api.WriteOffsApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class WriteOffsServiceImpl implements WriteOffsService {

    private final WriteOffsApi api;
    private final String url;

    public WriteOffsServiceImpl(@Value("${retrofit.restServices.writeOffs_url}") String url, Retrofit retrofit) {
        this.api = retrofit.create(WriteOffsApi.class);
        this.url = url;
    }

    @Override
    public List<WriteOffsDto> getAll() {
        Call<List<WriteOffsDto>> call = api.getAll(url);
        return new ServiceUtils<>(WriteOffsDto.class).getAll(call);
    }

    @Override
    public WriteOffsDto getById(Long id) {
        Call<WriteOffsDto> call = api.getById(url, id);
        return new ServiceUtils<>(WriteOffsDto.class).getById(call, id);
    }

    @Override
    public void create(WriteOffsDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(WriteOffsDto.class).create(call);
    }

    @Override
    public void update(WriteOffsDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(WriteOffsDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(WriteOffsDto.class).delete(call);
    }
}
