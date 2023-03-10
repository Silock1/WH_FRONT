package com.warehouse_accounting.components.production.forms;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.UtilView;
import com.warehouse_accounting.components.production.TechnologicalOperations;
import com.warehouse_accounting.components.sales.forms.order.components.CheckboxWithHelper;
import com.warehouse_accounting.models.dto.CompanyDto;
import com.warehouse_accounting.models.dto.ProjectDto;
import com.warehouse_accounting.models.dto.TechnologicalMapDto;
import com.warehouse_accounting.models.dto.TechnologicalOperationDto;
import com.warehouse_accounting.models.dto.WarehouseDto;
import com.warehouse_accounting.services.interfaces.CompanyService;
import com.warehouse_accounting.services.interfaces.TechnologicalOperationsService;
import com.warehouse_accounting.services.interfaces.ProjectService;
import com.warehouse_accounting.services.interfaces.TechnologicalMapService;
import com.warehouse_accounting.services.interfaces.WarehouseService;
import lombok.extern.log4j.Log4j2;


import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;

import static com.vaadin.flow.component.button.ButtonVariant.LUMO_TERTIARY_INLINE;

@Log4j2
@UIScope
public class TechnologicalOperationsForm extends VerticalLayout {

    private final TechnologicalOperations technologicalOperations;
    private final TechnologicalOperationDto technologicalOperationDto;
    private final Div returnDiv;
    private final WarehouseService warehouseService;
    private final TechnologicalMapService technologicalMapService;
    private final TechnologicalOperationsService productionOperationsService;
    private final CompanyService companyService;
    private final ProjectService projectService;
    private final Binder <TechnologicalOperationDto> technologicalOperationDtoBinder = new Binder<>(TechnologicalOperationDto.class);
    private List<CompanyDto> companies;
    private VerticalLayout contentForTabs = new VerticalLayout();


    public TechnologicalOperationsForm(TechnologicalOperations productionOperations, TechnologicalOperationDto technologicalOperation,
                                       WarehouseService warehouseService, TechnologicalMapService technologicalMapService, TechnologicalOperationsService productionOperationsService,
                                       CompanyService companyService, ProjectService projectService) throws IOException {
        this.technologicalOperationDto = technologicalOperation;
        this.technologicalOperations = productionOperations;
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
        Button continueButton = new Button("????????????????????");
        continueButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        continueButton.addClickListener(event -> {
            removeAll();
            createSecondPartTopGroup(technologicalOperations);
            createSecondPartForm();
        });
        return continueButton;
    }

    private Button closeProductionOperationButton() {
        Button closeButton = new Button("????????????");
        closeButton.addClickListener(c -> {
            technologicalOperations.removeAll();
            technologicalOperations.add(returnDiv);
        });
        return closeButton;
    }

