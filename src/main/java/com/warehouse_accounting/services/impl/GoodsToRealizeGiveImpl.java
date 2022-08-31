package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.GoodsToRealizeGiveDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.GoodsToRealizeGiveService;
import com.warehouse_accounting.services.interfaces.api.GoodsToRealizeGiveApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class GoodsToRealizeGiveImpl implements GoodsToRealizeGiveService {
    private final GoodsToRealizeGiveApi api;
    private final String url;

    public GoodsToRealizeGiveImpl(@Value("${retrofit.restServices.goods_to_realize_get_url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(GoodsToRealizeGiveApi.class);
    }

    @Override
    public List<GoodsToRealizeGiveDto> getAll() {
        Call<List<GoodsToRealizeGiveDto>> call = api.getAll(url);
        return new ServiceUtils<>(GoodsToRealizeGiveDto.class).getAll(call);
    }

    @Override
    public GoodsToRealizeGiveDto getById(Long id) {
        Call<GoodsToRealizeGiveDto> call = api.getById(url, id);
        return new ServiceUtils<>(GoodsToRealizeGiveDto.class).getById(call, id);
    }


    @Override
    public void create(GoodsToRealizeGiveDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(GoodsToRealizeGiveDto.class).create(call);
    }

    @Override
    public void update(GoodsToRealizeGiveDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(GoodsToRealizeGiveDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(GoodsToRealizeGiveDto.class).delete(call);
    }
}
