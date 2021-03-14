package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.ProductGroupDto;
import com.warehouse_accounting.services.interfaces.ProductGroupService;
import com.warehouse_accounting.services.interfaces.api.ProductGroupApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Log4j2
@Service
public class ProductGroupServiceImpl implements ProductGroupService {
    private final ProductGroupApi productGroupApi;
    private final String productGroupUrl;

    public ProductGroupServiceImpl(@Value("${retrofit.restServices.product_group_url}") String productGroupUrl, Retrofit retrofit) {
        this.productGroupUrl = productGroupUrl;
        this.productGroupApi = retrofit.create(ProductGroupApi.class);
    }

    @Override
    public List<ProductGroupDto> getAll() {
        List<ProductGroupDto> productGroupDtoList = Collections.emptyList();
        Call<List<ProductGroupDto>> groupApiAll = productGroupApi.getAll(productGroupUrl);
        return getProductGroupDtos(productGroupDtoList, groupApiAll);
    }

    @Override
    public ProductGroupDto getById(Long id) {
        ProductGroupDto productGroupDto = null;
        Call<ProductGroupDto> call = productGroupApi.getById(productGroupUrl, id);
        try {
            Response<ProductGroupDto> response = call.execute();
            if (response.isSuccessful()) {
                productGroupDto = response.body();
                log.info("Успешно выполнен запрос на получение ProductGroupDto по id: {}", id);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение ProductGroupDto по id: {}", response.code(),id);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение ProductGroupDto по id", e);
        }
        return productGroupDto;
    }

    @Override
    public void create(ProductGroupDto dto) {
        Call<Void> call = productGroupApi.create(productGroupUrl, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание ProductGroupDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создание ProductGroupDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создание ProductGroupDto", e);
        }
    }

    @Override
    public void update(ProductGroupDto dto) {
        Call<Void> call = productGroupApi.update(productGroupUrl, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на изменение ProductGroupDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на изменение ProductGroupDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на изменение ProductGroupDto", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = productGroupApi.deleteById(productGroupUrl, id);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление ProductGroupDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление ProductGroupDto по id", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление ProductGroupDto по id", e);
        }
    }

    @Override
    public List<ProductGroupDto> getAllByParentGroupId(Long id) {
        List<ProductGroupDto> productGroupDtoList = Collections.emptyList();
        Call<List<ProductGroupDto>> groupApiAll = productGroupApi.getAllByParentId(productGroupUrl + "/parent", id);
        return getProductGroupDtos(productGroupDtoList, groupApiAll);
    }

    private List<ProductGroupDto> getProductGroupDtos(List<ProductGroupDto> productGroupDtoList, Call<List<ProductGroupDto>> groupApiAll) {
        try {
            Response<List<ProductGroupDto>> response = groupApiAll.execute();
            if (response.isSuccessful()) {
                productGroupDtoList = response.body();
                log.info("Успешно выполнен запрос на получение списка ProductGroupDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка ProductGroupDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка ProductGroupDto", e);
        }
        return productGroupDtoList;
    }
}