package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.ProductDto;
import com.warehouse_accounting.services.interfaces.ProductService;
import com.warehouse_accounting.services.interfaces.api.ProductApi;

import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Log4j2
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductApi productApi;
    private final String productUrl;

    public ProductServiceImpl (@Value("${retrofit.restServices.product_url}") String productUrl, Retrofit retrofit){
        this.productUrl = productUrl;
        this.productApi = retrofit.create(ProductApi.class);
    }

    @Override
    public List<ProductDto> getAll() {
        List<ProductDto> productDtoList = Collections.emptyList();
        Call<List<ProductDto>> productApiAll = productApi.getAll(productUrl);
        try {
            Response<List<ProductDto>> response = productApiAll.execute();
            if(response.isSuccessful()) {
                productDtoList = response.body();
                log.info("Успешно выполнен запрос на получение списка ProductDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка ProductDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка ProductDto", e);
        }
        return productDtoList;
    }

    @Override
    public ProductDto getById(Long id) {
        ProductDto productDto = null;
        Call<ProductDto> call = productApi.getById(productUrl, id);
        try {
            Response<ProductDto> response = call.execute();
            if(response.isSuccessful()) {
                productDto = response.body();
                log.info("Успешно выполнен запрос на получение ProductDto по id: {}", id);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение ProductDto по id: {}", response.code(),id);
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение ProductDto по id", e);
        }
        return productDto;
    }

    @Override
    public void create(ProductDto dto) {
        Call<Void> call = productApi.create(productUrl, dto);
        try {
            Response<Void> response = call.execute();
            if(response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание ProductDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создание ProductDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создание ProductDto", e);
        }
    }

    @Override
    public void update(ProductDto dto) {
        Call<Void> call = productApi.update(productUrl, dto);
        try {
            Response<Void> response = call.execute();
            if(response.isSuccessful()) {
                log.info("Успешно выполнен запрос на изменение ProductDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на изменение ProductDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на изменение ProductDto", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = productApi.deleteById(productUrl, id);
        try {
            Response<Void> response = call.execute();
            if(response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление ProductDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на удаление ProductDto по id", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление ProductDto по id", e);
        }
    }

    @PostConstruct
    public void testMethod() {
        System.out.println("******************************************************************");
        getAll().stream().forEach(dto -> System.out.println(dto.toString()));
        System.out.println("******************************************************************");
        getAll().stream().forEach(dto -> System.out.println(getById(dto.getId())));
        System.out.println("******************************************************************");
        ProductDto productDto = new ProductDto();
        productDto.setName("TestDTOcreate");
        create(productDto);
        System.out.println(getAll().stream().filter(dto -> dto.getName().compareTo("TestDTOcreate")==0).findFirst().get().toString());
        System.out.println("******************************************************************");
        productDto = getAll().stream().filter(dto -> dto.getName().compareTo("TestDTOcreate")==0).findFirst().get();
        productDto.setName("TestDTOupdate");
        System.out.println(productDto.toString());
        update(productDto);
        System.out.println(getAll().stream().filter(dto -> dto.getName().compareTo("TestDTOupdate")==0).findFirst().get().toString());
        System.out.println("******************************************************************");
        deleteById(getAll().stream().filter(dto -> dto.getName().compareTo("TestDTOupdate")==0).findFirst().get().getId());
        System.out.println("******************************************************************");
        getAll().stream().forEach(dto -> System.out.println(dto.toString()));
        System.out.println("******************************************************************");
    }
}
