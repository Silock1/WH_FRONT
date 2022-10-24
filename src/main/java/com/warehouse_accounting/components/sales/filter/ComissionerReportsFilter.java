package com.warehouse_accounting.components.sales.filter;

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
import java.util.ArrayList;
import java.util.List;

@SpringComponent
@UIScope
public class ComissionerReportsFilter extends VerticalLayout {
    private CompanyService companyService;
    private ContractService contractService;
    private ContractorService contractorService;
    private ProjectService projectService;
    private WarehouseService warehouseService;
    private EmployeeService employeeService;
    private DepartmentService departmentService;

//    public ComissionerReportsFilter() {};

    public ComissionerReportsFilter(CompanyService companyService, ContractorService contractorService,
                                ContractService contractService, ProjectService projectService,
                                WarehouseService warehouseService, EmployeeService employeeService,
                                DepartmentService departmentService) {

        this.companyService = companyService;
        this.contractorService = contractorService;
        this.contractService = contractService;
        this.projectService = projectService;
        this.warehouseService = warehouseService;
        this.employeeService = employeeService;
        this.departmentService = departmentService;

        HorizontalLayout horizontalLayoutOne = getHorizontalLayoutOne();
        HorizontalLayout horizontalLayoutTwo = getHorizontalLayoutTwo();
        HorizontalLayout horizontalLayoutThree = getHorizontalLayoutThree();
        HorizontalLayout horizontalLayoutFour = getHorizontalLayoutFour();
        HorizontalLayout horizontalLayoutFive = getHorizontalLayoutFive();
        HorizontalLayout horizontalLayoutSix = getHorizontalLayoutSix();
        HorizontalLayout horizontalLayoutSeven = getHorizontalLayoutSeven();

        add(horizontalLayoutOne, horizontalLayoutTwo, horizontalLayoutThree, horizontalLayoutFour, horizontalLayoutFive,
                horizontalLayoutSix, horizontalLayoutSeven);

        setVisible(false);
    }
    private HorizontalLayout getHorizontalLayoutOne(){
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        Button find = new Button("Найти");
        Button clear = new Button("Очистить");
        Button bookmarks = new Button(new Icon(VaadinIcon.BOOKMARK));
        Button settingButton = new Button(new Icon(VaadinIcon.COG));
        find.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        clear.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        DatePicker periodStart = new DatePicker("Период");
        DatePicker periodEnd = new DatePicker("до");

        ComboBox<String> payment = new ComboBox<>("Оплата", "Оплачено", "Частично оплачено", "Не оплачено");


        ComboBox<String> shipped = new ComboBox<>("Отгружено", "Отгружено", "Частично отгружено",
                "Не отгружено", "Просрочено");


        horizontalLayout.add(find, clear, bookmarks, settingButton, periodStart, periodEnd, payment, shipped);
        return horizontalLayout;
    }

    private HorizontalLayout getHorizontalLayoutTwo(){
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        DatePicker periodStart = new DatePicker("План. дата огрузки");
        DatePicker periodEnd = new DatePicker("до");

        ComboBox<String> productComboBox = new ComboBox<>("Товар или группа");
        ComboBox<String> typeReturn = new ComboBox<>("Тип возврата", "Частично возвращено", "Без возвратов", "Полностью возвращено");

        List<WarehouseDto> warehouseDtos = warehouseService.getAll();
        ComboBox<WarehouseDto> warehouseDtoComboBox = new ComboBox<>("Склад");
        warehouseDtoComboBox.setItems(warehouseDtos);
        warehouseDtoComboBox.setItemLabelGenerator(WarehouseDto::getName);

        horizontalLayout.add(periodStart, periodEnd, productComboBox, typeReturn, warehouseDtoComboBox);
        return horizontalLayout;
    }

    private HorizontalLayout getHorizontalLayoutThree(){
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        List<ProjectDto> projectDtos = projectService.getAll();
        ComboBox<ProjectDto> projectCombo = new ComboBox<>("Проект");
        projectCombo.setItems(projectDtos);
        projectCombo.setItemLabelGenerator(ProjectDto::getName);

        List<ContractorDto> contractorDtos = contractorService.getAll();
        ComboBox<ContractorDto> contractorsCombo = new ComboBox<>("Контаргент");
        contractorsCombo.setItems(contractorDtos);
        contractorsCombo.setItemLabelGenerator(ContractorDto::getName);

        ComboBox<String> groupContractors = new ComboBox<>("Группа контрагента");

        TextField counterPartyAccount = new TextField("Счёт контрагента");

        horizontalLayout.add(projectCombo, contractorsCombo, groupContractors, counterPartyAccount);
        return horizontalLayout;
    }

