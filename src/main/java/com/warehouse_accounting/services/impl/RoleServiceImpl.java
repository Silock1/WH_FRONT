package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.RoleDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.RoleService;
import com.warehouse_accounting.services.interfaces.api.RoleApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleApi api;
    private final String url;

    public RoleServiceImpl(@Value("${retrofit.restServices.role_url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(RoleApi.class);
    }

    @Override
    public List<RoleDto> getAll() {
        Call<List<RoleDto>> call = api.getAll(url);
        return new ServiceUtils<>(RoleDto.class).getAll(call);
    }

    @Override
    public RoleDto getById(Long id) {
        Call<RoleDto> call = api.getById(url, id);
        return new ServiceUtils<>(RoleDto.class).getById(call, id);
    }

    @Override
    public void create(RoleDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(RoleDto.class).create(call);
    }

    @Override
    public void update(RoleDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(RoleDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(RoleDto.class).delete(call);
    }
}
