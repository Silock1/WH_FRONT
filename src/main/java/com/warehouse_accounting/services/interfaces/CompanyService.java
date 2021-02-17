package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.CompanyDto;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.List;

public interface CompanyService {
    List<CompanyDto> getAll() throws IOException;

    CompanyDto getById(long id) throws IOException;

    void create(CompanyDto companyDto) throws IOException;

    void update(CompanyDto companyDto) throws IOException;

    void deleteById(Long id) throws IOException;
}
