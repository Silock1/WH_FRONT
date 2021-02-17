package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.ImageDto;

import java.util.List;

public interface ImageService {

    List<ImageDto> getAll();

    ImageDto getById(Long id);

    void create(ImageDto dto);

    void update(ImageDto dto);

    void deleteById(Long id);
}
