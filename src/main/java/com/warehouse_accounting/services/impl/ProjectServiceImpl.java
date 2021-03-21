package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.ProjectDto;
import com.warehouse_accounting.services.interfaces.ProjectService;
import com.warehouse_accounting.services.interfaces.api.ProjectApi;
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
public class ProjectServiceImpl implements ProjectService {

    private final ProjectApi projectApi;
    private final String projectUrl;

    public ProjectServiceImpl(@Value("${retrofit.restServices.project_url}") String projectUrl, Retrofit retrofit) {
        this.projectUrl = projectUrl;
        this.projectApi = retrofit.create(ProjectApi.class);
    }

    @Override
    public List<ProjectDto> getAll() {
        List<ProjectDto> projectDtoList = Collections.emptyList();
        Call<List<ProjectDto>> projectApiAll = projectApi.getAll(projectUrl);
        try {
            Response<List<ProjectDto>> response = projectApiAll.execute();
            if (response.isSuccessful()) {
                projectDtoList = response.body();
                log.info("Успешно выполнен запрос на получение списка ProjectDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка ProjectDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка ProjectDto", e);
        }
        return projectDtoList;
    }

    @Override
    public ProjectDto getById(Long id) {
        ProjectDto projectDto = null;
        Call<ProjectDto> callSync = projectApi.getById(projectUrl, id);
        try {
            Response<ProjectDto> response = callSync.execute();
            if (response.isSuccessful()) {
                projectDto = response.body();
                log.info("Успешно выполнен запрос на получение ProjectDto по id: {}", id);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение ProjectDto по id {}", response.code(), id);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение ProjectDto по id", e);
        }
        return projectDto;
    }

    @Override
    public void create(ProjectDto projectDto) {
        Call<Void> call = projectApi.create(projectUrl, projectDto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание ProjectDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создании ProjectDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создании ProjectDto", e);
        }
    }

    @Override
    public void update(ProjectDto projectDto) {
        Call<Void> call = projectApi.update(projectUrl, projectDto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на изменение ProjectDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на изменение ProjectDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на изменение ProjectDto", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = projectApi.deleteById(projectUrl, id);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление ProjectDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление ProjectDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление ProjectDto по id", e);
        }
    }
}

