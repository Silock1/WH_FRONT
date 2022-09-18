package com.warehouse_accounting.components.production.forms;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.UtilView;
import com.warehouse_accounting.components.production.ProductionTasks;
import com.warehouse_accounting.components.util.DateConvertor;
import com.warehouse_accounting.models.dto.ContractDto;
import com.warehouse_accounting.models.dto.ContractorDto;
import com.warehouse_accounting.models.dto.EmployeeDto;
import com.warehouse_accounting.models.dto.ProductionTasksAdditionalFieldDto;
import com.warehouse_accounting.models.dto.ProductionTasksDto;
import com.warehouse_accounting.models.dto.TasksDto;
import com.warehouse_accounting.models.dto.WarehouseDto;
import com.warehouse_accounting.services.impl.TasksServiceImpl;
import com.warehouse_accounting.services.interfaces.ContractorService;
import com.warehouse_accounting.services.interfaces.EmployeeService;
import com.warehouse_accounting.services.interfaces.ProductionTasksAdditionalFieldService;
import com.warehouse_accounting.services.interfaces.ProductionTasksService;
import com.warehouse_accounting.services.interfaces.TasksService;
import com.warehouse_accounting.services.interfaces.WarehouseService;
import lombok.extern.log4j.Log4j2;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.warehouse_accounting.components.UtilView.subMenuTabs;


@UIScope
@Log4j2
public class ProductionTasksForm extends VerticalLayout {
    private final ProductionTasks productionTasks;
    private final Div returnDiv;

    private final transient ProductionTasksDto productionTasksDto;

    private transient List<ProductionTasksAdditionalFieldDto> productionTasksAdditionalFieldDtos = new ArrayList<>();
    private final Binder<ProductionTasksDto> productionTasksDtoBinder =
            new Binder<>(ProductionTasksDto.class);

    private final transient ProductionTasksService productionTasksService;

    private final transient ProductionTasksAdditionalFieldService productionTasksAdditionalFieldService;

    private final transient WarehouseService warehouseService;

    private static final String PROPERTY_MAP_VALUE_LITERAL = "value";

    private HorizontalLayout dialogGeneral;
    private ComboBox<EmployeeDto> comboBox;
    private com.vaadin.flow.component.textfield.TextArea titleField;
    private DatePicker deadline;
    private ComboBox<ContractorDto> contractorDtoComboBox;
    private final transient ContractorService contractorService;

    private Paragraph bindingContagentsAndDocumentsLabel;
    private Checkbox accessCheckbox;
    private HorizontalLayout contractorDtoAndAddLayout;
    private VerticalLayout deleteLinkAnchorLayout;

    private final transient EmployeeService employeeService;
    private HorizontalLayout fieldMustBeFilledLabelLayout;


    public ProductionTasksForm(ProductionTasks productionTasks,
                               ProductionTasksDto productionTasksDto,
                               ProductionTasksService productionTasksService,
                               ProductionTasksAdditionalFieldService productionTasksAdditionalFieldService,
                               WarehouseService warehouseService,
                               EmployeeService employeeService,
                               ContractorService contractorService) {
        this.productionTasks = productionTasks;
        this.returnDiv = productionTasks.getMainContent();
        this.productionTasksDto = productionTasksDto;
        this.productionTasksService = productionTasksService;
        this.productionTasksAdditionalFieldService = productionTasksAdditionalFieldService;
        this.warehouseService = warehouseService;
        this.employeeService = employeeService;
        this.contractorService = contractorService;
        init();
    }

    public void init() {
        if (this.productionTasksDto.getAdditionalFieldsIds() != null) {
            this.productionTasksAdditionalFieldDtos = this.productionTasksDto.getAdditionalFieldsIds().stream()
                    .map(productionTasksAdditionalFieldService::getById)
                    .collect(Collectors.toList());
        }
        productionTasks.removeAll();
        add(createTopGroupElements(), createInputFieldForm());
        productionTasksDtoBinder.readBean(this.productionTasksDto);
    }

