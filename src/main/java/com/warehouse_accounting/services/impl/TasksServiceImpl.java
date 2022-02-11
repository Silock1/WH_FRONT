package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.TasksDto;
import com.warehouse_accounting.services.interfaces.TasksService;
import com.warehouse_accounting.services.interfaces.api.TasksApi;
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
public class TasksServiceImpl implements TasksService {

    private final TasksApi tasksApi;
    private final String tasks_url;

    public TasksServiceImpl(@Value("${retrofit.restServices.tasks_url}") String tasks_url, Retrofit retrofit) {
        this.tasks_url = tasks_url;
        this.tasksApi = retrofit.create(TasksApi.class);
    }

    @Override
    public List<TasksDto> getAll() {
        List<TasksDto> tasksDtos = Collections.emptyList();
        Call<List<TasksDto>> tasksDtoList = tasksApi.getAll(tasks_url);
        try {
            Response<List<TasksDto>> response = tasksDtoList.execute();
            if (response.isSuccessful()) {
                tasksDtos = response.body();
                log.info("Успешно выполнен запрос на получение списка TasksDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка TasksDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка TasksDto", e);
        }
        return tasksDtos;
    }

    @Override
    public TasksDto getById(Long id) {
        TasksDto tasksDto = null;
        Call<TasksDto> tasksDtoCall = tasksApi.getById(tasks_url, id);
        try {
            Response<TasksDto> response = tasksDtoCall.execute();
            if (response.isSuccessful()) {
                tasksDto = response.body();
                log.info("Успешно выполнен запрос на получение TasksDto по id: {}", id);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение TasksDto по id {}", response.code(), id);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение TasksDto по id", e);
        }
        return tasksDto;
    }

    @Override
    public void create(TasksDto tasksDto) {
        Call<Void> tasksCall = tasksApi.create(tasks_url, tasksDto);
        try {
            Response<Void> response = tasksCall.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание TasksDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создании TasksDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создании TasksDto", e);
        }
    }

    @Override
    public void update(TasksDto tasksDto) {
        Call<Void> tasksCall = tasksApi.update(tasks_url, tasksDto);
        try {
            Response<Void> response = tasksCall.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на изменение TasksDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на изменинии TasksDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на изменении TasksDto", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> tasksCall = tasksApi.deleteById(tasks_url, id);
        try {
            Response<Void> response = tasksCall.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление TasksDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удалении TasksDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удалении TasksDto", e);
        }
    }
}
