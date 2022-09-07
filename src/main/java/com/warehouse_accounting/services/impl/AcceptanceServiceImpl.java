package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.AcceptancesDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.AcceptanceService;
import com.warehouse_accounting.services.interfaces.api.AcceptanceApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class AcceptanceServiceImpl implements AcceptanceService {

    private final AcceptanceApi api;
    private final String url;

    public AcceptanceServiceImpl(@Value("${retrofit.restServices.acceptance_url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(AcceptanceApi.class);
    }

    @Override
    public List<AcceptancesDto> getAll() {
        Call<List<AcceptancesDto>> call = api.getAll(url);
        return new ServiceUtils<>(AcceptancesDto.class).getAll(call);
    }

    @Override
    public AcceptancesDto getById(Long id) {
        Call<AcceptancesDto> call = api.getById(url, id);
        return new ServiceUtils<>(AcceptancesDto.class).getById(call, id);
    }

    @Override
    public void create(AcceptancesDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(AcceptancesDto.class).create(call);
    }

    @Override
    public void update(AcceptancesDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(AcceptancesDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(AcceptancesDto.class).delete(call);
    }

}
