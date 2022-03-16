package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.dadataDto.Example2;
import com.warehouse_accounting.models.dto.dadataDto.Query;
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

    public DadataServiceImpl(@Value("${retrofit2.restServices.inn_request}") String url,
                             Retrofit retrofit2, @Value("${retrofit2.token}") String authorization) {
        this.url = url;
        this.api = retrofit2.create(DadataApi.class);
        this.authorization = authorization;
    }

    @Override
    public Example2 getExample(String inn) {
        Example2 example = new Example2();
        Query queryInn = new Query(inn);
        Call<Example2> dadataApiGetExample = api.getExample(url, queryInn, authorization);
        try {
            Response<Example2> response = dadataApiGetExample.execute();
            if (response.isSuccessful()){
                example = response.body();
                log.info("Успешно выполнен запрос на получение данных по ИНН: " + inn);

                System.out.println("---------------------");
                example.getSuggestions().forEach(suggestion -> {
                    log.info("Наименование: " + suggestion.getData().getName().getFullWithOpf());
                    log.info("ИНН/КПП/ОГРН: " + inn + "/" + suggestion.getData().getKpp()
                    + "/" + suggestion.getData().getOgrn());
                    System.out.println("---------------------");
                });
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение данных по ИНН", response.code());
            }
        } catch (IOException e){
            log.error("Произошла ошибка ввода-вывода при выполнении запроса на получение данных по ИНН ", e);
        }
        return example;
    }
}
