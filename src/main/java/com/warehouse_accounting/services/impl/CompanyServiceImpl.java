package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.CompanyDto;
import com.warehouse_accounting.services.interfaces.CompanyApi;
import com.warehouse_accounting.services.interfaces.CompanyService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class CompanyServiceImpl implements CompanyService {

    private final String URL;

    private final CompanyApi service;

    public CompanyServiceImpl(@Value("${retrofit.restServices.company_url}") String url, Retrofit retrofit) {
        service = retrofit.create(CompanyApi.class);
        URL = url;
    }

    public List<CompanyDto> getAll() {
        List<CompanyDto> result = new ArrayList<>();
        try {
            result = service.getAll(URL).execute().body();
        } catch (IOException exception) {
            log.error("Something went wrong while receiving Company list", exception);
        }
        return result;
    }

    public CompanyDto getById(long id) {
        CompanyDto result = new CompanyDto();
        try {
            result = service.getById(URL, id).execute().body();
        } catch (IOException exception) {
            log.error("Something went wrong while receiving Company", exception);
        }
        return result;
    }

    public int create(CompanyDto companyDto) {
        int result = 0;
        try {
            result = service.create(URL, companyDto).execute().code();
        } catch (IOException exception) {
            log.error("Something went wrong while creating Company", exception);
        }
        return result;
    }

    public int update(CompanyDto companyDto) {
        int result = 0;
        try {
            result = service.update(URL, companyDto).execute().code();
        } catch (IOException exception) {
            log.error("Something went wrong while updating Company", exception);
        }
        return result;
    }

    public int deleteById(Long id) {
        int result = 0;
        try {
            result = service.deleteById(URL, id).execute().code();
        } catch (IOException exception) {
            log.error("Something went wrong while deleting Company", exception);
        }
        return result;
    }
}