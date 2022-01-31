package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.GoodsToRealizeGetDto;
import com.warehouse_accounting.services.interfaces.GoodsToRealizeGetService;
import com.warehouse_accounting.services.interfaces.api.GoodsToRealizeGetApi;
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
public class GoodsToRealizeGetImpl implements GoodsToRealizeGetService {

    private final GoodsToRealizeGetApi goodsToRealizeGetApi;
    private final String goods_to_realize_get_url;

    public GoodsToRealizeGetImpl(@Value("${retrofit.restServices.goods_to_realize_get_url}") String goods_to_realize_get_url, Retrofit retrofit) {
        this.goods_to_realize_get_url = goods_to_realize_get_url;
        this.goodsToRealizeGetApi = retrofit.create(GoodsToRealizeGetApi.class);
    }

    @Override
    public List<GoodsToRealizeGetDto> getAll() {
        List<GoodsToRealizeGetDto> goodsToRealizeGetDtos = Collections.emptyList();
        Call<List<GoodsToRealizeGetDto>> goodsToRealizeGetApiAll = goodsToRealizeGetApi.getAll(goods_to_realize_get_url);
        try {
            Response<List<GoodsToRealizeGetDto>> response = goodsToRealizeGetApiAll.execute();
            if (response.isSuccessful()) {
                goodsToRealizeGetDtos = response.body();
                log.info("Успешно выполнен запрос на получение списка GoodsToRealizeGetDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка GoodsToRealizeGetDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка GoodsToRealizeGetDto", e);
        }
        return goodsToRealizeGetDtos;
    }

    @Override
    public GoodsToRealizeGetDto getById(Long id) {
        GoodsToRealizeGetDto goodsToRealizeGetDto = null;
        Call<GoodsToRealizeGetDto> goodsToRealizeGetSync = goodsToRealizeGetApi.getById(goods_to_realize_get_url, id);
        try {
            Response<GoodsToRealizeGetDto> response = goodsToRealizeGetSync.execute();
            if (response.isSuccessful()) {
                goodsToRealizeGetDto = response.body();
                log.info("Успешно выполнен запрос на получение GoodsToRealizeGetDto по id: {}", id);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение GoodsToRealizeGetDto по id {}", response.code(), id);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение GoodsToRealizeGetDto по id", e);
        }
        return goodsToRealizeGetDto;
    }

    @Override
    public void create(GoodsToRealizeGetDto dto) {
        Call<Void> goodsToRealizeGet = goodsToRealizeGetApi.create(goods_to_realize_get_url, dto);
        try {
            Response<Void> response = goodsToRealizeGet.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание GoodsToRealizeGetDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создании GoodsToRealizeGetDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создании GoodsToRealizeGetDto", e);
        }
    }

    @Override
    public void update(GoodsToRealizeGetDto dto) {
        Call<Void> goodsToRealizeGet = goodsToRealizeGetApi.update(goods_to_realize_get_url, dto);
        try {
            Response<Void> response = goodsToRealizeGet.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на изменение GoodsToRealizeGetDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на изменении GoodsToRealizeGetDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на изменении GoodsToRealizeGetDto", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> goodsToRealizeGet = goodsToRealizeGetApi.deleteById(goods_to_realize_get_url, id);
        try {
            Response<Void> response = goodsToRealizeGet.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление GoodsToRealizeGetDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удалении GoodsToRealizeGetDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удалении GoodsToRealizeGetDto", e);
        }
    }
}
