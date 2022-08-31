package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.PrintingDocumentsDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.PrintingDocumentsService;
import com.warehouse_accounting.services.interfaces.api.PrintingDocumentsApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class PrintingDocumentsServiceImpl implements PrintingDocumentsService {

    private final String url;

    private final PrintingDocumentsApi api;

    public PrintingDocumentsServiceImpl(@Value("${retrofit.restServices.printing_documents_url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(PrintingDocumentsApi.class);
    }

    @Override
    public List<PrintingDocumentsDto> getAll() {
        Call<List<PrintingDocumentsDto>> call = api.getAll(url);
        return new ServiceUtils<>(PrintingDocumentsDto.class).getAll(call);
    }

    @Override
    public PrintingDocumentsDto getById(Long id) {
        Call<PrintingDocumentsDto> call = api.getById(url, id);
        return new ServiceUtils<>(PrintingDocumentsDto.class).getById(call, id);
    }

    @Override
    public void create(PrintingDocumentsDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(PrintingDocumentsDto.class).create(call);
    }

    @Override
    public void update(PrintingDocumentsDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(PrintingDocumentsDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(PrintingDocumentsDto.class).delete(call);
    }
}
