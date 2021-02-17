package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.TaxSystemDto;
import com.warehouse_accounting.services.intarfaces.api.TaxSystemApi;
import com.warehouse_accounting.services.intarfaces.TaxSystemService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import retrofit2.Call;
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
        Call<List<TaxSystemDto>> taxSystemDtoGetAllCall =  taxSystemApi.getAll(taxSystemUrl);
        try {
            taxSystemDtoList = taxSystemDtoGetAllCall.execute().body();
            log.info("Успешно выполнен запрос на получение списка TaxSystemDto");
        } catch (IOException ioException) {
            log.error("Ошибка при получении списка TaxSystemDto");
        }
        return taxSystemDtoList;
    }

    @Override
    public TaxSystemDto getById(Long id) {
        TaxSystemDto taxSystemDto = new TaxSystemDto();
        Call<TaxSystemDto> taxSystemDtoGetByIdCall =  taxSystemApi.getById(taxSystemUrl, id);
        try {
            taxSystemDto = taxSystemDtoGetByIdCall.execute().body();
            log.info("Успешно выполнен запрос на получение TaxSystemDto c id = {}", id);
        } catch (IOException ioException) {
            log.error("Ошибка при получении TaxSystemDto c id = {}", id);
        }
        return taxSystemDto;
    }

    @Override
    public void update(TaxSystemDto taxSystemDto) {
        Call<Void> updateCall = taxSystemApi.update(taxSystemUrl, taxSystemDto);
        try {
            updateCall.execute();
            log.info("Успешно выполнен запрос на обновление TaxSystemDto c id = {}", taxSystemDto.getId());
        } catch (IOException ioException) {
            log.error("Ошибка при обновлении TaxSystemDto c id = {}", taxSystemDto.getId());
        }
    }

    @Override
    public void create(TaxSystemDto taxSystemDto) {
        Call<Void> createCall = taxSystemApi.create(taxSystemUrl, taxSystemDto);
        try {
            createCall.execute();
            log.info("Успешно выполнен запрос на создание {}", taxSystemDto);
        } catch (IOException ioException) {
            log.error("Ошибка при создании {}", taxSystemDto);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> deleteCall = taxSystemApi.deleteById(taxSystemUrl, id);
        try {
            deleteCall.execute();
            log.info("Успешно выполнен запрос на удаление TaxSystemDto с id = {}", id);
        } catch (IOException ioException) {
            log.error("Ошибка при удалении TaxSystemDto с id = {}", id);
        }
    }
}
