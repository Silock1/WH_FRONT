package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.ContractDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.ContractService;
import com.warehouse_accounting.services.interfaces.api.ContractApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class ContractServiceImpl implements ContractService {
    private final ContractApi api;
    private final String url;

    public ContractServiceImpl(@Value("${retrofit.restServices.contract_url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(ContractApi.class);
    }

    @Override
    public List<ContractDto> getAll() {
        Call<List<ContractDto>> contractGetAllCall = api.getAll(url);
        return new ServiceUtils<>(ContractDto.class).getAll(contractGetAllCall);
    }

    @Override
    public ContractDto getById(Long id) {
        Call<ContractDto> callSync = api.getById(url, id);
        return new ServiceUtils<>(ContractDto.class).getById(callSync, id);
    }

    @Override
    public void create(ContractDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(ContractDto.class).create(call);
    }

    @Override
    public void update(ContractDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(ContractDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(ContractDto.class).delete(call);
    }
}