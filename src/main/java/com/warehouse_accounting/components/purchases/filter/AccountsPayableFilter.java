package com.warehouse_accounting.components.purchases.filter;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.util.ColumnToggleContextMenu;
import com.warehouse_accounting.models.dto.*;
import com.warehouse_accounting.services.interfaces.*;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.List;

//Счета поставщиков - фильтр

@SpringComponent
@UIScope
@Getter
@Setter
public class AccountsPayableFilter extends VerticalLayout {
    private final WarehouseService warehouseService;
    private final ContractService contractService;
    private final ContractorService contractorService;
    private final ProjectService projectService;
    private final EmployeeService employeeService;
    private final DepartmentService departmentService;
    private final ProductService productService;
    private final CompanyService companyService;

    public AccountsPayableFilter(WarehouseService warehouseService, ContractService contractService,
                                 ContractorService contractorService, ProjectService projectService,
                                 EmployeeService employeeService, DepartmentService departmentService,
                                 ProductService productService, CompanyService companyService) {
        this.warehouseService = warehouseService;
        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.contractorService = contractorService;
        this.contractService = contractService;
        this.projectService = projectService;
        this.productService = productService;
        this.companyService = companyService;

        HorizontalLayout horizontalLayoutOne = getHorizontalLayoutOne();
        HorizontalLayout horizontalLayoutTwo = getHorizontalLayoutTwo();
        HorizontalLayout horizontalLayoutThree = getHorizontalLayoutThree();
        HorizontalLayout horizontalLayoutFour = getHorizontalLayoutFour();
        HorizontalLayout horizontalLayoutFive = getHorizontalLayoutFive();
        HorizontalLayout horizontalLayoutSix = getHorizontalLayoutSix();
        add(horizontalLayoutOne, horizontalLayoutTwo, horizontalLayoutThree, horizontalLayoutFour, horizontalLayoutFive,
                horizontalLayoutSix);
        setSizeFull();
        actionClear();
        setVisible(false);
    }

    private Button find = new Button("Найти");
    private Button clear = new Button("Очистить");
    private Dialog dialog = new Dialog();
    private Button bookmarks = new Button(new Icon(VaadinIcon.BOOKMARK), buttonClickEvent -> dialog.open());
    private Button closeBookmarks = new Button(new Icon(VaadinIcon.CLOSE_SMALL), buttonClickEvent -> dialog.close());
    private Label bookmarksWindowName = new Label();
    private Header topBookmarkHeader = new Header();
    private Header middleBookmarkHeader = new Header();
    private Header bottomBookmarkHeader = new Header();
    private Button saveBookmark = new Button("Сохранить закладку");
    private Button cancelBookmark = new Button("Отменить", buttonClickEvent -> dialog.close());
    private Input bookmarkTitle = new Input();
    private Button settingButton = new Button(new Icon(VaadinIcon.COG));
    private ColumnToggleContextMenu<Button> columnToggleContextMenu = new ColumnToggleContextMenu<>(settingButton);
    private DatePicker periodStart = new DatePicker("Период");
    private DatePicker periodEnd = new DatePicker("до");
    private TextField incomingNumber = new TextField();
    private DatePicker incomingDataStart = new DatePicker("Входящая дата");
    private DatePicker incomingDataEnd = new DatePicker("до");
    private ComboBox<String> payment = new ComboBox<>("Оплата", "Оплачено",
            "Частично оплачено", "Не оплачено", "Просрочено");

    private ComboBox<String> acceptance = new ComboBox<>("Приемка", "Принято", "Частично принято", "Не принято");
    private DatePicker periodStart2 = new DatePicker("План. дата оплаты");
    private DatePicker periodEnd2 = new DatePicker("до");
    private ComboBox<ProductDto> productDtoComboBox = new ComboBox<>("Товар или группа");
    private ComboBox<WarehouseDto> warehouseDtoComboBox = new ComboBox<>("Склад");
    private ComboBox<ProjectDto> projectDtoComboBox = new ComboBox<>("Проект");

    private ComboBox<ContractorDto> contractorDtoComboBox = new ComboBox<>("Контрагент");
    private ComboBox<String> contractorGroupBox = new ComboBox<>("Группа контрагента");
    private TextField contractorAccount = new TextField("Счет контрагента");
    private ComboBox<ContractDto> contractDtoComboBox = new ComboBox<>("Договор");
    private ComboBox<EmployeeDto> employeeDtoComboBox = new ComboBox<>("Владелец контрагента");

    private ComboBox<CompanyDto> companyDtoComboBox = new ComboBox<>("Организация");
    private TextField companyAccount = new TextField("Счет организации");
    private ComboBox<String> status = new ComboBox<>("Статус");
    private ComboBox<String> accountingEntry = new ComboBox<>("Проведено", "Да", "Нет");
    private ComboBox<String> printed = new ComboBox<>("Напечатано", "Да", "Нет");

