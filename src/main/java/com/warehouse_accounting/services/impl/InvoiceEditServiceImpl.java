package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.InvoiceEditDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.InvoiceEditService;
import com.warehouse_accounting.services.interfaces.api.InvoiceEditApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class InvoiceEditServiceImpl implements InvoiceEditService {

    private final InvoiceEditApi api;
    private final String url;

    public InvoiceEditServiceImpl(@Value("${retrofit.restServices.invoice_edit_url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(InvoiceEditApi.class);
    }

    @Override
    public List<InvoiceEditDto> getAll() {
        Call<List<InvoiceEditDto>> call = api.getAll(url);
        return new ServiceUtils<>(InvoiceEditDto.class).getAll(call);
    }

    @Override
    public InvoiceEditDto getById(Long id) {
        Call<InvoiceEditDto> call = api.getById(url, id);
        return new ServiceUtils<>(InvoiceEditDto.class).getById(call, id);
    }

    @Override
    public void create(InvoiceEditDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(InvoiceEditDto.class).create(call);
    }

    @Override
    public void update(InvoiceEditDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(InvoiceEditDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(InvoiceEditDto.class).delete(call);
    }
}

