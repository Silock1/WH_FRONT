package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.EmployeeDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.EmployeeService;
import com.warehouse_accounting.services.interfaces.api.EmployeeApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.List;

@Log4j2
@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeApi api;
    private final String url;

    public EmployeeServiceImpl(@Value("${retrofit.restServices.employee_url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(EmployeeApi.class);
    }

    @Override
    public List<EmployeeDto> getAll() {
        Call<List<EmployeeDto>> call = api.getAll(url);
        return new ServiceUtils<>(EmployeeDto.class).getAll(call);
    }

    @Override
    public EmployeeDto getById(Long id) {
        Call<EmployeeDto> call = api.getById(url, id);
        return new ServiceUtils<>(EmployeeDto.class).getById(call, id);
    }

    @Override
    public void create(EmployeeDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(EmployeeDto.class).create(call);
    }

    @Override
    public void update(EmployeeDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(EmployeeDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(EmployeeDto.class).delete(call);
    }

    @Override
    public EmployeeDto getPrincipal() {
        EmployeeDto employeeDto = new EmployeeDto();
        Call<EmployeeDto> employeeDtoCall = api.getPrincipal(url);
        try {
            employeeDto = employeeDtoCall.execute().body();
            log.info("Успешно выполнен запрос информации о работнике");
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса информации о работнике");
        }
        return employeeDto;
    }


}