    private HorizontalLayout createTopGroupElements() {
        HorizontalLayout topPartLayout = new HorizontalLayout();
        topPartLayout.setAlignItems(Alignment.CENTER);
        topPartLayout.add(
                saveProductionTechnologyButton(),
                closeProductionTechnologyButton(),
                createMenuBar()
        );
        return topPartLayout;
    }

    private Button saveProductionTechnologyButton() {
        Button saveButton = new Button("Сохранить");
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        saveButton.addClickListener(c -> {
            if (!productionTasksDtoBinder.validate().isOk()) {
                return;
            }

            try {
                productionTasksDtoBinder.writeBean(productionTasksDto);

                productionTasksDto.setDateOfEdit(LocalDate.now());
                productionTasksDto.setDateOfCreate(LocalDate.now());

                productionTasksDto.setAdditionalFieldsIds(productionTasksAdditionalFieldDtos.stream()
                        .map(x -> {
                            if (x.getId() != null && x.getId() > 0) {
                                return productionTasksAdditionalFieldService.update(x);
                            } else {
                                return productionTasksAdditionalFieldService.create(x);
                            }
                        })
                        .map(ProductionTasksAdditionalFieldDto::getId)
                        .collect(Collectors.toList()));

                if (productionTasksDto.getId() != null && productionTasksDto.getId() > 0) {
                    productionTasksService.update(productionTasksDto);
                } else {
                    productionTasksService.create(productionTasksDto);
                }
                productionTasks.getProductionTasksGridLayout().updateGrid();

            } catch (ValidationException e) {
                log.error("Ошибка валидации");
            } finally {
                formCleanup();
            }
        });
        return saveButton;
    }

    private Button closeProductionTechnologyButton() {
        Button closeButton = new Button("Закрыть");
        closeButton.addClickListener(c -> formCleanup());
        return closeButton;

    }

    private void formCleanup() {
        productionTasksAdditionalFieldDtos = new ArrayList<>();
        productionTasks.removeAll();
        productionTasks.add(returnDiv);
    }

    private HorizontalLayout createEditButton() {

        MenuBar editMenuBar = new MenuBar();
        editMenuBar.addThemeVariants(MenuBarVariant.LUMO_SMALL);

        MenuItem editMenu = editMenuBar.addItem(UtilView.caretDownButtonLayout("Изменить"));
        editMenu.getSubMenu().addItem("Удалить", menuItemClickEvent -> {

        });
        editMenu.getSubMenu().addItem("Копировать", menuItemClickEvent -> {

        });
        editMenu.getSubMenu().addItem("Отменить производство", menuItemClickEvent -> {

        });
        editMenu.getSubMenu().addItem("Отменить все этапы", menuItemClickEvent -> {

        });

        HorizontalLayout groupEdit = new HorizontalLayout();
        groupEdit.add(editMenuBar);
        groupEdit.setSpacing(false);
        groupEdit.setAlignItems(Alignment.CENTER);
        return groupEdit;
    }

    private Button createSupplyButton() {
        Button supplyButton = new Button("Снабжение", e -> {

        });
        supplyButton.setEnabled(false);
        return supplyButton;
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        menuBar.addItem(createEditButton());
        menuBar.addItem(createSupplyButton());
        MenuItem printButton = menuBar.addItem(createPrintButton());
        printButton.getSubMenu().addItem("Производственное задание");
        printButton.getSubMenu().addItem("Комплект...");
        printButton.getSubMenu().addItem("Настроить...");
        MenuItem sendButton = menuBar.addItem(createSendButton());
        sendButton.getSubMenu().addItem("Производственное задание");
        sendButton.getSubMenu().addItem("Комплект...");
        return menuBar;
    }

    private HorizontalLayout createPrintButton() {

        return UtilView.caretDownButtonLayout("Печать");
    }

    private HorizontalLayout createSendButton() {

        return UtilView.caretDownButtonLayout("Отправить");
    }


    private VerticalLayout createInputFieldForm() {

        VerticalLayout returnLayout = new VerticalLayout();
        returnLayout.setAlignItems(Alignment.START);
        returnLayout.add(
                createInputFormHeader(),
                createInputFormRequiredFields(),
                createInputFormTabs(),
                createInputFormFooter()
        );
        return returnLayout;
    }

