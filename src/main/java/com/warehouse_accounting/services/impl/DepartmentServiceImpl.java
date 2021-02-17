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
            departmentDtoList = departmentGetAllCall.execute().body();
            log.info("Успешно выполнен запрос на получение списка DepartmentDto");
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка DepartmentDto");
        }
        return departmentDtoList;
    }

    @Override
    public DepartmentDto getById(Long id) {
        DepartmentDto departmentDto = null;
        Call<DepartmentDto> callSync = departmentApi.getById(departmentUrl, id);
        try {
            Response<DepartmentDto> response = callSync.execute();
            departmentDto = response.body();
            log.info("Успешно выполнен запрос на получение DepartmentDto по id: {}", id);
        } catch (Exception ex) {
            log.error("Произошла ошибка при выполнении запроса на получение DepartmentDto по id: {}", id);
        }
        return departmentDto;
    }

    @Override
    public void create(DepartmentDto departmentDto) {
        Call<Void> call = departmentApi.create(departmentUrl, departmentDto);
        try {
            call.execute();
            log.info("Успешно выполнен запрос на создание DepartmentDto");
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создании DepartmentDto");
        }
    }

    @Override
    public void update(DepartmentDto departmentDto) {
        Call<Void> call = departmentApi.update(departmentUrl, departmentDto);
        try {
            call.execute();
            log.info("Успешно выполнен запрос на изменении DepartmentDto");
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на изменении DepartmentDto");
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = departmentApi.deleteById(departmentUrl, id);
        try {
            call.execute();
            log.info("Успешно выполнен запрос на удаление DepartmentDto");
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление DepartmentDto по id: {}", id);
        }
    }
}