    private void createFirstPartColumns() {
        VerticalLayout column = new VerticalLayout();
        TextField numberOfOperation = new TextField("??????????");
        technologicalOperationDtoBinder
                .forField(numberOfOperation)
                .bind(TechnologicalOperationDto::getNumber, TechnologicalOperationDto::setNumber);

        DatePicker createOperationDate = new DatePicker("????????");
        createOperationDate.setValue(LocalDate.now());
        technologicalOperationDtoBinder
                .forField(createOperationDate)
                .bind(TechnologicalOperationDto::getTechnologicalOperationDateTime, TechnologicalOperationDto::setTechnologicalOperationDateTime);

        ComboBox<TechnologicalMapDto> technologicalMap = createTechnologicalMapComboBox();


        BigDecimalField volumeOfProduction = new BigDecimalField("?????????? ????????????????????????");
        volumeOfProduction.setWidth("150px");
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

    private void createSecondPartTopGroup(TechnologicalOperations productionOperations) {
        Button save = new Button("??????????????????", buttonClickEvent -> {
            try {
                technologicalOperationDtoBinder.writeBean(technologicalOperationDto);
                productionOperationsService.create(technologicalOperationDto);
            } catch (ValidationException e) {
                log.error("???????????? ???????????????? ??????. ????????????????", e);
            }
            productionOperations.removeAll();
            productionOperations.add(returnDiv);
        });
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        Button close = new Button("??????????????", buttonClickEvent -> {
            productionOperations.removeAll();
            productionOperations.add(returnDiv);
        });

        MenuBar menuBar = new MenuBar();
        MenuItem editMenu = menuBar.addItem("????????????????");
        editMenu.add(new Icon(VaadinIcon.CARET_DOWN));

        editMenu.getSubMenu().addItem("??????????????", menuItemClickEvent -> {
            //todo ??????????????
        }).getElement().setAttribute("disabled", true);
        editMenu.getSubMenu().addItem("????????????????????", menuItemClickEvent -> {
            //todo ????????????????????
        });

        MenuItem print = menuBar.addItem(new Icon(VaadinIcon.PRINT));
        print.add("????????????");
        print.add(new Icon(VaadinIcon.CARET_DOWN));

        print.getSubMenu().addItem("?????????????????????????????? ????????????????", e -> {
        });

        print.getSubMenu().addItem("???????????? (70x49,5????)", e -> {
        });

        print.getSubMenu().addItem("?????????????????????????? (58??40????)", e -> {
        });

        print.getSubMenu().addItem("????????????????...", e -> {
        });

        print.getSubMenu().addItem("??????????????????...", e -> {
        });

        MenuItem send = menuBar.addItem(new Icon(VaadinIcon.ENVELOPE_O));
        send.add("??????????????????");
        send.add(new Icon(VaadinIcon.CARET_DOWN));

        send.getSubMenu().addItem("?????????????????????????????? ????????????????", e -> {
        });
        send.getSubMenu().addItem("????????????????...", e -> {
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
        createTabSheet();
        TextArea comment = new TextArea();
        comment.setPlaceholder("??????????????????????");
        comment.setMinWidth("300px");
        add(comment, createTechnicalOperationFooter());
    }


    private void createDateLine() {
        HorizontalLayout dateLine = new HorizontalLayout();
        Label numberText = new Label("?????????????????????????????? ???????????????? ???");
        TextField numberField = new TextField();
        Label fromText = new Label("????");
        DatePicker createTechnologyOperationDate = new DatePicker();
        createTechnologyOperationDate.setValue(LocalDate.now());
        Button statusOfTechnologyOperation = new Button("????????????");
        CheckboxWithHelper isCompleted = new CheckboxWithHelper("??????????????????",
                "?????????????????????????? ???????????????? - ?????? ????????????????. ???? ???? ?????????????????????? ?? ??????????????.");
        isCompleted.setValue(true);
        isCompleted.addValueChangeListener(event -> technologicalOperationDto.setPosted(event.getValue()));

        dateLine.add(numberText, numberField, fromText, createTechnologyOperationDate, statusOfTechnologyOperation, isCompleted);
        add(dateLine);
    }

    private void createTechnologyOperationDetails() {
        HorizontalLayout technologyOperationDetails = new HorizontalLayout();
        VerticalLayout leftColumn = new VerticalLayout();

        ComboBox<CompanyDto> company = new ComboBox<>("??????????????????????");
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
        ComboBox<ProjectDto> project = new ComboBox<>("????????????");
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

    private void createTabSheet() {
        Tab mainTab = new Tab("??????????????");
        mainTab.addClassName("subMenuItem");
        Tab docTab = new Tab("?????????????????? ??????????????????");
        docTab.addClassName("subMenuItem");
        Tabs tabs = new Tabs(mainTab, docTab);
        contentForTabs.add(createContentForMainTab());
        tabs.addSelectedChangeListener(event -> {
            if (event.getSelectedTab().getLabel().equals("??????????????")) {
                contentForTabs.removeAll();
                contentForTabs.add(createContentForMainTab());
            } else {
                contentForTabs.removeAll();
                contentForTabs.add("docs");
            }
        });
        add(tabs, contentForTabs);
    }

    private VerticalLayout createContentForMainTab(){
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMaxWidth("600px");
        HorizontalLayout firstRow = new HorizontalLayout();
        Span series = new Span("??????????");
        series.setWidth("40px");
        Span amount = new Span("??????-????");
        amount.setWidth("120px");
        Span leftovers = new Span("??????????????");
        leftovers.setWidth("60px");
        Span priceSpan = new Span("????????");
        priceSpan.setWidth("150px");
        Span costSpan = new Span("??????????");
        costSpan.setWidth("150px");
        firstRow.add(series, amount, leftovers, priceSpan, costSpan);
        HorizontalLayout productionCosts = new HorizontalLayout();
        //???????????????????? ?????????? ?? ???????????? ?????????? ??????????
        IntegerField volume = new IntegerField();
        volume.setPlaceholder("????????????????????");
        NumberField price = new NumberField();
        price.setPlaceholder("????????");
        NumberField cost = new NumberField();
        cost.setPlaceholder("??????????");
        productionCosts.add(volume, price, cost);
        verticalLayout.add(firstRow, new Hr(), new HorizontalLayout(new Span("?????????????????????????????? ??????????")),
                new HorizontalLayout(),//?????? ?????????? ???????????????????? ?????????????????????????????? ?????????? ???? ?????????????? ?????????? ??????????
                new HorizontalLayout(new Span("?????????????? ??????????????????")),
                new HorizontalLayout(),//?????? ?????????? ???????????????????? ??????????????????
                new HorizontalLayout(new Span("??????????????????")),
                new HorizontalLayout(),// ??????????????????
                new HorizontalLayout(new Span("?????????????? ???? ????????????????????????")),
                productionCosts);
        return verticalLayout;
    }

    private VerticalLayout createTechnicalOperationFooter() {
        VerticalLayout footer = new VerticalLayout();
        HorizontalLayout tasks = new HorizontalLayout(new Details("????????????", new Span("?????? ??????????")),
                UtilView.plusButton("????????????"));
        tasks.setAlignItems(Alignment.CENTER);
        footer.add(tasks);

        HorizontalLayout files = new HorizontalLayout();
        files.add(new Span("??????????"));

        MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setUploadButton(new Button(UtilView.plusButton("????????")));
        upload.setDropLabel(new Span("?????? ???????????????????? ?????????? ????????"));

        upload.addSucceededListener(event -> {
            String fileName = event.getFileName();
            InputStream inputStream = buffer.getInputStream(fileName);
            //?????????????? ???????????????????? ?????????? ???? ????????????
        });
        files.add(upload);
        footer.add(files);

        return footer;
    }


    private ComboBox<TechnologicalMapDto> createTechnologicalMapComboBox() {
        ComboBox<TechnologicalMapDto> technologicalMap = new ComboBox<>("?????????????????????????????? ??????????");
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
        ComboBox<WarehouseDto> warehouseForProducts = new ComboBox<>("?????????? ?????? ??????????????????");
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
        ComboBox<WarehouseDto> warehouseForMaterials = new ComboBox<>("?????????? ?????? ????????????????????");
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
