package com.warehouse_accounting.components.production.forms;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.production.ProductionOperations;
import com.warehouse_accounting.components.sales.forms.order.components.CheckboxWithHelper;
import com.warehouse_accounting.models.dto.CompanyDto;
import com.warehouse_accounting.models.dto.ProjectDto;
import com.warehouse_accounting.models.dto.TechnologicalMapDto;
import com.warehouse_accounting.models.dto.TechnologicalOperationDto;
import com.warehouse_accounting.models.dto.WarehouseDto;
import com.warehouse_accounting.services.interfaces.CompanyService;
import com.warehouse_accounting.services.interfaces.ProductionOperationsService;
import com.warehouse_accounting.services.interfaces.ProjectService;
import com.warehouse_accounting.services.interfaces.TechnologicalMapService;
import com.warehouse_accounting.services.interfaces.WarehouseService;
import lombok.extern.log4j.Log4j2;


import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static com.vaadin.flow.component.button.ButtonVariant.LUMO_TERTIARY_INLINE;

@Log4j2
@UIScope
public class ProductionOperationsForm extends VerticalLayout {

    private final ProductionOperations productionOperations;
    private final TechnologicalOperationDto technologicalOperationDto;
    private final Div returnDiv;
    private final WarehouseService warehouseService;
    private final TechnologicalMapService technologicalMapService;
    private final ProductionOperationsService productionOperationsService;
    private final CompanyService companyService;
    private final ProjectService projectService;
    private final Binder <TechnologicalOperationDto> productionOperationDtoBinder = new Binder<>(TechnologicalOperationDto.class);
    private List<CompanyDto> companies;


    public ProductionOperationsForm(ProductionOperations productionOperations, TechnologicalOperationDto technologicalOperation,
                                    WarehouseService warehouseService, TechnologicalMapService technologicalMapService, ProductionOperationsService productionOperationsService,
                                    CompanyService companyService, ProjectService projectService) throws IOException {
        this.technologicalOperationDto = technologicalOperation;
        this.productionOperations = productionOperations;
        this.warehouseService = warehouseService;
        this.technologicalMapService = technologicalMapService;
        this.productionOperationsService = productionOperationsService;
        this.companyService = companyService;
        this.projectService = projectService;
        this.returnDiv = productionOperations.getPageContent();
        companies = companyService.getAll();
        productionOperations.removeAll();
        add(createFirstPartTopGroupElements());
        createFirstPartColumns();
    }


    private HorizontalLayout createFirstPartTopGroupElements() {
        HorizontalLayout topPartLayout = new HorizontalLayout();
        topPartLayout.setAlignItems(Alignment.CENTER);
        topPartLayout.add(
                continueProductionOperationsButton(),
                closeProductionOperationButton()
        );
        return topPartLayout;
    }

    private Button continueProductionOperationsButton() {
        Button continueButton = new Button("Продолжить");
        continueButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        continueButton.addClickListener(event -> {
            removeAll();
            createSecondPartTopGroup(productionOperations);
            createSecondPartForm();
        });
        return continueButton;
    }

    private Button closeProductionOperationButton() {
        Button closeButton = new Button("Отмена");
        closeButton.addClickListener(c -> {
            productionOperations.removeAll();
            productionOperations.add(returnDiv);
        });
        return closeButton;
    }

    private void createFirstPartColumns() {
        VerticalLayout column = new VerticalLayout();
        TextField numberOfOperation = new TextField("Номер");
        productionOperationDtoBinder
                .forField(numberOfOperation)
                .bind(TechnologicalOperationDto::getNumber, TechnologicalOperationDto::setNumber);

        DatePicker createOperationDate = new DatePicker("Дата");
        createOperationDate.setValue(LocalDate.now());
        productionOperationDtoBinder
                .forField(createOperationDate)
                .bind(TechnologicalOperationDto::getTechnologicalOperationDateTime, TechnologicalOperationDto::setTechnologicalOperationDateTime);

        ComboBox<TechnologicalMapDto> technologicalMap = createTechnologicalMapComboBox();


        BigDecimalField volumeOfProduction = new BigDecimalField("Объем производства");
        ComboBox<WarehouseDto> warehouseForMaterials = createComboBoxForMaterials();

        ComboBox<WarehouseDto> warehouseForProducts = createComboBoxForProducts();



        column.add(numberOfOperation, createOperationDate,
                technologicalMap, volumeOfProduction, warehouseForMaterials, warehouseForProducts);
        add(column);
    }


