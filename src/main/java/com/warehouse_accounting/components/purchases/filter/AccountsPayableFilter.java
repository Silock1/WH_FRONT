package com.warehouse_accounting.components.purchases.filter;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.models.dto.*;
import com.warehouse_accounting.services.interfaces.*;

import java.io.IOException;
import java.util.List;

//Счета поставщиков - фильтр

@SpringComponent
@UIScope
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

    Button find = new Button("Найти");
    Button clear = new Button("Очистить");
    Button bookmarks = new Button(new Icon(VaadinIcon.BOOKMARK));
    Button settingButton = new Button(new Icon(VaadinIcon.COG));
    DatePicker periodStart = new DatePicker("Период");
    DatePicker periodEnd = new DatePicker("до");
    TextField incomingNumber = new TextField();
    DatePicker incomingDataStart = new DatePicker("Входящая дата");
    DatePicker incomingDataEnd = new DatePicker("до");
    ComboBox<String> payment = new ComboBox<>("Оплата", "Оплачено",
            "Частично оплачено", "Не оплачено", "Просрочено");

    ComboBox<String> acceptance = new ComboBox<>("Приемка", "Принято", "Частично принято", "Не принято");
    DatePicker periodStart2 = new DatePicker("План. дата оплаты");
    DatePicker periodEnd2 = new DatePicker("до");
    ComboBox<ProductDto> productDtoComboBox = new ComboBox<>("Товар или группа");
    ComboBox<WarehouseDto> warehouseDtoComboBox = new ComboBox<>("Склад");
    ComboBox<ProjectDto> projectDtoComboBox = new ComboBox<>("Проект");

    ComboBox<ContractorDto> contractorDtoComboBox = new ComboBox<>("Контрагент");
    ComboBox<String> contractorGroupBox = new ComboBox<>("Группа контрагента");
    TextField contractorAccount = new TextField("Счет контрагента");
    ComboBox<ContractDto> contractDtoComboBox = new ComboBox<>("Договор");
    ComboBox<EmployeeDto> employeeDtoComboBox = new ComboBox<>("Владелец контрагента");

    ComboBox<CompanyDto> companyDtoComboBox = new ComboBox<>("Организация");
    TextField companyAccount = new TextField("Счет организации");
    ComboBox<String> status = new ComboBox<>("Статус");
    ComboBox<String> accountingEntry = new ComboBox<>("Проведено", "Да", "Нет");
    ComboBox<String> printed = new ComboBox<>("Напечатано", "Да", "Нет");

    ComboBox<String> sent = new ComboBox<>("Отправлено", "Да", "Нет");
    ComboBox<EmployeeDto> employeeDtoBox = new ComboBox<>("Владелец-сотрудник");
    ComboBox<DepartmentDto> departmentDtoComboBox = new ComboBox<>("Владелец-отдел");
    ComboBox<String> generalAccess = new ComboBox<>("Общий доступ", "Да", "Нет");
    DatePicker periodStart3 = new DatePicker("Когда изменен");
    DatePicker periodEnd3 = new DatePicker("до");

    ComboBox<EmployeeDto> employeeBox = new ComboBox<>("Кто изменил");

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
}
