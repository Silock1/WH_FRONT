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
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.AppView;
import com.warehouse_accounting.components.sales.filter.SalesOrderComissionerFilter;
import com.warehouse_accounting.components.sales.grids.SalesGridLayout;
import com.warehouse_accounting.services.interfaces.ComissionerReportsService;
import com.warehouse_accounting.services.interfaces.CompanyService;
import com.warehouse_accounting.services.interfaces.ContractService;
import com.warehouse_accounting.services.interfaces.ContractorService;
import com.warehouse_accounting.services.interfaces.DepartmentService;
import com.warehouse_accounting.services.interfaces.EmployeeService;
import com.warehouse_accounting.services.interfaces.ProductService;
import com.warehouse_accounting.services.interfaces.ProjectService;
import com.warehouse_accounting.services.interfaces.WarehouseService;
import org.springframework.stereotype.Component;

@UIScope
@Component
@Route(value = "comissionerReports", layout = AppView.class)
public class ComissionerReports extends VerticalLayout {

    private SalesGridLayout salesGridLayout;
    private SalesOrderComissionerFilter salesOrderComissionerFilter;
    private final TextField textFieldGridSelected = new TextField();
    //private final Div parentLayer;
    private CompanyService companyService;
    private ContractorService contractorService;
    private ContractService contractService;
    private ProjectService projectService;
    private WarehouseService warehouseService;
    private DepartmentService departmentService;
    private EmployeeService employeeService;
    private Div mainDiv;

    private /*final*/ ComissionerReportsService comissionerReportsService;

    private  ProductService productService;



    public ComissionerReports(/*Div parentLayer,*/CompanyService companyService, ContractorService contractorService,
                                                   ContractService contractService, ProjectService projectService, WarehouseService warehouseService,
                                                   DepartmentService departmentService, EmployeeService employeeService) {
        this.companyService = companyService;
        this.contractorService = contractorService;
        this.contractService = contractService;
        this.projectService = projectService;
        this.warehouseService = warehouseService;
        this.employeeService = employeeService;
        this.departmentService = departmentService;
        //this.parentLayer = parentLayer;
        this.salesOrderComissionerFilter = new SalesOrderComissionerFilter(companyService, contractorService, contractService,
                projectService, warehouseService, employeeService, departmentService);
        salesGridLayout = new SalesGridLayout(textFieldGridSelected);
        Div pageContent = new Div();
        pageContent.add(salesGridLayout);
        pageContent.setSizeFull();
        add(getGroupButtons(), /*salesOrderComissionerFilter,*/ pageContent);
    }



    private HorizontalLayout getGroupButtons() {
        HorizontalLayout groupControl = new HorizontalLayout();

        //ToDo Иконка вопроса. Сделать функционал.
        Button helpButton = new Button(new Icon(VaadinIcon.QUESTION_CIRCLE));
        helpButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);

        //ToDo Текстовая лейба Отчет комиссионера. Сделать функционал.
        Label textProducts = new Label();
        textProducts.setText("Отчет комиссионера");

        //ToDo Иконка Обновления. Сделать функционал.
        Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH));
        refreshButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);

        //ToDo кнопка Фильтр. Доработать функционал.
        Button addFilterButton = new Button("Фильтр");
        addFilterButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        addFilterButton.addClickListener(e->
                salesOrderComissionerFilter.setVisible(!salesOrderComissionerFilter.isVisible())
        );

        //ToDo поле Наподобии поиска
        TextField searchField = new TextField();
        searchField.setPlaceholder("Номер или комментарий");
        searchField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        searchField.addInputListener(inputEvent -> {

        });

        //ToDo кнопки-меню-бары (Добавления отчета коммисионера, изменить, статус, печать)
        HorizontalLayout addComissionerMenuBar = getComisseonerReport();
        HorizontalLayout editMenuBar = getEditMenuBar();
        HorizontalLayout statusMenuBar = getStatusMenuBar();
        HorizontalLayout printMenuBar = getPrintMenuBar();

        //ToDo кнопка-иконка шестеренка-настройка
        Button settingGearButton = createCog();


        groupControl.add(helpButton, textProducts, refreshButton, addComissionerMenuBar, /*addReportComissionerButton,*/
                addFilterButton, searchField, editMenuBar, statusMenuBar, printMenuBar, settingGearButton);
        setSizeFull();
        return groupControl;
    }


    private HorizontalLayout getComisseonerReport() {

        Icon plusIcon = new Icon(VaadinIcon.PLUS);
        plusIcon.setSize("12px");
        textFieldGridSelected.setReadOnly(true);
        textFieldGridSelected.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        textFieldGridSelected.setWidth("30px");
        textFieldGridSelected.setValue("0");

        MenuBar comissionerReportMenuBar = new MenuBar();
        comissionerReportMenuBar.addThemeVariants(MenuBarVariant.LUMO_SMALL);
        HorizontalLayout comissionerReport = new HorizontalLayout(plusIcon, new Text("Отчет комиссионеров"));

        comissionerReport.setSpacing(false);
        comissionerReport.setAlignItems(Alignment.CENTER);

        MenuItem comissionerReportMenu = comissionerReportMenuBar.addItem(comissionerReport);

        //SubMenu userSubMenu = profile.getSubMenu();



        //ToDO Можно сделать Переход по ссылкам, но именно так выполнено в самомприложении. соответствует концепции для этого случая
        comissionerReportMenu.getSubMenu().addItem("Выданный отчет комиссионера редактирование", menuItemClickEvent -> comissionerReportMenu.getUI().ifPresent(ui -> ui.navigate("profile/settings")));
        comissionerReportMenu.getSubMenu().addItem("Полученный отчет комиссионера редактирование", menuItemClickEvent -> comissionerReportMenu.getUI().ifPresent(ui -> ui.navigate("profile/settings")));


        //ToDO Можно сделать такой вызов форм для создания отчетов комиссионеров, но это не соответсвтует концепции реального приложения
//        comissionerReportMenu.getSubMenu().addItem("Выданный отчет комиссионера редактирование", event -> {
//            ComissionerReportSettingForm comissionerReportSettingForm = new ComissionerReportSettingForm(mainDiv, this, comissionerReportsService);
//            mainDiv.removeAll();
//            mainDiv.add(comissionerReportSettingForm);
//
//        });
//
//        comissionerReportMenu.getSubMenu().addItem("Полученный отчет комиссионера", event -> {
//            ComissionerReportGettingForm comissionerReportGettingForm = new ComissionerReportGettingForm(mainDiv, this, comissionerReportsService);
//            mainDiv.removeAll();
//            mainDiv.add(comissionerReportGettingForm);
//        });



        HorizontalLayout groupComissionerReport = new HorizontalLayout();
        groupComissionerReport.add(textFieldGridSelected, comissionerReportMenuBar);
        groupComissionerReport.setSpacing(false);
        groupComissionerReport.setAlignItems(Alignment.CENTER);

        return groupComissionerReport;
    }


    //ToDo кнопка-меню Изменить
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


    //ToDo кнопка-меню Статус
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

    //ToDo кнопка-меню-иконка Печать
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

    private Button createCog() {

        Icon settingsIcon = new Icon(VaadinIcon.COG);
        settingsIcon.setSize("22px");
        Button settingsButton = new Button(settingsIcon);

        settingsButton.addClickListener(event -> {

        });

        return settingsButton;
    }

}
