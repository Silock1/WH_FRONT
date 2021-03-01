package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.ContractorDto;
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
        List<ContractorDto> contractorDto = Collections.emptyList();
        Call<List<ContractorDto>> contractorApiAll = contractorApi.getAll(contractorUrl);
        try {
            Response<List<ContractorDto>> response = contractorApiAll.execute();
            if (response.isSuccessful()) {
                contractorDto = response.body();
                log.info("Успешно выполнен запрос на получение списка ContractorDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка ContractorDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка ContractorDto", e);
        }
        return contractorDto;
    }

    @Override
    public ContractorDto getById(Long id) {
        ContractorDto contractorDto = null;
        Call<ContractorDto> callSync = contractorApi.getById(contractorUrl, id);
        try {
            Response<ContractorDto> response = callSync.execute();
            if (response.isSuccessful()) {
                contractorDto = response.body();
                log.info("Успешно выполнен запрос на получение ContractorDto по id: {}", id);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение ContractorDto по id {}", response.code(), id);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение ContractorDto по id", e);
        }
        return contractorDto;
    }

    @Override
    public void create(ContractorDto dto) {
        Call<Void> call = contractorApi.create(contractorUrl, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание ContractorDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создании ContractorDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создании ContractorDto", e);
        }
    }

    @Override
    public void update(ContractorDto dto) {
        Call<Void> call = contractorApi.update(contractorUrl, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на изменение ContractorDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на изменение ContractorDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на изменение ContractorDto", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = contractorApi.deleteById(contractorUrl, id);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление ContractorDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление ContractorDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление ContractorDto по id", e);
        }
    }
}
