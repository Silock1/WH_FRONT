package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.ContractorGroupDto;
import com.warehouse_accounting.services.interfaces.ContractorGroupService;
import com.warehouse_accounting.services.interfaces.api.ContractorGroupApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
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

//    try {
//            Response<List<EmployeeDto>> response = employeeListCall.execute();
//            if (response.isSuccessful()){
//                employeeDtoList = response.body();
//                log.info("Список EmployeeDto получен успешно");
//            }else {
//                log.error("При получении списка EmployeeDto произошла ошибка: {}", response.code());
//            }
//        } catch (IOException ioException) {
//            log.error("При получении списка EmployeeDto произошла ошибка");
//        }



    @Override
    public List<ContractorGroupDto> getAll() {
        List<ContractorGroupDto> list = new ArrayList<>();
        Call<List<ContractorGroupDto>> listCall = api.getAll(url);
        try {
            list = listCall.execute().body();
            log.info("Успешно выполнен запрос на получение списка ContractorGroupDto");
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка ContractorGroupDto");
        }
        return list;
    }

    @Override
    public ContractorGroupDto getById(Long id) {
        ContractorGroupDto contractorGroupDto = new ContractorGroupDto();
        Call<ContractorGroupDto> call = api.getById(url, id);
        try {
            contractorGroupDto = call.execute().body();
            log.info("Успешно выполнен запрос на получение списка ContractorGroupDto");
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка ContractorGroupDto");
        }
        return contractorGroupDto;
    }

    @Override
    public void create(ContractorGroupDto dto) {
        Call<Void> call = api.create(url, dto);
        try {
            call.execute();
            log.info("Успешно выполнен запрос на создание ContractorGroupDto");
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создание ContractorGroupDto");
        }
    }

    @Override
    public void update(ContractorGroupDto dto) {
        Call<Void> call = api.update(url, dto);
        try {
            call.execute();
            log.info("Успешно выполнен запрос на обновление ContractorGroupDto");
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на обновление ContractorGroupDto");
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        try {
            call.execute();
            log.info("Успешно выполнен запрос на удаление ContractorGroupDto");
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление ContractorGroupDto");
        }
    }
}
