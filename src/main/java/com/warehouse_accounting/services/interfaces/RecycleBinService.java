package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.RecycleBinDto;
import okhttp3.ResponseBody;

import java.util.List;
public interface RecycleBinService {

    List<RecycleBinDto> getAll();

    RecycleBinDto getById(Long id);

    void create(RecycleBinDto recycleBinDto);

    void update(RecycleBinDto recycleBinDto);

    void deleteById(Long id);

    ResponseBody getExcel();
}
