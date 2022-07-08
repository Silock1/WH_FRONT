package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.PostingDto;

import java.util.List;

public interface PostingService {
    List<PostingDto> getAll();

    PostingDto getById(Long id);

    void create(PostingDto postingDto);

    void update(PostingDto postingDto);

    void deleteById(Long id);
}