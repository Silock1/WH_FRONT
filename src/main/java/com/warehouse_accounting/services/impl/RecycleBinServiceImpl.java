package com.warehouse_accounting.services.impl;


import com.warehouse_accounting.models.dto.RecycleBinDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.RecycleBinService;
import com.warehouse_accounting.services.interfaces.api.RecycleBinApi;
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
public class RecycleBinServiceImpl implements RecycleBinService {

    private final RecycleBinApi api;
    private final String url;

    public RecycleBinServiceImpl(@Value("${retrofit.restServices.recycleBin_url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(RecycleBinApi.class);
    }

    @Override
    public List<RecycleBinDto> getAll() {
        Call<List<RecycleBinDto>> call = api.getAll(url);
        return new ServiceUtils<>(RecycleBinDto.class).getAll(call);
    }

    @Override
    public RecycleBinDto getById(Long id) {
        Call<RecycleBinDto> call = api.getById(url, id);
        return new ServiceUtils<>(RecycleBinDto.class).getById(call, id);
    }

    @Override
    public void create(RecycleBinDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(RecycleBinDto.class).create(call);
    }

    @Override
    public void update(RecycleBinDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(RecycleBinDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(RecycleBinDto.class).delete(call);
    }

    @Override
    public ResponseBody getExcel() {
        Call<ResponseBody> call = api.getExcel(url + "/export/xlsx");
        return new ServiceUtils<>(RecycleBinDto.class).getResponseBody(call);
    }


    @Override
    public ResponseBody getPDF() {
        Call<ResponseBody> call = api.getPDF(url + "/export/pdf");
        return new ServiceUtils<>(RecycleBinDto.class).getResponseBody(call);
    }

    @Override
    public ResponseBody getTermsConditions() {
        Call<ResponseBody> call = api.getTermsConditions(url + "/terms-conditions/application/pdf");
        return new ServiceUtils<>(RecycleBinDto.class).getResponseBody(call);
    }


}
