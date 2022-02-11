package com.warehouse_accounting.components.tasks.grids;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.models.dto.TasksDto;
import com.warehouse_accounting.services.interfaces.TasksService;

import java.util.HashMap;
import java.util.LinkedHashMap;

@SpringComponent
@UIScope
public class TasksGrid extends HorizontalLayout {
    private final Grid<TasksDto> taskDtoGrid = new Grid<>(TasksDto.class, false);

    public TasksGrid(TasksService tasksService) {
        taskDtoGrid.setItems(tasksService.getAll());
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
