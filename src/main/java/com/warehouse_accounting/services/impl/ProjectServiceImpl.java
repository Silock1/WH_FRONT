package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.ProjectDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.ProjectService;
import com.warehouse_accounting.services.interfaces.api.ProjectApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectApi api;
    private final String url;

    public ProjectServiceImpl(@Value("${retrofit.restServices.project_url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(ProjectApi.class);
    }

    @Override
    public List<ProjectDto> getAll() {
        Call<List<ProjectDto>> call = api.getAll(url);
        return new ServiceUtils<>(ProjectDto.class).getAll(call);
    }

    @Override
    public ProjectDto getById(Long id) {
        Call<ProjectDto> call = api.getById(url, id);
        return new ServiceUtils<>(ProjectDto.class).getById(call, id);
    }

    @Override
    public void create(ProjectDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(ProjectDto.class).create(call);
    }

    @Override
    public void update(ProjectDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(ProjectDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(ProjectDto.class).delete(call);
    }
}