    private void showErrorNotification(String showText) {
        Notification notification = new Notification();
        notification.setPosition(Notification.Position.TOP_START);
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

        Icon icon = VaadinIcon.WARNING.create();
        icon.getElement().getStyle().set("margin-right", "var(--lumo-space-m)");
        Button closeBtn = new Button(
                VaadinIcon.CLOSE_SMALL.create(),
                clickEvent -> notification.close());
        closeBtn.addThemeVariants(LUMO_TERTIARY_INLINE);

        HorizontalLayout layout = new HorizontalLayout();
        layout.setAlignItems(Alignment.CENTER);
        Text text = new Text(showText);
        layout.add(icon, text,closeBtn);
        layout.setMaxWidth("350px");

        notification.add(layout);
        notification.open();

    }

//    private HorizontalLayout createSecondPartTopGroupElements() {
//        HorizontalLayout topPartLayout = new HorizontalLayout();
//        topPartLayout.setAlignItems(Alignment.CENTER);
//        topPartLayout.add(
//                continueProductionOperationsButton(),
//                closeProductionOperationButton()
//        );
//        return topPartLayout;
//    }

    private void createSecondPartTopGroup(ProductionOperations productionOperations) {
        Button save = new Button("Сохранить", buttonClickEvent -> {
            try {
                productionOperationDtoBinder.writeBean(technologicalOperationDto);
                productionOperationsService.create(technologicalOperationDto);
            } catch (ValidationException e) {
                log.error("Ошибка создания тех. операции", e);
            }
            productionOperations.removeAll();
            productionOperations.add(returnDiv);
        });
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        Button close = new Button("Закрыть", buttonClickEvent -> {
            productionOperations.removeAll();
            productionOperations.add(returnDiv);
        });

        MenuBar menuBar = new MenuBar();
        MenuItem editMenu = menuBar.addItem("Изменить");
        editMenu.add(new Icon(VaadinIcon.CARET_DOWN));

        editMenu.getSubMenu().addItem("Удалить", menuItemClickEvent -> {
            //todo удалить
        }).getElement().setAttribute("disabled", true);
        editMenu.getSubMenu().addItem("Копировать", menuItemClickEvent -> {
            //todo копировать
        });

        MenuItem print = menuBar.addItem(new Icon(VaadinIcon.PRINT));
        print.add("Печать");
        print.add(new Icon(VaadinIcon.CARET_DOWN));

        print.getSubMenu().addItem("Технологическая операция", e -> {
        });

        print.getSubMenu().addItem("Ценник (70x49,5мм)", e -> {
        });

        print.getSubMenu().addItem("Термоэтикетка (58х40мм)", e -> {
        });

        print.getSubMenu().addItem("Комплект...", e -> {
        });

        print.getSubMenu().addItem("Настроить...", e -> {
        });

        MenuItem send = menuBar.addItem(new Icon(VaadinIcon.ENVELOPE_O));
        send.add("Отправить");
        send.add(new Icon(VaadinIcon.CARET_DOWN));

        send.getSubMenu().addItem("Технологическая операция", e -> {
        });
        send.getSubMenu().addItem("Комплект...", e -> {
        });

        Div div = new Div(save, close, menuBar);
        div.setClassName("toolPanel");
        div.getStyle().set("display", "flex");
        div.setSizeFull();
        add(div);
    }

    private void createSecondPartForm() {
        createDateLine();
        createTechnologyOperationDetails();
    }

    private void createDateLine() {
        HorizontalLayout dateLine = new HorizontalLayout();
        Label numberText = new Label("Технологическая операция №");
        TextField numberField = new TextField();
        Label fromText = new Label("от");
        DatePicker createTechnologyOperationDate = new DatePicker();
        createTechnologyOperationDate.setValue(LocalDate.now());
        Button statusOfTechnologyOperation = new Button("Статус");
        CheckboxWithHelper isCompleted = new CheckboxWithHelper("Проведено", "Непроведённый документ - это черновик. Он не учитывается в отчетах.");
        isCompleted.setValue(true);
        isCompleted.addValueChangeListener(event -> technologicalOperationDto.setPosted(event.getValue()));

        dateLine.add(numberText, numberField, fromText, createTechnologyOperationDate, statusOfTechnologyOperation, isCompleted);
        add(dateLine);
    }

