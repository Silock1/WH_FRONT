package com.warehouse_accounting.components.purchases;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.dependency.CssImport;
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
import com.warehouse_accounting.components.purchases.filter.AccountsPayableFilter;
import com.warehouse_accounting.components.purchases.forms.CreateInvoiceForm;
import com.warehouse_accounting.components.purchases.grids.SupplierInvoiceGridLayout;
import com.warehouse_accounting.models.dto.StatusDto;
import com.warehouse_accounting.services.interfaces.CompanyService;
import com.warehouse_accounting.services.interfaces.ContractService;
import com.warehouse_accounting.services.interfaces.ContractorService;
import com.warehouse_accounting.services.interfaces.DepartmentService;
import com.warehouse_accounting.services.interfaces.EmployeeService;
import com.warehouse_accounting.services.interfaces.ProductService;
import com.warehouse_accounting.services.interfaces.ProjectService;
import com.warehouse_accounting.services.interfaces.StatusService;
import com.warehouse_accounting.services.interfaces.WarehouseService;


/*
Счета поставщиков
 */

@CssImport(value = "./css/account_payable.css", themeFor = "vaadin-*-overlay")
public class AccountsPayable extends VerticalLayout {

    private SupplierInvoiceGridLayout supplierInvoiceGridLayout;
    private AccountsPayableFilter filterLayout;
    private final TextField textField = new TextField();
    private final Div parentLayer;

    private StatusService statusService;
    private GridForStatus gridForStatus;


    public AccountsPayable(Div parentLayer, AccountsPayableFilter filterLayout, StatusService statusService,
                           WarehouseService warehouseService, ContractService contractService,
                           ContractorService contractorService, ProjectService projectService,
                           EmployeeService employeeService, DepartmentService departmentService,
                           ProductService productService, CompanyService companyService) {
        this.filterLayout = new AccountsPayableFilter(warehouseService, contractService, contractorService,
                projectService, employeeService, departmentService,
                productService, companyService);
        this.parentLayer = parentLayer;
        this.statusService = statusService;
        this.supplierInvoiceGridLayout = new SupplierInvoiceGridLayout();
//        Div pageContent = new Div();
//        pageContent.add(supplierInvoiceGridLayout.settingButton);
//        pageContent.add(supplierInvoiceGridLayout.initSupplierInvoiceGrid()); // здесь статика была
//        pageContent.setSizeFull();
        initPage();
    }

    private void initPage() {
        removeAll();
        supplierInvoiceGridLayout.setSizeFull();
        add(getGroupButtons(), filterLayout, supplierInvoiceGridLayout);
        filterLayout.setVisible(false);
    }

