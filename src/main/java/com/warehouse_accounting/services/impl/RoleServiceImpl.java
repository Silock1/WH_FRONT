package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.RoleDto;
import com.warehouse_accounting.services.interfaces.RoleService;
import com.warehouse_accounting.services.interfaces.api.RoleApi;
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
            Response<List<RoleDto>> response = roleGetAllCall.execute();
            if (response.isSuccessful()) {
                roleDtoList = response.body();
                log.info("Успешно выполнен запрос на получение списка RoleDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка RoleDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка RoleDto", e);
        }
        return roleDtoList;
    }

    @Override
    public RoleDto getById(Long id) {
        RoleDto roleDto = null;
        Call<RoleDto> callSync = roleApi.getById(roleUrl, id);
        try {
            Response<RoleDto> response = callSync.execute();
            if (response.isSuccessful()) {
                roleDto = response.body();
                log.info("Успешно выполнен запрос на получение RoleDto по id: {}", id);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение RoleDto по id {}",response.code(), id);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение RoleDto по id", e);
        }
        return roleDto;
    }

    @Override
    public void create(RoleDto dto) {
        Call<Void> call = roleApi.create(roleUrl, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание RoleDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создание RoleDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создание RoleDto", e);
        }
    }

    @Override
    public void update(RoleDto dto) {
        Call<Void> call = roleApi.update(roleUrl, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на изменение RoleDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на изменение RoleDto",response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на изменение RoleDto", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = roleApi.deleteById(roleUrl, id);
        try {
            Response<Void> response = call.execute();
            if(response.isSuccessful()){
                log.info("Успешно выполнен запрос на удаление RoleDto");
            }else{
                log.error("Произошла ошибка {} при выполнении запроса на удаление RoleDto по id",response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление RoleDto по id", e);
        }
    }
}
