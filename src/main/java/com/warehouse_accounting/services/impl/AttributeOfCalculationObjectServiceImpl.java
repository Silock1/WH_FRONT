package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.AttributeOfCalculationObjectDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.AttributeOfCalculationObjectService;
import com.warehouse_accounting.services.interfaces.api.AttributeOfCalculationObjectApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class AttributeOfCalculationObjectServiceImpl implements AttributeOfCalculationObjectService {

    private final AttributeOfCalculationObjectApi api;
    private final String url;

    public AttributeOfCalculationObjectServiceImpl(Retrofit retrofit, @Value("${retrofit.restServices.attribute_of_calculation_object_url}") String url) {
        this.api = retrofit.create(AttributeOfCalculationObjectApi.class);
        this.url = url;
    }

    @Override
    public List<AttributeOfCalculationObjectDto> getAll() {
        Call<List<AttributeOfCalculationObjectDto>> call = api.getAll(url);
        return new ServiceUtils<>(AttributeOfCalculationObjectDto.class).getAll(call);
    }

    @Override
    public AttributeOfCalculationObjectDto getById(Long id) {
        Call<AttributeOfCalculationObjectDto> call = api.getById(url, id);
        return new ServiceUtils<>(AttributeOfCalculationObjectDto.class).getById(call, id);
    }

    @Override
    public void create(AttributeOfCalculationObjectDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(AttributeOfCalculationObjectDto.class).create(call);
    }

    @Override
    public void update(AttributeOfCalculationObjectDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(AttributeOfCalculationObjectDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(AttributeOfCalculationObjectDto.class).delete(call);
    }
}