    private HorizontalLayout getHorizontalLayoutFour(){
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        List<ContractDto> contractDtos = contractService.getAll();
        ComboBox<ContractDto> contractDtoComboBox = new ComboBox<>("Договор");
        contractDtoComboBox.setItems(contractDtos);
        contractDtoComboBox.setItemLabelGenerator(ContractDto::getNumber);

        List<EmployeeDto> employeeDtos = employeeService.getAll();
        ComboBox<EmployeeDto> employeeDtoComboBox = new ComboBox<>("Владелец контрагента");
        employeeDtoComboBox.setItems(employeeDtos);
        employeeDtoComboBox.setItemLabelGenerator(EmployeeDto::getLastName);

        List<CompanyDto> companyDtos = new ArrayList<>();

        try {
            companyDtos = companyService.getAll();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ComboBox<CompanyDto> companyDtoComboBox = new ComboBox<>("Организация");
        companyDtoComboBox.setItems(companyDtos);
        companyDtoComboBox.setItemLabelGenerator(CompanyDto::getName);

        TextField organizationAccount = new TextField("Счет организации");


        horizontalLayout.add(contractDtoComboBox, employeeDtoComboBox, companyDtoComboBox, organizationAccount);
        return horizontalLayout;
    }

    private HorizontalLayout getHorizontalLayoutFive(){
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        ComboBox<String> statusCombo = new ComboBox<>("Статус", "Новый", "Подтвержден", "Собран", "Отгружен", "Доставлен", "Возврат", "Отменен");
        ComboBox<String> carriedOutCombo = new ComboBox<>("Проведено", "Да", "Нет");
        ComboBox<String> printedCombo = new ComboBox<>("Напечатано", "Да", "Нет");
        ComboBox<String> sentCombo = new ComboBox<>("Отправлено", "Да", "Нет");
        horizontalLayout.add(statusCombo, carriedOutCombo, printedCombo, sentCombo);

        return horizontalLayout;
    }

    private HorizontalLayout getHorizontalLayoutSix(){
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        ComboBox<String> salesChannel = new ComboBox<>("Канал продаж");

        TextField deliveryAddress = new TextField("Адрес доставки");
        TextField shippingAddressComment = new TextField("Комментарий к адресу доставки");

        List<EmployeeDto> employeeDtos = employeeService.getAll();
        ComboBox<EmployeeDto> employeeDtoComboBox = new ComboBox<>("Владелец-сотрудник");
        employeeDtoComboBox.setItems(employeeDtos);
        employeeDtoComboBox.setItemLabelGenerator(EmployeeDto::getLastName);

        horizontalLayout.add(salesChannel, deliveryAddress, shippingAddressComment,employeeDtoComboBox);
        return horizontalLayout;
    }

    private HorizontalLayout getHorizontalLayoutSeven(){
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        List<DepartmentDto> departmentDtos = departmentService.getAll();
        ComboBox<DepartmentDto> departmentDtoComboBox = new ComboBox<>("Владелец-отдел");
        departmentDtoComboBox.setItems(departmentDtos);
        departmentDtoComboBox.setItemLabelGenerator(DepartmentDto::getName);

        ComboBox<String> generalAccess = new ComboBox<>("Общий доступ","Да", "Нет");
        DatePicker whenChangedStart = new DatePicker("Когда изменен: от");
        DatePicker whenChangedEnd = new DatePicker("до");

        List<EmployeeDto> employeeDtos = employeeService.getAll();
        ComboBox<EmployeeDto> employeeDtoComboBox = new ComboBox<>("Кто изменил");
        employeeDtoComboBox.setItems(employeeDtos);
        employeeDtoComboBox.setItemLabelGenerator(EmployeeDto::getLastName);

        horizontalLayout.add(departmentDtoComboBox, generalAccess, whenChangedStart, whenChangedEnd, employeeDtoComboBox);
        return horizontalLayout;
    }
}
