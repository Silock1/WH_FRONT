package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.ImageDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.ImageService;
import com.warehouse_accounting.services.interfaces.api.ImageApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class ImageServiceImpl implements ImageService {

    private final ImageApi api;
    private final String url;

    public ImageServiceImpl(Retrofit retrofit, @Value("${retrofit.restServices.image_url}") String url) {
        this.api = retrofit.create(ImageApi.class);
        this.url = url;
    }

    @Override
    public List<ImageDto> getAll() {
        Call<List<ImageDto>> call = api.getAll(url);
        return new ServiceUtils<>(ImageDto.class).getAll(call);
    }

    @Override
    public ImageDto getById(Long id) {
        Call<ImageDto> call = api.getById(url, id);
        return new ServiceUtils<>(ImageDto.class).getById(call, id);
    }

    @Override
    public void create(ImageDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(ImageDto.class).create(call);
    }

    @Override
    public void update(ImageDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(ImageDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(ImageDto.class).delete(call);
    }
}
