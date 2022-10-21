package com.warehouse_accounting.services.interfaces;


import com.warehouse_accounting.models.dto.FileDto;

import java.util.List;

public interface FileService {
    List<FileDto> getAll();

    FileDto getById(Long id);

    void create(FileDto dto);

    FileDto createWithResponse(FileDto dto);

    void update(FileDto dto);

    void deleteById(Long id);

    List<FileDto> getFilesByTransactionId(Long id);

}
