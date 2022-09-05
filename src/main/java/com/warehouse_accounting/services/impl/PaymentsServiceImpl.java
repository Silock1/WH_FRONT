package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.PaymentsDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.PaymentsService;
import com.warehouse_accounting.services.interfaces.api.PaymentsApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;


@Log4j2
@Service
public class PaymentsServiceImpl implements PaymentsService {

    private final PaymentsApi api;
    private final String url;

    public PaymentsServiceImpl(@Value("${retrofit.restServices.payments_url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(PaymentsApi.class);
    }

    @Override
    public List<PaymentsDto> getAll() {
        Call<List<PaymentsDto>> call = api.getAll(url);
        return new ServiceUtils<>(PaymentsDto.class).getAll(call);
    }

    @Override
    public PaymentsDto getById(Long id) {
        Call<PaymentsDto> call = api.getById(url, id);
        return new ServiceUtils<>(PaymentsDto.class).getById(call, id);
    }

    @Override
    public void create(PaymentsDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(PaymentsDto.class).create(call);
    }

    @Override
    public void update(PaymentsDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(PaymentsDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(PaymentsDto.class).delete(call);
    }

}
