package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.PositionDto;
import com.warehouse_accounting.services.interfaces.PositionService;
import com.warehouse_accounting.services.interfaces.api.PositionApi;
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
public class PositionServiceImpl implements PositionService {

    private final PositionApi api;
    private final String url;

    public PositionServiceImpl(Retrofit retrofit, @Value("${retrofit.restServices.position_url}") String url) {
        this.api = retrofit.create(PositionApi.class);
        this.url = url;
    }


    @Override
    public List<PositionDto> getAll() {
        List<PositionDto> positionDtoList = new ArrayList<>();
        Call<List<PositionDto>> listCall = api.getAll(url);
        try {
            Response<List<PositionDto>> response = listCall.execute();
            if (response.isSuccessful()) {
                positionDtoList = response.body();
                log.info("Успешно выполнен запрос на получение списка PositionDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка PositionDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка PositionDto", e);
        }
        return positionDtoList;
    }

    @Override
    public PositionDto getById(Long id) {
        PositionDto positionDto = new PositionDto();
        Call<PositionDto> positionCall = api.getById(url, id);
        try {
            Response<PositionDto> response = positionCall.execute();
            if (response.isSuccessful()) {
                positionDto = response.body();
                log.info("Успешно выполнен запрос на получение PositionDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение PositionDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение PositionDto", e);
        }
        return positionDto;
    }

    @Override
    public void create(PositionDto dto) {
        Call<Void> call = api.create(url, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()){
                log.info("Успешно выполнен запрос на создание PositionDto");
            }else{
                log.error("Произошла ошибка {} при выполнении запроса на создание PositionDto",response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создание PositionDto",e);
        }
    }

    @Override
    public void update(PositionDto dto) {
        Call<Void> call = api.update(url, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на обновление PositionDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на обновление PositionDto",response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на обновление PositionDto",e);
        }

    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление PositionDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление PositionDto",response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление PositionDto",e);
        }
    }
}
