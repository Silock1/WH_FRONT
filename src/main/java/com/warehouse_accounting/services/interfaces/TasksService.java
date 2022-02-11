package com.warehouse_accounting.services.interfaces;


import com.warehouse_accounting.models.dto.TasksDto;

import java.util.List;

public interface TasksService {

    List<TasksDto> getAll();

    TasksDto getById(Long id);

    void create(TasksDto dto);

    void update(TasksDto dto);

    void deleteById(Long id);
}
