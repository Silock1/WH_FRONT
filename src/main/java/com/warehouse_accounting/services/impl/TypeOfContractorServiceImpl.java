package com.warehouse_accounting.services.impl;


import com.warehouse_accounting.models.dto.TypeOfContractorDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.TypeOfContractorService;
import com.warehouse_accounting.services.interfaces.api.TypeOfContractorApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Service
@Log4j2
public class TypeOfContractorServiceImpl implements TypeOfContractorService {

    private final TypeOfContractorApi api;
    private final String url;

    public TypeOfContractorServiceImpl(Retrofit retrofit, @Value("${retrofit.restServices.type_of_contractor_url}") String url) {
        this.api = retrofit.create(TypeOfContractorApi.class);
        this.url = url;
    }

    @Override
    public List<TypeOfContractorDto> getAll() {
        Call<List<TypeOfContractorDto>> call = api.getAll(url);
        return new ServiceUtils<>(TypeOfContractorDto.class).getAll(call);
    }


    @Override
    public TypeOfContractorDto getById(Long id) {
        Call<TypeOfContractorDto> call = api.getById(url, id);
        return new ServiceUtils<>(TypeOfContractorDto.class).getById(call, id);
    }

    @Override
    public void create(TypeOfContractorDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(TypeOfContractorDto.class).create(call);
    }

    @Override
    public void update(TypeOfContractorDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(TypeOfContractorDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.delete(url, id);
        new ServiceUtils<>(TypeOfContractorDto.class).delete(call);
    }
}
