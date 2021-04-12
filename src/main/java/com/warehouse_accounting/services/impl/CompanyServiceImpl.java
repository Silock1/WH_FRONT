package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.CompanyDto;
import com.warehouse_accounting.services.interfaces.api.CompanyApi;
import com.warehouse_accounting.services.interfaces.CompanyService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Response;
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

    @Override
    public List<CompanyDto> getAll() {
        List<CompanyDto> result = new ArrayList<>();
        try {
            Response<List<CompanyDto>> listResponse = service.getAll(URL).execute();
            if (listResponse.isSuccessful()) {
                result = listResponse.body();
                log.info("Успешно выполнен запрос на получение списка Company");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка Company", listResponse.code());
            }
        } catch (IOException exception) {
            log.error("Произошла ошибка при выполнении запроса на получение списка Company", exception);
        }
        return result;
    }

    @Override
    public CompanyDto getById(long id) {
        CompanyDto result = new CompanyDto();
        try {
            Response<CompanyDto> response = service.getById(URL, id).execute();
            if (response.isSuccessful()) {
                result = response.body();
                log.info("Успешно выполнен запрос на получение Company");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение Company по id {}", response.code(), id);
            }
        } catch (IOException exception) {
            log.error("Произошла ошибка при выполнении запроса на получение Company по id", exception);
        }
        return result;
    }

    @Override
    public void create(CompanyDto companyDto) {
        try {
            Response<Void> response = service.create(URL, companyDto).execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание Company");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создане Company", response.code());
            }
        } catch (IOException exception) {
            log.error("Произошла ошибка при выполнении запроса на создане Company", exception);
        }
    }

    @Override
    public void update(CompanyDto companyDto) {
        try {
            Response<Void> response = service.update(URL, companyDto).execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на изменение Company");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на изменение Company", response.code());
            }
        } catch (IOException exception) {
            log.error("Произошла ошибка при выполнении запроса на изменение Company", exception);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            Response<Void> response = service.deleteById(URL, id).execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление Company");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление Company по id", response.code());
            }
        } catch (IOException exception) {
            log.error("Произошла ошибка при выполнении запроса на удаление Company по id", exception);
        }
    }
}