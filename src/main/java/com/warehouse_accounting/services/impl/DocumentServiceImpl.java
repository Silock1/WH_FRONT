package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.DocumentDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.DocumentService;
import com.warehouse_accounting.services.interfaces.api.DocumentApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class DocumentServiceImpl implements DocumentService<DocumentDto> {

    private final String url;

    private final DocumentApi api;

    public DocumentServiceImpl(@Value("${retrofit.restServices.documents_url}") String url, Retrofit retrofit) {
        api = retrofit.create(DocumentApi.class);
        this.url = url;
    }


    @Override
    public List<DocumentDto> getAll() {
        Call<List<DocumentDto>> call = api.getAll(url);
        return new ServiceUtils<>(DocumentDto.class).getAll(call);
    }

    @Override
    public DocumentDto getById(Long id) {
        Call<DocumentDto> call = api.getById(url, id);
        return new ServiceUtils<>(DocumentDto.class).getById(call, id);
    }

    @Override
    public void create(DocumentDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(DocumentDto.class).create(call);
    }

    @Override
    public void update(DocumentDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(DocumentDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(DocumentDto.class).delete(call);
    }
}
