package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.components.RetrofitServiceGenerator;
import com.warehouse_accounting.models.dto.RoleDto;
import com.warehouse_accounting.services.interfaces.RoleService;
import com.warehouse_accounting.services.interfaces.retrofit.RoleRetrofitService;
import com.warehouse_accounting.util.exception.NotFoundEntityException;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

@Log4j2
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRetrofitService service;

    public RoleServiceImpl(RetrofitServiceGenerator generator) {
        this.service = generator.createService(RoleRetrofitService.class);
    }

    @Override
    public List<RoleDto> getAll() {
        try {
            Call<List<RoleDto>> callSync = service.getAll();
            Response<List<RoleDto>> response = callSync.execute();
            return response.body();
        } catch (Exception ex) {
            log.log(Level.ERROR, "Ошибка при получении списка ролей", ex);
            throw new NotFoundEntityException("Ошибка при получении списка ролей", ex);
        }
    }

    @Override
    public RoleDto getById(Long id) {
        try {
            Call<RoleDto> callSync = service.getById(id);
            Response<RoleDto> response = callSync.execute();
            return response.body();
        } catch (Exception ex) {
            log.log(Level.ERROR, "Ошибка при получении роли по id: {}", id, ex);
            throw new NotFoundEntityException(String.format("Ошибка при получении роли по id: %d", id), ex);
        }
    }

    @Override
    public void create(RoleDto dto) {
        try {
            Call<Void> call = service.create(dto);
            call.execute();
        } catch (IOException e) {
            log.log(Level.ERROR, "Ошибка при создании роли: {}", dto, e);
            throw new RuntimeException(String.format("Ошибка при создании роли: %s", dto), e);
        }
    }

    @Override
    public void update(RoleDto dto) {
        try {
            Call<Void> call = service.update(dto);
            call.execute();
        } catch (IOException e) {
            log.log(Level.ERROR, "Ошибка при изменении роли : {}", dto, e);
            throw new RuntimeException(String.format("Ошибка при изменении роли: %s", dto), e);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            Call<Void> call = service.deleteById(id);
            call.execute();
        } catch (IOException e) {
            log.log(Level.ERROR, "Ошибка при удалении роли по id: {}", id, e);
            throw new NotFoundEntityException(String.format("Ошибка при удалении роли по id: %d", id), e);
        }
    }
}
