package com.warehouse_accounting.components.tasks.grids;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.tasks.forms.TasksEditModal;
import com.warehouse_accounting.components.util.ColumnToggleContextMenu;
import com.warehouse_accounting.models.dto.TasksDto;
import com.warehouse_accounting.services.interfaces.ContractorService;
import com.warehouse_accounting.services.interfaces.EmployeeService;
import com.warehouse_accounting.services.interfaces.TasksService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

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
    private TasksService tasksService;
    private TextField numberText;
    private List<TasksDto> tasksDtoList = new ArrayList<>();
    private ContractorService contractorService;
    private EmployeeService employeeService;

    @Autowired
    public TasksGrid(TasksService tasksService, EmployeeService employeeService, ContractorService contractorService) {
        this.contractorService = contractorService;
        this.employeeService = employeeService;
        this.tasksService = tasksService;
        tasksDto = tasksService.getAll();
        taskDtoGrid.setItems(tasksDto);
        taskDtoGrid.setHeightByRows(true);
        initGrid();
    }

    private void initGrid() {
        taskDtoGrid.setColumns(getVisibleColumn().keySet().toArray(String[]::new));
        taskDtoGrid.setWidth("1000px");
        taskDtoGrid.getColumnByKey("description").setWidth("600px");
        taskDtoGrid.getStyle().set("font-size", "14px").set("line-height", "30px").set("padding-left", "30px")
                .set("background-color", "#ffffff");//.set("font-style", "bold");
        Button menuButton = new Button(new Icon(VaadinIcon.COG));
        menuButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        ColumnToggleContextMenu<TasksDto> columnToggleContextMenu = new ColumnToggleContextMenu<>(menuButton);

        getVisibleColumn().forEach((key, value) -> {
            Grid.Column<TasksDto> tasksDtoColumn = taskDtoGrid.getColumnByKey(key).setHeader(value);
            columnToggleContextMenu.addColumnToggleItem(value, tasksDtoColumn);
        });
        taskDtoGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_BORDER);
        HorizontalLayout headerLayout = new HorizontalLayout(menuButton);
        headerLayout.setAlignItems(Alignment.BASELINE);
        taskDtoGrid.addItemClickListener(e -> editButton(e.getItem()));
        add(taskDtoGrid, headerLayout);
    }

    private HashMap<String, String> getVisibleColumn() {
        HashMap<String, String> fieldNameColumnName = new LinkedHashMap<>();
        fieldNameColumnName.put("description", "Описание");
        fieldNameColumnName.put("deadline", "Срок");
        fieldNameColumnName.put("employeeName", "Исполнитель");
        fieldNameColumnName.put("contractorName", "Контрагент");
        return fieldNameColumnName;
    }

    private void editButton(TasksDto dto) {
        TasksEditModal tasksEditModal = new TasksEditModal(dto, employeeService, tasksService, contractorService);
        add(tasksEditModal);
    }
}
