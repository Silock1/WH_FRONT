package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.ProductDto;
import com.warehouse_accounting.services.interfaces.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class ProductServiceImpl implements ProductService {

    @Override
    public List<ProductDto> getAll() {
        return null;
    }

    @Override
    public ProductDto getById(Long id) {
        return null;
    }

    @Override
    public void create(ProductDto productDto) {

    }

    @Override
    public void update(ProductDto productDto) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
