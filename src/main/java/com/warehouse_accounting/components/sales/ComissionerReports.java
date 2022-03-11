package com.warehouse_accounting.components.sales;
//
//import com.vaadin.flow.component.Text;
//import com.vaadin.flow.component.button.Button;
//import com.vaadin.flow.component.button.ButtonVariant;
//import com.vaadin.flow.component.contextmenu.MenuItem;
//import com.vaadin.flow.component.contextmenu.SubMenu;
//import com.vaadin.flow.component.dialog.Dialog;
//import com.vaadin.flow.component.html.Div;
//import com.vaadin.flow.component.html.Label;
//import com.vaadin.flow.component.html.Span;
//import com.vaadin.flow.component.icon.Icon;
//import com.vaadin.flow.component.icon.VaadinIcon;
//import com.vaadin.flow.component.menubar.MenuBar;
//import com.vaadin.flow.component.menubar.MenuBarVariant;
//import com.vaadin.flow.component.notification.Notification;
//import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
//import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.component.textfield.NumberField;
//import com.vaadin.flow.component.textfield.TextField;
//import com.vaadin.flow.component.textfield.TextFieldVariant;
//import com.vaadin.flow.router.Route;
//import com.vaadin.flow.spring.annotation.UIScope;
//import com.warehouse_accounting.components.AppView;
//import com.warehouse_accounting.components.sales.filter.SalesShipmentsFilter;
//import com.warehouse_accounting.components.sales.forms.InvoiceForm;
//import com.warehouse_accounting.components.sales.grids.ComissionerReportsGridLayout;
//import com.warehouse_accounting.components.sales.grids.SalesGridLayout;
//import com.warehouse_accounting.services.interfaces.ComissionerReportsService;
//import com.warehouse_accounting.services.interfaces.CompanyService;
//import com.warehouse_accounting.services.interfaces.ContractService;
//import com.warehouse_accounting.services.interfaces.ContractorService;
//import com.warehouse_accounting.services.interfaces.DepartmentService;
//import com.warehouse_accounting.services.interfaces.EmployeeService;
//import com.warehouse_accounting.services.interfaces.ProjectService;
//import com.warehouse_accounting.services.interfaces.WarehouseService;
//import org.springframework.stereotype.Component;
//
//@Component
//@UIScope
//@Route(value = "comissioner_reports", layout = AppView.class)
//public class ComissionerReports extends VerticalLayout {
//
//    private ComissionerReportsService comissionerReportsService;
//    private /*final*/ ComissionerReportsGridLayout reportsGridLayout;
//    private Div parentLayer;
//
//
//
//
////    public ComissionerReports(/*ComissionerReportsService comissionerReportsService/*Div parentLayer*/) {
//////        this.parentLayer = parentLayer;
////        reportsGridLayout = new ComissionerReportsGridLayout();
////        Div pageContent = new Div();
////        pageContent.add(reportsGridLayout);
////        pageContent.setSizeFull();
////        add(buttonsGroup(), pageContent);
////    }
//
//    private HorizontalLayout buttonsGroup() {
//        HorizontalLayout groupControl = new HorizontalLayout();
//
//// "?" — информация о странице **************************************************************************
//        Icon questionIcon = new Icon(VaadinIcon.QUESTION_CIRCLE_O);
//        questionIcon.setSize("22px");
//        Button helpButton = new Button(questionIcon);
//        helpButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
//        Dialog dialog = new Dialog();
//        dialog.add(new Text("В разделе представлены выданные и полученные отчеты комиссионера. " +
//            "В отчетах указываются проданные товары, сумма продажи, вознаграждение комиссионера. " +
//            "На основе отчетов формируется долг комиссионера перед комитентом." +
//            "Выданные отчеты создает комиссионер. Полученные — комитент."));
//        helpButton.addClickListener(event -> dialog.open());
//        dialog.setHeight("150px");
//        dialog.setWidth("400px");
//
//// Название страницы
//        Span pageName = new Span("Отчеты комиссионера23");
//        pageName.setClassName("pageTitle");
//        setAlignItems(Alignment.AUTO);
//
//// Кнопка обновления
//        Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH),
//            event -> {
//
//            });
//
//// Кнопка "Фильтр"
//        Button filterButton = new Button("Фильтр", event -> {
//
//        });
//
//// Поле ввода
//        TextField searchField = new TextField();
//        searchField.setPlaceholder("Номер или комментарий");
//        searchField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
//        searchField.setWidth("160px");
//        searchField.addInputListener(inputEvent -> {
//
//        });
//
//// Вся группа:
//        groupControl.add(
//            helpButton,
//            pageName,
//            refreshButton,
//            createReportTypeBar(),
//            filterButton,
//            searchField,
//            createNumberField(),
//            createChangeBar(),
//            createCog()
//        );
//        groupControl.setAlignItems(Alignment.BASELINE);
//        setSizeFull();
//        return groupControl;
//    }
//
//    private HorizontalLayout createReportTypeBar() {
//
//        Icon plusIcon = new Icon(VaadinIcon.PLUS_CIRCLE);
//        plusIcon.setSize("12px");
//        Icon caretDownIcon = new Icon(VaadinIcon.CARET_DOWN);
//        caretDownIcon.setSize("12px");
//
//        TextField textField = new TextField();
//        textField.setReadOnly(true);
//        textField.setWidth("30px");
//        textField.setValue("0");
//
//        MenuBar statusMenuBar = new MenuBar();
//        HorizontalLayout horizontalLayout = new HorizontalLayout(plusIcon, new Text("_Отчёт комиссионера"), caretDownIcon);
//        horizontalLayout.setSpacing(false);
//        horizontalLayout.setAlignItems(Alignment.CENTER);
//
//        MenuItem statusItem = statusMenuBar.addItem(horizontalLayout);
//
//        statusItem.getSubMenu().addItem("Полученный отчет комиссионера", e -> {
//
//        });
//
//        statusItem.getSubMenu().addItem("Выданный отчет комиссионера", e -> {
//
//        });
//
//        HorizontalLayout groupStatus = new HorizontalLayout();
//        groupStatus.add(statusMenuBar);
//        groupStatus.setSpacing(false);
//        groupStatus.setAlignItems(Alignment.CENTER);
//        return groupStatus;
//    }
//
//    // Поле вывода числа
//    private TextField createNumberField() {
//        TextField textField = new TextField();
//        textField.setReadOnly(true);
//        textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
//        textField.setWidth("30px");
//        textField.setValue("0");
////        textField.addSelectionListener(event -> textField.setValue(     // Добавить функционал в грид
////            (double) (reportsGridLayout.getSelectedItems().size())
////        ));
//        return textField;
//    }
//
//    private MenuBar createChangeBar() {
//        MenuBar menuBar = new MenuBar();
//
//        MenuItem change = menuBar.addItem("Изменить");
//        change.add(new Icon(VaadinIcon.CARET_DOWN));
//        MenuItem status = menuBar.addItem("Статус");
//        status.add(new Icon(VaadinIcon.CARET_DOWN));
//        MenuItem print = menuBar.addItem(new Icon(VaadinIcon.PRINT));
//        print.add("Печать");
//        print.add(new Icon(VaadinIcon.CARET_DOWN));
//
//        SubMenu changeSubMenu = change.getSubMenu();
//        MenuItem delete = changeSubMenu.addItem("Удалить");
//        delete.addClickListener(event -> {
//
//        });
//        MenuItem copy = changeSubMenu.addItem("Копировать");
//        copy.addClickListener(event -> {
//
//        });
//        MenuItem massEdit = changeSubMenu.addItem("Массовое редактирование");
//        massEdit.addClickListener(event -> {
//
//        });
//        MenuItem operate = changeSubMenu.addItem("Провести");
//        operate.addClickListener(event -> {
//
//        });
//        MenuItem undoOperate = changeSubMenu.addItem("Снять проведение");
//        undoOperate.addClickListener(event -> {
//
//        });
//
//
//        SubMenu statusSubMenu = status.getSubMenu();
//        MenuItem configureStatus = statusSubMenu.addItem("Настроить...");
//        configureStatus.addClickListener(event -> {
//
//        });
//
//        SubMenu printSubMenu = print.getSubMenu();
//        MenuItem allReportsList = printSubMenu.addItem("Список отчетов комиссионера");
//        allReportsList.addClickListener(event -> {
//
//        });
//        MenuItem receivedReportsList = printSubMenu.addItem("Список полученных отчетов");
//        receivedReportsList.addClickListener(event -> {
//
//        });
//        MenuItem receivedReport = printSubMenu.addItem("Полученный отчет комиссионера");
//        receivedReport.addClickListener(event -> {
//
//        });
//        MenuItem givenReportsList = printSubMenu.addItem("Список выданных отчетов");
//        givenReportsList.addClickListener(event -> {
//
//        });
//        MenuItem givenReport = printSubMenu.addItem("Выданный отчет комиссионера");
//        givenReport.addClickListener(event -> {
//
//        });
//        MenuItem configurePrint = printSubMenu.addItem("Настроить...");
//        configurePrint.addClickListener(event -> {
//
//        });
//
//        return menuBar;
//    }
//
//    private Button createCog() {
//
//        Icon settingsIcon = new Icon(VaadinIcon.COG);
//        settingsIcon.setSize("22px");
//        Button settingsButton = new Button(settingsIcon);
//
//        settingsButton.addClickListener(event -> {
//
//        });
//
//        return settingsButton;
//    }
//}