    private ComboBox<String> sent = new ComboBox<>("Отправлено", "Да", "Нет");
    private ComboBox<EmployeeDto> employeeDtoBox = new ComboBox<>("Владелец-сотрудник");
    private ComboBox<DepartmentDto> departmentDtoComboBox = new ComboBox<>("Владелец-отдел");
    private ComboBox<String> generalAccess = new ComboBox<>("Общий доступ", "Да", "Нет");
    private  DatePicker periodStart3 = new DatePicker("Когда изменен");
    private DatePicker periodEnd3 = new DatePicker("до");

    private ComboBox<EmployeeDto> employeeBox = new ComboBox<>("Кто изменил");

    private void actionClear() {
        clear.addClickListener(e -> {
            periodStart.clear();
            periodEnd.clear();
            incomingNumber.clear();
            incomingDataStart.clear();
            incomingDataEnd.clear();
            payment.clear();
            acceptance.clear();
            periodStart2.clear();
            periodEnd2.clear();
            productDtoComboBox.clear();
            warehouseDtoComboBox.clear();
            contractorDtoComboBox.clear();
            contractorGroupBox.clear();
            contractorAccount.clear();
            contractDtoComboBox.clear();
            employeeDtoComboBox.clear();
            companyDtoComboBox.clear();
            companyAccount.clear();
            status.clear();
            accountingEntry.clear();
            printed.clear();
            sent.clear();
            employeeDtoBox.clear();
            departmentDtoComboBox.clear();
            generalAccess.clear();
            periodStart3.clear();
            periodEnd3.clear();
            employeeBox.clear();
        });
    }

    private HorizontalLayout getHorizontalLayoutOne() {
        var horizontalLayout = new HorizontalLayout();
        find.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        clear.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        dialog.add(bookmarkVerticalLayout());
        columnToggleContextMenu.add(settingsCheckboxesLayout());
        periodStart.setWidth("100px");
        periodEnd.setWidth("100px");
        incomingNumber.setLabel("Входящий номер");
        incomingDataStart.setWidth("100px");
        incomingDataEnd.setWidth("100px");
        payment.setWidth("200px");
        horizontalLayout.add(find, clear, bookmarks, settingButton, periodStart,
                periodEnd, incomingNumber, incomingDataStart, incomingDataEnd, payment);
        return horizontalLayout;
    }

    private HorizontalLayout getHorizontalLayoutTwo() {
        var horizontalLayout = new HorizontalLayout();
        acceptance.setWidth("200px");
        periodStart2.setWidth("100px");
        periodEnd2.setWidth("100px");
        List<ProductDto> productDtos = productService.getAll();
        productDtoComboBox.setItems(productDtos);
        productDtoComboBox.setItemLabelGenerator(ProductDto::getName);
        productDtoComboBox.setWidth("190px");

        List<WarehouseDto> warehouseDtos = warehouseService.getAll();
        warehouseDtoComboBox.setItems(warehouseDtos);
        warehouseDtoComboBox.setItemLabelGenerator(WarehouseDto::getName);
        warehouseDtoComboBox.setWidth("200px");

        List<ProjectDto> projectDtos = projectService.getAll();
        projectDtoComboBox.setItems(projectDtos);
        projectDtoComboBox.setItemLabelGenerator(ProjectDto::getName);
        projectDtoComboBox.setWidth("200px");

        horizontalLayout.add(acceptance, periodStart2, periodEnd2, productDtoComboBox, warehouseDtoComboBox, projectDtoComboBox);
        return horizontalLayout;
    }

    private HorizontalLayout getHorizontalLayoutThree() {
        var horizontalLayout = new HorizontalLayout();

        List<ContractorDto> contractorDtos = contractorService.getAll();
        contractorDtoComboBox.setItems(contractorDtos);
        contractorDtoComboBox.setItemLabelGenerator(ContractorDto::getName);
        contractorDtoComboBox.setWidth("200px");

        contractorGroupBox.setWidth("200px");
        contractorAccount.setWidth("200px");

        List<ContractDto> contractDtos = contractService.getAll();
        contractDtoComboBox.setItems(contractDtos);
        contractDtoComboBox.setItemLabelGenerator(ContractDto::getNumber);
        contractDtoComboBox.setWidth("200px");

        List<EmployeeDto> employeeDtos = employeeService.getAll();
        employeeDtoComboBox.setItems(employeeDtos);
        employeeDtoComboBox.setItemLabelGenerator(EmployeeDto::getLastName);
        employeeDtoComboBox.setWidth("200px");

        horizontalLayout.add(contractorDtoComboBox, contractorGroupBox, contractorAccount, contractDtoComboBox, employeeDtoComboBox);
        return horizontalLayout;
    }

