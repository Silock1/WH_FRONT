package com.warehouse_accounting.components.purchases;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.dialog.Dialog;
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
import com.warehouse_accounting.services.impl.SupplierInvoiceServiceImpl;
import com.warehouse_accounting.services.interfaces.CompanyService;
import com.warehouse_accounting.services.interfaces.ContractService;
import com.warehouse_accounting.services.interfaces.ContractorService;
import com.warehouse_accounting.services.interfaces.DepartmentService;
import com.warehouse_accounting.services.interfaces.EmployeeService;
import com.warehouse_accounting.services.interfaces.ProductService;
import com.warehouse_accounting.services.interfaces.ProjectService;
import com.warehouse_accounting.services.interfaces.SupplierInvoiceService;
import com.warehouse_accounting.services.interfaces.WarehouseService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
Счета поставщиков
 */
public class AccountsPayable extends VerticalLayout {

    private final TextField textField = new TextField();
    private final Div parentLayer;
    private AccountsPayableFilter accountsPayableFilter;
    private final WarehouseService warehouseService;
    private final ContractService contractService;
    private final ContractorService contractorService;
    private final ProjectService projectService;
    private final EmployeeService employeeService;
    private final DepartmentService departmentService;
    private final ProductService productService;
    private final CompanyService companyService;

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://localhost:4446")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private SupplierInvoiceService supplierInvoiceService = new SupplierInvoiceServiceImpl("/api/supplier_invoices",retrofit);

    public AccountsPayable(Div parentLayer, WarehouseService warehouseService, ContractService contractService,
                           ContractorService contractorService, ProjectService projectService, EmployeeService employeeService,
                           DepartmentService departmentService, ProductService productService, CompanyService companyService) {
        this.parentLayer = parentLayer;
        this.warehouseService = warehouseService;
        this.contractService = contractService;
        this.contractorService = contractorService;
        this.projectService = projectService;
        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.productService = productService;
        this.companyService = companyService;
        this.accountsPayableFilter = new AccountsPayableFilter(warehouseService, contractService,
                contractorService, projectService, employeeService, departmentService, productService, companyService);
        SupplierInvoiceGridLayout.parentLayer = parentLayer;
        SupplierInvoiceGridLayout.returnLayer = this;
        Div pageContent = new Div();

        pageContent.setSizeFull();
        add(getGroupButtons(), accountsPayableFilter, pageContent);
    }


    private HorizontalLayout getGroupButtons() {
        HorizontalLayout groupControl = new HorizontalLayout();

        Button helpButton = new Button(new Icon(VaadinIcon.QUESTION_CIRCLE));
        helpButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
        helpButton.addClickListener(e->{
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

        //--------- Реализация кнопки Счет - временно здесь ----------------//
        addOrderButton.addClickListener(event -> {
            removeAll();
            add(getGroupButtons());
            CreateInvoiceForm invoiceForm = new CreateInvoiceForm(parentLayer, this);
            parentLayer.removeAll();
            parentLayer.add(invoiceForm);
        });

        Button addFilterButton = new Button("Фильтр");
        addFilterButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        addFilterButton.addClickListener(e->
                accountsPayableFilter.setVisible(!accountsPayableFilter.isVisible())
        );

        TextField searchField = new TextField();
        searchField.setPlaceholder("Номер или комментарий");
        searchField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        searchField.addInputListener(inputEvent -> {

        });

        Button settingButton = new Button(new Icon(VaadinIcon.COG));
        refreshButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);

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

        MenuItem editMenu = editMenuBar.addItem(editItem);

        editMenu.getSubMenu().addItem("Удалить выбранные", menuItemClickEvent -> {
            deleteSelected();
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

        HorizontalLayout groupEdit = new HorizontalLayout();
        groupEdit.add(textField, editMenuBar);
        groupEdit.setSpacing(false);
        groupEdit.setAlignItems(Alignment.CENTER);
        return groupEdit;
    }

    private void deleteSelected(){ // Метод удаляет выбранные счета

        Button delete = new Button("Удалить");
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        Button cancel = new Button("Отмена");
        HorizontalLayout buttonLayout = new HorizontalLayout(cancel,delete);

        Dialog dialog = new Dialog();
        dialog.add("Подтвердите удаление");
        dialog.add(new VerticalLayout());
        dialog.add(buttonLayout);

        dialog.open();

        delete.addClickListener(event -> {
            Set<Long> setId = new HashSet<>();
            setId.addAll(SupplierInvoiceGridLayout.gridEntityId);
            List<Long> listId = new ArrayList<>();
            listId.addAll(setId);
            SupplierInvoiceGridLayout.gridEntityId.removeAll(SupplierInvoiceGridLayout.gridEntityId);

            for(int i=0; i<listId.size(); i++){
                supplierInvoiceService.deleteById(listId.get(i));
            }

            parentLayer.removeAll();
            parentLayer.add(this, SupplierInvoiceGridLayout.initSupplierInvoiceGrid());
            dialog.close();
        });

        cancel.addClickListener(event -> dialog.close());
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
