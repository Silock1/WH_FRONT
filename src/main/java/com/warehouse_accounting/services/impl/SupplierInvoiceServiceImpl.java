package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.SupplierInvoiceDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.SupplierInvoiceService;
import com.warehouse_accounting.services.interfaces.api.SupplierInvoiceApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class SupplierInvoiceServiceImpl implements SupplierInvoiceService {

    private final String url;
    private final SupplierInvoiceApi api;

    public SupplierInvoiceServiceImpl(@Value("${retrofit.restServices.supplier_invoice_url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(SupplierInvoiceApi.class);
    }

    @Override
    public List<SupplierInvoiceDto> getAll() {
        Call<List<SupplierInvoiceDto>> call = api.getAll(url);
        return new ServiceUtils<>(SupplierInvoiceDto.class).getAll(call);
    }

    @Override
    public SupplierInvoiceDto getById(Long id) {
        Call<SupplierInvoiceDto> call = api.getById(url, id);
        return new ServiceUtils<>(SupplierInvoiceDto.class).getById(call, id);
    }

    @Override
    public void create(SupplierInvoiceDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(SupplierInvoiceDto.class).create(call);
    }

    @Override
    public void update(SupplierInvoiceDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(SupplierInvoiceDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.delete(url, id);
        new ServiceUtils<>(SupplierInvoiceDto.class).delete(call);
    }
}
