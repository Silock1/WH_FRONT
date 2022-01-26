package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.ProductDto;

import java.util.List;

public interface ProductService {

    List<ProductDto> getAll();

    ProductDto getById(Long id);

    void create(ProductDto productDto);

    void update(ProductDto productDto);

    void deleteById(Long id);

}
