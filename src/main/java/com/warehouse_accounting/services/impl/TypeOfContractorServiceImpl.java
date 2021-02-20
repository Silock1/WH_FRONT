package com.warehouse_accounting.services.impl;


import com.warehouse_accounting.model.dto.TypeOfContractorDto;
import com.warehouse_accounting.services.interfaces.api.RetrofitTypeOfContractorApi;
import com.warehouse_accounting.services.interfaces.TypeOfContractorService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class TypeOfContractorServiceImpl implements TypeOfContractorService {

    private final RetrofitTypeOfContractorApi api;
    private final String url;

    public TypeOfContractorServiceImpl(Retrofit retrofit, @Value("${retrofit.restServices.type_of_contractor_url}") String url) {
        this.api = retrofit.create(RetrofitTypeOfContractorApi.class);
        this.url = url;
    }

    @Override
    public List<TypeOfContractorDto> getAll() {
        List<TypeOfContractorDto> typeOfContractorDtoList = new ArrayList<>();
        Call<List<TypeOfContractorDto>> listCall = api.getAll(url);
        try {
            Response<List<TypeOfContractorDto>> response = listCall.execute();
            if (response.isSuccessful()) {
                typeOfContractorDtoList = listCall.execute().body();
                log.info("Успешно выполнен запрон на получение списка type of contractors");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка type of contractors", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка type of contractors" + e);
        }
        return typeOfContractorDtoList;
    }


    @Override
    public TypeOfContractorDto getById(Long id) {
        TypeOfContractorDto typeOfContractorDto = new TypeOfContractorDto();
        Call<TypeOfContractorDto> call = api.getById(url, id);
        try {
            Response<TypeOfContractorDto> response = call.execute();
            if (response.isSuccessful()) {
                typeOfContractorDto = response.body();
                log.info("Успешно выполнен запрон на получение type of contractors");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса при получение  type of contractors c id {}", response.code(), id);
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса при получение  type of contractors" + e);
        }
        return typeOfContractorDto;
    }

    @Override
    public void create(TypeOfContractorDto typeOfContractorDto) {
        Call<Void> call = api.create(url, typeOfContractorDto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Type of contractor успешно создан");
            } else {
                log.error("Произошла ошибка {} при создании Type of Contractor c id {}", response.code(), typeOfContractorDto.getId());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при создании Type of Contractor" + e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.delete(url, id);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Type of contractor успешно удален");
            } else {
                log.error("Произошла ошибка {} при удалении Type of Contractor c id {}", response.code(), id);
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при удалении Type of Contractor" + e);
        }
    }

    @Override
    public void update(TypeOfContractorDto typeOfContractorDto) {
        Call<Void> call = api.update(url, typeOfContractorDto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Type of contractor успешно обновлен");
            } else {
                log.error("Произошла ошибка {} при обновлении Type of Contractor c id {}", response.code(), typeOfContractorDto.getId());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при обновлении Type of Contractor" + e);
        }
    }
}
