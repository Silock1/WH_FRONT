package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.TaxSystemDto;
import com.warehouse_accounting.services.interfaces.api.TaxSystemApi;
import com.warehouse_accounting.services.interfaces.TaxSystemService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class TaxSystemServiceImpl implements TaxSystemService {
    private final TaxSystemApi taxSystemApi;
    @Value("${retrofit.restServices.tax_system_url}")
    private String taxSystemUrl;

    public TaxSystemServiceImpl(Retrofit retrofit) {
        this.taxSystemApi = retrofit.create(TaxSystemApi.class);
    }

    @Override
    public List<TaxSystemDto> getAll() {
        List<TaxSystemDto> taxSystemDtoList = new ArrayList<>();
        Call<List<TaxSystemDto>> taxSystemDtoGetAllCall = taxSystemApi.getAll(taxSystemUrl);
        try {
            Response<List<TaxSystemDto>> response = taxSystemDtoGetAllCall.execute();
            if (response.isSuccessful()) {
                taxSystemDtoList = response.body();
                log.info("Успешно выполнен запрос на получение списка TaxSystemDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка TaxSystemDto",response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка TaxSystemDto", e);
        }
        return taxSystemDtoList;
    }

    @Override
    public TaxSystemDto getById(Long id) {
        TaxSystemDto taxSystemDto = new TaxSystemDto();
        Call<TaxSystemDto> taxSystemDtoGetByIdCall = taxSystemApi.getById(taxSystemUrl, id);
        try {
            Response<TaxSystemDto> response = taxSystemDtoGetByIdCall.execute();
            if (response.isSuccessful()) {
                taxSystemDto = response.body();
                log.info("Успешно выполнен запрос на получение TaxSystemDto c id = {}", id);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение TaxSystemDto c id {}",response.code(), id);
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение TaxSystemDto по id", e);

        }
        return taxSystemDto;
    }

    @Override
    public void update(TaxSystemDto taxSystemDto) {
        Call<Void> updateCall = taxSystemApi.update(taxSystemUrl, taxSystemDto);
        try {
            Response<Void> response = updateCall.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на обновление TaxSystemDto c id = {}", taxSystemDto.getId());
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на обновление TaxSystemDto",response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на обновление TaxSystemDto", e);
        }

    }

    @Override
    public void create(TaxSystemDto taxSystemDto) {
        Call<Void> createCall = taxSystemApi.create(taxSystemUrl, taxSystemDto);
        try {
            Response<Void> response = createCall.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание TaxSystemDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создание TaxSystemDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создание TaxSystemDto", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> deleteCall = taxSystemApi.deleteById(taxSystemUrl, id);
        try {
            Response<Void> response = deleteCall.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление TaxSystemDto с id = {}", id);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление TaxSystemDto по id",response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление TaxSystemDto по id", e);
        }
    }
}
