package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.CustomerOrderDto;
import com.warehouse_accounting.models.dto.InvoiceDto;
import com.warehouse_accounting.services.interfaces.InvoiceService;
import com.warehouse_accounting.services.interfaces.api.InvoiceApi;
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
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceApi invoiceApi;
    private final String invoiceUrl;

    public InvoiceServiceImpl(@Value("${retrofit.restServices.invoice_url}") String invoiceUrl, Retrofit retrofit) {
        this.invoiceUrl = invoiceUrl;
        this.invoiceApi = retrofit.create(InvoiceApi.class);
    }

    @Override
    public List<InvoiceDto> getAll() {
        List<InvoiceDto> invoiceDtoList = Collections.emptyList();
        Call<List<InvoiceDto>> invoiceApiAll = invoiceApi.getAll(invoiceUrl);
        try {
            Response<List<InvoiceDto>> response = invoiceApiAll.execute();
            if (response.isSuccessful()) {
                invoiceDtoList = response.body();
                log.info("Успешно выполнен запрос на получение списка InvoiceDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка InvoiceDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка InvoiceDto", e);
        }
        return invoiceDtoList;
    }

    @Override
    public InvoiceDto getById(Long id) {
        InvoiceDto invoiceDto = null;
        Call<InvoiceDto> callSync = invoiceApi.getById(invoiceUrl, id);
        try {
            Response<InvoiceDto> response = callSync.execute();
            if (response.isSuccessful()) {
                invoiceDto = response.body();
                log.info("Успешно выполнен запрос на получение InvoiceDto по id: {}", id);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение InvoiceDto по id {}", response.code(), id);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение InvoiceDto по id", e);
        }
        return invoiceDto;
    }

    @Override
    public void create(CustomerOrderDto invoiceDto) {
        Call<Void> call = invoiceApi.create(invoiceUrl, invoiceDto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание InvoiceDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создании InvoiceDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создании InvoiceDto", e);
        }
    }

    @Override
    public void update(CustomerOrderDto invoiceDto) {
        Call<Void> call = invoiceApi.update(invoiceUrl, invoiceDto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на изменение InvoiceDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на изменение InvoiceDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на изменение InvoiceDto", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = invoiceApi.deleteById(invoiceUrl, id);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление InvoiceDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление InvoiceDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление InvoiceDto по id", e);
        }
    }
}

