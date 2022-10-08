package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.BonusProgramDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.BonusProgramService;
import com.warehouse_accounting.services.interfaces.api.BonusProgramApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;
@Log4j2
@Service
public class BonusProgramServiceImpl implements BonusProgramService {
    private final BonusProgramApi api;
    private final String url;

    @Autowired
    public BonusProgramServiceImpl(@Value("${retrofit.restServices.bonusProgram_url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(BonusProgramApi.class);
    }


    @Override
    public List<BonusProgramDto> getAll() {
        Call<List<BonusProgramDto>> call = api.getAll(url);
        return new ServiceUtils<>(BonusProgramDto.class).getAll(call);
    }

    @Override
    public BonusProgramDto getById(Long id) {
        Call<BonusProgramDto> call = api.getById(url, id);
        return new ServiceUtils<>(BonusProgramDto.class).getById(call, id);
    }

    @Override
    public void create(BonusProgramDto bonusProgramDto) {
        Call<Void> call = api.create(url, bonusProgramDto);
        new ServiceUtils<>(BonusProgramDto.class).create(call);

    }

    @Override
    public void update(BonusProgramDto bonusProgramDto) {
        Call<Void> call = api.update(url, bonusProgramDto);
        new ServiceUtils<>(BonusProgramDto.class).update(call);

    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(BonusProgramDto.class).delete(call);

    }
}
