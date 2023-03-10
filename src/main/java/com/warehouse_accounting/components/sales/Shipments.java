package com.warehouse_accounting.components.sales;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.warehouse_accounting.components.sales.filter.SalesShipmentsFilter;
import com.warehouse_accounting.components.sales.grids.SalesGridLayout;
import com.warehouse_accounting.services.interfaces.*;

public class Shipments extends VerticalLayout {

    private SalesGridLayout salesGridLayout;

    private SalesShipmentsFilter salesShipmentsFilter;
    private final TextField textFieldGridSelected = new TextField();
    private final Div parentLayer;

    private CompanyService companyService;
    private ContractorService contractorService;
    private ContractService contractService;
    private ProjectService projectService;
    private WarehouseService warehouseService;
    private DepartmentService departmentService;
    private EmployeeService employeeService;

    public Shipments(Div parentLayer, CompanyService companyService, ContractorService contractorService,
                     ContractService contractService, ProjectService projectService, WarehouseService warehouseService,
                     DepartmentService departmentService, EmployeeService employeeService) {
        this.companyService = companyService;
        this.contractorService = contractorService;
        this.contractService = contractService;
        this.projectService = projectService;
        this.warehouseService = warehouseService;
        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.parentLayer = parentLayer;
        this.salesShipmentsFilter = new SalesShipmentsFilter(companyService, contractorService, contractService,
                projectService, warehouseService, employeeService, departmentService);
        salesGridLayout = new SalesGridLayout(textFieldGridSelected);
        Div pageContent = new Div();
        pageContent.add(salesGridLayout);
        pageContent.setSizeFull();
        add(getGroupButtons(), salesShipmentsFilter, pageContent);
    }

    private HorizontalLayout getGroupButtons() {
        HorizontalLayout groupControl = new HorizontalLayout();

        Button helpButton = new Button(new Icon(VaadinIcon.QUESTION_CIRCLE));
        helpButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);

