package com.warehouse_accounting.components.tasks.forms;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.tasks.grids.TasksGrid;
import com.warehouse_accounting.models.dto.ContractorDto;
import com.warehouse_accounting.models.dto.EmployeeDto;
import com.warehouse_accounting.models.dto.TaskDto;
import com.warehouse_accounting.models.dto.TasksDto;
import com.warehouse_accounting.services.impl.TasksServiceImpl;
import com.warehouse_accounting.services.interfaces.ContractorService;
import com.warehouse_accounting.services.interfaces.EmployeeService;
import com.warehouse_accounting.services.interfaces.TasksService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@UIScope
@SpringComponent
@Log4j2
public class TasksEditForm extends VerticalLayout {

    private TextField numberText;
    private DatePicker deadline;
    private ComboBox<EmployeeDto> comboBox;
    private Checkbox accessCheckbox;
    private final EmployeeService employeeService;
    private final TasksService tasksService;
    private final Binder<TasksDto> taskEditFormBinder = new Binder<>(TasksDto.class);

    private ComboBox<ContractorDto> contractorDtoComboBox;

    private final ContractorService contractorService;

    public TasksEditForm(EmployeeService employeeService, TasksService tasksService, ContractorService contractorService) {
        this.contractorService = contractorService;
        this.employeeService = employeeService;
        this.tasksService = tasksService;
        createMenu();
    }

    private void createMenu() {
        TasksGrid tasksGrid = new TasksGrid(tasksService, employeeService, contractorService);
        HorizontalLayout topLine = new HorizontalLayout();
        Button saveButton = new Button("Сохранить");
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        saveButton.addClickListener(buttonClickEvent -> {
            if (!taskEditFormBinder.validate().isOk()) {
                return;
            }

            try {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://localhost:4446")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                TasksService tasksService = new TasksServiceImpl("/api/tasks_employee", retrofit);
                TasksDto tasksDto = new TasksDto();
                tasksDto.setId(tasksService.getAll().stream().findFirst().get().getId());
                tasksDto.setDescription(numberText.getValue());
                tasksDto.setEmployeeId(comboBox.getValue().getId());
                tasksDto.setEmployeeName(comboBox.getValue().getFirstName());
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                String date = dtf.format(deadline.getValue());
                tasksDto.setDeadline(date);

                tasksDto.setContractorId(contractorDtoComboBox.getValue().getId());
                tasksDto.setContractorName(contractorDtoComboBox.getValue().getName());

                tasksDto.setIsDone(accessCheckbox.getValue());
                System.out.println(tasksDto);
                tasksService.update(tasksDto);
                removeAll();
                tasksGrid.refreshDate();
                add(tasksGrid);

            } catch (Exception e) {
                log.error("Ошибка валидации при создании нового этапа", e);

            }

        });

        Button closeButton = new Button("Отменить");
        closeButton.addClickListener(c -> {
            removeAll();
            tasksGrid.refreshDate();
            add(tasksGrid);
        });
        topLine.add(saveButton, closeButton);


// линия 1
        HorizontalLayout line1 = new HorizontalLayout();
        VerticalLayout left = new VerticalLayout();
        VerticalLayout right = new VerticalLayout();
        TextField numberText = new TextField();
        numberText.setValue(tasksService.getAll().stream().findFirst().get().getDescription());

//        numberText.setPlaceholder(tasksService.getAll().stream().findFirst().get().getDescription());
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
        comboBox.setValue(employeeService.getAll().stream().findFirst().get());
        comboBox.setItemLabelGenerator(EmployeeDto::getFirstName);
        comboBox.setWidth("300px");
        right.add(comboBox);

        line2.add(left, right);

// линия 3 -
        HorizontalLayout line3 = new HorizontalLayout();

        deadline = new DatePicker();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        LocalDate localDate = LocalDate.parse(tasksService.getAll().stream().findFirst().get().getDeadline(), formatter);
        deadline.setValue(localDate);
        deadline.setLabel("Срок");
        deadline.setPlaceholder("Не ограничен");
        deadline.setWidth("300px");

        right.add(deadline);
        left.setWidth("500px");

        line3.add(left, right);

//      линия 4, контрагенты

        HorizontalLayout line4 = new HorizontalLayout();

        contractorDtoComboBox = new ComboBox<>("Свяжите с контрагентом");
        contractorDtoComboBox.setItems(contractorService.getAll());
        contractorDtoComboBox.setValue(contractorService.getAll().stream().findFirst().get());
        contractorDtoComboBox.setItemLabelGenerator(ContractorDto::getName);
        contractorDtoComboBox.setWidth("300px");
        right.add(contractorDtoComboBox);
        line4.add(left, right);

        add(topLine, line1, line2, line3, line4);
    }

}

