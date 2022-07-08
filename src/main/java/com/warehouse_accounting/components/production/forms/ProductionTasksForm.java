package com.warehouse_accounting.components.production.forms;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.UtilView;
import com.warehouse_accounting.components.production.ProductionTasks;
import com.warehouse_accounting.models.dto.ProductionTasksDto;
import com.warehouse_accounting.models.dto.WarehouseDto;
import com.warehouse_accounting.services.interfaces.ProductionTasksService;
import com.warehouse_accounting.services.interfaces.WarehouseService;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;
import java.util.Arrays;

import static com.warehouse_accounting.components.UtilView.subMenuTabs;


@UIScope
@Log4j2
public class ProductionTasksForm extends VerticalLayout {
    private final ProductionTasks productionTasks;
    private final Div returnDiv;

    private transient ProductionTasksDto productionTasksDto;
    private Binder<ProductionTasksDto> productionTasksDtoBinder =
            new Binder<>(ProductionTasksDto.class);

    private final transient ProductionTasksService productionTasksService;
    
    private final transient WarehouseService warehouseService;

    public ProductionTasksForm(ProductionTasks productionTasks,
                               ProductionTasksDto productionTasksDto,
                               ProductionTasksService productionTasksService,
                               WarehouseService warehouseService) {
        this.productionTasks = productionTasks;
        this.returnDiv = productionTasks.getMainContent();
        this.productionTasksDto = productionTasksDto;
        this.productionTasksService = productionTasksService;
        this.warehouseService = warehouseService;

        productionTasks.removeAll();
        add(createTopGroupElements(), createInputFieldForm());

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

                if (productionTasksDto.getId() != null && productionTasksDto.getId() > 0) {
                    productionTasksService.update(productionTasksDto);
                } else {
                    productionTasksService.create(productionTasksDto);
                }
                productionTasks.getProductionTasksGridLayout().updateGrid();
                productionTasks.removeAll();
                productionTasks.add(returnDiv);
            } catch (ValidationException e) {
                log.error("Ошибка валидации");
            }
        });
        return saveButton;
    }

    private Button closeProductionTechnologyButton() {
        Button closeButton = new Button("Закрыть");
        closeButton.addClickListener(c -> {
            productionTasks.removeAll();
            productionTasks.add(returnDiv);
        });
        return closeButton;

    }

    private HorizontalLayout createEditButton(){

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
                .bind(ProductionTasksDto::getDescription,ProductionTasksDto::setDescription);

        VerticalLayout timeManagement = new VerticalLayout(new Span("Начало производства"),
                new Span("Завершение производства"), new Span("Время производства"));
        footer.add(new Details("Учет времени",timeManagement));

        HorizontalLayout tasks = new HorizontalLayout(new Details("Задачи",new Span("Нет задач")),
                UtilView.plusButton("Задача"));
        tasks.setAlignItems(Alignment.CENTER);
        footer.add(tasks);

        HorizontalLayout files = new HorizontalLayout(new Details("Файлы",new Span("Нет файлов")),
                UtilView.plusButton("Файл"));
        files.setAlignItems(Alignment.CENTER);
        footer.add(files);

        return footer;
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
        return formLayout;
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