    private void createTechnologyOperationDetails() {
        HorizontalLayout technologyOperationDetails = new HorizontalLayout();
        VerticalLayout leftColumn = new VerticalLayout();

        ComboBox<CompanyDto> company = new ComboBox<>("Организация");
        company.setClearButtonVisible(true);
        company.setItems(companies);
        company.setItemLabelGenerator(CompanyDto::getName);
        company.setRequired(true);
        company.addValueChangeListener(event -> {
            CompanyDto companyDto = event.getValue();
            technologicalOperationDto.setCompanyId(companyDto.getId());
            technologicalOperationDto.setCompanyName(companyDto.getName());
        });

        ComboBox<WarehouseDto> warehouseForMaterials = createComboBoxForMaterials();
        ComboBox<TechnologicalMapDto> technologicalMap = createTechnologicalMapComboBox();
        leftColumn.add(company, warehouseForMaterials, technologicalMap);

        VerticalLayout rightColumn = new VerticalLayout();
        ComboBox<WarehouseDto> warehouseForProducts = createComboBoxForProducts();
        ComboBox<ProjectDto> project = new ComboBox<>("Проект");
        project.setClearButtonVisible(true);
        List<ProjectDto> projects = projectService.getAll();
        project.setItems(projects);
        project.setItemLabelGenerator(ProjectDto::getName);
        project.addValueChangeListener(event -> {
            ProjectDto projectDto = event.getValue();
            technologicalOperationDto.setProjectId(projectDto.getId());
            technologicalOperationDto.setProjectName(projectDto.getName());
        });
        rightColumn.add(warehouseForProducts, project);

        technologyOperationDetails.add(leftColumn, rightColumn);
        add(technologyOperationDetails);

    }

    private ComboBox<TechnologicalMapDto> createTechnologicalMapComboBox() {
        ComboBox<TechnologicalMapDto> technologicalMap = new ComboBox<>("Технологическая карта");
        technologicalMap.setClearButtonVisible(true);
        technologicalMap.setItems(technologicalMapService.getAll());
        technologicalMap.setItemLabelGenerator(TechnologicalMapDto::getName);
        technologicalMap.addValueChangeListener(event -> {
            TechnologicalMapDto technologicalMapDto = event.getValue();
            technologicalOperationDto.setTechnologicalMapId(technologicalMapDto.getId());
            technologicalOperationDto.setTechnologicalMapName(technologicalMapDto.getName());
        });
        return technologicalMap;
    }

    private ComboBox<WarehouseDto> createComboBoxForProducts() {
        ComboBox<WarehouseDto> warehouseForProducts = new ComboBox<>("Склад для продукции");
        warehouseForProducts.setClearButtonVisible(true);
        warehouseForProducts.setRequired(true);
        warehouseForProducts.setItems(warehouseService.getAll());
        warehouseForProducts.setItemLabelGenerator(WarehouseDto::getName);
        warehouseForProducts.addValueChangeListener(event -> {
            WarehouseDto warehouseDto = event.getValue();
            technologicalOperationDto.setWarehouseForProductId(warehouseDto.getId());
            technologicalOperationDto.setWarehouseForProductName(warehouseDto.getName());
        });
        return warehouseForProducts;
    }

    private ComboBox<WarehouseDto> createComboBoxForMaterials() {
        ComboBox<WarehouseDto> warehouseForMaterials = new ComboBox<>("Склад для материалов");
        warehouseForMaterials.setClearButtonVisible(true);
        warehouseForMaterials.setRequired(true);
        warehouseForMaterials.setItems(warehouseService.getAll());
        warehouseForMaterials.setItemLabelGenerator(WarehouseDto::getName);
        warehouseForMaterials.addValueChangeListener(event -> {
            WarehouseDto warehouseDto = event.getValue();
            technologicalOperationDto.setWarehouseForMaterialsId(warehouseDto.getId());
            technologicalOperationDto.setWarehouseForMaterialsName(warehouseDto.getName());
        });
        return warehouseForMaterials;
    }

}
