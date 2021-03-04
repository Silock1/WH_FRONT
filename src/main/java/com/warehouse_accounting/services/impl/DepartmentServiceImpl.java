package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.DepartmentDto;
import com.warehouse_accounting.services.interfaces.DepartmentService;
import com.warehouse_accounting.services.interfaces.api.DepartmentApi;
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
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentApi departmentApi;
    private final String departmentUrl;

    public DepartmentServiceImpl(@Value("${retrofit.restServices.department_url}") String departmentUrl, Retrofit retrofit) {
        this.departmentUrl = departmentUrl;
        this.departmentApi = retrofit.create(DepartmentApi.class);
    }

    @Override
    public List<DepartmentDto> getAll() {
        List<DepartmentDto> departmentDtoList = Collections.emptyList();
        Call<List<DepartmentDto>> departmentGetAllCall = departmentApi.getAll(departmentUrl);
        try {
            Response<List<DepartmentDto>> response = departmentGetAllCall.execute();
            if (response.isSuccessful()) {
                departmentDtoList = response.body();
                log.info("Успешно выполнен запрос на получение списка DepartmentDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка DepartmentDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка DepartmentDto", e);
        }
        return departmentDtoList;
    }

    @Override
    public DepartmentDto getById(Long id) {
        DepartmentDto departmentDto = null;
        Call<DepartmentDto> callSync = departmentApi.getById(departmentUrl, id);
        try {
            Response<DepartmentDto> response = callSync.execute();
            if (response.isSuccessful()) {
                departmentDto = response.body();
                log.info("Успешно выполнен запрос на получение DepartmentDto по id: {}", id);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение DepartmentDto по id: {}", response.code(), id);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение DepartmentDto по id", e);
        }
        return departmentDto;
    }

    @Override
    public void create(DepartmentDto departmentDto) {
        Call<Void> call = departmentApi.create(departmentUrl, departmentDto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание DepartmentDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создание DepartmentDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создание DepartmentDto", e);
        }
    }

    @Override
    public void update(DepartmentDto departmentDto) {
        Call<Void> call = departmentApi.update(departmentUrl, departmentDto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на изменениуе DepartmentDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на изменение DepartmentDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на изменение DepartmentDto", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = departmentApi.deleteById(departmentUrl, id);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление DepartmentDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление DepartmentDto по id: {}", response.code(), id);
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление DepartmentDto по id", e);
        }
    }
}
