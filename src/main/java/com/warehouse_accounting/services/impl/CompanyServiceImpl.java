package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.CompanyDto;
import com.warehouse_accounting.services.interfaces.CompanyApi;
import com.warehouse_accounting.services.interfaces.CompanyService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final String URL;


    private final CompanyApi service;

    public CompanyServiceImpl(@Value("${retrofit.restServices.company_url}") String url, Retrofit retrofit) {
        service = retrofit.create(CompanyApi.class);
        URL = url;
        System.out.println(URL);
    }

    public List<CompanyDto> getAll() throws IOException {
        return (service.getAll(URL)).execute().body();
    }

    public CompanyDto getById(long id) throws IOException {
        return service.getById(URL, id).execute().body();
    }

    public int create(CompanyDto companyDto) throws IOException {
        return service.create(URL, companyDto).execute().code();
    }

    public int update(CompanyDto companyDto) throws IOException {
        return service.update(URL, companyDto).execute().code();
    }

    public int deleteById(Long id) throws IOException {
        return service.deleteById(URL, id).execute().code();
    }
}