    private HorizontalLayout getHorizontalLayoutFour() {
        var horizontalLayout = new HorizontalLayout();

        List<CompanyDto> companyDtos = null;
        try {
            companyDtos = companyService.getAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
        companyDtoComboBox.setItems(companyDtos);
        companyDtoComboBox.setItemLabelGenerator(CompanyDto::getName);
        companyDtoComboBox.setWidth("200px");

        companyAccount.setWidth("200px");
        status.setWidth("200px");
        accountingEntry.setWidth("200px");
        printed.setWidth("200px");
        horizontalLayout.add(companyDtoComboBox, companyAccount, status, accountingEntry, printed);
        return horizontalLayout;
    }


    private HorizontalLayout getHorizontalLayoutFive() {
        var horizontalLayout = new HorizontalLayout();
        sent.setWidth("200px");

        List<EmployeeDto> employeeDtos = employeeService.getAll();
        employeeDtoBox.setItems(employeeDtos);
        employeeDtoBox.setItemLabelGenerator(EmployeeDto::getLastName);
        employeeDtoBox.setWidth("200px");

        List<DepartmentDto> departmentDtos = departmentService.getAll();
        departmentDtoComboBox.setItems(departmentDtos);
        departmentDtoComboBox.setItemLabelGenerator(DepartmentDto::getName);
        departmentDtoComboBox.setWidth("200px");

        generalAccess.setWidth("190px");
        periodStart3.setWidth("100px");
        periodEnd3.setWidth("100px");

        horizontalLayout.add(sent, employeeDtoBox, departmentDtoComboBox, generalAccess, periodStart3, periodEnd3);
        return horizontalLayout;
    }

    private HorizontalLayout getHorizontalLayoutSix() {
        var horizontalLayout = new HorizontalLayout();

        List<EmployeeDto> employeeDtos = employeeService.getAll();
        employeeBox.setItems(employeeDtos);
        employeeBox.setItemLabelGenerator(EmployeeDto::getLastName);
        employeeBox.setWidth("200px");

        horizontalLayout.add(employeeBox);
        return horizontalLayout;
    }

    private VerticalLayout bookmarkVerticalLayout() {
        var verticalLayout = new VerticalLayout();
        var horizontalLayout = new HorizontalLayout();
        bookmarksWindowName.setText("Закладки");
        bookmarkTitle.getStyle().set("margin", "1rem");
        saveBookmark.getStyle().set("margin", "0.2rem");
        saveBookmark.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        cancelBookmark.getStyle().set("margin", "0.2rem");
        topBookmarkHeader.add(bookmarksWindowName);
        topBookmarkHeader.getStyle().set("font-size", "20px").set("font-weight", "bold");
        topBookmarkHeader.setWidth("500px");
        bookmarkTitle.setWidth("420px");
        middleBookmarkHeader.setText("Название");
        middleBookmarkHeader.add(bookmarkTitle);
        bottomBookmarkHeader.add(saveBookmark, cancelBookmark);
        horizontalLayout.add(topBookmarkHeader, closeBookmarks);
        verticalLayout.add(horizontalLayout, middleBookmarkHeader, bottomBookmarkHeader);
        return verticalLayout;
    }

    private VerticalLayout settingsCheckboxesLayout() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(false);
        String[] namesOfFields = {"Период", "до", "Входящий номер", "Входящая дата", "до", "Оплата", "Приемка",
                "План. дата оплаты", "до", "Товар или группа", "Склад", "Проект", "Контрагент", "Группа контрагента",
                "Счет контрагента", "Договор", "Владелец контрагента", "Организация", "Счет организации", "Статус",
                "Проведено", "Напечатано", "Отправлено", "Владелец-сотрудник", "Владелец-отдел", "Общий доступ",
                "Когда изменен", "до", "Кто изменил"};
        Component[] valuesOfFields = {periodStart, periodEnd, incomingNumber, incomingDataStart, incomingDataEnd, payment,
                acceptance, periodStart2, periodEnd2, productDtoComboBox, warehouseDtoComboBox, projectDtoComboBox,
                contractorDtoComboBox, contractorGroupBox, contractorAccount, contractDtoComboBox, employeeDtoComboBox,
                companyDtoComboBox, companyAccount, status, accountingEntry, printed, sent, employeeDtoBox,
                departmentDtoComboBox, generalAccess, periodStart3, periodEnd3, employeeBox};
        for (int i = 0; i < namesOfFields.length; i++) {
            layout.add(createCheckBoxWithListener(new Checkbox(namesOfFields[i]), valuesOfFields[i]));
        }
        return layout;
    }

    Checkbox createCheckBoxWithListener(Checkbox checkbox, Component component) {
        checkbox.setValue(true);
        checkbox.addClickListener(event -> component.setVisible(checkbox.getValue()));
        return checkbox;
    }
}
