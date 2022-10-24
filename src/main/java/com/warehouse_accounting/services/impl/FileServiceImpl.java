package com.warehouse_accounting.services.impl;


import com.warehouse_accounting.models.dto.FileDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.FileService;
import com.warehouse_accounting.services.interfaces.api.FileApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class FileServiceImpl implements FileService {

    private final FileApi api;
    private final String url;

    @Autowired
    public FileServiceImpl(@Value("${retrofit.restServices.file_url}") String url, Retrofit retrofit) {
        this.api = retrofit.create(FileApi.class);
        this.url = url;
    }

    @Override
    public List<FileDto> getAll() {
        Call<List<FileDto>> call = api.getAll(url);
        return new ServiceUtils<>(FileDto.class).getAll(call);
    }



    @Override
    public FileDto getById(Long id) {
        Call<FileDto> call = api.getById(url, id);
        return new ServiceUtils<>(FileDto.class).getById(call, id);
    }

    @Override
    public void create(FileDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(FileDto.class).create(call);
    }

    @Override
    public FileDto createWithResponse(FileDto dto) {
        Call<FileDto> call = api.createWithResponse(url, dto);
        return new ServiceUtils<>(FileDto.class).requestWithResponse(call);
    }

    @Override
    public void update(FileDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(FileDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(FileDto.class).delete(call);
    }

    @Override
    public List<FileDto> getFilesByTransactionId(Long id) {
       Call<List<FileDto>> call = api.getFilesByTransactionId(url, id);
       return new ServiceUtils<>(FileDto.class).getAll(call);
    }
}
