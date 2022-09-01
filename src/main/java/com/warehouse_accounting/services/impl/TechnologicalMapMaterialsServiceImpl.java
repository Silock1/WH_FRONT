package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.TechnologicalMapDto;
import com.warehouse_accounting.models.dto.TechnologicalMapMaterialDto;
import com.warehouse_accounting.services.interfaces.TechnologicalMapMaterialsService;
import com.warehouse_accounting.services.interfaces.api.TechnologicalMapApi;
import com.warehouse_accounting.services.interfaces.api.TechnologicalMapMaterialsApi;
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
public class TechnologicalMapMaterialsServiceImpl implements TechnologicalMapMaterialsService {
    private final TechnologicalMapMaterialsApi api;
    private final String url;

    public TechnologicalMapMaterialsServiceImpl(@Value("api/technological_map_material") String url, Retrofit retrofit) {
        this.api = retrofit.create(TechnologicalMapMaterialsApi.class);
        this.url = url;
    }

    @Override
    public List<TechnologicalMapMaterialDto> getAll() {
        List<TechnologicalMapMaterialDto> technologicalMapMaterialDtos = Collections.emptyList();
        Call<List<TechnologicalMapMaterialDto>> call = api.getAll(url);

        try {
            Response<List<TechnologicalMapMaterialDto>> response = call.execute();
            if (response.isSuccessful()) {
                technologicalMapMaterialDtos = response.body();
                log.info("Успешно выполнен запрос на получение списка TechnologicalMapDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка TechnologicalMapDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка TechnologicalMapDto", e);
        }
        return technologicalMapMaterialDtos;
    }

    @Override
    public TechnologicalMapMaterialDto getById(Long id) {
        TechnologicalMapMaterialDto technologicalMapMaterialDto = null;
        Call<TechnologicalMapMaterialDto> call = api.getById(url, id);
        try {
            Response<TechnologicalMapMaterialDto> response = call.execute();
            if (response.isSuccessful()) {
                technologicalMapMaterialDto = response.body();
                log.info("Успешно выполнен запрос на получение TechnologicalMapDto по id: {}", id);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение TechnologicalMapDto по id {}", response.code(), id);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение TechnologicalMapDto по id", e);
        }
        return technologicalMapMaterialDto;
    }

    @Override
    public void create(TechnologicalMapMaterialDto dto) {
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
    public void update(TechnologicalMapMaterialDto dto) {
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
