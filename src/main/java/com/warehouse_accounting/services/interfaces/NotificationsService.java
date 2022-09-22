package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.NotificationsDto;

import java.util.List;

public interface NotificationsService {
    List<NotificationsDto> getAll();

    NotificationsDto getById(Long id);

    void create(NotificationsDto dto);

    void update(NotificationsDto dto);

    void deleteById(Long id);
}
