package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.CompanyDto;
import com.warehouse_accounting.services.interfaces.CompanyServiceInterface;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.List;

@Service
public class CompanyService {

    private final CompanyServiceInterface service;

    public CompanyService(Retrofit retrofit) {
        service = retrofit.create(CompanyServiceInterface.class);
    }

    public List<CompanyDto> getAll() throws IOException {
        return (service.getAll()).execute().body();
    }

    public CompanyDto getById(long id) throws IOException {
        return service.getById(id).execute().body();
    }

    public int create(CompanyDto companyDto) throws IOException {
        return service.create(companyDto).execute().code();
    }

    public int update(CompanyDto companyDto) throws IOException {
        return service.update(companyDto).execute().code();
    }

    public int deleteById(Long id) throws IOException {
        return service.deleteById(id).execute().code();
    }
}