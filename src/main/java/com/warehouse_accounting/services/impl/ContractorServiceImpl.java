package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.ContractorDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.ContractorService;
import com.warehouse_accounting.services.interfaces.api.ContractorApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class ContractorServiceImpl implements ContractorService {

    private final ContractorApi api;
    private final String url;

    public ContractorServiceImpl(@Value("${retrofit.restServices.contractor_url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(ContractorApi.class);
    }

    @Override
    public List<ContractorDto> getAll() {
        Call<List<ContractorDto>> call = api.getAll(url);
        return new ServiceUtils<>(ContractorDto.class).getAll(call);
    }

    @Override
    public ContractorDto getById(Long id) {
        Call<ContractorDto> call = api.getById(url, id);
        return new ServiceUtils<>(ContractorDto.class).getById(call, id);
    }

    @Override
    public void create(ContractorDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(ContractorDto.class).create(call);
    }

    @Override
    public void update(ContractorDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(ContractorDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(ContractorDto.class).delete(call);
    }

    @Override
    public ContractorDto findByName(List<ContractorDto> list, String name) {
        ContractorDto dto = new ContractorDto();
       for(ContractorDto contractor: list) {
           if (name.equals(contractor.getName())) {
               dto = contractor;
           }
       }
       return dto;
    }
}
