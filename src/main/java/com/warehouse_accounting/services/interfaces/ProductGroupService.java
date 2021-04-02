package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.ProductGroupDto;

import java.util.List;

public interface ProductGroupService {

    List<ProductGroupDto> getAll();

    ProductGroupDto getById(Long id);

    void create(ProductGroupDto productGroupDto);

    void update(ProductGroupDto productGroupDto);

    void deleteById(Long id);

    List<ProductGroupDto> getAllByParentGroupId(Long id);
}
