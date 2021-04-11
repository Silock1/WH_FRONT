package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.InvoiceProductDto;
import com.warehouse_accounting.services.interfaces.InvoiceProductService;
import com.warehouse_accounting.services.interfaces.api.InvoiceProductApi;
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
public class InvoiceProductServiceImpl implements InvoiceProductService {

    private final InvoiceProductApi invoiceProductApi;
    private final String invoiceProductUrl;

    public InvoiceProductServiceImpl(Retrofit retrofit, @Value("${retrofit.restServices.invoice_product_url}") String invoiceProductUrl) {
        this.invoiceProductApi = retrofit.create(InvoiceProductApi.class);
        this.invoiceProductUrl = invoiceProductUrl;
    }

    @Override
    public List<InvoiceProductDto> getAll() {
        List<InvoiceProductDto> invoiceProductDtoList = new ArrayList<>(0);
        Call<List<InvoiceProductDto>> listCall = invoiceProductApi.getAll(invoiceProductUrl);
        try {
            Response<List<InvoiceProductDto>> response = listCall.execute();
            if (response.isSuccessful()) {
                invoiceProductDtoList = response.body();
                log.info("Успешно выполнен запрос на получение списка InvoiceProductDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка InvoiceProductDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка InvoiceProductDto", e);
        }
        return invoiceProductDtoList;
    }

    @Override
    public InvoiceProductDto getById(Long id) {
        InvoiceProductDto invoiceProductDto = new InvoiceProductDto();
        Call<InvoiceProductDto> call = invoiceProductApi.getById(invoiceProductUrl, id);
        try {
            Response<InvoiceProductDto> response = call.execute();
            if (response.isSuccessful()) {
                invoiceProductDto = response.body();
                log.info("Успешно выполнен запрос на получение InvoiceProductDto по id: {}", id);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение InvoiceProductDto по id {}", response.code(), id);
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение InvoiceProductDto", e);
        }
        return invoiceProductDto;
    }

    @Override
    public void create(InvoiceProductDto dto) {
        Call<Void> call = invoiceProductApi.create(invoiceProductUrl, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание InvoiceProductDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создание InvoiceProductDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создание InvoiceProductDto", e);
        }
    }

    @Override
    public void update(InvoiceProductDto dto) {
        Call<Void> call = invoiceProductApi.update(invoiceProductUrl, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на обновление InvoiceProductDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на обновление InvoiceProductDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на обновление InvoiceProductDto", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = invoiceProductApi.deleteById(invoiceProductUrl, id);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление InvoiceProductDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление InvoiceProductDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление InvoiceProductDto", e);
        }
    }
}