    private VerticalLayout createInputFormFooter() {
        VerticalLayout footer = new VerticalLayout();
        TextField description = new TextField();
        description.setPlaceholder("Комментарий");
        description.setMinWidth("210px");
        description.setMinHeight("210px");
        footer.add(description);
        productionTasksDtoBinder
                .forField(description)
                .bind(ProductionTasksDto::getDescription, ProductionTasksDto::setDescription);

        VerticalLayout timeManagement = new VerticalLayout(new Span("Начало производства"),
                new Span("Завершение производства"), new Span("Время производства"));
        footer.add(new Details("Учет времени", timeManagement));


        Button taskButton = UtilView.plusButton("Задача");
        taskButton.addClickListener(e -> createTaskDialog().open());
        HorizontalLayout tasks = new HorizontalLayout(new Details("Задачи", new Span("Нет задач")), taskButton);
        tasks.setAlignItems(Alignment.CENTER);
        footer.add(tasks);

        HorizontalLayout files = new HorizontalLayout(new Details("Файлы", new Span("Нет файлов")),
                UtilView.plusButton("Файл"));
        files.setAlignItems(Alignment.CENTER);
        footer.add(files);

        return footer;
    }

    // Создание диалогового окна сохранения задачи
    private Dialog createTaskDialog() {
        Dialog taskDialog = new Dialog();
            taskDialog.setCloseOnOutsideClick(false);
            dialogGeneral = createTaskDialogLayout(taskDialog);
            dialogGeneral.getStyle().set("width", "820px").set("height", "300px").set("max-width", "100%").set("max-height", "100%");
            taskDialog.add(dialogGeneral);
        return taskDialog;
    }

