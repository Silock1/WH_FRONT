package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.EmployeeDto;
import com.warehouse_accounting.services.interfaces.EmployeeService;
import com.warehouse_accounting.services.interfaces.api.EmployeeApi;
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
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeApi employeeApi;
    private final String url;

    public EmployeeServiceImpl(@Value("${retrofit.restServices.employee_url}") String url, Retrofit retrofit) {
        this.employeeApi = retrofit.create(EmployeeApi.class);
        this.url = url;
    }

    @Override
    public List<EmployeeDto> getAll() {
        List<EmployeeDto> employeeDtoList = new ArrayList<>();
        Call<List<EmployeeDto>> employeeListCall = employeeApi.getAll(url);
        try {
            Response<List<EmployeeDto>> response = employeeListCall.execute();
            if (response.isSuccessful()) {
                employeeDtoList = response.body();
                log.info("Список EmployeeDto получен успешно");
            } else {
                log.error("При получении списка EmployeeDto произошла ошибка: {}", response.code());
            }
        } catch (IOException ioException) {
            log.error("При получении списка EmployeeDto произошла ошибка", ioException);
        }
        return employeeDtoList;
    }

    @Override
    public EmployeeDto getById(Long id) {
        EmployeeDto employeeDto = EmployeeDto.builder().build();
        Call<EmployeeDto> employeeCall = employeeApi.getById(url, id);
        try {
            Response<EmployeeDto> response = employeeCall.execute();
            if (response.isSuccessful()) {
                employeeDto = response.body();
                log.info("EmployeeDto c id: {} получен успешно", id);
            } else {
                log.error("При получении EmployeeDto c id: {} произошла ошибка: {}", id, response.code());
            }
        } catch (IOException ioException) {
            log.error("Произошла ошибка при выполнении запроса на получение EmployeeDto по id",ioException);
        }
        return employeeDto;
    }

    @Override
    public void create(EmployeeDto employeeDto) {
        Call<Void> voidCall = employeeApi.create(url, employeeDto);
        try {
            Response<Void> response = voidCall.execute();
            if (response.isSuccessful()) {
                log.info("{} создан успешно", employeeDto);
            } else {
                log.error("При создании {} произошла ошибка: {}", employeeDto, response.code());
            }
        } catch (IOException ioException) {
            log.error("Произошла ошибка при выполнении запроса на создание EmployeeDto",ioException);
        }
    }

    @Override
    public void update(EmployeeDto employeeDto) {
        Call<Void> voidCall = employeeApi.update(url, employeeDto);
        try {
            Response<Void> response = voidCall.execute();
            if (response.isSuccessful()) {
                log.info("EmployeeDto с id: {} обновлен успешно", employeeDto.getId());
            } else {
                log.error("При обновлении EmployeeDto с id: {} произошла ошибка: {}",
                        employeeDto.getId(), response.code());
            }
        } catch (IOException ioException) {
            log.error("Произошла ошибка при выполнении запроса на обновление EmployeeDto по id",ioException);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> voidCall = employeeApi.deleteById(url, id);
        try {
            Response<Void> response = voidCall.execute();
            if (response.isSuccessful()) {
                log.info("EmployeeDto с id: {} удален успешно", id);
            } else {
                log.error("При удалении EmployeeDto с id: {} произошла ошибка: {}",
                        id, response.code());
            }
        } catch (IOException ioException) {
            log.error("Произошла ошибка при выполнении запроса на удаление EmployeeDto", ioException);
        }
    }

    @Override
    public EmployeeDto getPrincipal() {
        EmployeeDto employeeDto = new EmployeeDto();
        Call<EmployeeDto> employeeDtoCall = employeeApi.getPrincipal(url);
        try {
            employeeDto = employeeDtoCall.execute().body();
            log.info("Успешно выполнен запрос информации о работнике");
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса информации о работнике");
        }
        return employeeDto;
    }
}
