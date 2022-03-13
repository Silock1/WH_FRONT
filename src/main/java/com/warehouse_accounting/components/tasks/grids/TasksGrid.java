package com.warehouse_accounting.components.tasks.grids;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.models.dto.TasksDto;
import com.warehouse_accounting.services.interfaces.TasksService;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@SpringComponent
@UIScope
@Getter
@Setter
public class TasksGrid extends HorizontalLayout {
    private Grid<TasksDto> taskDtoGrid = new Grid<>(TasksDto.class, false);
    private List<TasksDto> tasksDto;

    public TasksGrid(TasksService tasksService) {
        tasksDto = tasksService.getAll();
        taskDtoGrid.setItems(tasksDto);
        add(initGrid());
    }

    private Grid<TasksDto> initGrid() {
        taskDtoGrid.setColumns(getVisibleColumn().keySet().toArray(String[]::new));
        getVisibleColumn().forEach((key, value) -> taskDtoGrid.getColumnByKey(key).setHeader(value));
        taskDtoGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES);
        return taskDtoGrid;
    }

    private HashMap<String, String> getVisibleColumn() {
        HashMap<String, String> fieldNameColumnName = new LinkedHashMap<>();
        fieldNameColumnName.put("id", "#");
        fieldNameColumnName.put("description", "Описание");
        fieldNameColumnName.put("deadline", "Срок");
        fieldNameColumnName.put("isDone", "Выполнение");
        fieldNameColumnName.put("employeeName", "Исполнитель");
        return fieldNameColumnName;
    }
}