    // Создание Layout'a для createTaskDialog
    private HorizontalLayout createTaskDialogLayout(Dialog dialog) {
        // Левая часть окна
        Button saveButton = new Button("Сохранить");
        Button closeButton = new Button("Закрыть");
            saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
            saveButton.addClickListener(e -> {
                if (titleField.isEmpty()) {
                    fieldMustBeFilledLabelLayout.setVisible(true);
                } else {
                    createAfterSaveButtonDialog();
                    saveDataInDB();
                    dialog.close();
                }
        });

        closeButton.addClickListener(e -> createSavingChangesDialog(dialog));

        Label fieldMustBeFilledLabel = new Label("Поле Задача должно быть заполнено");
        fieldMustBeFilledLabel.getStyle().set("font-size","12px");
        Icon icon = new Icon(VaadinIcon.CLOSE_CIRCLE);
        icon.getStyle().set("color", "red").set("size","8px");
        fieldMustBeFilledLabelLayout = new HorizontalLayout(icon, fieldMustBeFilledLabel);
        fieldMustBeFilledLabelLayout.setVisible(false);
        Button linkButton = new Button(new Icon(VaadinIcon.LINK));
        MultiFileMemoryBuffer buffer2 = new MultiFileMemoryBuffer();
        Upload dropDisabledUploadLink = new Upload(buffer2);
            dropDisabledUploadLink.setDropAllowed(false);
            dropDisabledUploadLink.setId("upload-drop-disabled");
            dropDisabledUploadLink.setUploadButton(linkButton);
            titleField = new TextArea("Описание задачи");
            titleField.getStyle().set("width", "430px").set("height", "200px").set("background-color", "white");
            titleField.setSuffixComponent(dropDisabledUploadLink);
        VerticalLayout leftDialogLayout = new VerticalLayout(new HorizontalLayout(saveButton, closeButton),fieldMustBeFilledLabelLayout, titleField);

        // Правая часть окна
        Button dotButton = new Button(new Icon(VaadinIcon.ELLIPSIS_DOTS_H));
        VerticalLayout dotButtonLayout = new VerticalLayout(dotButton);
            dotButtonLayout.setAlignItems(Alignment.END);
            accessCheckbox = new Checkbox("Выполнено");
            accessCheckbox.getStyle().set("background-color", "white");

            comboBox = new ComboBox<>("Исполниель");
            comboBox.setItems(employeeService.getAll());
            comboBox.setItemLabelGenerator(EmployeeDto::getFirstName);

            comboBox.getStyle().set("background-color", "white").set("width", "300px");
            comboBox.setValue(employeeService.getById(2L));

        Button editButton = new Button(new Icon(VaadinIcon.PENCIL));
            editButton.getStyle().set("background-color", "white");
        HorizontalLayout executorAndEditLayout = new HorizontalLayout(comboBox, editButton);
            executorAndEditLayout.setAlignItems(Alignment.END);

            deadline = new DatePicker();
            deadline.setLabel("Срок");
            deadline.setPlaceholder("Не ограничен");
            deadline.getStyle().set("background-color", "white").set("width", "300px");

            contractorDtoComboBox = new ComboBox<>("Контрагент");
            contractorDtoComboBox.setItems(contractorService.getAll());
            contractorDtoComboBox.setItemLabelGenerator(ContractorDto::getName);
            contractorDtoComboBox.setWidth("300px");
        Button addContragentButton = new Button(new Icon(VaadinIcon.PLUS));
            contractorDtoAndAddLayout = new HorizontalLayout(contractorDtoComboBox, addContragentButton);
            contractorDtoAndAddLayout.setAlignItems(Alignment.END);
            contractorDtoAndAddLayout.setVisible(false);

        Anchor conrtagentAnchor = new Anchor("#", "контрагентом");
            conrtagentAnchor.removeHref();
        Anchor deleteLinkAnchor = new Anchor("#", "Удалить ссылку");
            deleteLinkAnchor.removeHref();
            deleteLinkAnchorLayout = new VerticalLayout(deleteLinkAnchor);
            deleteLinkAnchorLayout.setAlignItems(Alignment.END);
            deleteLinkAnchorLayout.setVisible(false);

        conrtagentAnchor.getElement().addEventListener("click", e -> {
            contractorDtoAndAddLayout.setVisible(!contractorDtoAndAddLayout.isVisible());
            bindingContagentsAndDocumentsLabel.setVisible(!bindingContagentsAndDocumentsLabel.isVisible());
            deleteLinkAnchorLayout.setVisible(!deleteLinkAnchorLayout.isVisible());
            dialogGeneral.getStyle().set("height", "400px");
        });

        deleteLinkAnchor.getElement().addEventListener("click", e -> {
            contractorDtoAndAddLayout.setVisible(!contractorDtoAndAddLayout.isVisible());
            bindingContagentsAndDocumentsLabel.setVisible(!bindingContagentsAndDocumentsLabel.isVisible());
            deleteLinkAnchorLayout.setVisible(!deleteLinkAnchorLayout.isVisible());
            dialogGeneral.getStyle().set("height", "300px");
        });

        Anchor documentAnchor = new Anchor("#", "документом");
        documentAnchor.removeHref();
        bindingContagentsAndDocumentsLabel = new Paragraph(new Text("Связать с "), conrtagentAnchor, new Text(" или "), documentAnchor);
        documentAnchor.getElement().addEventListener("click", e -> createDocumentDialog());

        VerticalLayout rightDialogLayout = new VerticalLayout(dotButtonLayout, accessCheckbox, executorAndEditLayout, deadline, bindingContagentsAndDocumentsLabel,contractorDtoAndAddLayout,deleteLinkAnchorLayout);
                rightDialogLayout.getStyle().set("background-color", "white");
        return new HorizontalLayout(leftDialogLayout, rightDialogLayout);
    }