/////////////////////////////

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
import com.warehouse_accounting.components.sales.filter.SalesOrderComissionerFilter;
import com.warehouse_accounting.components.sales.filter.SalesShipmentsFilter;
import com.warehouse_accounting.components.sales.forms.ComissionerReportsAddForm;
import com.warehouse_accounting.components.sales.grids.SalesGridLayout;
        import com.warehouse_accounting.services.interfaces.CompanyService;
        import com.warehouse_accounting.services.interfaces.ContractService;
        import com.warehouse_accounting.services.interfaces.ContractorService;
        import com.warehouse_accounting.services.interfaces.DepartmentService;
        import com.warehouse_accounting.services.interfaces.EmployeeService;
        import com.warehouse_accounting.services.interfaces.ProjectService;
        import com.warehouse_accounting.services.interfaces.WarehouseService;

public class ComissionerReports extends VerticalLayout {

    private SalesGridLayout salesGridLayout;

    private SalesOrderComissionerFilter salesOrderComissionerFilter;
    private final TextField textFieldGridSelected = new TextField();
    private final Div parentLayer;
    private CompanyService companyService;
    private ContractorService contractorService;
    private ContractService contractService;
    private ProjectService projectService;
    private WarehouseService warehouseService;
    private DepartmentService departmentService;
    private EmployeeService employeeService;

