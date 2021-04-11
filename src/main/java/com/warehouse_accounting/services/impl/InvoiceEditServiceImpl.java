package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.InvoiceEditDto;
import com.warehouse_accounting.services.interfaces.InvoiceEditService;
import com.warehouse_accounting.services.interfaces.api.InvoiceEditApi;
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
public class InvoiceEditServiceImpl implements InvoiceEditService {

    private final InvoiceEditApi invoiceEditApi;
    private final String invoiceEditUrl;

    public InvoiceEditServiceImpl(@Value("${retrofit.restServices.invoice_edit_url}") String invoiceEditUrl, Retrofit retrofit) {
        this.invoiceEditUrl = invoiceEditUrl;
        this.invoiceEditApi = retrofit.create(InvoiceEditApi.class);
    }

    @Override
    public List<InvoiceEditDto> getAll() {
        List<InvoiceEditDto> invoiceEditDtoList = Collections.emptyList();
        Call<List<InvoiceEditDto>> invoiceEditApiAll = invoiceEditApi.getAll(invoiceEditUrl);
        try {
            Response<List<InvoiceEditDto>> response = invoiceEditApiAll.execute();
            if (response.isSuccessful()) {
                invoiceEditDtoList = response.body();
                log.info("Успешно выполнен запрос на получение списка InvoiceEditDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка InvoiceEditDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка InvoiceEditDto", e);
        }
        return invoiceEditDtoList;
    }

    @Override
    public InvoiceEditDto getById(Long id) {
        InvoiceEditDto invoiceEditDto = null;
        Call<InvoiceEditDto> callSync = invoiceEditApi.getById(invoiceEditUrl, id);
        try {
            Response<InvoiceEditDto> response = callSync.execute();
            if (response.isSuccessful()) {
                invoiceEditDto = response.body();
                log.info("Успешно выполнен запрос на получение InvoiceEditDto по id: {}", id);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение InvoiceEditDto по id {}", response.code(), id);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение InvoiceEditDto по id", e);
        }
        return invoiceEditDto;
    }

    @Override
    public void create(InvoiceEditDto invoiceEditDto) {
        Call<Void> call = invoiceEditApi.create(invoiceEditUrl, invoiceEditDto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание InvoiceEditDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создании InvoiceEditDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создании InvoiceEditDto", e);
        }
    }

    @Override
    public void update(InvoiceEditDto invoiceEditDto) {
        Call<Void> call = invoiceEditApi.update(invoiceEditUrl, invoiceEditDto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на изменение InvoiceEditDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на изменение InvoiceEditDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на изменение InvoiceEditDto", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = invoiceEditApi.deleteById(invoiceEditUrl, id);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление InvoiceEditDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление InvoiceEditDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление InvoiceEditDto по id", e);
        }
    }
}

