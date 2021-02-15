package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.CompanyDto;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.List;

public interface CompanyService {
    List<CompanyDto> getAll() throws IOException;

    CompanyDto getById(long id) throws IOException;

    int create(CompanyDto companyDto) throws IOException;

    int update(CompanyDto companyDto) throws IOException;

    int deleteById(Long id) throws IOException;
}
