package com.warehouse_accounting.components.production;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.UtilView;
import com.warehouse_accounting.components.production.forms.ProductionTasksForm;
import com.warehouse_accounting.components.production.grids.ProductionTasksFilterLayout;
import com.warehouse_accounting.components.production.grids.ProductionTasksGridLayout;
import com.warehouse_accounting.components.util.AdditionalFieldConvertor;
import com.warehouse_accounting.models.dto.ProductionTasksAdditionalFieldDto;
import com.warehouse_accounting.models.dto.ProductionTasksDto;
import com.warehouse_accounting.services.interfaces.ProductionTasksAdditionalFieldService;
import com.warehouse_accounting.services.interfaces.ProductionTasksService;
import com.warehouse_accounting.services.interfaces.WarehouseService;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringComponent
@UIScope
public class ProductionTasks extends VerticalLayout {

    private final transient ProductionTasksService productionTasksService;

    private final transient WarehouseService warehouseService;

    private final transient ProductionTasksAdditionalFieldService productionTasksAdditionalFieldService;

    private final ProductionTasksFilterLayout productionTasksFilterLayout;

    @Getter
    private final ProductionTasksGridLayout productionTasksGridLayout;

    private transient List<ProductionTasksAdditionalFieldDto> customFields = new ArrayList<>();

    @Getter
    @Setter
    private Div mainContent;

    public ProductionTasks(ProductionTasksFilterLayout productionTasksFilterLayout,
                           ProductionTasksGridLayout productionTasksGridLayout,
                           ProductionTasksService productionTasksService,
                           WarehouseService warehouseService,
                           ProductionTasksAdditionalFieldService productionTasksAdditionalFieldService) {
        this.productionTasksFilterLayout = productionTasksFilterLayout;
        this.productionTasksGridLayout = productionTasksGridLayout;
        this.productionTasksService = productionTasksService;
        this.warehouseService = warehouseService;
        this.productionTasksAdditionalFieldService = productionTasksAdditionalFieldService;

        mainContent = new Div();
        mainContent.setSizeFull();
        mainContent.add(getGroupButton(), this.productionTasksFilterLayout, this.productionTasksGridLayout);
        add(mainContent);
    }

