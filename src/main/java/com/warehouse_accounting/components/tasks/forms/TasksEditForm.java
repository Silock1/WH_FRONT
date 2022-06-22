package com.warehouse_accounting.components.tasks.forms;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.tasks.grids.TasksGrid;
import com.warehouse_accounting.models.dto.EmployeeDto;
import com.warehouse_accounting.models.dto.TaskDto;
import com.warehouse_accounting.models.dto.TasksDto;
import com.warehouse_accounting.services.interfaces.EmployeeService;
import com.warehouse_accounting.services.interfaces.TasksService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

@UIScope
@SpringComponent
@Log4j2
public class TasksEditForm extends VerticalLayout {


    private TextField numberText;
    private DatePicker deadline;
    private ComboBox<EmployeeDto> comboBox;
    private Notification notification;
    private Checkbox accessCheckbox;
    private EmployeeService employeeService;
    private TasksDto tasksDto;
    private TasksService tasksService;
    private Binder<TasksDto> taskEditFormBinder = new Binder<>(TasksDto.class);


    @Autowired
    public TasksEditForm(EmployeeService employeeService, TasksService tasksService) {
        this.employeeService = employeeService;
        this.tasksService = tasksService;
        createMenu();
    }

    private void createMenu() {
        HorizontalLayout topLine = new HorizontalLayout();
        Button saveButton = new Button("Сохранить");
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        saveButton.addClickListener(buttonClickEvent -> {
            if (!taskEditFormBinder.validate().isOk()) {
                return;
            }

            try {
                taskEditFormBinder.writeBean(tasksDto);
                tasksDto.setDescription(numberText.getValue());
                tasksDto.setContractorName(tasksDto.getContractorName());
                tasksDto.setDeadline(deadline.toString());
                tasksDto.setIsDone(accessCheckbox.getValue());
                System.out.println(tasksDto);
                tasksService.update(tasksDto);

            } catch (ValidationException e) {
                log.error("Ошибка валидации при создании нового этапа", e);

            }

        });

        Button closeButton = new Button("Отменить");
        TasksGrid tasksGrid = new TasksGrid(tasksService, employeeService);
        closeButton.addClickListener(c -> {
            removeAll();
            tasksGrid.refreshDate();
        });
        topLine.add(saveButton, closeButton);


// линия 1
        HorizontalLayout line1 = new HorizontalLayout();
        VerticalLayout left = new VerticalLayout();
        VerticalLayout right = new VerticalLayout();

        TextField numberText = new TextField();
        numberText.setPlaceholder("Описание Задачи");
        numberText.setWidth("500px");
        this.numberText = numberText;
        left.add(numberText);

        accessCheckbox = new Checkbox();
        accessCheckbox.setLabel("Выполнено");

        right.add(accessCheckbox);
        line1.add(left, right);

// линия 2 -
        HorizontalLayout line2 = new HorizontalLayout();

        comboBox = new ComboBox<>("Исполниель");
        comboBox.setItems(employeeService.getAll());
        comboBox.setItemLabelGenerator(EmployeeDto::getFirstName);
        comboBox.setWidth("300px");
        right.add(comboBox);

        line2.add(left, right);

// линия 3 -
        HorizontalLayout line3 = new HorizontalLayout();

        deadline = new DatePicker();
        deadline.setLabel("Срок");
        deadline.setPlaceholder("Не ограничен");
        deadline.setWidth("300px");

        right.add(deadline);
        left.setWidth("500px");

        line3.add(left, right);

        add(line1, line2, line3, topLine);
    }

//    public void build(TasksDto tasksDto1) {
//
//    }
}