        Label textProducts = new Label();
        textProducts.setText("????????????????");


        Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH));
        refreshButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);


        Button addOrderButton = new Button("????????????????", new Icon(VaadinIcon.PLUS));
        addOrderButton.addThemeVariants(ButtonVariant.LUMO_SMALL);

        addOrderButton.addClickListener(buttonClickEvent -> {
//            ShipmentsForm shipmentsForm = new ShipmentsForm(parentLayer, this);
//            parentLayer.removeAll();
//            parentLayer.add(shipmentsForm);

        });


        Button addFilterButton = new Button("????????????");
        addFilterButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        addFilterButton.addClickListener(e ->
                salesShipmentsFilter.setVisible(!salesShipmentsFilter.isVisible())
        );

        TextField searchField = new TextField();
        searchField.setPlaceholder("?????????? ?????? ??????????????????????");
        searchField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        searchField.addInputListener(inputEvent -> {

        });

        HorizontalLayout editMenuBar = getEditMenuBar();
        HorizontalLayout statusMenuBar = getStatusMenuBar();
        HorizontalLayout createMenuBar = getCreateMenuBar();
        HorizontalLayout printMenuBar = getPrintMenuBar();

        Button setting = new Button(new Icon(VaadinIcon.COG));
        setting.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        setting.addClickListener(event -> {

        });

        groupControl.add(helpButton, textProducts, refreshButton, addOrderButton,
                addFilterButton, searchField, editMenuBar, statusMenuBar, createMenuBar, printMenuBar, setting);
        setSizeFull();
        return groupControl;
    }

    private HorizontalLayout getEditMenuBar() {
        Icon caretDownIcon = new Icon(VaadinIcon.CARET_DOWN);
        caretDownIcon.setSize("12px");
        textFieldGridSelected.setReadOnly(true);
        textFieldGridSelected.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        textFieldGridSelected.setWidth("30px");
        textFieldGridSelected.setValue("0");

        MenuBar editMenuBar = new MenuBar();
        editMenuBar.addThemeVariants(MenuBarVariant.LUMO_SMALL);
        HorizontalLayout editItem = new HorizontalLayout(new Text("????????????????"), caretDownIcon);
        editItem.setSpacing(false);
        editItem.setAlignItems(Alignment.CENTER);

        MenuItem editMenu = editMenuBar.addItem(editItem);

        editMenu.getSubMenu().addItem("??????????????", menuItemClickEvent -> {
            int selected = salesGridLayout.getProductGrid().asMultiSelect().getSelectedItems().size();
            Notification notification = new Notification(String.format("???????????????? ?????? ???????????????? %d", selected),
                    3000, Notification.Position.MIDDLE);
            notification.open();
        });

        editMenu.getSubMenu().addItem("????????????????????", menuItemClickEvent -> {

        });

        editMenu.getSubMenu().addItem("???????????????? ????????????????????????????", menuItemClickEvent -> {

        });
        editMenu.getSubMenu().addItem("????????????????", menuItemClickEvent -> {

        });
        editMenu.getSubMenu().addItem("?????????? ????????????????????", menuItemClickEvent -> {

        });
        editMenu.getSubMenu().addItem("????????????????????", menuItemClickEvent -> {

        });


        HorizontalLayout groupEdit = new HorizontalLayout();
        groupEdit.add(textFieldGridSelected, editMenuBar);
        groupEdit.setSpacing(false);
        groupEdit.setAlignItems(Alignment.CENTER);
        return groupEdit;
    }

    private HorizontalLayout getStatusMenuBar() {
        Icon caretDownIcon = new Icon(VaadinIcon.CARET_DOWN);
        caretDownIcon.setSize("12px");
        textFieldGridSelected.setReadOnly(true);
        textFieldGridSelected.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        textFieldGridSelected.setWidth("30px");
        textFieldGridSelected.setValue("0");

        MenuBar statusMenuBar = new MenuBar();
        statusMenuBar.addThemeVariants(MenuBarVariant.LUMO_SMALL);
        HorizontalLayout horizontalLayout = new HorizontalLayout(new Text("????????????"), caretDownIcon);
        horizontalLayout.setSpacing(false);
        horizontalLayout.setAlignItems(Alignment.CENTER);

        MenuItem statusItem = statusMenuBar.addItem(horizontalLayout);
        statusItem.getSubMenu().addItem("??????????", e -> {

        });
        statusItem.getSubMenu().addItem("??????????e??????????", e -> {

        });
        statusItem.getSubMenu().addItem("????????????", e -> {

        });
        statusItem.getSubMenu().addItem("????????????????", e -> {

        });
        statusItem.getSubMenu().addItem("??????????????????", e -> {

        });
        statusItem.getSubMenu().addItem("??????????????", e -> {

        });
        statusItem.getSubMenu().addItem("??????????????", e -> {

        });
        statusItem.getSubMenu().addItem("??????????????????...", e -> {

        });
        HorizontalLayout groupStatus = new HorizontalLayout();
        groupStatus.add(statusMenuBar);
        groupStatus.setSpacing(false);
        groupStatus.setAlignItems(Alignment.CENTER);
        return groupStatus;
    }

    private HorizontalLayout getCreateMenuBar() {
        Icon caretDownIcon = new Icon(VaadinIcon.CARET_DOWN);
        caretDownIcon.setSize("12px");
        textFieldGridSelected.setReadOnly(true);
        textFieldGridSelected.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        textFieldGridSelected.setWidth("30px");
        textFieldGridSelected.setValue("0");

        MenuBar createMenuBar = new MenuBar();
        createMenuBar.addThemeVariants(MenuBarVariant.LUMO_SMALL);
        HorizontalLayout horizontalLayout = new HorizontalLayout(new Text("??????????????"), caretDownIcon);
        horizontalLayout.setSpacing(false);
        horizontalLayout.setAlignItems(Alignment.CENTER);


        HorizontalLayout groupCreate = new HorizontalLayout();
        groupCreate.add(createMenuBar);
        groupCreate.setSpacing(false);
        groupCreate.setAlignItems(Alignment.CENTER);
        return groupCreate;
    }

    private HorizontalLayout getPrintMenuBar() {
        MenuBar printMenuBar = new MenuBar();
        printMenuBar.addThemeVariants(MenuBarVariant.LUMO_SMALL);
        Icon printIcon = new Icon(VaadinIcon.PRINT);
        printIcon.setSize("12px");
        Icon caretDownIcon = new Icon(VaadinIcon.CARET_DOWN);
        caretDownIcon.setSize("12px");
        HorizontalLayout printItem = new HorizontalLayout(printIcon, new Text("  ????????????"), caretDownIcon);
        printItem.setSpacing(false);
        printItem.setAlignItems(Alignment.CENTER);
        MenuItem print = printMenuBar.addItem(printItem);

        print.getSubMenu().addItem("???????????? ????????????????", e -> {

        });
        print.getSubMenu().addItem("?????? ?? ??????????????????????????????????", e -> {

        });
        print.getSubMenu().addItem("?????? ?????? ????????????????????????????????", e -> {

        });

        print.getSubMenu().addItem("??????", e -> {

        });

        print.getSubMenu().addItem("???????????????? ??????", e -> {

        });
        print.getSubMenu().addItem("????????-12", e -> {

        });

        print.getSubMenu().addItem("?????????????????? ??????????????????", e -> {

        });

        print.getSubMenu().addItem("????????-12", e -> {

        });

        print.getSubMenu().addItem("???????????????????????? ??????????????????", e -> {

        });

        print.getSubMenu().addItem("???????? ????????????????????: ?????? 1162", e -> {

        });

        print.getSubMenu().addItem("?????????????????? ????????", e -> {

        });

        print.getSubMenu().addItem("????????????????", e -> {

        });

        print.getSubMenu().addItem("??????????????????...", e -> {

        });

        HorizontalLayout groupPrint = new HorizontalLayout();
        groupPrint.add(printMenuBar);
        groupPrint.setSpacing(false);
        groupPrint.setAlignItems(Alignment.CENTER);
        return groupPrint;
    }
}
