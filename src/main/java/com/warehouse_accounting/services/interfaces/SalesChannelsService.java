package com.warehouse_accounting.services.interfaces;


import com.warehouse_accounting.models.dto.SalesChannelDto;

import java.util.List;

public interface SalesChannelsService {

    List<SalesChannelDto> getAll();

    SalesChannelDto getById(Long id);

    void create(SalesChannelDto dto);

    void update(SalesChannelDto dto);

    void deleteById(Long id);
}
