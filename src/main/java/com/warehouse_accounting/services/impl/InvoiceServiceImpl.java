package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.InvoiceDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.InvoiceService;
import com.warehouse_accounting.services.interfaces.api.InvoiceApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceApi api;
    private final String url;

    public InvoiceServiceImpl(@Value("${retrofit.restServices.invoice_url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(InvoiceApi.class);
    }

    @Override
    public List<InvoiceDto> getAll() {
        Call<List<InvoiceDto>> call = api.getAll(url);
        return new ServiceUtils<>(InvoiceDto.class).getAll(call);
    }

    @Override
    public InvoiceDto getById(Long id) {
        Call<InvoiceDto> call = api.getById(url, id);
        return new ServiceUtils<>(InvoiceDto.class).getById(call, id);
    }

    @Override
    public void create(InvoiceDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(InvoiceDto.class).create(call);
    }

    @Override
    public void update(InvoiceDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(InvoiceDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(InvoiceDto.class).delete(call);
    }
}

