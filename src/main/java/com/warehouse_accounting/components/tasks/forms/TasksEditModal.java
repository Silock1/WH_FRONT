package com.warehouse_accounting.components.tasks.forms;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
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
import lombok.extern.log4j.Log4j2;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


@Log4j2
public class TasksEditModal extends Div {

    private final ContractorService contractorService;
    private final TasksService tasksService;
    private final EmployeeService employeeService;
    private TextArea description;
    private DatePicker deadline;
    private ComboBox<EmployeeDto> employeeName;
    private ComboBox<ContractorDto> contractorName;
    private Checkbox accessCheckbox;
    private final Dialog dialog;
    private final TasksDto task;

    public TasksEditModal(TasksDto task, EmployeeService employeeService, TasksService tasksService, ContractorService contractorService) {
        this.task = task;
        this.contractorService = contractorService;
        this.employeeService = employeeService;
        this.tasksService = tasksService;

        VerticalLayout dialogLayout = createMenu();
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialog = new Dialog(dialogLayout);
        add(dialog);
        dialog.open();
    }

    private VerticalLayout createMenu() {
// ????????????
        VerticalLayout modalWindow = new VerticalLayout();
        modalWindow.addClassName("editModal");
        Button saveButton = new Button("??????????????????", e -> updateTask());
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        Button closeButton = new Button("??????????????", event -> dialog.close());
        Button deleteButton = new Button("??????????????", event -> deleteButton(task));
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        HorizontalLayout buttons = new HorizontalLayout();
        buttons.add(saveButton, closeButton, deleteButton);

// ?????????? 1
        HorizontalLayout line1 = new HorizontalLayout();
        VerticalLayout left = new VerticalLayout();
        VerticalLayout right = new VerticalLayout();
        left.setWidth("400px");
        right.setWidth("20em");

        description = new TextArea("???????????????? ????????????");
        description.setWidthFull();
        description.setHeightFull();
        description.getStyle().set("background-color", "#ffffff");
        description.setValue(task.getDescription());
        left.add(description, commentField());
        accessCheckbox = new Checkbox("??????????????????");
//        accessCheckbox.setLabel("??????????????????");
        right.add(accessCheckbox);
        right.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.END);
        line1.setSizeFull();
        line1.add(left, right);

// ?????????? 2 ??????????????????????
        HorizontalLayout line2 = new HorizontalLayout();
        employeeName = new ComboBox<>("??????????????????????");
        employeeName.setRequiredIndicatorVisible(true);
        employeeName.setAllowCustomValue(false);
        employeeName.setPlaceholder("Field is required");
        employeeName.setItems(employeeService.getAll());
        employeeName.setValue(employeeService.getById(task.getEmployeeId()));
        employeeName.setItemLabelGenerator(EmployeeDto::getFirstName);
        employeeName.setWidth("200px");
        employeeName.getStyle().set("background-color", "#ffffff");
        right.add(employeeName);
        line2.add(left, right);

// ?????????? 3 ????????
        HorizontalLayout line3 = new HorizontalLayout();
        deadline = new DatePicker();
        deadline.setLabel("????????");
        deadline.setPlaceholder("???? ??????????????????");
        if (task.getDeadline() != null) {
            deadline.setValue(DateConvertor.fromTextDate(task.getDeadline()));
        }
        deadline.setWidth("200px");
        deadline.getStyle().set("background-color", "#ffffff");
        right.add(deadline);
        line3.add(left, right);

// ?????????? 4 ??????????????????????
        HorizontalLayout line4 = new HorizontalLayout();
        contractorName = new ComboBox<>("?????????????? ?? ????????????????????????");
        contractorName.setItems(contractorService.getAll());
        contractorName.setValue(contractorService.getById(task.getContractorId()));
        contractorName.setItemLabelGenerator(ContractorDto::getName);
        contractorName.setWidth("200px");
        contractorName.getStyle().set("background-color", "#ffffff");
        right.add(contractorName);
        line4.add(left, right);
        modalWindow.add(buttons, line1, line2, line3, line4);
        return modalWindow;
    }

    public VerticalLayout commentField() {
        Label commentLabel = new Label("??????????????????????");
        commentLabel.getStyle().set("font-size", "16px").set("color", "red");
        HorizontalLayout labelLayout = new HorizontalLayout(commentLabel);
        labelLayout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.START);
        TextArea comments = new TextArea();
        comments.setPlaceholder("?????????????? ??????????????????????");
        comments.setWidthFull();
        comments.setHeight("50px");
        comments.getStyle().set("background-color", "#ffffff");
        VerticalLayout commentFieldLayout = new VerticalLayout(comments);
        commentFieldLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.END);
        commentFieldLayout.setMargin(false);
        commentFieldLayout.setPadding(false);
        VerticalLayout block = new VerticalLayout(labelLayout, commentFieldLayout);
        block.setSpacing(false);
        block.getStyle().set("padding-left", "0px").set("padding-bottom", "0px").set("padding-right", "0px");
        return block;
    }

    private void updateTask() {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://localhost:4446")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            TasksService tasksService = new TasksServiceImpl("/api/tasks_employee", retrofit);
            TasksDto tasksDto = task;
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
            tasksService.update(tasksDto);
            UI.getCurrent().getPage().reload();
        } catch (Exception ex) {
            Notification notification = Notification.show("???????????? ???????????????? ????????????");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.setPosition(Notification.Position.BOTTOM_STRETCH);
        }
    }

    private void deleteButton(TasksDto tasksDto) {

        Button delete = new Button("??????????????");
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        Button cancel = new Button("????????????");
        HorizontalLayout buttonLayout = new HorizontalLayout(delete, cancel);

        Dialog dialog = new Dialog();
        dialog.add("?????????????????????? ????????????????");
        dialog.add(new VerticalLayout());
        dialog.add(buttonLayout);

        dialog.open();

        delete.addClickListener(event -> {
            tasksService.deleteById(tasksDto.getId());
            UI.getCurrent().getPage().reload();
            dialog.close();
        });

        cancel.addClickListener(event -> dialog.close());
    }
}
