package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.DocumentDto;
import com.warehouse_accounting.services.interfaces.DocumentService;
import com.warehouse_accounting.services.interfaces.api.DocumentApi;
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
public class DocumentServiceImpl implements DocumentService<DocumentDto> {

    private final String URL;

    private final DocumentApi documentApi;

    public DocumentServiceImpl(@Value("${retrofit.restServices.documents_url}") String url, Retrofit retrofit) {
        documentApi = retrofit.create(DocumentApi.class);
        URL = url;
    }


    @Override
    public List<DocumentDto> getAll() {
        List<DocumentDto> result = new ArrayList<>();
        try {
            Response<List<DocumentDto>> listResponse = documentApi.getAll(URL).execute();
            if (listResponse.isSuccessful()) {
                result = listResponse.body();
                log.info("Успешно выполнен запрос на получение списка Document");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка Document", listResponse.code());
            }
        } catch (IOException exception) {
            log.error("Произошла ошибка при выполнении запроса на получение списка Document", exception);
        }
        return result;
    }

    @Override
    public DocumentDto getById(Long id) {
        DocumentDto result = new DocumentDto();
        try {
            Response<DocumentDto> response = documentApi.getById(URL, id).execute();
            if (response.isSuccessful()) {
                result = response.body();
                log.info("Успешно выполнен запрос на получение Document");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение Document по id {}", response.code(), id);
            }
        } catch (IOException exception) {
            log.error("Произошла ошибка при выполнении запроса на получение Document по id", exception);
        }
        return result;
    }

    @Override
    public void create(DocumentDto dto) {
        try {
            Response<Void> response = documentApi.create(URL, dto).execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание Document");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создане Document", response.code());
            }
        } catch (IOException exception) {
            log.error("Произошла ошибка при выполнении запроса на создане Document", exception);
        }
    }

    @Override
    public void update(DocumentDto dto) {
        try {
            Response<Void> response = documentApi.update(URL, dto).execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на изменение Document");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на изменение Document", response.code());
            }
        } catch (IOException exception) {
            log.error("Произошла ошибка при выполнении запроса на изменение Document", exception);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            Response<Void> response = documentApi.deleteById(URL, id).execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление Document");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление Document по id: " + id, response.code());
            }
        } catch (IOException exception) {
            log.error("Произошла ошибка при выполнении запроса на удаление Document по id", exception);
        }
    }
}
