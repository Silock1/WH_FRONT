package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.TechnologicalMapGroupDto;
import com.warehouse_accounting.services.interfaces.TechnologicalMapGroupService;
import com.warehouse_accounting.services.interfaces.api.TechnologicalMapGroupApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
@Log4j2
public class TechnologicalMapGroupServiceImpl implements TechnologicalMapGroupService {
    private final TechnologicalMapGroupApi api;
    private final String url;

    public TechnologicalMapGroupServiceImpl(@Value("api/technological_map_group") String url, Retrofit retrofit) {
        this.api = retrofit.create(TechnologicalMapGroupApi.class);
        this.url = url;
    }

    @Override
    public List<TechnologicalMapGroupDto> getAll() {
        List<TechnologicalMapGroupDto> technologicalMapGroupDtos = Collections.emptyList();
        Call<List<TechnologicalMapGroupDto>> call = api.getAll(url);

        try {
            Response<List<TechnologicalMapGroupDto>> response = call.execute();
            if (response.isSuccessful()) {
                technologicalMapGroupDtos = response.body();
                log.info("Успешно выполнен запрос на получение списка TechnologicalMapDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка TechnologicalMapDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка TechnologicalMapDto", e);
        }
        return technologicalMapGroupDtos;
    }

    @Override
    public TechnologicalMapGroupDto getById(Long id) {
        TechnologicalMapGroupDto technologicalMapGroupDto = null;
        Call<TechnologicalMapGroupDto> call = api.getById(url, id);
        try {
            Response<TechnologicalMapGroupDto> response = call.execute();
            if (response.isSuccessful()) {
                technologicalMapGroupDto = response.body();
                log.info("Успешно выполнен запрос на получение TechnologicalMapDto по id: {}", id);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение TechnologicalMapDto по id {}", response.code(), id);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение TechnologicalMapDto по id", e);
        }
        return technologicalMapGroupDto;
    }

    @Override
    public void create(TechnologicalMapGroupDto dto) {
        Call<Void> mapCall = api.create(url, dto);
        try {
            Response<Void> response = mapCall.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание TechnologicalMapDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создании TechnologicalMapDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создании TechnologicalMapDto", e);
        }
    }

    @Override
    public void update(TechnologicalMapGroupDto dto) {
        Call<Void> call = api.update(url, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на изменение TechnologicalMapDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на изменинии TechnologicalMapDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на изменении TechnologicalMapDto", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление TechnologicalMapDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удалении TechnologicalMapDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удалении TechnologicalMapDto", e);
        }
    }
}
