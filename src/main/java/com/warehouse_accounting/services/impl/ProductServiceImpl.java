package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.ProductDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.ProductService;
import com.warehouse_accounting.services.interfaces.api.ProductApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductApi api;
    private final String url;

    public ProductServiceImpl (@Value("${retrofit.restServices.product_url}") String url, Retrofit retrofit){
        this.url = url;
        this.api = retrofit.create(ProductApi.class);
    }

    @Override
    public List<ProductDto> getAll() {
        Call<List<ProductDto>> call = api.getAll(url);
        return new ServiceUtils<>(ProductDto.class).getAll(call);
    }

    @Override
    public ProductDto getById(Long id) {
        Call<ProductDto> call = api.getById(url, id);
        return new ServiceUtils<>(ProductDto.class).getById(call, id);
    }

    @Override
    public void create(ProductDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(ProductDto.class).create(call);
    }

    @Override
    public void update(ProductDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(ProductDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(ProductDto.class).delete(call);
    }
}
