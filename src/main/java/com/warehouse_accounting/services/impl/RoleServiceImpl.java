package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.RoleDto;
import com.warehouse_accounting.services.interfaces.RoleService;
import com.warehouse_accounting.services.interfaces.retrofit.RoleApi;
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
public class RoleServiceImpl implements RoleService {
    private final RoleApi roleApi;
    private final String roleUrl;

    public RoleServiceImpl(@Value("${retrofit.restServices.role_url}") String roleUrl, Retrofit retrofit) {
        this.roleUrl = roleUrl;
        this.roleApi = retrofit.create(RoleApi.class);
    }

    @Override
    public List<RoleDto> getAll() {
        List<RoleDto> roleDtoList = Collections.emptyList();
        Call<List<RoleDto>> roleGetAllCall = roleApi.getAll(roleUrl);
        try {
            roleDtoList = roleGetAllCall.execute().body();
            log.info("Успешно выполнен запрос на получение списка RoleDto");
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка RoleDto");
        }
        return roleDtoList;
    }

    @Override
    public RoleDto getById(Long id) {
        RoleDto roleDto = null;
        Call<RoleDto> callSync = roleApi.getById(roleUrl, id);
        try {
            Response<RoleDto> response = callSync.execute();
            roleDto = response.body();
            log.info("Успешно выполнен запрос на получение RoleDto по id: {}", id);
        } catch (Exception ex) {
            log.error("Произошла ошибка при выполнении запроса на получение RoleDto по id: {}", id);
        }
        return roleDto;
    }

    @Override
    public void create(RoleDto dto) {
        Call<Void> call = roleApi.create(roleUrl, dto);
        try {
            call.execute();
            log.info("Успешно выполнен запрос на создание RoleDto");
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создании RoleDto");
        }
    }

    @Override
    public void update(RoleDto dto) {
        Call<Void> call = roleApi.update(roleUrl, dto);
        try {
            call.execute();
            log.info("Успешно выполнен запрос на изменении RoleDto");
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на изменении RoleDto");
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = roleApi.deleteById(roleUrl, id);
        try {
            call.execute();
            log.info("Успешно выполнен запрос на удаление RoleDto");
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление RoleDto по id: {}", id);
        }
    }
}
