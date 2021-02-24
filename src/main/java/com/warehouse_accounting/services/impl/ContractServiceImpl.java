package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.ContractDto;
import com.warehouse_accounting.services.interfaces.ContractService;
import com.warehouse_accounting.services.interfaces.api.ContractApi;
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
public class ContractServiceImpl implements ContractService {
    private final ContractApi contractApi;
    private final String contractUrl;

    public ContractServiceImpl(@Value("${retrofit.restServices.contract_url}") String contractUrl, Retrofit retrofit) {
        this.contractUrl = contractUrl;
        this.contractApi = retrofit.create(ContractApi.class);
    }

    @Override
    public List<ContractDto> getAll() {
        List<ContractDto> contractDtoList = Collections.emptyList();
        Call<List<ContractDto>> roleGetAllCall = contractApi.getAll(contractUrl);
        try {
            Response<List<ContractDto>> response = roleGetAllCall.execute();
            if (response.isSuccessful()) {
                contractDtoList = response.body();
                log.info("Успешно выполнен запрос на получение списка ContractDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка ContractDto",response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка ContractDto", e);
        }
        return contractDtoList;
    }

    @Override
    public ContractDto getById(Long id) {
        ContractDto contractDto = null;
        Call<ContractDto> callSync = contractApi.getById(contractUrl, id);
        try {
            Response<ContractDto> response = callSync.execute();
            if (response.isSuccessful()){
                contractDto = response.body();
                log.info("Успешно выполнен запрос на получение ContractDto по id: {}", id);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение ContractDto по id {}",response.code(),id);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение ContractDto по id", e);
        }
        return contractDto;
    }

    @Override
    public void create(ContractDto dto) {
        Call<Void> call = contractApi.create(contractUrl, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание ContractDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создании ContractDto",response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создании ContractDto", e);
        }
    }

    @Override
    public void update(ContractDto dto) {
        Call<Void> call = contractApi.update(contractUrl, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на изменение ContractDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на изменение ContractDto",response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на изменениие ContractDto", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = contractApi.deleteById(contractUrl, id);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление ContractDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление ContractDto по id",response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление ContractDto по id", e);
        }
    }
}