    private HorizontalLayout getGroupButton() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Button helpButton = new Button(new Image("icons/helpApp.svg", "helpApp"));
        helpButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_LARGE);
        helpButton.addClickListener(c -> Notification.show("Производственное задание позволяет выстроить процесс изготовления" +
                        "продукции по техкартам и рассчитать расходы. Тут же можно отмечать выполнение производственных этапов" +
                        "и контролировать результаты."+
                "\n" +
                "Читать инструкцию: Расширенный учет производственных операций" + "Видео: Расширенный способ", 5000, Notification.Position.MIDDLE));

        Text productionTasks = new Text("Производственные задания");
        Icon refresh = new Icon(VaadinIcon.REFRESH);
        refresh.setColor("silver");
        Button refreshButton = new Button(refresh);
        refreshButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        refreshButton.addClickListener(click -> productionTasksGridLayout.updateGrid());
        Image image = new Image("icons/plus.png", "Plus");
        image.setWidth("15px");
        Button exercise = new Button("Задание", image);
        exercise.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        exercise.addClickListener(click -> add(new ProductionTasksForm(this,
                ProductionTasksDto.builder()
                        .taskId(1L)
                        .productionWarehouseId(1L)
                        .materialWarehouseId(1L)
                        .editEmployeeId(1L)
                        .ownerDepartmentId(1L)
                        .ownerEmployeeId(1L)
                        .isAccessed(true).build(),
                productionTasksService,
                warehouseService)));

        Button filter = new Button("Фильтр");
        filter.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        filter.addClickListener(e -> productionTasksFilterLayout.setVisible(!productionTasksFilterLayout.isVisible()));

        TextField textField = new TextField();
        textField.setPlaceholder("Номер и комментарий");
        textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        textField.setWidth("210px");

        MenuBar menuBar = new MenuBar(); // Создаем меню бар
        menuBar.addThemeVariants(MenuBarVariant.LUMO_ICON, MenuBarVariant.LUMO_CONTRAST);

        TextField count = new TextField();
        count.setValue("0");
        count.setWidth("20px");
        count.addThemeVariants(TextFieldVariant.LUMO_SMALL);

        menuBar.addItem(count); // Добавим поле с кол-вом

        Icon caretDownPrint = new Icon(VaadinIcon.CARET_DOWN);
        caretDownPrint.setSize("13px");
        Icon caretDownStatus = new Icon(VaadinIcon.CARET_DOWN);
        caretDownStatus.setSize("13px");
        Icon caretDownEdit = new Icon(VaadinIcon.CARET_DOWN);
        caretDownEdit.setSize("13px");

        HorizontalLayout editVision = new HorizontalLayout(new Text("Изменить"), caretDownEdit);
        editVision.setSpacing(false);

        MenuItem edit = menuBar.addItem(editVision);
        SubMenu editSubMenu = edit.getSubMenu();
        editSubMenu.addItem("Удалить").onEnabledStateChanged(false);
        editSubMenu.addItem("Копировать").onEnabledStateChanged(false);
        editSubMenu.addItem("Массовое редактирование");
        editSubMenu.addItem("Провести").onEnabledStateChanged(false);
        editSubMenu.addItem("Снять проведение").onEnabledStateChanged(false);

        HorizontalLayout statusVision = new HorizontalLayout(new Text("Статус"), caretDownStatus);
        statusVision.setSpacing(false);

        MenuItem status = menuBar.addItem(statusVision);
        SubMenu statusSubMenu = status.getSubMenu();
        statusSubMenu.addItem("Настроить");

        Icon printIcon = new Icon(VaadinIcon.PRINT);
        printIcon.setSize("13px");
        HorizontalLayout printVision = new HorizontalLayout(new Text("Печать"), printIcon, caretDownPrint);
        printVision.setSpacing(false);

        MenuItem print = menuBar.addItem(printVision);
        SubMenu printSubMenu = print.getSubMenu();
        printSubMenu.addItem("Список производственных заданий");
        printSubMenu.addItem("Производственное задание").onEnabledStateChanged(false);
        printSubMenu.addItem("Комплект").onEnabledStateChanged(false);
        printSubMenu.addItem("Настроить");

        Button setting = new Button(new Icon(VaadinIcon.COG)); // Настройки
        Dialog dialog = getCogDialog();
        setting.addClickListener(click -> dialog.open());
        setting.addThemeVariants(ButtonVariant.LUMO_CONTRAST);


        horizontalLayout.add(helpButton, productionTasks, refreshButton, exercise, filter, textField, menuBar,
                setting);

        return horizontalLayout;
    }

    private Dialog getCogDialog() {
        Dialog dialog = new Dialog();
        dialog.getElement().setAttribute("aria-label", "Create new employee");
        dialog.add(createDialogLayout(dialog));
        dialog.setHeightFull();
        return dialog;

    }

    private VerticalLayout createDialogLayout(Dialog dialog) {
        H2 headline = new H2("Настройки производственных заданий");
        headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0")
                .set("font-size", "1.5em").set("font-weight", "bold");
        Button saveButton = new Button("Сохранить", e -> {

            List<ProductionTasksDto> productionTasksDtoList = productionTasksService.getAll();
            for (ProductionTasksDto productionTasksDto : productionTasksDtoList) {
                productionTasksDto.setAdditionalFieldsIds(customFields.stream()
                        .map(x -> productionTasksAdditionalFieldService.create(x).getId())
                        .collect(Collectors.toList()));
                productionTasksService.update(productionTasksDto);
            }

            dialog.close();
        });
        Button cancelButton = new Button("Отменить", e -> dialog.close());
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        HorizontalLayout buttonLayout = new HorizontalLayout(saveButton, cancelButton);

        CheckboxGroup<String> checkboxGroupNumeration = new CheckboxGroup<>();
        checkboxGroupNumeration.setLabel("Нумерация");
        checkboxGroupNumeration.setItems("Независимая нумерация для юр.лиц");
        checkboxGroupNumeration.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);

        HorizontalLayout lastCalendarDays = new HorizontalLayout(new Span("За последние ")
                ,new NumberField()
                ,new Span(" календарных дней"));
        lastCalendarDays.setAlignItems(Alignment.CENTER);

        HorizontalLayout lastWorkingDays = new HorizontalLayout(new Span("За последние ")
                ,new NumberField()
                ,new Span(" рабочих дней"));
        lastWorkingDays.setAlignItems(Alignment.CENTER);

        HorizontalLayout startedWith = new HorizontalLayout(new Span("Начиная с ")
                , new DatePicker());
        startedWith.setAlignItems(Alignment.CENTER);

        RadioButtonGroup<Component> radioGroup = new RadioButtonGroup<>();
        radioGroup.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        radioGroup.setLabel("Разрешить изменение документов");
        radioGroup.setItems(new Span("Всех"), lastCalendarDays, lastWorkingDays, startedWith);

        radioGroup.setRenderer(new ComponentRenderer<>(x -> x));

        CheckboxGroup<String> checkboxGroupAccess = new CheckboxGroup<>();
        checkboxGroupAccess.setLabel("Доступ");
        checkboxGroupAccess.setItems("Создавать новые документы с меткой \"Общий\"");
        checkboxGroupAccess.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);

        HorizontalLayout additionalFieldsHeader = new HorizontalLayout(UtilView.createHelpButton(
                "Если вы хотите сохранить информацию, для которой нет подходящего поля, вы можете создать дополнительные поля. Подробнее в центре поддержки."
        ), new H6("Дополнительные поля"));
        additionalFieldsHeader.setSpacing(false);

        Accordion customFieldsAccordion = new Accordion();

        Button addFieldButton = UtilView.plusButton("Поле");
        addFieldButton.addClickListener(click -> customFieldsAccordion.add(createCustomFieldForm()));
        VerticalLayout additionalFields = new VerticalLayout(additionalFieldsHeader,
                addFieldButton);
        additionalFields.setPadding(false);

        VerticalLayout dialogLayout = new VerticalLayout(headline,
                buttonLayout, checkboxGroupNumeration, radioGroup,
                checkboxGroupAccess, additionalFields, customFieldsAccordion);
        dialogLayout.setPadding(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "300px").set("max-width", "100%");

        return dialogLayout;
    }

    private AccordionPanel createCustomFieldForm() {
        FormLayout formLayout = new FormLayout();
        TextField fieldName = new TextField();
        formLayout.addFormItem(fieldName,"Название");
        Select<String> select = new Select<>();
        select.setItems("Строка", "Число целое", "Дата", "Справочник",
                "Файл", "Число дробное", "Флажок", "Текст", "Ссылка");
        select.setValue("Строка");
        HorizontalLayout typeName = new HorizontalLayout(
                UtilView.createHelpButton("Тип данных, которые могут храниться в поле."),
                new Span("Тип")
        );
        typeName.setSpacing(false);
        formLayout.addFormItem(select, typeName);

        HorizontalLayout necessaryName = new HorizontalLayout(
                UtilView.createHelpButton("Если включено, документ нельзя сохранить, не заполнив это поле."),
                new Span("Обязательный")
        );
        necessaryName.setSpacing(false);
        Checkbox necessaryNameCheckbox = new Checkbox();
        formLayout.addFormItem(necessaryNameCheckbox, necessaryName);

        HorizontalLayout hidingName = new HorizontalLayout(
                UtilView.createHelpButton("Если включить, поле не будет показываться в карточке документа"),
                new Span("Скрывать в карточке")
        );
        hidingName.setSpacing(false);
        Checkbox hidingNameCheckbox = new Checkbox();
        formLayout.addFormItem(hidingNameCheckbox, hidingName);

        HorizontalLayout descriptionName = new HorizontalLayout(
                UtilView.createHelpButton("Этот текст будет подсказкой, как данный текст."),
                new Span("Описание")
        );
        descriptionName.setSpacing(false);
        TextArea descriptionNameTextArea = new TextArea();
        formLayout.addFormItem(descriptionNameTextArea, descriptionName);

        AccordionPanel accordionPanel = new AccordionPanel("", formLayout);

        ProductionTasksAdditionalFieldDto fieldDto = new ProductionTasksAdditionalFieldDto();
        Map<String,Object> propertyMap = new HashMap<>();
        propertyMap.put("type","String");
        fieldDto.setProperty(propertyMap);
        fieldDto.setName(fieldName.getValue());
        fieldDto.setRequired(necessaryNameCheckbox.getValue());
        fieldDto.setHide(hidingNameCheckbox.getValue());
        fieldDto.setDescription(descriptionNameTextArea.getValue());

        fieldName.addValueChangeListener(click -> {
            accordionPanel.setSummaryText(fieldName.getValue());
            fieldDto.setName(fieldName.getValue());
        });

        select.addValueChangeListener(click -> {
            propertyMap.put("type", AdditionalFieldConvertor.convertToType(select.getValue()));
            fieldDto.setProperty(propertyMap);
        });

        necessaryNameCheckbox.addValueChangeListener(
                click -> fieldDto.setRequired(necessaryNameCheckbox.getValue()));

        hidingNameCheckbox.addValueChangeListener(
                click -> fieldDto.setHide(hidingNameCheckbox.getValue()));

        descriptionNameTextArea.addValueChangeListener(
                click -> fieldDto.setDescription(descriptionNameTextArea.getValue()));

        customFields.add(fieldDto);
        return accordionPanel;
    }


}
