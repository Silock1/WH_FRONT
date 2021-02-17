package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.PositionDto;
import com.warehouse_accounting.services.interfaces.PositionService;
import com.warehouse_accounting.services.interfaces.api.PositionApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class PositionServiceImpl implements PositionService {

    private  final PositionApi api;
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
            positionDtoList = listCall.execute().body();
            log.info("Успешно выполнен запрос на получение списка PositionDto");
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка PositionDto");
        }
        return positionDtoList;
    }

    @Override
    public PositionDto getById(Long id) {
        PositionDto positionDto = new PositionDto();
        Call<PositionDto> positionCall = api.getById(url, id);
        try {
            positionDto = positionCall.execute().body();
            log.info("Успешно выполнен запрос на получение PositionDto");
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение PositionDto");
        }
        return positionDto;
    }

    @Override
    public void create(PositionDto dto) {
        Call<Void> call = api.create(url, dto);
        try {
            call.execute();
            log.info("Успешно выполнен запрос на создание PositionDto");
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создание PositionDto");
        }
    }

    @Override
    public void update(PositionDto dto) {
        Call<Void> call = api.update(url, dto);
        try {
            call.execute();
            log.info("Успешно выполнен запрос на обновление PositionDto");
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на обновление PositionDto");
        }

    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        try {
            call.execute();
            log.info("Успешно выполнен запрос на удаление PositionDto");
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление PositionDto");
        }
    }
}
