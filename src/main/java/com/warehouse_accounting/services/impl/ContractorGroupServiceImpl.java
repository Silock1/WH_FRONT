package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.ContractorGroupDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.ContractorGroupService;
import com.warehouse_accounting.services.interfaces.api.ContractorGroupApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class ContractorGroupServiceImpl implements ContractorGroupService {

    private final ContractorGroupApi api;
    private final String url;

    public ContractorGroupServiceImpl(Retrofit retrofit, @Value("${retrofit.restServices.contractor_group_url}") String url) {
        this.api = retrofit.create(ContractorGroupApi.class);
        this.url = url;
    }

    @Override
    public List<ContractorGroupDto> getAll() {
        Call<List<ContractorGroupDto>> call = api.getAll(url);
        return new ServiceUtils<>(ContractorGroupDto.class).getAll(call);
    }

    @Override
    public ContractorGroupDto getById(Long id) {
        Call<ContractorGroupDto> call = api.getById(url, id);
        return new ServiceUtils<>(ContractorGroupDto.class).getById(call, id);
    }

    @Override
    public void create(ContractorGroupDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(ContractorGroupDto.class).create(call);
    }

    @Override
    public void update(ContractorGroupDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(ContractorGroupDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(ContractorGroupDto.class).delete(call);
    }
}