    // Диалоговое окно: Сохранение изменений
    private void createSavingChangesDialog(Dialog dialog) {
    Dialog savingChangesDialog = new Dialog();
            savingChangesDialog.setCloseOnOutsideClick(false);
    Label savingChangesLabel = new Label("Сохранение изменений");
            savingChangesLabel.getStyle().set("background-color","white").set("font-size","24px").set("font-weight","bold");

    Label dataWasChangedLabel = new Label("Данные были изменены.");
    Label isSaveDataLabel = new Label("Сохранить изменения?");
    Button savingChangesCloseButton = new Button(new Icon(VaadinIcon.CLOSE_CIRCLE_O));
            savingChangesCloseButton.getStyle().set("background-color","white").set("font-size","22px");
            savingChangesCloseButton.addClickListener(es ->savingChangesDialog.close());
    VerticalLayout savingChangesLabelLayout = new VerticalLayout(savingChangesLabel);
            savingChangesLabelLayout.getStyle().set("width","450px");

    VerticalLayout savingChangesCloseButtonLayout = new VerticalLayout(savingChangesCloseButton);
            savingChangesCloseButtonLayout.getStyle().set("width","20px");
            savingChangesCloseButtonLayout.setAlignItems(Alignment.END);
    HorizontalLayout line1Layout = new HorizontalLayout(savingChangesLabelLayout, savingChangesCloseButtonLayout);

    Button yesButton = new Button("Да");
    Button noButton = new Button("Нет");
    Button cancelButton = new Button("Отмена");
            cancelButton.addClickListener(es ->savingChangesDialog.close());

            noButton.addClickListener(es -> {
                    savingChangesDialog.close();
                    dialog.close();
                    clearDataInForms();
                    contractorDtoAndAddLayout.setVisible(false);
                    bindingContagentsAndDocumentsLabel.setVisible(true);
                    deleteLinkAnchorLayout.setVisible(false);
                    dialogGeneral.getStyle().set("height", "300px");
                });
            yesButton.addClickListener(ess -> {
                    if (titleField.isEmpty()) {
                        savingChangesDialog.close();
                        fieldMustBeFilledLabelLayout.setVisible(true);
                    } else{
                        savingChangesDialog.close();
                        dialog.close();
                        saveDataInDB();
                    }
                });
    VerticalLayout line2Layout = new VerticalLayout(dataWasChangedLabel, isSaveDataLabel);
    VerticalLayout line3Layout = new VerticalLayout(new HorizontalLayout(yesButton, noButton, cancelButton));
        line3Layout.setAlignItems(Alignment.END);
        savingChangesDialog.add(line1Layout);
        savingChangesDialog.add(line2Layout);
        savingChangesDialog.add(line3Layout);
        savingChangesDialog.open();
    }

    // Диалоговое окно, открывающее после нажатия кнопки Сохранить
    private void createAfterSaveButtonDialog() {
        Dialog afterSaveButtonDialog = new Dialog();
        afterSaveButtonDialog.setCloseOnOutsideClick(false);
        // Левая часть окна
        Button closeButton = new Button("Закрыть");

        closeButton.addClickListener(e -> {
            afterSaveButtonDialog.close();
            saveDataInDB();
        });

        Label taskDescriptionLabel = new Label("Описание задачи");
        Label taskDescriptionFromTitleFieldLabel = new Label(titleField.getValue());
        taskDescriptionFromTitleFieldLabel.getStyle().set("min-width", "430px").set("min-height", "100px").set("background-color", "white");
        Label commentsFieldLabel = new Label("Комментарии 0");
        TextArea commentTextField = new TextArea();
        commentTextField.setPlaceholder("Написать комментарий");
        commentTextField.getStyle().set("width", "330px").set("height", "50px").set("background-color", "white");
        VerticalLayout leftDialogLayout = new VerticalLayout(closeButton, taskDescriptionLabel, taskDescriptionFromTitleFieldLabel,commentsFieldLabel, new HorizontalLayout(new Icon(VaadinIcon.USER), commentTextField));

        // Правая часть окна
        Label aboutChangesLabel = new Label("Изменения: " + employeeService.getById(2L).getFirstName());
        Button dotButton = new Button(new Icon(VaadinIcon.ELLIPSIS_DOTS_H));
        VerticalLayout dotButtonLayout = new VerticalLayout(dotButton);
        dotButtonLayout.setAlignItems(Alignment.END);

        Label executorLabel = new Label("Исполниель");
        Label executorFromComboBoxLabel = new Label(comboBox.getValue().getFirstName());

        Label deadlineLabel = new Label("Срок");
        Label deadlineFromDeadlineLabel = new Label(deadline.isEmpty() ? "Не ограничен": DateConvertor.asText(deadline.getValue()));

        Label authorLabel = new Label("Автор");
        Label authorDescriptionLabel = new Label("Вы");

        VerticalLayout rightDialogLayout = new VerticalLayout(new HorizontalLayout(aboutChangesLabel, new Icon(VaadinIcon.USER), dotButtonLayout), accessCheckbox, executorLabel, executorFromComboBoxLabel, deadlineLabel, deadlineFromDeadlineLabel,authorLabel, authorDescriptionLabel);
        rightDialogLayout.getStyle().set("background-color", "white");
        HorizontalLayout horizontalLayout = new HorizontalLayout(leftDialogLayout, rightDialogLayout);
        horizontalLayout.getStyle().set("max-height", "700px").set("width", "750px");
        afterSaveButtonDialog.add(horizontalLayout);
        afterSaveButtonDialog.open();
    }

