package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.PrintingDocumentsDto;
import com.warehouse_accounting.services.interfaces.PrintingDocumentsService;
import com.warehouse_accounting.services.interfaces.api.PrintingDocumentsApi;
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
public class PrintingDocumentsServiceImpl implements PrintingDocumentsService {

    private final String URL;

    private final PrintingDocumentsApi service;

    public PrintingDocumentsServiceImpl(@Value("${retrofit.restServices.printing_documents_url}") String url, Retrofit retrofit) {
        this.URL = url;
        this.service = retrofit.create(PrintingDocumentsApi.class);
    }

    @Override
    public List<PrintingDocumentsDto> getAll() {
        List<PrintingDocumentsDto> result = new ArrayList<>();
        try {
            Response<List<PrintingDocumentsDto>> listResponse = service.getAll(URL).execute();
            if (listResponse.isSuccessful()) {
                result = listResponse.body();
                log.info("Успешно выполнен запрос на получение списка PrintingDocuments");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка PrintingDocuments", listResponse.code());
            }
        } catch (IOException ex) {
            log.error("Произошла ошибка при выполнении запроса на получение списка PrintingDocuments", ex);
        }
        return result;
    }

    @Override
    public PrintingDocumentsDto getById(Long id) {
        PrintingDocumentsDto result = new PrintingDocumentsDto();
        try {
            Response<PrintingDocumentsDto> response = service.getById(URL, id).execute();
            if (response.isSuccessful()) {
                result = response.body();
                log.info("Успешно выполнен запрос на получение PrintingDocuments");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение PrintingDocuments по id {}", response.code(), id);
            }
        } catch (IOException ex) {
            log.error("Произошла ошибка при выполнении запроса на получение PrintingDocuments по id {}", ex, id);
        }
        return result;
    }

    @Override
    public void create(PrintingDocumentsDto dto) {
        try {
            Response<Void> response = service.create(URL, dto).execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание PrintingDocuments");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создание PrintingDocuments", response.code());
            }
        } catch (IOException ex) {
            log.error("Произошла ошибка при выполнении запроса на создание PrintingDocuments", ex);
        }
    }

    @Override
    public void update(PrintingDocumentsDto dto) {
        try {
            Response<Void> response = service.update(URL, dto).execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на изменение PrintingDocuments");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на изменение PrintingDocuments", response.code());
            }
        } catch (IOException ex) {
            log.error("Произошла ошибка при выполнении запроса на изменение PrintingDocuments", ex);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            Response<Void> response = service.deleteById(URL, id).execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление PrintingDocuments");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление PrintingDocuments с id {}", response.code(), id);
            }
        } catch (IOException ex) {
            log.error("Произошла ошибка при выполнении запроса на удаление PrintingDocuments с id {}", ex, id);
        }
    }
}
