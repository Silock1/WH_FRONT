package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.PostingDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.PostingService;
import com.warehouse_accounting.services.interfaces.api.PostingApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Service
@Log4j2
public class PostingServiceImpl implements PostingService {
    private final PostingApi api;
    private final String url;

    public PostingServiceImpl(@Value("${retrofit.restServices.posting_url}") String url, Retrofit retrofit) {
        this.api = retrofit.create(PostingApi.class);
        this.url = url;
    }

    @Override
    public List<PostingDto> getAll() {
        Call<List<PostingDto>> call = api.getAll(url);
        return new ServiceUtils<>(PostingDto.class).getAll(call);
    }

    @Override
    public PostingDto getById(Long id) {
        Call<PostingDto> call = api.getById(url, id);
        return new ServiceUtils<>(PostingDto.class).getById(call, id);
    }

    @Override
    public void create(PostingDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(PostingDto.class).create(call);
    }

    @Override
    public void update(PostingDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(PostingDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(PostingDto.class).delete(call);
    }
}