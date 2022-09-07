package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.TasksDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.TasksService;
import com.warehouse_accounting.services.interfaces.api.TasksApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class TasksServiceImpl implements TasksService {

    private final TasksApi api;
    private final String url;

    public TasksServiceImpl(@Value("${retrofit.restServices.tasks_url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(TasksApi.class);
    }

    @Override
    public List<TasksDto> getAll() {
        Call<List<TasksDto>> call = api.getAll(url);
        return new ServiceUtils<>(TasksDto.class).getAll(call);
    }

    @Override
    public TasksDto getById(Long id) {
        Call<TasksDto> call = api.getById(url, id);
        return new ServiceUtils<>(TasksDto.class).getById(call, id);
    }

    @Override
    public void create(TasksDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(TasksDto.class).create(call);
    }

    @Override
    public void update(TasksDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(TasksDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(TasksDto.class).delete(call);
    }
}