    private HorizontalLayout getGroupButtons() {
        HorizontalLayout groupControl = new HorizontalLayout();

        Button helpButton = new Button(new Icon(VaadinIcon.QUESTION_CIRCLE));
        helpButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
        helpButton.addClickListener(e -> {
            Notification.show("Счета поставщиков помогают планировать оплату товаров. Счета не меняют количество товара на складе — для этого нужно создать приемку, а чтобы учесть оплату — платеж.\n" +
                    "\n" +
                    "Дату оплаты можно запланировать. Не оплаченные вовремя счета отображаются в разделе Показатели.\n" +
                    "\n" +
                    "Счета можно создавать сразу из заказа поставщику.\n" +
                    "\n" +
                    "Читать инструкцию: Счета поставщиков", 5000, Notification.Position.TOP_START);
        });

        Label textProducts = new Label();
        textProducts.setText("Счета поставщиков");

        Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH));
        refreshButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);

        Button addOrderButton = new Button("Счет", new Icon(VaadinIcon.PLUS));
        addOrderButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        addOrderButton.addClickListener(buttonClickEvent -> {
            removeAll();
            CreateInvoiceForm сreateInvoiceForm = new CreateInvoiceForm(parentLayer, supplierInvoiceGridLayout);
            сreateInvoiceForm.setOnCloseHandler(() -> initPage());
            add(сreateInvoiceForm);
        });

        Button addFilterButton = new Button("Фильтр");
        addFilterButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        addFilterButton.addClickListener(e -> filterLayout.setVisible(!filterLayout.isVisible()));

        TextField searchField = new TextField();
        searchField.setPlaceholder("Номер или комментарий");
        searchField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        searchField.addInputListener(inputEvent -> {

        });

        Button settingButton = new Button(new Icon(VaadinIcon.COG));
        settingButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        settingButton.addClickListener(event -> {

        });


        HorizontalLayout editMenuBar = getEditMenuBar();
        HorizontalLayout statusMenuBar = getStatusMenuBar();
        HorizontalLayout createMenuBar = getCreateMenuBar();
        HorizontalLayout printMenuBar = getPrintMenuBar();

        groupControl.add(helpButton, textProducts, refreshButton, addOrderButton,
                addFilterButton, searchField, editMenuBar, statusMenuBar, createMenuBar, printMenuBar, settingButton);
        setSizeFull();
        return groupControl;
    }

    private HorizontalLayout getEditMenuBar() {
        Icon caretDownIcon = new Icon(VaadinIcon.CARET_DOWN);
        caretDownIcon.setSize("12px");
        textField.setReadOnly(true);
        textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        textField.setWidth("30px");
        textField.setValue("0");

        MenuBar editMenuBar = new MenuBar();
        editMenuBar.addThemeVariants(MenuBarVariant.LUMO_SMALL);
        HorizontalLayout editItem = new HorizontalLayout(new Text("Изменить"), caretDownIcon);
        editItem.setSpacing(false);
        editItem.setAlignItems(Alignment.CENTER);

        HorizontalLayout groupEdit = new HorizontalLayout();
        groupEdit.add(textField, editMenuBar);
        groupEdit.setSpacing(false);
        groupEdit.setAlignItems(Alignment.CENTER);
        return groupEdit;
    }

    private HorizontalLayout getStatusMenuBar() {
        Icon caretDownIcon = new Icon(VaadinIcon.CARET_DOWN);
        caretDownIcon.setSize("12px");
        textField.setReadOnly(true);
        textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        textField.setWidth("30px");
        textField.setValue("0");

        MenuBar statusMenuBar = new MenuBar();
        statusMenuBar.addThemeVariants(MenuBarVariant.LUMO_SMALL);
        HorizontalLayout horizontalLayout = new HorizontalLayout(new Text("Статус"), caretDownIcon);
        horizontalLayout.setSpacing(false);
        horizontalLayout.setAlignItems(Alignment.CENTER);

        MenuItem statusItem = statusMenuBar.addItem(horizontalLayout);
        for (StatusDto st : statusService.getAllByNameOfClass(getClass().getSimpleName())) {
            Icon icon = new Icon(VaadinIcon.STOP);
            icon.getStyle().set("color", st.getColorCode());
            icon.setSize("20px");
            statusItem.getSubMenu().addItem(new HorizontalLayout(icon, new Label(st.getTitleOfStatus())), e -> {
            }).setEnabled(false);
        }
        statusItem.getSubMenu().addItem("Настроить...", e -> {
            gridForStatus = new GridForStatus(statusService, getClass().getSimpleName());
            gridForStatus.setOnCloseHandler(() -> {
                initPage();
            });
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
        textField.setReadOnly(true);
        textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        textField.setWidth("30px");
        textField.setValue("0");

        MenuBar createMenuBar = new MenuBar();
        createMenuBar.addThemeVariants(MenuBarVariant.LUMO_SMALL);
        HorizontalLayout horizontalLayout = new HorizontalLayout(new Text("Создать"), caretDownIcon);
        horizontalLayout.setSpacing(false);
        horizontalLayout.setAlignItems(Alignment.CENTER);

        MenuItem createItem = createMenuBar.addItem(horizontalLayout);
        createItem.getSubMenu().addItem("Исходящие платежи", e -> {

        });
        createItem.getSubMenu().addItem("Расходные ордеры", e -> {

        });

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
        HorizontalLayout printItem = new HorizontalLayout(printIcon, new Text("  Печать"), caretDownIcon);
        printItem.setSpacing(false);
        printItem.setAlignItems(Alignment.CENTER);
        MenuItem print = printMenuBar.addItem(printItem);

        print.getSubMenu().addItem("Список счетов", e -> {

        });
        print.getSubMenu().addItem("Счет поставщика", e -> {

        });
        print.getSubMenu().addItem("Комплект...", e -> {

        });
        print.getSubMenu().addItem("Настроить...", e -> {

        });

        HorizontalLayout groupPrint = new HorizontalLayout();
        groupPrint.add(printMenuBar);
        groupPrint.setSpacing(false);
        groupPrint.setAlignItems(Alignment.CENTER);
        return groupPrint;
    }
}