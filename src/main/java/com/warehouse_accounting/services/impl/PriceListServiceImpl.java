package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.PriceListDto;
import com.warehouse_accounting.services.interfaces.PriceListService;
import com.warehouse_accounting.services.interfaces.api.PriceListApi;
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
public class PriceListServiceImpl implements PriceListService {
    private final PriceListApi priceListApi;
    private final String priceListUrl;

    public PriceListServiceImpl(@Value("${retrofit.restServices.priceList_url}") String priceListUrl, Retrofit retrofit) {
        this.priceListUrl = priceListUrl;
        this.priceListApi = retrofit.create(PriceListApi.class);
    }

    @Override
    public List<PriceListDto> getAll() {
        List<PriceListDto> priceListDtoList = new ArrayList<>();
        Call<List<PriceListDto>> listCall = priceListApi.getAll(priceListUrl);
        try {
            Response<List<PriceListDto>> response = listCall.execute();
            if (response.isSuccessful()) {
                priceListDtoList = response.body();
                log.info("Успешно выполнен запрос на получение списка PriceListDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка PriceListDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка {} при выполнении запроса на получение списка PriceListDto", e);
        }
        return priceListDtoList;
    }

    @Override
    public PriceListDto getById(Long id) {
        PriceListDto priceListDto = new PriceListDto();
        Call<PriceListDto> priceListDtoCall = priceListApi.getById(priceListUrl, id);
        try {
            Response<PriceListDto> response = priceListDtoCall.execute();
            if (response.isSuccessful()) {
                priceListDto = response.body();
                log.info("Успешно выполнен запрос на получение PriceListDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение PriceListDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение PriceListDto", e);
        }
        return priceListDto;
    }

    @Override
    public void create(PriceListDto dto) {
        Call<Void> call = priceListApi.create(priceListUrl, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание PriceListDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создание PriceListDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создание PriceListDto", e);
        }
    }

    @Override
    public void update(PriceListDto dto) {
        Call<Void> call = priceListApi.update(priceListUrl, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на обновление PriceListDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на обновление PriceListDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на обновление PriceListDto", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = priceListApi.deleteById(priceListUrl, id);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление PriceListDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление PriceListDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление PriceListDto", e);
        }
    }
}

