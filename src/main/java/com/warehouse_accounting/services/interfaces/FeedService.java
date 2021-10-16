package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.FeedDto;

import java.util.List;

public interface FeedService {

    List<FeedDto> getAll();

    FeedDto getById(Long id);

    void create(FeedDto dto);

    void update(FeedDto dto);

    void deleteById(Long id);
}
