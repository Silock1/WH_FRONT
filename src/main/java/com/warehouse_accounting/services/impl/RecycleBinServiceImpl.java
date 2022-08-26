package com.warehouse_accounting.services.impl;


import com.warehouse_accounting.models.dto.RecycleBinDto;
import com.warehouse_accounting.services.interfaces.RecycleBinService;
import com.warehouse_accounting.services.interfaces.api.RecycleBinApi;
import lombok.extern.log4j.Log4j2;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Log4j2
@Service
public class RecycleBinServiceImpl implements RecycleBinService {

    private final RecycleBinApi recycleBinApi;
    private final String recycleBinUrl;

    public RecycleBinServiceImpl(@Value("${retrofit.restServices.recycleBin_url}") String recycleBinUrl, Retrofit retrofit) {
        this.recycleBinUrl = recycleBinUrl;
        this.recycleBinApi = retrofit.create(RecycleBinApi.class);
    }

    @Override
    public List<RecycleBinDto> getAll() {
        List<RecycleBinDto> recycleBinDtoList = Collections.emptyList();
        Call<List<RecycleBinDto>> recycleBinAllCall = recycleBinApi.getAll(recycleBinUrl);
        try {
            Response<List<RecycleBinDto>> response = recycleBinAllCall.execute();
            if (response.isSuccessful()) {
                recycleBinDtoList = response.body();
                log.info("Успешно выполнен запрос на получение списка RecycleBinDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка RecycleBinDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка RecycleBinDto", e);
        }
        return recycleBinDtoList;
    }

    @Override
    public RecycleBinDto getById(Long id) {
        RecycleBinDto recycleBinDto = null;
        Call<RecycleBinDto> callSync = recycleBinApi.getById(recycleBinUrl, id);
        try {
            Response<RecycleBinDto> response = callSync.execute();
            if (response.isSuccessful()) {
                recycleBinDto = response.body();
                log.info("Успешно выполнен запрос на получение RecycleBinDto по id: {}", id);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение RecycleBinDto по id {}", response.code(), id);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение RecycleBinDto по id", e);
        }
        return recycleBinDto;
    }

    @Override
    public void create(RecycleBinDto recycleBinDto) {
        Call<Void> call = recycleBinApi.create(recycleBinUrl, recycleBinDto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание RecycleBinDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создании RecycleBinDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создании RecycleBinDto", e);
        }
    }

    @Override
    public void update(RecycleBinDto recycleBinDto) {
        Call<Void> call = recycleBinApi.update(recycleBinUrl, recycleBinDto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на изменение RecycleBinDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на изменение RecycleBinDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на изменение RecycleBinDto", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = recycleBinApi.deleteById(recycleBinUrl, id);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление RecycleBinDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление RecycleBinDto по id", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление RecycleBinDto по id", e);
        }
    }

    @Override
    public ResponseBody getExcel() {
        Call<ResponseBody> call = recycleBinApi.getExcel(recycleBinUrl + "/export/xlsx");
        ResponseBody responseBody = null;
        try {
            Response<ResponseBody> response = call.execute();
            if (response.isSuccessful()) {
                responseBody = response.body();
                log.info("Успешно выполнен запрос на получение списка RecycleBinDto в виде таблички");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка RecycleBinDto в виде таблички", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка RecycleBinDto в виде таблички", e);
        }
        return responseBody;
    }


    @Override
    public ResponseBody getPDF() {
        Call<ResponseBody> call = recycleBinApi.getPDF(recycleBinUrl + "/export/pdf");
        ResponseBody responseBody = null;
        try {
            Response<ResponseBody> response = call.execute();
            if (response.isSuccessful()) {
                responseBody = response.body();
                log.info("Успешно выполнен запрос на получение pdf RecycleBinDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение pdf RecycleBinDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение pdf RecycleBinDto", e);
        }
        return responseBody;
    }

    @Override
    public ResponseBody getTermsConditions() {
        Call<ResponseBody> call = recycleBinApi.getTermsConditions(recycleBinUrl + "/terms-conditions/application/pdf");
        ResponseBody responseBody = null;
        try {
            Response<ResponseBody> response = call.execute();
            if (response.isSuccessful()) {
                responseBody = response.body();
                log.info("Успешно выполнен запрос на получение pdf RecycleBinDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение pdf RecycleBinDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение pdf RecycleBinDto", e);
        }
        return responseBody;
    }


}
