package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.FeedDto;
import com.warehouse_accounting.services.interfaces.FeedService;
import com.warehouse_accounting.services.interfaces.api.FeedApi;
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
public class FeedServiceImpl implements FeedService {

    private final FeedApi feedApi;
    private final String feedUrl;

    public FeedServiceImpl(Retrofit retrofit, @Value("${retrofit.restServices.feed_url}") String feedUrl) {
        this.feedApi = retrofit.create(FeedApi.class);
        this.feedUrl = feedUrl;
    }

    @Override
    public List<FeedDto> getAll() {
        List<FeedDto> feedDtoList = new ArrayList<>(0);
        Call<List<FeedDto>> listCall = feedApi.getAll(feedUrl);
        try {
            Response<List<FeedDto>> response = listCall.execute();
            if (response.isSuccessful()) {
                feedDtoList = response.body();
                log.info("Успешно выполнен запрос на получение списка FeedDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка FeedDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка FeedDto", e);
        }
        return feedDtoList;
    }

    @Override
    public FeedDto getById(Long id) {
        FeedDto feedDto = new FeedDto();
        Call<FeedDto> call = feedApi.getById(feedUrl, id);
        try {
            Response<FeedDto> response = call.execute();
            if (response.isSuccessful()) {
                feedDto = response.body();
                log.info("Успешно выполнен запрос на получение FeedDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение FeedDto по id {}", response.code(), id);
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение FeedDto", e);
        }
        return feedDto;
    }

    @Override
    public void create(FeedDto dto) {
        Call<Void> call = feedApi.create(feedUrl, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание FeedDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создание FeedDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создание FeedDto", e);
        }
    }

    @Override
    public void update(FeedDto dto) {
        Call<Void> call = feedApi.update(feedUrl, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на обновление FeedDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на обновление FeedDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на обновление FeedDto", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = feedApi.deleteById(feedUrl, id);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление FeedDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление FeedDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление FeedDto", e);
        }
    }
}
