package com.warehouse_accounting.components.tasks.forms;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.warehouse_accounting.components.util.DateConvertor;
import com.warehouse_accounting.models.dto.ContractorDto;
import com.warehouse_accounting.models.dto.EmployeeDto;
import com.warehouse_accounting.models.dto.TasksDto;
import com.warehouse_accounting.services.impl.TasksServiceImpl;
import com.warehouse_accounting.services.interfaces.ContractorService;
import com.warehouse_accounting.services.interfaces.EmployeeService;
import com.warehouse_accounting.services.interfaces.TasksService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TaskModal extends Div {

    private final EmployeeService employeeService;
    private final ContractorService contractorService;
    private Notification notification;
    private TextArea description;
    private DatePicker deadline;
    private ComboBox<EmployeeDto> employeeName;
    private ComboBox<ContractorDto> contractorName;
    private Checkbox accessCheckbox;
    private Dialog dialog;

    public TaskModal(EmployeeService employeeService, ContractorService contractorService) {

        this.employeeService = employeeService;
        this.contractorService = contractorService;
        dialog = new Dialog();
        VerticalLayout dialogLayout = createMenu();
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialog.add(dialogLayout);
        add(dialog);
        dialog.open();
    }

    private VerticalLayout createMenu() {
// кнопки
        VerticalLayout modalWindow = new VerticalLayout();
        Button saveButton = new Button("Сохранить", e -> saveTask());
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        Button closeButton = new Button("Закрыть", e -> dialog.close());
        HorizontalLayout buttons = new HorizontalLayout(saveButton, closeButton);

// линия 1
        HorizontalLayout line1 = new HorizontalLayout();
        VerticalLayout left = new VerticalLayout();
        VerticalLayout right = new VerticalLayout();
        left.setWidth("400px");
        right.setWidth("20em");

        description = new TextArea("Описание задачи");
        description.setWidthFull();
        description.setHeightFull();
        description.getStyle().set("background-color", "#ffffff");
        left.add(description);
        accessCheckbox = new Checkbox();
        accessCheckbox.setLabel("Выполнено");
        right.add(accessCheckbox);
        right.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.END);
        line1.setSizeFull();
        line1.add(left, right);

// линия 2 Исполнитель
        HorizontalLayout line2 = new HorizontalLayout();
        employeeName = new ComboBox<>("Исполнитель");
        employeeName.setRequiredIndicatorVisible(true);
        employeeName.setAllowCustomValue(false);
        employeeName.setPlaceholder("Field is required");
        employeeName.setItems(employeeService.getAll());
        employeeName.setItemLabelGenerator(EmployeeDto::getFirstName);
        employeeName.setWidth("200px");
        employeeName.getStyle().set("background-color", "#ffffff");
        right.add(employeeName);
        line2.add(left, right);

// линия 3 Дата
        HorizontalLayout line3 = new HorizontalLayout();
        deadline = new DatePicker();
        deadline.setLabel("Срок");
        deadline.setPlaceholder("Не ограничен");
        deadline.setWidth("200px");
        deadline.getStyle().set("background-color", "#ffffff");
        right.add(deadline);
        line3.add(left, right);

// линия 4 Контрагенты
        HorizontalLayout line4 = new HorizontalLayout();
        contractorName = new ComboBox<>("Свяжите с контрагентом");
        contractorName.setItems(contractorService.getAll());
        contractorName.setItemLabelGenerator(ContractorDto::getName);
        contractorName.setWidth("200px");
        contractorName.getStyle().set("background-color", "#ffffff");
        right.add(contractorName);
        line4.add(left, right);
        modalWindow.add(buttons, line1, line2, line3, line4);

        return modalWindow;
    }

    private void saveTask() {
        notification = null;
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://localhost:4446")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            TasksService tasksService = new TasksServiceImpl("/api/tasks_employee", retrofit);
            TasksDto tasksDto = new TasksDto();
            tasksDto.setDescription(description.getValue());
            tasksDto.setEmployeeId(employeeName.getValue().getId());
            tasksDto.setEmployeeName(employeeName.getValue().getFirstName());
            if (deadline.getValue() != null) {
                tasksDto.setDeadline(DateConvertor.asText(deadline.getValue()));
            }
            if (contractorName.getValue() != null) {
                tasksDto.setContractorId(contractorName.getValue().getId());
                tasksDto.setContractorName(contractorName.getValue().getName());
            }
            tasksDto.setIsDone(accessCheckbox.getValue());
            System.out.println(tasksDto);
            tasksService.create(tasksDto);
            UI.getCurrent().getPage().reload();
        } catch (Exception ex) {
            notification = Notification.show("Ошибка создания Задачи");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.setPosition(Notification.Position.BOTTOM_STRETCH);
        }
    }
}
