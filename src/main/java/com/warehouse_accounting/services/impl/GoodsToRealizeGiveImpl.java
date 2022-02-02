package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.GoodsToRealizeGiveDto;
import com.warehouse_accounting.services.interfaces.GoodsToRealizeGiveService;
import com.warehouse_accounting.services.interfaces.api.GoodsToRealizeGiveApi;
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
public class GoodsToRealizeGiveImpl implements GoodsToRealizeGiveService {
    private final GoodsToRealizeGiveApi goodsToRealizeGiveApi;
    private final String goods_to_realize_give_url;

    public GoodsToRealizeGiveImpl(@Value("${retrofit.restServices.goods_to_realize_get_url}") String goods_to_realize_give_url, Retrofit retrofit) {
        this.goods_to_realize_give_url = goods_to_realize_give_url;
        this.goodsToRealizeGiveApi = retrofit.create(GoodsToRealizeGiveApi.class);
    }

    @Override
    public List<GoodsToRealizeGiveDto> getAll() {
        List<GoodsToRealizeGiveDto> goodsToRealizeGiveDtos = Collections.emptyList();
        Call<List<GoodsToRealizeGiveDto>> goodsToRealizeGetApiAll = goodsToRealizeGiveApi.getAll(goods_to_realize_give_url);
        try {
            Response<List<GoodsToRealizeGiveDto>> response = goodsToRealizeGetApiAll.execute();
            if (response.isSuccessful()) {
                goodsToRealizeGiveDtos = response.body();
                log.info("Успешно выполнен запрос на получение списка GoodsToRealizeGiveDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка GoodsToRealizeGiveDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка GoodsToRealizeGiveDto", e);
        }
        return goodsToRealizeGiveDtos;
    }

    @Override
    public GoodsToRealizeGiveDto getById(Long id) {
        GoodsToRealizeGiveDto goodsToRealizeGiveDto = null;
        Call<GoodsToRealizeGiveDto> goodsToRealizeGetSync = goodsToRealizeGiveApi.getById(goods_to_realize_give_url, id);
        try {
            Response<GoodsToRealizeGiveDto> response = goodsToRealizeGetSync.execute();
            if (response.isSuccessful()) {
                goodsToRealizeGiveDto = response.body();
                log.info("Успешно выполнен запрос на получение GoodsToRealizeGiveDto по id: {}", id);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение GoodsToRealizeGiveDto по id {}", response.code(), id);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение GoodsToRealizeGiveDto по id", e);
        }
        return goodsToRealizeGiveDto;
    }


    @Override
    public void create(GoodsToRealizeGiveDto dto) {
        Call<Void> goodsToRealizeGive = goodsToRealizeGiveApi.create(goods_to_realize_give_url, dto);
        try {
            Response<Void> response = goodsToRealizeGive.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание GoodsToRealizeGiveDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создании GoodsToRealizeGiveDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создании GoodsToRealizeGiveDto", e);
        }
    }

    @Override
    public void update(GoodsToRealizeGiveDto dto) {
        Call<Void> goodsToRealizeGive = goodsToRealizeGiveApi.update(goods_to_realize_give_url, dto);
        try {
            Response<Void> response = goodsToRealizeGive.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на изменение GoodsToRealizeGiveDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на изменении GoodsToRealizeGiveDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на изменении GoodsToRealizeGiveDto", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> goodsToRealizeGive = goodsToRealizeGiveApi.deleteById(goods_to_realize_give_url, id);
        try {
            Response<Void> response = goodsToRealizeGive.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление GoodsToRealizeGiveDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удалении GoodsToRealizeGiveDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удалении GoodsToRealizeGiveDto", e);
        }
    }
}
