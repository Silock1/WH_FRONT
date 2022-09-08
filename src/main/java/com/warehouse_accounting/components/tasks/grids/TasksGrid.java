package com.warehouse_accounting.components.tasks.grids;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.tasks.Tasks;
import com.warehouse_accounting.components.tasks.forms.TaskModal;
import com.warehouse_accounting.components.tasks.forms.TasksEditForm;
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
//        setSizeFull();
        tasksDto = tasksService.getAll();
        taskDtoGrid.setItems(tasksDto);
        taskDtoGrid.setHeightByRows(true);
        initGrid();
    }


    private Grid<TasksDto> initGrid() {
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
//

            columnToggleContextMenu.addColumnToggleItem(value, tasksDtoColumn);

        });
        taskDtoGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_BORDER);

//        Grid.Column<TasksDto> edit = taskDtoGrid.addColumn((editMethod())).setHeader("Редактировать").setSortable(true).setAutoWidth(true);
//        Grid.Column<TasksDto> delete = taskDtoGrid.addColumn(deleteMethod()).setHeader("Удалить").setSortable(true).setAutoWidth(true);


//        columnToggleContextMenu.addColumnToggleItem("Редактировать", edit);
//        columnToggleContextMenu.addColumnToggleItem("Удалить", delete);

        HorizontalLayout headerLayout = new HorizontalLayout(menuButton);
        headerLayout.setAlignItems(Alignment.BASELINE);
//        headerLayout.setFlexGrow(1);

//        setSizeFull();
        taskDtoGrid.addItemClickListener(e -> editButton(e.getItem()));


        add(taskDtoGrid, headerLayout);

        return taskDtoGrid;
    }

    public void refreshDate() {
        removeAll();
        taskDtoGrid.setItems(tasksService.getAll());
        add(taskDtoGrid);
        setSizeFull();
    }

    private HashMap<String, String> getVisibleColumn() {
        HashMap<String, String> fieldNameColumnName = new LinkedHashMap<>();
//        fieldNameColumnName.put("id", "#");
        fieldNameColumnName.put("description", "Описание");
        fieldNameColumnName.put("deadline", "Срок");
//        fieldNameColumnName.put("isDone", "Выполнение");
        fieldNameColumnName.put("employeeName", "Исполнитель");
        fieldNameColumnName.put("contractorName", "Контрагент");
        return fieldNameColumnName;
    }
//
//    private TemplateRenderer<TasksDto> editMethod() {
//        return TemplateRenderer.<TasksDto>of(
//                        "<vaadin-button theme=\"tertiary\" on-click=\"handleClick\">Редактировать</vaadin-button>")
//                .withEventHandler("handleClick", this::editButton);
//    }
//
//    private TemplateRenderer<TasksDto> deleteMethod() {
//        return TemplateRenderer.<TasksDto>of(
//                        "<vaadin-button theme=\"tertiary\" on-click=\"handleClick\">Удалить</vaadin-button>")
//                .withEventHandler("handleClick", this::deleteButton);
//    }

    private void editButton(TasksDto dto) {
        TasksEditModal tasksEditModal = new TasksEditModal(dto, employeeService, tasksService, contractorService);
        add(tasksEditModal);
    }

    private void deleteButton(TasksDto tasksDto) {

        Button delete = new Button("Удалить");
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        Button cancel = new Button("Отмена");
        HorizontalLayout buttonLayout = new HorizontalLayout(cancel, delete);

        Dialog dialog = new Dialog();
        dialog.add("Подтвердите удаление");
        dialog.add(new VerticalLayout());
        dialog.add(buttonLayout);

        dialog.open();

        delete.addClickListener(event -> {
            tasksService.deleteById(tasksDto.getId());
            refreshDate();
            dialog.close();
        });

        cancel.addClickListener(event -> {
            dialog.close();
        });
    }


}
