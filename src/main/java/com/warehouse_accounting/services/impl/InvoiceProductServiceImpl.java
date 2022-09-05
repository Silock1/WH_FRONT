package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.InvoiceProductDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.InvoiceProductService;
import com.warehouse_accounting.services.interfaces.api.InvoiceProductApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class InvoiceProductServiceImpl implements InvoiceProductService {

    private final InvoiceProductApi api;
    private final String url;

    public InvoiceProductServiceImpl(Retrofit retrofit, @Value("${retrofit.restServices.invoice_product_url}") String url) {
        this.api = retrofit.create(InvoiceProductApi.class);
        this.url = url;
    }

    @Override
    public List<InvoiceProductDto> getAll() {
        Call<List<InvoiceProductDto>> call = api.getAll(url);
        return new ServiceUtils<>(InvoiceProductDto.class).getAll(call);
    }

    @Override
    public InvoiceProductDto getById(Long id) {
        Call<InvoiceProductDto> call = api.getById(url, id);
        return new ServiceUtils<>(InvoiceProductDto.class).getById(call, id);
    }

    @Override
    public void create(InvoiceProductDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(InvoiceProductDto.class).create(call);
    }

    @Override
    public void update(InvoiceProductDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(InvoiceProductDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(InvoiceProductDto.class).delete(call);
    }
}
