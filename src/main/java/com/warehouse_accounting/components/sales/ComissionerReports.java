package com.warehouse_accounting.components.sales;

/**
 * РАБОЧИЙ КОД
 */

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
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
import com.warehouse_accounting.components.goods.forms.ComplectForm;
import com.warehouse_accounting.components.goods.forms.GoodsForm;
import com.warehouse_accounting.components.goods.forms.GroupForm;
import com.warehouse_accounting.components.sales.filter.SalesOrderComissionerFilter;
import com.warehouse_accounting.components.sales.forms.ComissionerReportGettingForm;
import com.warehouse_accounting.components.sales.forms.ComissionerReportSettingForm;
import com.warehouse_accounting.components.sales.grids.SalesGridLayout;
import com.warehouse_accounting.models.dto.ProductGroupDto;
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

import java.util.Optional;

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

        //ToDo Тестовая кнопка
        Button testButton = new Button("Тест кнопка", new Icon(VaadinIcon.PLUS));
        testButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        testButton.addClickListener(event -> {
                ComissionerReportSettingForm comissionerReportSettingForm = new ComissionerReportSettingForm(mainDiv, this, comissionerReportsService);
                mainDiv.removeAll();
                mainDiv.add(comissionerReportSettingForm);
        });


        //ToDo Иконка вопроса
        Button helpButton = new Button(new Icon(VaadinIcon.QUESTION_CIRCLE));
        helpButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);

        //ToDo Текстовая лейба Отчет комиссионера
        Label textProducts = new Label();
        textProducts.setText("Отчет комиссионера");

        //ToDo Иконка Обновления
        Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH));
        refreshButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);

        //ToDo кнопка Фильтр
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


        groupControl.add(testButton, helpButton, textProducts, refreshButton, addComissionerMenuBar, /*addReportComissionerButton,*/
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
/**
 * РАБОЧИЙ КОД
 */


/**
 * ТестоВЫЙ КОД
 */
//
//
//import com.vaadin.flow.component.Text;
//import com.vaadin.flow.component.button.Button;
//import com.vaadin.flow.component.button.ButtonVariant;
//import com.vaadin.flow.component.contextmenu.MenuItem;
//import com.vaadin.flow.component.grid.Grid;
//import com.vaadin.flow.component.html.Div;
//import com.vaadin.flow.component.html.Label;
//import com.vaadin.flow.component.icon.Icon;
//import com.vaadin.flow.component.icon.VaadinIcon;
//import com.vaadin.flow.component.menubar.MenuBar;
//import com.vaadin.flow.component.menubar.MenuBarVariant;
//import com.vaadin.flow.component.notification.Notification;
//import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
//import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.component.textfield.TextField;
//import com.vaadin.flow.component.textfield.TextFieldVariant;
//import com.vaadin.flow.component.treegrid.TreeGrid;
//import com.vaadin.flow.router.Route;
//import com.vaadin.flow.spring.annotation.UIScope;
//import com.warehouse_accounting.components.AppView;
//import com.warehouse_accounting.components.goods.forms.ComplectForm;
//import com.warehouse_accounting.components.goods.forms.GoodsForm;
//import com.warehouse_accounting.components.goods.forms.GroupForm;
//import com.warehouse_accounting.components.goods.forms.ServiceForm;
//import com.warehouse_accounting.components.goods.grids.GoodsGridLayout;
//import com.warehouse_accounting.components.sales.forms.ComissionerReportSettingForm;
//import com.warehouse_accounting.models.dto.ProductDto;
//import com.warehouse_accounting.models.dto.ProductGroupDto;
//import com.warehouse_accounting.services.interfaces.ComissionerReportsService;
//import com.warehouse_accounting.services.interfaces.ProductGroupService;
//import com.warehouse_accounting.services.interfaces.ProductService;
//import org.springframework.stereotype.Component;
//
//import java.util.Optional;
//
//@UIScope
//@Component
//@Route(value = "goodsAndServiceView", layout = AppView.class)
//public class ComissionerReports extends VerticalLayout {
//    private /*final*/ ProductGroupService productGroupService;
//    private /*final*/ ProductService productService;
//    private final TreeGrid<ProductGroupDto> treeGrid = new TreeGrid<>();
//    private final TextField textFieldGridSelected = new TextField();
//    private final Grid<ProductDto> productDtoGrid = new Grid<>(ProductDto.class, false);
//    private Div mainDiv;
//    private GoodsGridLayout goodsGridLayout;
//    private Long rootGroupId = 1L; //TODO а нужно ли это?
//
//    private /*final*/ ComissionerReportsService comissionerReportsService;
//
//
//    public ComissionerReports(ProductGroupService productGroupService, ProductService productService) {
//        this.productGroupService = productGroupService;
//        this.productService = productService;
//        //this.complectService = complectService;
//        goodsGridLayout = new GoodsGridLayout(productGroupService, productService, this);
//        Div pageContent = new Div();
//        pageContent.setSizeFull();
//        pageContent.add(goodsGridLayout);
//        add(initGroupButtons(), pageContent);
//    }
//
//    public ComissionerReports() {
//
//    }
//
//    private HorizontalLayout initGroupButtons() {
//        HorizontalLayout groupControl = new HorizontalLayout();
//
//        Button helpButton = new Button(new Icon(VaadinIcon.QUESTION_CIRCLE));
//        helpButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
//
//        Label textProducts = new Label();
//        textProducts.setText("Товары и услуги");
//
//
//        //ToDo Тестовая кнопка
//        Button testButton = new Button("Тест кнопка", new Icon(VaadinIcon.PLUS));
//        testButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
//        testButton.addClickListener(event -> {
//            ComissionerReportSettingForm comissionerReportSettingForm = new ComissionerReportSettingForm(mainDiv, this, comissionerReportsService);
//            mainDiv.removeAll();
//            mainDiv.add(comissionerReportSettingForm);
//        });
//
//        Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH));
//        refreshButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
//        refreshButton.addClickListener(buttonClickEvent -> {
//            goodsGridLayout.initGrid(rootGroupId);
//            goodsGridLayout.initThreeGrid(rootGroupId);
//        });
//
//        Button addProductButton = new Button("Товар", new Icon(VaadinIcon.PLUS));
//        addProductButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
//        addProductButton.addClickListener(event -> {
//            GoodsForm goodsForm = new GoodsForm(mainDiv, this, productService);
//            mainDiv.removeAll();
//            mainDiv.add(goodsForm);
//        });
//
//        //TODO не сделана. не работает
//        Button addServiceButton = new Button("Услуга", new Icon(VaadinIcon.PLUS));
//        addServiceButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
//        addServiceButton.addClickListener(event -> {
//            ServiceForm serviceForm = new ServiceForm(mainDiv, this);
//            mainDiv.removeAll();
//            mainDiv.add(serviceForm);
//        });
//
//        Button addComplectButton = new Button("Комплект", new Icon(VaadinIcon.PLUS));
//        addComplectButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
//        addComplectButton.addClickListener(event -> {
//            ComplectForm complectForm = new ComplectForm(mainDiv, this, productService);
//            mainDiv.removeAll();
//            mainDiv.add(complectForm);
//        });
//
//        Button addGroupButton = new Button("Группа", new Icon(VaadinIcon.PLUS));
//        addGroupButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
//        addGroupButton.addClickListener(buttonClickEvent -> {
//            Optional<ProductGroupDto> selectedGroup = treeGrid.getSelectedItems().stream().findFirst();
//            ProductGroupDto selectGroup = selectedGroup.orElseGet(() -> productGroupService.getById(rootGroupId));
//            GroupForm groupForm = new GroupForm(mainDiv, this, productGroupService, selectGroup, false);
//            mainDiv.removeAll();
//            mainDiv.add(groupForm);
//        });
//
//        Button addFilterButton = new Button("Фильтр", new Icon(VaadinIcon.PLUS));
//        addFilterButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
//
//        TextField searchField = new TextField();
//        searchField.setPlaceholder("наименование, код или артикул");
//        searchField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
//        searchField.addInputListener(inputEvent -> {
//            //grid filter
//        });
//
//        HorizontalLayout editMenuBar = initEditMenuBar();
//
//        HorizontalLayout printMenuBar = getPrintMenuBar();
//
//        HorizontalLayout importMenuBar = getImportMenuBar();
//
//        HorizontalLayout exportMenuBar = getExportMenuBar();
//
//        groupControl.add(testButton, helpButton, textProducts, refreshButton, addProductButton, addServiceButton,
//                addComplectButton, addGroupButton, addFilterButton, searchField, editMenuBar,
//                printMenuBar, importMenuBar, exportMenuBar);
//        setSizeFull();
//        return groupControl;
//    }
//
//    private HorizontalLayout initEditMenuBar() {
//        Icon agileDownIcon = new Icon(VaadinIcon.ANGLE_DOWN);
//        agileDownIcon.setSize("12px");
//        textFieldGridSelected.setReadOnly(true);
//        textFieldGridSelected.addThemeVariants(TextFieldVariant.LUMO_SMALL);
//        textFieldGridSelected.setWidth("30px");
//        textFieldGridSelected.setValue("0");
//
//        MenuBar editMenuBar = new MenuBar();
//        editMenuBar.addThemeVariants(MenuBarVariant.LUMO_SMALL);
//        HorizontalLayout editItem = new HorizontalLayout(new Text("Изменить"), agileDownIcon);
//        editItem.setSpacing(false);
//        editItem.setAlignItems(Alignment.CENTER);
//
//        MenuItem editMenu = editMenuBar.addItem(editItem);
//        editMenu.getSubMenu().addItem("Удалить", menuItemClickEvent -> {
//            int selected = goodsGridLayout.getProductGrid().asMultiSelect().getSelectedItems().size();
//            Notification notification = new Notification(String.format("Выделено для удаления %d", selected),
//                    3000,
//                    Notification.Position.MIDDLE);
//            notification.open(); //TODO реализовать форму подтверждения удаления товара
//            //TODO написать метод удаляющий группу продуктов одним запросом
//            goodsGridLayout .getProductGrid()
//                    .asMultiSelect()
//                    .getSelectedItems()
//                    .stream().forEach(productDto -> productService.deleteById(productDto.getId()));
//            goodsGridLayout.initGrid(rootGroupId); //FixMe TEST
//            goodsGridLayout.initThreeGrid(rootGroupId); //FixMe TEST
//
//        });
//        editMenu.getSubMenu().addItem("Массовое редактирование", menuItemClickEvent -> {
//
//        });
//        editMenu.getSubMenu().addItem("Переместить", menuItemClickEvent -> {
//
//        });
//        editMenu.getSubMenu().addItem("Поместить в архив", menuItemClickEvent -> {
//
//        });
//        editMenu.getSubMenu().addItem("Извлечь из архива", menuItemClickEvent -> {
//
//        });
//        editMenu.getSubMenu().addItem("Цены...", menuItemClickEvent -> {
//
//        });
//
//        HorizontalLayout groupEdit = new HorizontalLayout();
//        groupEdit.add(textFieldGridSelected, editMenuBar);
//        groupEdit.setSpacing(false);
//        groupEdit.setAlignItems(Alignment.CENTER);
//        return groupEdit;
//    }
//
//    private HorizontalLayout getPrintMenuBar() {
//        MenuBar printMenuBar = new MenuBar();
//        printMenuBar.addThemeVariants(MenuBarVariant.LUMO_SMALL);
//        Icon printIcon = new Icon(VaadinIcon.PRINT);
//        printIcon.setSize("12px");
//        Icon agileDownIcon = new Icon(VaadinIcon.ANGLE_DOWN);
//        agileDownIcon.setSize("12px");
//        HorizontalLayout printItem = new HorizontalLayout(printIcon, agileDownIcon);
//        printItem.setSpacing(false);
//        printItem.setAlignItems(Alignment.CENTER);
//        MenuItem print = printMenuBar.addItem(printItem);
//
//        print.getSubMenu().addItem("Ценник (70x49,5мм)", e -> {
//
//        });
//        print.getSubMenu().addItem("Термоэтикетка (58х40мм)", e -> {
//
//        });
//        print.getSubMenu().addItem("Настроить...", e -> {
//
//        });
//
//        HorizontalLayout groupPrint = new HorizontalLayout();
//        groupPrint.add(printMenuBar);
//        groupPrint.setSpacing(false);
//        groupPrint.setAlignItems(Alignment.CENTER);
//        return groupPrint;
//    }
//
//    private HorizontalLayout getImportMenuBar() {
//        Icon agileDownIcon = new Icon(VaadinIcon.ANGLE_DOWN);
//        agileDownIcon.setSize("12px");
//        MenuBar importMenuBar = new MenuBar();
//        importMenuBar.addThemeVariants(MenuBarVariant.LUMO_SMALL);
//        HorizontalLayout horizontalLayout = new HorizontalLayout(new Text("Импорт"), agileDownIcon);
//        horizontalLayout.setSpacing(false);
//        horizontalLayout.setAlignItems(Alignment.CENTER);
//        MenuItem importItem = importMenuBar.addItem(horizontalLayout);
//
//        importItem.getSubMenu().addItem("Импорт из Excel", e -> {
//
//        });
//
//        importItem.getSubMenu().addItem("Импорт из YML", e -> {
//
//        });
//
//        HorizontalLayout groupImport = new HorizontalLayout();
//        groupImport.add(importMenuBar);
//        groupImport.setSpacing(false);
//        groupImport.setAlignItems(Alignment.CENTER);
//        return groupImport;
//    }
//
//    private HorizontalLayout getExportMenuBar() {
//        Icon agileDownIcon = new Icon(VaadinIcon.ANGLE_DOWN);
//        agileDownIcon.setSize("12px");
//        MenuBar exportMenuBar = new MenuBar();
//        HorizontalLayout horizontalLayout = new HorizontalLayout(new Text("Экспорт"), agileDownIcon);
//        horizontalLayout.setSpacing(false);
//        horizontalLayout.setAlignItems(Alignment.CENTER);
//        exportMenuBar.addThemeVariants(MenuBarVariant.LUMO_SMALL);
//        MenuItem exportItem = exportMenuBar.addItem(horizontalLayout);
//
//        exportItem.getSubMenu().addItem("Экспорт в Excel", e -> {
//
//        });
//        exportItem.getSubMenu().addItem("Экспорт в YML", e -> {
//
//        });
//
//        HorizontalLayout groupImport = new HorizontalLayout();
//        groupImport.add(exportMenuBar);
//        groupImport.setSpacing(false);
//        groupImport.setAlignItems(Alignment.CENTER);
//        return groupImport;
//    }
//
//    public Long getRootGroupId() {
//        return rootGroupId;
//    }
//
//    public GoodsGridLayout getGoodsGridLayout() {
//        return goodsGridLayout;
//    }
//
//    public void setMainDiv(Div div) {
//        this.mainDiv = div;
//    }
//
//    public Div getMainDiv() {
//        return mainDiv;
//    }
//
//    public TextField getTextFieldGridSelected() {
//        return textFieldGridSelected;
//    }
//
//    public Grid<ProductDto> getProductDtoGrid() {
//        return productDtoGrid;
//    }
//
//    public TreeGrid<ProductGroupDto> getTreeGrid() {
//        return treeGrid;
//    }
//}