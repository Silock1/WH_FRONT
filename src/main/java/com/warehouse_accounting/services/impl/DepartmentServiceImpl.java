package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.DepartmentDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.DepartmentService;
import com.warehouse_accounting.services.interfaces.api.DepartmentApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentApi api;
    private final String url;

    public DepartmentServiceImpl(@Value("${retrofit.restServices.department_url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(DepartmentApi.class);
    }

    @Override
    public List<DepartmentDto> getAll() {
        Call<List<DepartmentDto>> call = api.getAll(url);
        return new ServiceUtils<>(DepartmentDto.class).getAll(call);
    }

    @Override
    public DepartmentDto getById(Long id) {
        Call<DepartmentDto> call = api.getById(url, id);
        return new ServiceUtils<>(DepartmentDto.class).getById(call, id);
    }

    @Override
    public void create(DepartmentDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(DepartmentDto.class).create(call);
    }

    @Override
    public void update(DepartmentDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(DepartmentDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(DepartmentDto.class).delete(call);
    }
}
