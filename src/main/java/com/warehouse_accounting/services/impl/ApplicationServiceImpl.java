package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.ApplicationDto;
import com.warehouse_accounting.services.interfaces.ApplicationService;
import com.warehouse_accounting.services.interfaces.api.ApplicationApi;
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
public class ApplicationServiceImpl implements ApplicationService {

    private final String URL;

    private final ApplicationApi service;

    public ApplicationServiceImpl(@Value("${retrofit.restServices.application_url}") String url, Retrofit retrofit) {
        this.URL = url;
        this.service = retrofit.create(ApplicationApi.class);
    }

    @Override
    public List<ApplicationDto> getAll() {
        List<ApplicationDto> result = new ArrayList<>();
        try {
            Response<List<ApplicationDto>> listResponse = service.getAll(URL).execute();
            if (listResponse.isSuccessful()) {
                result = listResponse.body();
                log.info("Успешно выполнен запрос на получение списка Application");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка Application", listResponse.code());
            }
        } catch (IOException ex) {
            log.error("Произошла ошибка при выполнении запроса на получение списка Application", ex);
        }
        return result;
    }

    @Override
    public ApplicationDto getById(Long id) {
        ApplicationDto result = new ApplicationDto();
        try {
            Response<ApplicationDto> response = service.getById(URL, id).execute();
            if (response.isSuccessful()) {
                result = response.body();
                log.info("Успешно выполнен запрос на получение Application");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение Application по id {}", response.code(), id);
            }
        } catch (IOException ex) {
            log.error("Произошла ошибка при выполнении запроса на получение Application по id {}", ex, id);
        }
        return result;
    }

    @Override
    public void create(ApplicationDto dto) {
        try {
            Response<Void> response = service.create(URL, dto).execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание Application");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создание Application", response.code());
            }
        } catch (IOException ex) {
            log.error("Произошла ошибка при выполнении запроса на создание Application", ex);
        }
    }

    @Override
    public void update(ApplicationDto dto) {
        try {
            Response<Void> response = service.update(URL, dto).execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на изменение Application");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на изменение Application", response.code());
            }
        } catch (IOException ex) {
            log.error("Произошла ошибка при выполнении запроса на изменение Application", ex);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            Response<Void> response = service.delete(URL, id).execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление Application");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление Application с id {}", response.code(), id);
            }
        } catch (IOException ex) {
            log.error("Произошла ошибка при выполнении запроса на удаление Application с id {}", ex, id);
        }
    }
}
