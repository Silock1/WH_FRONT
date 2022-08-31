package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.ComissionerReportsDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.ComissionerReportsService;
import com.warehouse_accounting.services.interfaces.api.CommissionerReportsApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class ComissionerReportsServiceImpl implements ComissionerReportsService {

    private final CommissionerReportsApi api;
    private final String url;

    public ComissionerReportsServiceImpl(@Value("${retrofit.restServices.commissioner_reports_url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(CommissionerReportsApi.class);
    }
    // -----------------------Удалить комментарии если все норм--------------------

    //    private final String reportsUrl = "/api/comissioner_reports";
//    private final ComissionerReportsApi reportsApi = buildRetrofit().create(ComissionerReportsApi.class);
//
//    Retrofit buildRetrofit() {
//        return new Retrofit.Builder()
//            .baseUrl("http://localhost:4446")
//            .addConverterFactory(GsonConverterFactory.create()).build();
//    }

    @Override
    public List<ComissionerReportsDto> getAll() {
        Call<List<ComissionerReportsDto>> call = api.getAll(url);
        return new ServiceUtils<>(ComissionerReportsDto.class).getAll(call);
    }

    @Override
    public ComissionerReportsDto getById(Long id) {
        Call<ComissionerReportsDto> call = api.getById(url, id);
        return new ServiceUtils<>(ComissionerReportsDto.class).getById(call, id);
    }

    @Override
    public void create(ComissionerReportsDto dto) {
            Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(ComissionerReportsDto.class).create(call);
    }

    @Override
    public void update(ComissionerReportsDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(ComissionerReportsDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(ComissionerReportsDto.class).delete(call);
    }
}
