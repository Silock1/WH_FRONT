package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.GoodsToRealizeGetDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.GoodsToRealizeGetService;
import com.warehouse_accounting.services.interfaces.api.GoodsToRealizeGetApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class GoodsToRealizeGetImpl implements GoodsToRealizeGetService {

    private final GoodsToRealizeGetApi api;
    private final String url;

    public GoodsToRealizeGetImpl(@Value("${retrofit.restServices.goods_to_realize_get_url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(GoodsToRealizeGetApi.class);
    }

    @Override
    public List<GoodsToRealizeGetDto> getAll() {
        Call<List<GoodsToRealizeGetDto>> call = api.getAll(url);
        return new ServiceUtils<>(GoodsToRealizeGetDto.class).getAll(call);
    }

    @Override
    public GoodsToRealizeGetDto getById(Long id) {
        Call<GoodsToRealizeGetDto> call = api.getById(url, id);
        return new ServiceUtils<>(GoodsToRealizeGetDto.class).getById(call, id);
    }

    @Override
    public void create(GoodsToRealizeGetDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(GoodsToRealizeGetDto.class).create(call);
    }

    @Override
    public void update(GoodsToRealizeGetDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(GoodsToRealizeGetDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(GoodsToRealizeGetDto.class).delete(call);
    }
}
