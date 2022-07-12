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
import com.warehouse_accounting.components.sales.filter.CustomerOrdersFilter;
import com.warehouse_accounting.components.sales.forms.order.OrderPanel;
import com.warehouse_accounting.components.sales.grids.SalesGridLayout;
import com.warehouse_accounting.services.interfaces.*;

/*
Эта страница должна быть главной страницей Продаж, а не CustomerOrder
 */
public class CustomerOrders extends VerticalLayout {
    private CompanyService companyService;
    private ContractorService contractorService;
    private ContractService contractService;
    private ProjectService projectService;
    private WarehouseService warehouseService;
    private EmployeeService employeeService;
    private DepartmentService departmentService;
    private final ProductService productService;
    private final InvoiceService invoiceService;
    private final Div parentLayer;
    private final CustomerOrdersFilter customerOrdersFilter;
    private final SalesGridLayout salesGridLayout;
    private final TextField textFieldGridSelected = new TextField();

    public CustomerOrders(Div parentLayer, CompanyService companyService, ContractorService contractorService,
                          ContractService contractService, ProjectService projectService, WarehouseService warehouseService,
                          EmployeeService employeeService, DepartmentService departmentService, ProductService productService,
                          InvoiceService invoiceService) {
        this.parentLayer = parentLayer;
        this.companyService = companyService;
        this.contractorService = contractorService;
        this.contractService = contractService;
        this.projectService = projectService;
        this.warehouseService = warehouseService;
        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.productService = productService;
        this.invoiceService = invoiceService;

        customerOrdersFilter = new CustomerOrdersFilter(companyService, contractorService, contractService,
                projectService, warehouseService, employeeService, departmentService);
        salesGridLayout = new SalesGridLayout(textFieldGridSelected);
        initPage();
    }

    private void initPage() {
        removeAll();
        salesGridLayout.setSizeFull();
        add(getGroupButtons(), customerOrdersFilter, salesGridLayout);
        customerOrdersFilter.setVisible(false);
    }

    private HorizontalLayout getGroupButtons() {
        HorizontalLayout groupControl = new HorizontalLayout();

        Button helpButton = new Button(new Icon(VaadinIcon.QUESTION_CIRCLE));
        helpButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);

        Label textProducts = new Label();
        textProducts.setText("Заказы покупателей");

        Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH));
        refreshButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);

        Button addOrderButton = new Button("Заказ", new Icon(VaadinIcon.PLUS));
        addOrderButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        addOrderButton.addClickListener(event -> {
            removeAll();
            OrderPanel orderPanel = new OrderPanel(companyService, contractorService, productService, warehouseService,
                    contractService, projectService, invoiceService);
            orderPanel.setOnCloseHandler(() -> initPage());
            add(orderPanel);
        });

        Button addFilterButton = new Button("Фильтр");
        addFilterButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        addFilterButton.addClickListener(e ->
                customerOrdersFilter.setVisible(!customerOrdersFilter.isVisible())
        );

        TextField searchField = new TextField();
        searchField.setPlaceholder("Номер или комментарий");
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
        HorizontalLayout editItem = new HorizontalLayout(new Text("Изменить"), caretDownIcon);
        editItem.setSpacing(false);
        editItem.setAlignItems(Alignment.CENTER);

        MenuItem editMenu = editMenuBar.addItem(editItem);
        editMenu.getSubMenu().addItem("Удалить", menuItemClickEvent -> {
            int selected = salesGridLayout.getProductGrid().asMultiSelect().getSelectedItems().size();
            Notification notification = new Notification(String.format("Выделено для удаления %d", selected),
                    3000, Notification.Position.MIDDLE);
            notification.open();
        });

        editMenu.getSubMenu().addItem("Копировать", menuItemClickEvent -> {

        });

        editMenu.getSubMenu().addItem("Массовое редактирование", menuItemClickEvent -> {

        });
        editMenu.getSubMenu().addItem("Провести", menuItemClickEvent -> {

        });
        editMenu.getSubMenu().addItem("Снять проведение", menuItemClickEvent -> {

        });
        editMenu.getSubMenu().addItem("Объединить", menuItemClickEvent -> {

        });
        editMenu.getSubMenu().addItem("Зарезервировать", menuItemClickEvent -> {

        });
        editMenu.getSubMenu().addItem("Очистить резерв", menuItemClickEvent -> {

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
        HorizontalLayout horizontalLayout = new HorizontalLayout(new Text("Статус"), caretDownIcon);
        horizontalLayout.setSpacing(false);
        horizontalLayout.setAlignItems(Alignment.CENTER);

        MenuItem statusItem = statusMenuBar.addItem(horizontalLayout);
        statusItem.getSubMenu().addItem("Новый", e -> {

        });
        statusItem.getSubMenu().addItem("Подтвeржден", e -> {

        });
        statusItem.getSubMenu().addItem("Собран", e -> {

        });
        statusItem.getSubMenu().addItem("Отгружен", e -> {

        });
        statusItem.getSubMenu().addItem("Доставлен", e -> {

        });
        statusItem.getSubMenu().addItem("Возврат", e -> {

        });
        statusItem.getSubMenu().addItem("Отменен", e -> {

        });
        statusItem.getSubMenu().addItem("Настроить...", e -> {

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
        HorizontalLayout horizontalLayout = new HorizontalLayout(new Text("Создать"), caretDownIcon);
        horizontalLayout.setSpacing(false);
        horizontalLayout.setAlignItems(Alignment.CENTER);

        MenuItem createItem = createMenuBar.addItem(horizontalLayout);
        createItem.getSubMenu().addItem("Заказ поставщикам", e -> {

        });
        createItem.getSubMenu().addItem("Заказ поставщикам (с учетом «доступно»)", e -> {

        });
        createItem.getSubMenu().addItem("Отгрузки", e -> {

        });
        createItem.getSubMenu().addItem("Приходные ордеры", e -> {

        });
        createItem.getSubMenu().addItem("Входящие платежи", e -> {

        });
        createItem.getSubMenu().addItem("Сбор заказа", e -> {

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

        print.getSubMenu().addItem("Список заказов", e -> {

        });
        print.getSubMenu().addItem("Заказ", e -> {

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
