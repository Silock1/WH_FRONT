package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.ProductGroupDto;
import com.warehouse_accounting.services.ServiceUtils;
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
    private final ProductGroupApi api;
    private final String url;

    public ProductGroupServiceImpl(@Value("${retrofit.restServices.product_group_url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(ProductGroupApi.class);
    }

    @Override
    public List<ProductGroupDto> getAll() {
        Call<List<ProductGroupDto>> call = api.getAll(url);
        return new ServiceUtils<>(ProductGroupDto.class).getAll(call);
    }

    @Override
    public ProductGroupDto getById(Long id) {
        Call<ProductGroupDto> call = api.getById(url, id);
        return new ServiceUtils<>(ProductGroupDto.class).getById(call, id);
    }

    @Override
    public void create(ProductGroupDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(ProductGroupDto.class).create(call);
    }

    @Override
    public void update(ProductGroupDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(ProductGroupDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(ProductGroupDto.class).delete(call);
    }

    @Override
    public List<ProductGroupDto> getAllByParentGroupId(Long id) {
        List<ProductGroupDto> productGroupDtoList = Collections.emptyList();
        Call<List<ProductGroupDto>> groupApiAll = api.getAllByParentId(url + "/parent", id);
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