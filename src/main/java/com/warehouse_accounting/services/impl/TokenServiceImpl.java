package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.TokenDto;
import com.warehouse_accounting.services.interfaces.TokenService;
import com.warehouse_accounting.services.interfaces.api.TokenApi;
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
public class TokenServiceImpl implements TokenService {

    private final TokenApi tokenApi;

    private final String tokenUrl;

    public TokenServiceImpl(@Value("api/token") String tokenUrl, Retrofit retrofit) {
        this.tokenUrl = tokenUrl;
        this.tokenApi = retrofit.create(TokenApi.class);
    }

    @Override
    public List<TokenDto> getAll() {
        List<TokenDto> tokenDtos = Collections.emptyList();
        Call<List<TokenDto>> tokenApiAll = tokenApi.getAll(tokenUrl);
        try {
            Response<List<TokenDto>> response = tokenApiAll.execute();
            if (response.isSuccessful()) {
                tokenDtos = response.body();
                log.info("Успешно выполнен запрос на получение списка TokenDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка TokenDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка TokenDto", e);
        }
        return tokenDtos;
    }

    @Override
    public TokenDto getById(Long id) {
        TokenDto tokenDto = null;
        Call<TokenDto> tokenDtoCall = tokenApi.getById(tokenUrl, id);
        try {
            Response<TokenDto> response = tokenDtoCall.execute();
            if (response.isSuccessful()) {
                tokenDto = response.body();
                log.info("Успешно выполнен запрос на получение TokenDto по id: {}", id);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение TokenDto по id {}", response.code(), id);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение TokenDto по id", e);
        }
        return tokenDto;
    }

    @Override
    public void create(TokenDto dto) {
        Call<Void> call = tokenApi.create(tokenUrl, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание TokenDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создание TokenDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создание TokenDto", e);
        }
    }

    @Override
    public void update(TokenDto dto) {
        Call<Void> call = tokenApi.update(tokenUrl, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на изменение TokenDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на изменение TokenDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на изменение TokenDto", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = tokenApi.deleteById(tokenUrl, id);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление TokenDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление TokenDto по id", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление TokenDto по id", e);
        }
    }
}

