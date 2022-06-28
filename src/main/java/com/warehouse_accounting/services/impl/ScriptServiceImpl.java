package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.ScriptDto;
import com.warehouse_accounting.services.interfaces.ScriptService;
import com.warehouse_accounting.services.interfaces.api.ScriptApi;
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
public class ScriptServiceImpl implements ScriptService {

    private final ScriptApi scriptApi;

    private final String scriptUrl;

    public ScriptServiceImpl(@Value("${retrofit.restServices.scripts_url}") String scriptUrl, Retrofit retrofit) {
        this.scriptUrl = scriptUrl;
        this.scriptApi = retrofit.create(ScriptApi.class);
    }

    @Override
    public List<ScriptDto> getAll() {
        List<ScriptDto> scriptDtos = Collections.emptyList();
        Call<List<ScriptDto>> scriptApiAll = scriptApi.getAll(scriptUrl);
        try {
            Response<List<ScriptDto>> response = scriptApiAll.execute();
            if (response.isSuccessful()) {
                scriptDtos = response.body();
                log.info("Успешно выполнен запрос на получение списка ScriptDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка ScriptDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка ScriptDto", e);
        }
        return scriptDtos;
    }

    @Override
    public ScriptDto getById(Long id) {
        ScriptDto scriptDto = null;
        Call<ScriptDto> scriptDtoCall = scriptApi.getById(scriptUrl, id);
        try {
            Response<ScriptDto> response = scriptDtoCall.execute();
            if (response.isSuccessful()) {
                scriptDto = response.body();
                log.info("Успешно выполнен запрос на получение ScriptDto по id: {}", id);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение ScriptDto по id {}", response.code(), id);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение ScriptDto по id", e);
        }
        return scriptDto;
    }

    @Override
    public void create(ScriptDto dto) {
        Call<Void> call = scriptApi.create(scriptUrl, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание ScriptDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создание ScriptDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создание ScriptDto", e);
        }
    }

    @Override
    public void update(ScriptDto dto) {
        Call<Void> call = scriptApi.update(scriptUrl, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на изменение ScriptDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на изменение ScriptDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на изменение ScriptDto", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = scriptApi.deleteById(scriptUrl, id);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление ScriptDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление ScriptDto по id", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление ScriptDto по id", e);
        }
    }
}
