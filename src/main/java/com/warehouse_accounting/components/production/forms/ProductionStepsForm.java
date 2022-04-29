package com.warehouse_accounting.components.production.forms;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.warehouse_accounting.components.production.grids.ProductionStepsGridLayout;
import com.warehouse_accounting.models.dto.EmployeeDto;
import com.warehouse_accounting.models.dto.ProductDto;
import com.warehouse_accounting.models.dto.ProductionStageDto;
import com.warehouse_accounting.services.interfaces.EmployeeService;
import com.warehouse_accounting.services.interfaces.ProductionStageService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.Date;

@Log4j2
public class ProductionStepsForm extends VerticalLayout {

    private  Div parentLayer;
    private  Component returnLayer;
    private TextArea textArea;
    private final ProductionStageService productionStageService;
    private final EmployeeService employeeService;
    private Binder<ProductionStageDto> productionStageDtoBinder = new Binder<>(ProductionStageDto.class);
    private ProductionStageDto productionStageDto;
    private ProductionStepsGridLayout productionStepsGridLayout;

    public ProductionStepsForm(
            Div parentLayer,
            Component returnLayer,
            ProductionStageService productionStageService,
            EmployeeService employeeService,
            ProductionStageDto productionStageDto,
            ProductionStepsGridLayout productionStepsGridLayout
    ) {
        this.parentLayer = parentLayer;
        this.returnLayer = returnLayer;
        this.productionStageService = productionStageService;
        this.employeeService = employeeService;
        this.productionStageDto = productionStageDto;
        this.productionStepsGridLayout = productionStepsGridLayout;
        createButtons();
        text();
        createColumns();
        //add(getFormLayout());
    }


    private void createButtons() {
        HorizontalLayout buttons = new HorizontalLayout();
        Button saveButton = new Button("Сохранить", e -> {
            parentLayer.removeAll();
            EmployeeDto employeeDto = employeeService.getById(1L);
            try {
                productionStageDtoBinder.writeBean(productionStageDto);
                productionStageDto.setEditorEmployeeId(employeeDto.getId());
                productionStageDto.setOwnerEmployeeId(employeeDto.getId());
                productionStageDto.setOwnerDepartmentId(employeeDto.getDepartment().getId());

                if (productionStageDto.getId() != null && productionStageDto.getId() > 0) {
                    productionStageDto.setDateOfEdit(null);
                    productionStageService.update(productionStageDto);
                } else {
                    productionStageService.create(productionStageDto);
                }


            } catch (ValidationException ex) {
                log.error("Ошибка валидации при создании нового этапа", ex);
            }
            //productionStepsGridLayout = new ProductionStepsGridLayout(productionStageService, employeeService);
            productionStepsGridLayout.updateGrid();
            parentLayer.add(productionStepsGridLayout);
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);

        Button closeButton = new Button("Закрыть", e -> {
            parentLayer.removeAll();
            //productionStepsGridLayout = new ProductionStepsGridLayout(productionStageService, employeeService);
            parentLayer.add(returnLayer);
        });

//        Button deleteButton = new Button("Удалить", e -> {
//            parentLayer.removeAll();
//            //productionStepsGridLayout = new ProductionStepsGridLayout(productionStageService, employeeService);
//            parentLayer.add(returnLayer);
//        });

        HorizontalLayout editButton = getEditButton();

        Button helpButton = new Button(new Image("icons/helpApp.svg", "helpApp"));
        helpButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_LARGE);
        helpButton.addClickListener(c -> Notification.show(
                "Элементы, перемещенные в архив, не отображаются в справочниках и отчетах. Архив позволяет скрывать неактуальные элементы, не удаляя их." +
                        "\n", 3000, Notification.Position.MIDDLE));


        Icon refresh = new Icon(VaadinIcon.REFRESH);
        refresh.setColor("silver");
        Button refreshButton = new Button(refresh);
        refreshButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        refreshButton.addClickListener(click -> {
            System.out.println("перезагрузка");
        });


        Button archiveButton = new Button("Поместить в архив", e -> {
            parentLayer.removeAll();
            parentLayer.add(returnLayer);
        });

        buttons.add(saveButton, closeButton, editButton, helpButton, archiveButton );
        add(buttons);
    }

    private HorizontalLayout getEditButton(){
        Icon caretDownIcon = new Icon(VaadinIcon.CARET_DOWN);
        caretDownIcon.setSize("12px");

        MenuBar editMenuBar = new MenuBar();
        editMenuBar.addThemeVariants(MenuBarVariant.LUMO_SMALL);
        HorizontalLayout editItem = new HorizontalLayout(new Text("Изменить"), caretDownIcon);
        editItem.setSpacing(false);
        editItem.setAlignItems(Alignment.CENTER);

        MenuItem editMenu = editMenuBar.addItem(editItem);
        boolean visibleItemDelete = true;
        if (productionStageDto.getId() != null && productionStageDto.getId() > 1) {
            visibleItemDelete = false;
        }
        editMenu.getSubMenu().addItem("Удалить", menuItemClickEvent -> {
            productionStageService.delete(productionStageDto.getId());
            productionStepsGridLayout.updateGrid();
            parentLayer.removeAll();
            parentLayer.add(productionStepsGridLayout);
        }).getElement().setAttribute("disabled", visibleItemDelete);
        editMenu.getSubMenu().addItem("Копировать", menuItemClickEvent -> {

        });

        HorizontalLayout groupEdit = new HorizontalLayout();
        groupEdit.add(editMenuBar);
        groupEdit.setSpacing(false);
        groupEdit.setAlignItems(Alignment.CENTER);
        return groupEdit;
    }

    private void text() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Text productionTasks = new Text("Этапы");
        horizontalLayout.add( productionTasks);
        add(horizontalLayout);
    }


    private void createColumns() {
        TextArea textArea = new TextArea();
        textArea.setMinHeight("100px");
        textArea.setMaxHeight("150px");
        textArea.setLabel("Описание");

        TextField nameField = new TextField();
        nameField.setLabel("Наименование");
        nameField.setMinWidth("300px");
        productionStageDtoBinder.forField(nameField).asRequired().bind(ProductionStageDto::getName, ProductionStageDto::setName);
        nameField.setValue(productionStageDto.getName() == null ? "" : productionStageDto.getName());

        TextArea descriptionField = new TextArea();
        descriptionField.setMinWidth("300px");
        descriptionField.setMinHeight("100px");
        descriptionField.setMaxHeight("150px");
        descriptionField.setLabel("Описание");
        productionStageDtoBinder.forField(descriptionField).bind(ProductionStageDto::getDescription, ProductionStageDto::setDescription);
        descriptionField.setValue(productionStageDto.getDescription() == null ? "" : productionStageDto.getDescription());
        add(nameField, descriptionField);
    }

    private HorizontalLayout createSubColumn(String title, String className) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addClassName(className);
        Label label = new Label(title);
        TextField textField = new TextField();
        horizontalLayout.add(label, textField);
        return horizontalLayout;
    }

    private FormLayout getFormLayout() {
        FormLayout form = new FormLayout();

        //Operations are grouped by fields : new field + something else + bind + form.add
        TextField nameField = new TextField();
        productionStageDtoBinder.forField(nameField).asRequired().bind(ProductionStageDto::getName, ProductionStageDto::setName);
        form.addFormItem(nameField,"Наименование");

        TextField descriptionField = new TextField();
        productionStageDtoBinder.forField(descriptionField).bind(ProductionStageDto::getDescription, ProductionStageDto::setDescription);
        form.addFormItem(descriptionField,"Описание");

//
//        form.setWidthFull();
        form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 10));
        return form;
   }
}
