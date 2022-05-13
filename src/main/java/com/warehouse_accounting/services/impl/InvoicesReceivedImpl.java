package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.InvoicesReceivedDto;
import com.warehouse_accounting.services.interfaces.InvoicesReceivedService;
import com.warehouse_accounting.services.interfaces.api.InvoicesReceivedApi;
import lombok.extern.log4j.Log4j2;
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
public class InvoicesReceivedImpl implements InvoicesReceivedService {

    private final String URL;

    private final InvoicesReceivedApi service;

    public InvoicesReceivedImpl(@Value("${retrofit.restServices.invoice_received_url}") String URL, Retrofit retrofit) {
        this.URL = URL;
        this.service = retrofit.create(InvoicesReceivedApi.class);
    }

    @Override
    public List<InvoicesReceivedDto> getAll() {
        List<InvoicesReceivedDto> result = Collections.emptyList();
        Call<List<InvoicesReceivedDto>> roleGetAllCall = service.getAll(URL);
        try {
            Response<List<InvoicesReceivedDto>> listResponse = roleGetAllCall.execute();
            if (listResponse.isSuccessful()) {
                result = listResponse.body();
                log.info("Успешно выполнен запрос на получение списка InvoicesReceived");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка InvoicesReceived", listResponse.code());
            }
        } catch (IOException ex) {
            log.error("Произошла ошибка при выполнении запроса на получение списка InvoicesReceived", ex);
        }
        return result;
    }

    @Override
    public InvoicesReceivedDto getById(Long id) {
        InvoicesReceivedDto result = null;
        Call<InvoicesReceivedDto> callSync = service.getById(URL, id);
        try {
            Response<InvoicesReceivedDto> response = callSync.execute();
            if (response.isSuccessful()) {
                result = response.body();
                log.info("Успешно выполнен запрос на получение InvoicesReceived");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение InvoicesReceived по id {}", response.code(), id);
            }
        } catch (IOException ex) {
            log.error("Произошла ошибка при выполнении запроса на получение InvoicesReceived по id {}", ex, id);
        }
        return result;
    }

    @Override
    public void create(InvoicesReceivedDto dto) {
        Call<Void> call = service.create(URL, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание InvoicesReceived");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создание InvoicesReceived", response.code());
            }
        } catch (IOException ex) {
            log.error("Произошла ошибка при выполнении запроса на создание InvoicesReceived", ex);
        }
    }

    @Override
    public void update(InvoicesReceivedDto dto) {
        Call<Void> call = service.update(URL, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на изменение InvoicesReceived");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на изменение InvoicesReceived", response.code());
            }
        } catch (IOException ex) {
            log.error("Произошла ошибка при выполнении запроса на изменение InvoicesReceived", ex);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = service.delete(URL, id);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление InvoicesReceived");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление InvoicesReceived с id {}", response.code(), id);
            }
        } catch (IOException ex) {
            log.error("Произошла ошибка при выполнении запроса на удаление InvoicesReceived с id {}", ex, id);
        }
    }
}
