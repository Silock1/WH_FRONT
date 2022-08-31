package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.MovementDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.MovementService;
import com.warehouse_accounting.services.interfaces.api.MovementApi;
import lombok.extern.log4j.Log4j2;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.List;

@Log4j2
@Service
public class MovementServiceImpl implements MovementService {
    private final MovementApi api;
    private final String url;

    public MovementServiceImpl(@Value("${retrofit.restServices.movement_url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(MovementApi.class);
    }

    @Override
    public List<MovementDto> getAll() {
        Call<List<MovementDto>> call = api.getAll(url);
        return new ServiceUtils<>(MovementDto.class).getAll(call);
    }

    @Override
    public MovementDto getById(Long id) {
        Call<MovementDto> call = api.getById(url, id);
        return new ServiceUtils<>(MovementDto.class).getById(call, id);
    }

    @Override
    public void create(MovementDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(MovementDto.class).create(call);
    }

    @Override
    public void update(MovementDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(MovementDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(MovementDto.class).delete(call);
    }

    @Override
    public ResponseBody getExcel() {
        Call<ResponseBody> call = api.getExcel(url + "/export/xlsx");
        ResponseBody responseBody = null;
        try {
            Response<ResponseBody> response = call.execute();
            if (response.isSuccessful()) {
                responseBody = response.body();
                log.info("Успешно выполнен запрос на получение списка MovementDto в виде таблички");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка MovementDto в виде таблички", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка MovementDto в виде таблички", e);
        }
        return responseBody;
    }
}