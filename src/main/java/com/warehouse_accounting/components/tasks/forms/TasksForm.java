package com.warehouse_accounting.components.tasks.forms;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
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


public class TasksForm extends VerticalLayout {

    private final Div parentLayer;
    private final Component returnLayer;
    private TextField numberText;
    private DatePicker deadline;
    private ComboBox<EmployeeDto> comboBox;
    private Notification notification;
    private Checkbox accessCheckbox;
    private EmployeeService employeeService;
    private ContractorService contractorService;
    private ComboBox<ContractorDto> contractorDtoComboBox;
    private Long contractorId;
    private String contractorName;



    private ContractorDto contractorDto;
    private Select<String> linkedContractor;

    public TasksForm(Div parentLayer, Component returnLayer
            , EmployeeService employeeService, ContractorService contractorService) {
        this.parentLayer = parentLayer;
        this.returnLayer = returnLayer;
        this.employeeService = employeeService;
        this.contractorService = contractorService;
        createButtons();
        createMenu();
    }

    private void createButtons() {
        HorizontalLayout buttons = new HorizontalLayout();


        Button saveButton = new Button("Сохранить", e -> {

            try {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://localhost:4446")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                TasksService tasksService = new TasksServiceImpl("/api/tasks_employee", retrofit);
                TasksDto tasksDto = new TasksDto();
                tasksDto.setDescription(numberText.getValue());
                tasksDto.setEmployeeId(comboBox.getValue().getId());
                tasksDto.setEmployeeName(comboBox.getValue().getFirstName());
                String date = DateConvertor.asText(deadline.getValue());
                tasksDto.setDeadline(date);
                tasksDto.setContractorId(contractorDtoComboBox.getValue().getId());
                tasksDto.setContractorName(contractorDtoComboBox.getValue().getName());


                tasksDto.setIsDone(accessCheckbox.getValue());
                System.out.println(tasksDto);
                tasksService.create(tasksDto);

            } catch (Exception ex) {
                notification = Notification.show("Ошибка создания Задачи");
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.setPosition(Notification.Position.BOTTOM_STRETCH);
            }

            parentLayer.removeAll();
            parentLayer.add(returnLayer);
        });

        Button closeButton = new Button("Закрыть", e -> {
            parentLayer.removeAll();
            parentLayer.add(returnLayer);
        });


        buttons.add(saveButton, closeButton);
        add(buttons);
    }


    private void createMenu() {

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

//      линия 4, контрагенты

        HorizontalLayout line4 = new HorizontalLayout();

       contractorDtoComboBox = new ComboBox<>("Свяжите с контрагентом");
       contractorDtoComboBox.setItems(contractorService.getAll());
       contractorDtoComboBox.setItemLabelGenerator(ContractorDto::getName);
       contractorDtoComboBox.setWidth("300px");
       right.add(contractorDtoComboBox);
       line4.add(left, right);

        add(line1, line2, line3, line4);
    }

}