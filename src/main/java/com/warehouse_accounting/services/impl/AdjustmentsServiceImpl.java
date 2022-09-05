package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.AdjustmentsDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.AdjustmentsService;
import com.warehouse_accounting.services.interfaces.api.AdjustmentsApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class AdjustmentsServiceImpl implements AdjustmentsService {

    private final AdjustmentsApi api;
    private final String url;

    public AdjustmentsServiceImpl(@Value("${retrofit.restServices.adjustments_url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(AdjustmentsApi.class);
    }

    @Override
    public List<AdjustmentsDto> getAll() {
        Call<List<AdjustmentsDto>> call = api.getAll(url);
        return new ServiceUtils<>(AdjustmentsDto.class).getAll(call);
    }

    @Override
    public AdjustmentsDto getById(Long id) {
        Call<AdjustmentsDto> call = api.getById(url, id);
        return new ServiceUtils<>(AdjustmentsDto.class).getById(call, id);
    }

    @Override
    public void create(AdjustmentsDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(AdjustmentsDto.class).create(call);
    }

    @Override
    public void update(AdjustmentsDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(AdjustmentsDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(AdjustmentsDto.class).delete(call);
    }
}
