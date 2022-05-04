package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.FacturienDto;
import com.warehouse_accounting.models.dto.SupplierInvoiceDto;
import com.warehouse_accounting.services.interfaces.FacturienService;
import com.warehouse_accounting.services.interfaces.api.FacturienApi;
import com.warehouse_accounting.services.interfaces.api.SupplierInvoiceApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class FacturienServiceImpl implements FacturienService {

    private final String URL;

    private final FacturienApi service;

    public FacturienServiceImpl(@Value("${retrofit.restServices.facturien_url}") String url, Retrofit retrofit) {
        URL = url;
        this.service = retrofit.create(FacturienApi.class);
    }

    @Override
    public List<FacturienDto> getAll() {
        List<FacturienDto> result = new ArrayList<>();
        try {
            Response<List<FacturienDto>> listResponse = service.getAll(URL).execute();
            if (listResponse.isSuccessful()) {
                result = listResponse.body();
                log.info("Успешно выполнен запрос на получение списка SupplierInvoice");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка SupplierInvoice", listResponse.code());
            }
        } catch (IOException ex) {
            log.error("Произошла ошибка при выполнении запроса на получение списка SupplierInvoice", ex);
        }
        return result;
    }

    @Override
    public FacturienDto getById(Long id) {
        FacturienDto result = new FacturienDto();
        try {
            Response<FacturienDto> response = service.getById(URL, id).execute();
            if (response.isSuccessful()) {
                result = response.body();
                log.info("Успешно выполнен запрос на получение SupplierInvoice");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение SupplierInvoice по id {}", response.code(), id);
            }
        } catch (IOException ex) {
            log.error("Произошла ошибка при выполнении запроса на получение SupplierInvoice по id {}", ex, id);
        }
        return result;
    }

    @Override
    public void create(FacturienDto dto) {
        try {
            Response<Void> response = service.create(URL, dto).execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание SupplierInvoice");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создание SupplierInvoice", response.code());
            }
        } catch (IOException ex) {
            log.error("Произошла ошибка при выполнении запроса на создание SupplierInvoice", ex);
        }
    }

    @Override
    public void update(FacturienDto dto) {
        try {
            Response<Void> response = service.update(URL, dto).execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на изменение SupplierInvoice");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на изменение SupplierInvoice", response.code());
            }
        } catch (IOException ex) {
            log.error("Произошла ошибка при выполнении запроса на изменение SupplierInvoice", ex);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            Response<Void> response = service.delete(URL, id).execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление SupplierInvoice");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление SupplierInvoice с id {}", response.code(), id);
            }
        } catch (IOException ex) {
            log.error("Произошла ошибка при выполнении запроса на удаление SupplierInvoice с id {}", ex, id);
        }
    }
}
