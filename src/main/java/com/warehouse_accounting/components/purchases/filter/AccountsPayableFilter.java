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
/*
Счета поставщиков - фильтр
 */
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
        setVisible(false);
    }

    private HorizontalLayout getHorizontalLayoutOne(){
        var horizontalLayout = new HorizontalLayout();
        Button find = new Button("Найти");
        Button clear = new Button("Очистить");
        Button bookmarks = new Button(new Icon(VaadinIcon.BOOKMARK));
        Button settingButton = new Button(new Icon(VaadinIcon.COG));
        find.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        clear.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        DatePicker periodStart = new DatePicker("Период");
        DatePicker periodEnd = new DatePicker("до");

        TextField incomingNumber = new TextField("Входящий номер");

        DatePicker incomingDataStart = new DatePicker("Входящая дата");
        DatePicker incomingDataEnd = new DatePicker("до");

        ComboBox<String> payment = new ComboBox<>("Оплата", "Оплачено",
                "Частично оплачено", "Не оплачено", "Просрочено");

        horizontalLayout.add(find,clear,bookmarks,settingButton, periodStart,
                periodEnd,incomingNumber,incomingDataStart,incomingDataEnd,payment);
        return horizontalLayout;
    }

    private HorizontalLayout getHorizontalLayoutTwo(){
        var horizontalLayout = new HorizontalLayout();
        ComboBox<String> acceptance = new ComboBox<>("Приемка","Принято", "Частично принято", "Не принято");
        DatePicker periodStart = new DatePicker("План. дата оплаты");
        DatePicker periodEnd = new DatePicker("до");

        List<ProductDto> productDtos = productService.getAll();
        ComboBox<ProductDto> productDtoComboBox = new ComboBox<>("Товар или группа");
        productDtoComboBox.setItems(productDtos);
        productDtoComboBox.setItemLabelGenerator(ProductDto::getName);

        List<WarehouseDto> warehouseDtos = warehouseService.getAll();
        ComboBox<WarehouseDto> warehouseDtoComboBox = new ComboBox<>("Склад");
        warehouseDtoComboBox.setItems(warehouseDtos);
        warehouseDtoComboBox.setItemLabelGenerator(WarehouseDto::getName);

        List<ProjectDto> projectDtos = projectService.getAll();
        ComboBox<ProjectDto> projectDtoComboBox = new ComboBox<>("Проект");
        projectDtoComboBox.setItems(projectDtos);
        projectDtoComboBox.setItemLabelGenerator(ProjectDto::getName);

        horizontalLayout.add(acceptance, periodStart, periodEnd, productDtoComboBox, warehouseDtoComboBox, projectDtoComboBox);
        return horizontalLayout;
    }

    private HorizontalLayout getHorizontalLayoutThree(){
        var horizontalLayout = new HorizontalLayout();

        List<ContractorDto> contractorDtos = contractorService.getAll();
        ComboBox<ContractorDto> contractorDtoComboBox = new ComboBox<>("Контрагент");
        contractorDtoComboBox.setItems(contractorDtos);
        contractorDtoComboBox.setItemLabelGenerator(ContractorDto::getName);

        ComboBox<String> contractorGroupBox = new ComboBox<>("Группа контрагента");

        TextField contractorAccount = new TextField("Счет контрагента");

        List<ContractDto> contractDtos = contractService.getAll();
        ComboBox<ContractDto> contractDtoComboBox = new ComboBox<>("Договор");
        contractDtoComboBox.setItems(contractDtos);
        contractDtoComboBox.setItemLabelGenerator(ContractDto::getNumber);

        List<EmployeeDto> employeeDtos = employeeService.getAll();
        ComboBox<EmployeeDto> employeeDtoComboBox = new ComboBox<>("Владелец контрагента");
        employeeDtoComboBox.setItems(employeeDtos);
        employeeDtoComboBox.setItemLabelGenerator(EmployeeDto::getLastName);

        horizontalLayout.add(contractorDtoComboBox, contractorGroupBox, contractorAccount, contractDtoComboBox, employeeDtoComboBox);
        return horizontalLayout;
    }

    private HorizontalLayout getHorizontalLayoutFour(){
        var horizontalLayout = new HorizontalLayout();

        List<CompanyDto> companyDtos = null;
        try {
            companyDtos = companyService.getAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ComboBox<CompanyDto> companyDtoComboBox = new ComboBox<>("Организация");
        companyDtoComboBox.setItems(companyDtos);
        companyDtoComboBox.setItemLabelGenerator(CompanyDto::getName);

        TextField companyAccount = new TextField("Счет организации");

        ComboBox<String> status = new ComboBox<>("Статус");

        ComboBox<String> accountingEntry = new ComboBox<>("Проведено", "Да", "Нет");

        ComboBox<String> printed = new ComboBox<>("Напечатано", "Да", "Нет");

        horizontalLayout.add(companyDtoComboBox, companyAccount, status, accountingEntry, printed);
        return horizontalLayout;
    }

    private HorizontalLayout getHorizontalLayoutFive(){
        var horizontalLayout = new HorizontalLayout();

        ComboBox<String> sent = new ComboBox<>("Отправлено", "Да", "Нет");

        List<EmployeeDto> employeeDtos = employeeService.getAll();
        ComboBox<EmployeeDto> employeeDtoComboBox = new ComboBox<>("Владелец-сотрудник");
        employeeDtoComboBox.setItems(employeeDtos);
        employeeDtoComboBox.setItemLabelGenerator(EmployeeDto::getLastName);

        List<DepartmentDto> departmentDtos = departmentService.getAll();
        ComboBox<DepartmentDto> departmentDtoComboBox = new ComboBox<>("Владелец-отдел");
        departmentDtoComboBox.setItems(departmentDtos);
        departmentDtoComboBox.setItemLabelGenerator(DepartmentDto::getName);

        ComboBox<String> generalAccess = new ComboBox<>("Общий доступ", "Да", "Нет");

        DatePicker periodStart = new DatePicker("Когда изменен");
        DatePicker periodEnd = new DatePicker("до");

        horizontalLayout.add(sent, employeeDtoComboBox, departmentDtoComboBox, generalAccess, periodStart, periodEnd);
        return horizontalLayout;
    }

    private HorizontalLayout getHorizontalLayoutSix(){
        var horizontalLayout = new HorizontalLayout();

        List<EmployeeDto> employeeDtos = employeeService.getAll();
        ComboBox<EmployeeDto> employeeDtoComboBox = new ComboBox<>("Кто изменил");
        employeeDtoComboBox.setItems(employeeDtos);
        employeeDtoComboBox.setItemLabelGenerator(EmployeeDto::getLastName);

        horizontalLayout.add(employeeDtoComboBox);
        return horizontalLayout;
    }
}
