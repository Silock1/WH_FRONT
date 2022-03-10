package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.dadata.Example;
import com.warehouse_accounting.models.dto.dadata.Query;
import com.warehouse_accounting.services.interfaces.DadataService;
import com.warehouse_accounting.services.interfaces.api.DadataApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;

@Log4j2
@Service
public class DadataServiceImpl implements DadataService {

    private final DadataApi api;
    private final String url;
    private final String authorization;

    public DadataServiceImpl(@Value("${retrofit2.restservices.inn_request}") String url,
                             Retrofit retrofit2, @Value("${retrofit2.token}") String authorization) {
        this.url = url;
        this.api = retrofit2.create(DadataApi.class);
        this.authorization = authorization;
    }

    @Override
    public Example getExample(String inn) {
        Example example = new Example();
        Query queryInn = new Query(inn);
        Call<Example> dadataApiGetExample = api.getExample(url, queryInn, authorization);
        try {
            Response<Example> response = dadataApiGetExample.execute();
            if (response.isSuccessful()){
                example = response.body();
                log.info("Успешно выполнен запрос на получение данных по ИНН: " + inn);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение данных по ИНН", response.code());
            }
        } catch (IOException e){
            log.error("Произошла ошибка ввода-вывода при выполнении запроса на получение данных по ИНН ", e);
        }
        return example;
    }
}