    // Метод в котором сохраняются данные в бд
    private void saveDataInDB() {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://localhost:4446")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            TasksService tasksService = new TasksServiceImpl("/api/tasks_employee", retrofit);
            TasksDto tasksDto = new TasksDto();
                tasksDto.setDescription(titleField.getValue());
                tasksDto.setEmployeeId(comboBox.getValue().getId());
                tasksDto.setEmployeeName(comboBox.getValue().getFirstName());
            String date = deadline.isEmpty() ? null: DateConvertor.asText(deadline.getValue());
                tasksDto.setDeadline(date);
                tasksDto.setContractorId(contractorDtoComboBox.isEmpty()? null : contractorDtoComboBox.getValue().getId());
                tasksDto.setContractorName(contractorDtoComboBox.isEmpty()? null : contractorDtoComboBox.getValue().getName());

                tasksDto.setIsDone(accessCheckbox.getValue());
                tasksService.create(tasksDto);
                clearDataInForms();
                contractorDtoAndAddLayout.setVisible(false);
                bindingContagentsAndDocumentsLabel.setVisible(true);
                deleteLinkAnchorLayout.setVisible(false);
                dialogGeneral.getStyle().set("height", "300px");
        } catch (Exception ex) {
            Notification notification = Notification.show("Ошибка создания Задачи");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.setPosition(Notification.Position.BOTTOM_STRETCH);
        }
    }

    // Метод для очищения заполненных форм
    private void clearDataInForms() {
        titleField.clear();
        comboBox.setValue(employeeService.getById(2L));
        deadline.clear();
        contractorDtoComboBox.clear();
    }

    // Создание диалогового окна при нажатии ссылки "документом" в производство/производственные задания/ +задания/ +задача
    private void createDocumentDialog() {
        Dialog documentBindingDialog = new Dialog();
            documentBindingDialog.setCloseOnOutsideClick(false);
        Label documentAddLabel = new Label("Привязка документа");
        Button documentAddCloseButton = new Button(new Icon(VaadinIcon.CLOSE_CIRCLE_O));
            documentAddCloseButton.getStyle().set("background-color", "white").set("font-size", "24px");
            documentAddCloseButton.addClickListener(esp -> documentBindingDialog.close());
        VerticalLayout documentAddLabelLayout = new VerticalLayout(documentAddLabel);
            documentAddLabelLayout.getStyle().set("width", "600px").set("font-weight", "bold").set("font-size", "24px");
        VerticalLayout documentAddCloseButtonLayout = new VerticalLayout(documentAddCloseButton);
            documentAddCloseButtonLayout.getStyle().set("width", "600px");
            documentAddCloseButtonLayout.setAlignItems(Alignment.END);
        HorizontalLayout labelButtonLayout = new HorizontalLayout(documentAddLabelLayout, documentAddCloseButtonLayout);
        VerticalLayout documentBindingLayout = new VerticalLayout(labelButtonLayout);
            documentBindingLayout.getStyle().set("width", "1210px").set("margin-top", "0px").set("margin-left", "0px").set("margin-right", "0px");
            documentBindingLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
            documentBindingLayout.setPadding(false);
            documentBindingDialog.add(documentBindingLayout);

        Button findButton = new Button("Найти");
        Button clearButton = new Button("Очистить");
            findButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        ComboBox<ContractDto> contractDtoComboBox = new ComboBox<>("Тип документа");
            contractDtoComboBox.getStyle().set("width", "250px");
        contractorDtoComboBox = new ComboBox<>("Контрагент или организация");
            contractorDtoComboBox.getStyle().set("width", "250px").set("font-size", "16px");
        DatePicker periodDatePicker1 = new DatePicker("Период от");
        DatePicker periodDatePicker2 = new DatePicker("до");
            periodDatePicker1.addValueChangeListener(e -> periodDatePicker2.setMin(e.getValue()));
            periodDatePicker2.addValueChangeListener(e -> periodDatePicker1.setMax(e.getValue()));
        HorizontalLayout findButtonClearButtonLayout = new HorizontalLayout(findButton, clearButton);
            findButtonClearButtonLayout.setAlignItems(Alignment.CENTER);
            findButtonClearButtonLayout.getStyle().set("margin-left", "50px").set("margin-top", "5px").set("margin-right", "100px");
        HorizontalLayout line2Layout = new HorizontalLayout(findButtonClearButtonLayout, contractDtoComboBox, contractorDtoComboBox, periodDatePicker1, periodDatePicker2);
            documentBindingDialog.add(line2Layout);

            documentBindingDialog.open();
    }

    private Tabs createInputFormTabs() {
        return subMenuTabs(Arrays.asList("Продукция","Материалы","Выполнение","Связанные документы"));
    }

    private FormLayout createInputFormRequiredFields() {
        FormLayout formLayout = new FormLayout();

        TextField organization = new TextField();
        formLayout.addFormItem(organization,"Организация");
        productionTasksDtoBinder
                .forField(organization)
                .asRequired("Организация не должна быть пустой!")
                .bind(ProductionTasksDto::getOrganization,ProductionTasksDto::setOrganization);

        Select<WarehouseDto> materialWarehouseSelect = new Select<>();
        materialWarehouseSelect.setItemLabelGenerator(WarehouseDto::getName);
        materialWarehouseSelect.setItems(warehouseService.getAll());
        formLayout.addFormItem(materialWarehouseSelect,"Склад материалов");
        productionTasksDtoBinder
                .forField(materialWarehouseSelect)
                .bind(productionTasksDto1 -> warehouseService.getById(productionTasksDto1.getMaterialWarehouseId()),
                        (productionTasksDto2, materialWarehouse) ->
                                productionTasksDto2.setMaterialWarehouseId(materialWarehouse.getId()));

        DatePicker plannedDate = new DatePicker();
        formLayout.addFormItem(plannedDate,"Планируемая дата производства");
        productionTasksDtoBinder
                .forField(plannedDate)
                .bind(ProductionTasksDto::getPlannedDate,ProductionTasksDto::setPlannedDate);

        Select<WarehouseDto> productionWarehouseSelect = new Select<>();
        productionWarehouseSelect.setItemLabelGenerator(WarehouseDto::getName);
        productionWarehouseSelect.setItems(warehouseService.getAll());
        formLayout.addFormItem(productionWarehouseSelect,"Склад продукции");
        productionTasksDtoBinder
                .forField(productionWarehouseSelect)
                .bind(productionTasksDto1 -> warehouseService.getById(productionTasksDto1.getProductionWarehouseId()),
                        (productionTasksDto2, productionWarehouse) ->
                                productionTasksDto2.setProductionWarehouseId(productionWarehouse.getId()));
        ProductionTasksDto productionTasksDtoTemplate = productionTasksService.getById(1L);
        for (Long fieldId : productionTasksDtoTemplate.getAdditionalFieldsIds()) {
            ProductionTasksAdditionalFieldDto dto = productionTasksAdditionalFieldService.getById(fieldId);
            HasValue customField = createCustomField(dto);
            String customFieldName = productionTasksDtoTemplate.getAdditionalFieldsNames()
                    .get(productionTasksDtoTemplate.getAdditionalFieldsIds().indexOf(fieldId));
            if (productionTasksDto.getAdditionalFieldsNames() != null) {
                ProductionTasksAdditionalFieldDto dtoField = productionTasksAdditionalFieldService
                        .getById(productionTasksDto.getAdditionalFieldsIds()
                        .get(productionTasksDto.getAdditionalFieldsNames().indexOf(customFieldName)));
                customField.setValue(dtoField.getProperty().get(PROPERTY_MAP_VALUE_LITERAL));
                customField.addValueChangeListener(x -> productionTasksAdditionalFieldDtos
                        .get(productionTasksAdditionalFieldDtos.indexOf(dtoField))
                        .getProperty().put(PROPERTY_MAP_VALUE_LITERAL,customField.getValue()));
            } else {
                ProductionTasksAdditionalFieldDto dtoField = ProductionTasksAdditionalFieldDto.builder()
                        .description(dto.getDescription())
                        .hide(dto.getHide())
                        .required(dto.getRequired())
                        .name(dto.getName())
                        .build();
                productionTasksAdditionalFieldDtos.add(dtoField);
                Map<String,Object> propertyMap = new HashMap<>();
                propertyMap.put("type",dto.getProperty().get("type"));
                dtoField.setProperty(propertyMap);
                customField.addValueChangeListener(event ->
                        dtoField.getProperty().put(PROPERTY_MAP_VALUE_LITERAL, customField.getValue()));
            }
            formLayout.addFormItem((Component) customField, customFieldName);

        }
        return formLayout;
    }

    private HasValue createCustomField(ProductionTasksAdditionalFieldDto dto) {
        HasValue component;
        switch ((String)dto.getProperty().get("type")) {
            case "Integer" : component = new NumberField(); break;
            case "String" : component = new TextField(); break;
            default: component = new TextField();
        }
        return component;
    }

    private FormLayout createInputFormHeader() {
        FormLayout formLayout = new FormLayout();
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0",5));
        NumberField numberField = new NumberField();
        formLayout.addFormItem(numberField,"Производственное задание №");
        productionTasksDtoBinder
                .forField(numberField)
                .asRequired("Номер не должен быть пустым!")
                .bind(productionTasksDto2 -> Double.valueOf(productionTasksDto2.getTaskId()),
                        (productionTasksDto1, taskId) -> productionTasksDto1.setTaskId(taskId.longValue()));

        DatePicker datePickerField = new DatePicker();
        formLayout.addFormItem(datePickerField,"от");
        productionTasksDtoBinder
                .forField(datePickerField)
                .asRequired("Дата создания не должна быть пустой!")
                .bind(ProductionTasksDto::getDateOfCreate, ProductionTasksDto::setDateOfCreate);
        datePickerField.setValue(productionTasksDto.getDateOfCreate() == null
                ? LocalDate.now()
                : productionTasksDto.getDateOfCreate());

        MenuBar statusMenuBar = new MenuBar();
        MenuItem statusMenu = statusMenuBar.addItem(UtilView.caretDownButtonLayout("Статус"));
        statusMenu.getSubMenu().addItem("Настроить...", menuItemClickEvent -> {

        });
        formLayout.add(statusMenuBar);

        HorizontalLayout helpCheckboxLayout = new HorizontalLayout(
                UtilView.createHelpButton(
                        "Непроведенный документ — это черновик. Он не учитывается в отчетах."),
                new Checkbox("Проведено"));
        helpCheckboxLayout.setAlignItems(Alignment.CENTER);
        formLayout.add(helpCheckboxLayout);

        HorizontalLayout reserveCheckboxLayout = new HorizontalLayout(
                UtilView.createHelpButton("Резерв действует на все материалы в документе."),
                new Checkbox("Резерв"));
        reserveCheckboxLayout.setAlignItems(Alignment.CENTER);
        formLayout.add(reserveCheckboxLayout);

        return formLayout;
    }

}

