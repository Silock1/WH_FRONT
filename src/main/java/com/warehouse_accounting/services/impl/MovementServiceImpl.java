package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.MovementDto;
import com.warehouse_accounting.services.interfaces.MovementService;
import com.warehouse_accounting.services.interfaces.api.MovementApi;
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
public class MovementServiceImpl implements MovementService {
    private final MovementApi movementApi;
    private final String movementUrl;

    public MovementServiceImpl(@Value("${retrofit.restServices.movement_url}") String movementUrl, Retrofit retrofit) {
        this.movementUrl = movementUrl;
        this.movementApi = retrofit.create(MovementApi.class);
    }

    @Override
    public List<MovementDto> getAll() {
        List<MovementDto> movementDtoList = Collections.emptyList();
        Call<List<MovementDto>> roleGetAllCall = movementApi.getAll(movementUrl);
        try {
            Response<List<MovementDto>> response = roleGetAllCall.execute();
            if (response.isSuccessful()) {
                movementDtoList = response.body();
                log.info("Успешно выполнен запрос на получение списка MovementDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка MovementDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка MovementDto", e);
        }
        return movementDtoList;
    }

    @Override
    public MovementDto getById(Long id) {
        MovementDto movementDto = null;
        Call<MovementDto> callSync = movementApi.getById(movementUrl, id);
        try {
            Response<MovementDto> response = callSync.execute();
            if (response.isSuccessful()) {
                movementDto = response.body();
                log.info("Успешно выполнен запрос на получение MovementDto по id: {}", id);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение MovementDto по id {}", response.code(), id);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение MovementDto по id", e);
        }
        return movementDto;
    }

    @Override
    public void create(MovementDto dto) {
        Call<Void> call = movementApi.create(movementUrl, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание MovementDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создании MovementDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создании MovementDto", e);
        }
    }

    @Override
    public void update(MovementDto dto) {
        Call<Void> call = movementApi.update(movementUrl, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на изменение MovementDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на изменение MovementDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на изменениие MovementDto", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = movementApi.deleteById(movementUrl, id);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление MovementDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление MovementDto по id", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление MovementDto по id", e);
        }
    }
}