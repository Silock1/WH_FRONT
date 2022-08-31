package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.ContractorDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.ContractorService;
import com.warehouse_accounting.services.interfaces.api.ContractorApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Log4j2
@Service
public class ContractorServiceImpl implements ContractorService {

    private final ContractorApi contractorApi;
    private final String contractorUrl;

    public ContractorServiceImpl(@Value("${retrofit.restServices.contractor_url}") String contractorUrl, Retrofit retrofit) {
        this.contractorUrl = contractorUrl;
        this.contractorApi = retrofit.create(ContractorApi.class);
    }

    @Override
    public List<ContractorDto> getAll() {
        Call<List<ContractorDto>> contractorApiAll = contractorApi.getAll(contractorUrl);
        return new ServiceUtils<>(ContractorDto.class).getAll(contractorApiAll);
    }

    @Override
    public ContractorDto getById(Long id) {
        Call<ContractorDto> callSync = contractorApi.getById(contractorUrl, id);
        return new ServiceUtils<>(ContractorDto.class).getById(callSync, id);
    }

    @Override
    public void create(ContractorDto dto) {
        Call<Void> call = contractorApi.create(contractorUrl, dto);
        new ServiceUtils<>(ContractorDto.class).create(call);
    }

    @Override
    public void update(ContractorDto dto) {
        Call<Void> call = contractorApi.update(contractorUrl, dto);
        new ServiceUtils<>(ContractorDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = contractorApi.deleteById(contractorUrl, id);
        new ServiceUtils<>(ContractorDto.class).deleteById(call);
    }
}
