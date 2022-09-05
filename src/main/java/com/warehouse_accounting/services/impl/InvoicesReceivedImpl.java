package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.InvoicesReceivedDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.InvoicesReceivedService;
import com.warehouse_accounting.services.interfaces.api.InvoicesReceivedApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class InvoicesReceivedImpl implements InvoicesReceivedService {

    private final String url;

    private final InvoicesReceivedApi api;

    public InvoicesReceivedImpl(@Value("${retrofit.restServices.invoice_received_url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(InvoicesReceivedApi.class);
    }

    @Override
    public List<InvoicesReceivedDto> getAll() {
        Call<List<InvoicesReceivedDto>> call = api.getAll(url);
        return new ServiceUtils<>(InvoicesReceivedDto.class).getAll(call);
    }

    @Override
    public InvoicesReceivedDto getById(Long id) {
        Call<InvoicesReceivedDto> call = api.getById(url, id);
        return new ServiceUtils<>(InvoicesReceivedDto.class).getById(call, id);
    }

    @Override
    public void create(InvoicesReceivedDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(InvoicesReceivedDto.class).create(call);
    }

    @Override
    public void update(InvoicesReceivedDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(InvoicesReceivedDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.delete(url, id);
        new ServiceUtils<>(InvoicesReceivedDto.class).delete(call);
    }
}