    public ComissionerReports(Div parentLayer, CompanyService companyService, ContractorService contractorService,
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
        this.salesOrderComissionerFilter = new SalesOrderComissionerFilter(companyService, contractorService, contractService,
                projectService, warehouseService, employeeService, departmentService);
        salesGridLayout = new SalesGridLayout(textFieldGridSelected);
        Div pageContent = new Div();
        pageContent.add(salesGridLayout);
        pageContent.setSizeFull();
        add(getGroupButtons(), salesOrderComissionerFilter, pageContent);
    }

    private HorizontalLayout getGroupButtons() {
        HorizontalLayout groupControl = new HorizontalLayout();

        Button helpButton = new Button(new Icon(VaadinIcon.QUESTION_CIRCLE));
        helpButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);

        Label textProducts = new Label();
        textProducts.setText("Отчет комиссионера");


        Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH));
        refreshButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);

//ToDo кнопка комиссионеров. Не работает.
        Button addReportComissionerButton = new Button("Отчет комиссионера", new Icon(VaadinIcon.PLUS));
        addReportComissionerButton.addThemeVariants(ButtonVariant.LUMO_SMALL);

        addReportComissionerButton.addClickListener(buttonClickEvent -> {
//            ComissionerReportsAddForm comissionerReportsAddForm = new ComissionerReportsAddForm(parentLayer, this);
//            parentLayer.removeAll();
//            parentLayer.add(comissionerReportsAddForm);

        });


        Button addFilterButton = new Button("Фильтр");
        addFilterButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        addFilterButton.addClickListener(e->
                salesOrderComissionerFilter.setVisible(!salesOrderComissionerFilter.isVisible())
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

        Button gearButton = new Button(new Icon(VaadinIcon.COG));
        gearButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);

        //TODO Настроить кнопку COG - gearButton
//    private Button createCog() {
//
//        Icon settingsIcon = new Icon(VaadinIcon.COG);
//        settingsIcon.setSize("22px");
//        Button settingsButton = new Button(settingsIcon);
//
//        settingsButton.addClickListener(event -> {
//
//        });
//
//        return settingsButton;
//    }


        groupControl.add(helpButton, textProducts, refreshButton, addReportComissionerButton,
                addFilterButton, searchField, editMenuBar, statusMenuBar, createMenuBar, printMenuBar, gearButton);
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

        print.getSubMenu().addItem("Список отгрузок", e -> {

        });
        print.getSubMenu().addItem("УПД с прослеживаемостью", e-> {

        });
        print.getSubMenu().addItem("УПД без прослеживаемости", e->{

        });

        print.getSubMenu().addItem("Акт", e->{

        });

        print.getSubMenu().addItem("Товарный чек", e -> {

        });
        print.getSubMenu().addItem("ТОРГ-12", e -> {

        });

        print.getSubMenu().addItem("Расходная накладная", e -> {

        });

        print.getSubMenu().addItem("ТОРГ-12", e -> {

        });

        print.getSubMenu().addItem("Транспортная накладная", e -> {

        });

        print.getSubMenu().addItem("Коды маркировки: тег 1162", e -> {

        });

        print.getSubMenu().addItem("Сборочный лист", e -> {

        });

        print.getSubMenu().addItem("Комплект", e -> {

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
