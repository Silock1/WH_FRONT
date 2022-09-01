package com.warehouse_accounting.services.impl;


import com.warehouse_accounting.models.dto.UnitsOfMeasureDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.UnitsOfMeasureService;
import com.warehouse_accounting.services.interfaces.api.UnitsOfMeasureApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class UnitsOfMeasureServiceImpl implements UnitsOfMeasureService {

    private final UnitsOfMeasureApi api;
    private final String url;

    public UnitsOfMeasureServiceImpl(@Value("${retrofit.restServices.units_of_measure_url}") String url, Retrofit retrofit) {
        this.api = retrofit.create(UnitsOfMeasureApi.class);
        this.url = url;
    }

    @Override
    public List<UnitsOfMeasureDto> getAll() {
        Call<List<UnitsOfMeasureDto>> call = api.getAll(url);
        return new ServiceUtils<>(UnitsOfMeasureDto.class).getAll(call);
    }

    @Override
    public UnitsOfMeasureDto getById(Long id) {
        Call<UnitsOfMeasureDto> call = api.getById(url, id);
        return new ServiceUtils<>(UnitsOfMeasureDto.class).getById(call, id);
    }

    @Override
    public void create(UnitsOfMeasureDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(UnitsOfMeasureDto.class).create(call);
    }

    @Override
    public void update(UnitsOfMeasureDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(UnitsOfMeasureDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(UnitsOfMeasureDto.class).delete(call);
    }
}
