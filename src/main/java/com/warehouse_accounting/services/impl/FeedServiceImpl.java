package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.FeedDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.FeedService;
import com.warehouse_accounting.services.interfaces.api.FeedApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class FeedServiceImpl implements FeedService {

    private final FeedApi api;
    private final String url;

    public FeedServiceImpl(Retrofit retrofit, @Value("${retrofit.restServices.feed_url}") String url) {
        this.api = retrofit.create(FeedApi.class);
        this.url = url;
    }

    @Override
    public List<FeedDto> getAll() {
        Call<List<FeedDto>> call = api.getAll(url);
        return new ServiceUtils<>(FeedDto.class).getAll(call);
    }

    @Override
    public FeedDto getById(Long id) {
        Call<FeedDto> call = api.getById(url, id);
        return new ServiceUtils<>(FeedDto.class).getById(call, id);
    }

    @Override
    public void create(FeedDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(FeedDto.class).create(call);
    }

    @Override
    public void update(FeedDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(FeedDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(FeedDto.class).delete(call);
    }
}
