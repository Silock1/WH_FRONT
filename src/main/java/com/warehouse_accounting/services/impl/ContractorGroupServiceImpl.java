package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.ContractorGroupDto;
import com.warehouse_accounting.services.interfaces.ContractorGroupService;
import com.warehouse_accounting.services.interfaces.api.ContractorGroupApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.ArrayList;
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
        List<ContractorGroupDto> list = new ArrayList<>();
        Call<List<ContractorGroupDto>> listCall = api.getAll(url);
        try {
            Response<List<ContractorGroupDto>> response = listCall.execute();
            if (response.isSuccessful()){
                list = response.body();
                log.info("Успешно выполнен запрос на получение списка ContractorGroupDto");
            }else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка ContractorGroupDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка ContractorGroupDto " + e);
        }
        return list;
    }

    @Override
    public ContractorGroupDto getById(Long id) {
        ContractorGroupDto dto = new ContractorGroupDto();
        Call<ContractorGroupDto> call = api.getById(url, id);
        try {
            Response<ContractorGroupDto> response = call.execute();
            if (response.isSuccessful()){
                dto = response.body();
                log.info("Успешно выполнен запрос на получение ContractorGroupDto");
            }else {
                log.error("Произошла ошибка {} при выполнении запроса на получение ContractorGroupDto c id {}", response.code(), id);
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение ContractorGroupDto " + e);
        }
        return dto;
    }

    @Override
    public void create(ContractorGroupDto dto) {
        Call<Void> call = api.create(url, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()){
                log.info("Успешно выполнен запрос на создание ContractorGroupDto");
            }else {
                log.error("Произошла ошибка {} при выполнении запроса на создание ContractorGroupDto c id {}", response.code(), dto.getId());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создание ContractorGroupDto " + e);
        }
    }

    @Override
    public void update(ContractorGroupDto dto) {
        Call<Void> call = api.update(url, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()){
                log.info("Успешно выполнен запрос на обновление ContractorGroupDto");
            }else {
                log.error("Произошла ошибка {} при выполнении запроса на обновление ContractorGroupDto c id {}", response.code(), dto.getId());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на обновление ContractorGroupDto" + e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()){
                log.info("Успешно выполнен запрос на удаление ContractorGroupDto");
            }else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление ContractorGroupDto c id {}", response.code(), id);
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление ContractorGroupDto" + e);
        }
    }
}
