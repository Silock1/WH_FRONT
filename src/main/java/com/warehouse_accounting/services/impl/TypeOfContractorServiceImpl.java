package com.warehouse_accounting.services.impl;


import com.warehouse_accounting.model.dto.TypeOfContractorDto;
import com.warehouse_accounting.services.interfaces.api.RetrofitTypeOfContractorApi;
import com.warehouse_accounting.services.interfaces.TypeOfContractorService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class TypeOfContractorServiceImpl implements TypeOfContractorService {

    private final RetrofitTypeOfContractorApi api;
    String url;

    public TypeOfContractorServiceImpl(Retrofit retrofit, @Value("${retrofit.restServices.type_of_contractor_url}") String url) {
        this.api = retrofit.create(RetrofitTypeOfContractorApi.class);
        this.url = url;
    }

    @Override
    public List<TypeOfContractorDto> getAll() {
        List<TypeOfContractorDto> typeOfContractorDtoList = new ArrayList<>(0);
        Call<List<TypeOfContractorDto>> listCall = api.getAll(url);
        try {
            typeOfContractorDtoList = listCall.execute().body();
            log.info("Успешно выполнен запрон на получение списка type of contractors");
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка type of contractors");
        }
        return typeOfContractorDtoList;
    }


    @Override
    public TypeOfContractorDto getById(Long id) {
        TypeOfContractorDto typeOfContractorDto = new TypeOfContractorDto();
        Call<TypeOfContractorDto> call = api.getById(url, id);
        try {
            call.execute().body();
            log.info("Успешно выполнен запрон на получение type of contractors");
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса при получение  type of contractors");
        }
        return typeOfContractorDto;
    }

    @Override
    public void create(TypeOfContractorDto typeOfContractorDto) {
        Call<Void> call = api.create(url, typeOfContractorDto);
        try {
            call.execute();
            log.info("Type of contractor успешно создан");
        } catch (IOException e) {
            log.error("Произошла ошибка при создании Type of Contractor");
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.delete(url, id);
        try {
            call.execute();
            log.info("Type of contractor успешно удален");

        } catch (IOException e) {
            log.error("Произошла ошибка при удалении Type of Contractor");
        }
    }

    @Override
    public void update(TypeOfContractorDto typeOfContractorDto) {
        Call<Void> call = api.update(url, typeOfContractorDto);
        try {
            call.execute();
            log.info("Type of contractor успешно обновлен");
        } catch (IOException e) {
            log.error("Произошла ошибка при обновлении Type of Contractor");
        }
    }
}
