package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.ImageDto;
import com.warehouse_accounting.services.interfaces.ImageService;
import com.warehouse_accounting.services.interfaces.api.ImageApi;
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
            Response<List<ImageDto>> response = listCall.execute();
            if (response.isSuccessful()) {
                imageDtoList = response.body();
                log.info("Успешно выполнен запрос на получение списка ImageDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка ImageDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка ImageDto", e);
        }
        return imageDtoList;
    }

    @Override
    public ImageDto getById(Long id) {
        ImageDto imageDto = new ImageDto();
        Call<ImageDto> call = api.getById(url, id);
        try {
            Response<ImageDto> response = call.execute();
            if (response.isSuccessful()) {
                imageDto = response.body();
                log.info("Успешно выполнен запрос на получение сImageDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение ImageDto по id {}", response.code(), id);
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение ImageDto", e);
        }
        return imageDto;
    }

    @Override
    public void create(ImageDto dto) {
        Call<Void> call = api.create(url, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание ImageDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создание ImageDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создание ImageDto", e);
        }
    }

    @Override
    public void update(ImageDto dto) {
        Call<Void> call = api.update(url, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на обновление ImageDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на обновление ImageDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на обновление ImageDto", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление ImageDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление ImageDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление ImageDto", e);
        }
    }
}
