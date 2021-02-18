package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.ImageDto;
import com.warehouse_accounting.services.interfaces.ImageService;
import com.warehouse_accounting.services.interfaces.api.ImageApi;
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
public class ImageServiceImpl implements ImageService {

    private final ImageApi api;
    private final String url;

    public ImageServiceImpl(Retrofit retrofit, @Value("${retrofit.restServices.image_url}") String url) {
        this.api = retrofit.create(ImageApi.class);
        this.url = url;
    }

    @Override
    public List<ImageDto> getAll() {
        List<ImageDto> imageDtoList = new ArrayList<>(0);
        Call<List<ImageDto>> listCall = api.getAll(url);
        try {
            imageDtoList = listCall.execute().body();
            log.info("Успешно выполнен запрос на получение списка ImageDto");
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка ImageDto");
        }
        return imageDtoList;
    }

    @Override
    public ImageDto getById(Long id) {
        ImageDto imageDto = new ImageDto();
        Call<ImageDto> call = api.getById(url,id);
        try {
            call.execute().body();
            log.info("Успешно выполнен запрос на получение сImageDto");
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение ImageDto");
        }
        return imageDto;
    }

    @Override
    public void create(ImageDto dto) {
        Call<Void> call = api.create(url, dto);
        try {
            call.execute();
            log.info("Успешно выполнен запрос на создание ImageDto");
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создание ImageDto");
        }
    }

    @Override
    public void update(ImageDto dto) {
        Call<Void> call = api.update(url, dto);
        try {
            call.execute();
            log.info("Успешно выполнен запрос на обновление ImageDto");
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на обновление ImageDto");
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        try {
            call.execute();
            log.info("Успешно выполнен запрос на удаление ImageDto");
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление ImageDto");
        }
    }
}
