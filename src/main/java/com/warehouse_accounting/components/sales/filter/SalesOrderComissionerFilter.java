package com.warehouse_accounting.components.sales.filter;

import com.vaadin.flow.component.Component;
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
import com.warehouse_accounting.models.dto.CompanyDto;
import com.warehouse_accounting.models.dto.ContractDto;
import com.warehouse_accounting.models.dto.ContractorDto;
import com.warehouse_accounting.models.dto.DepartmentDto;
import com.warehouse_accounting.models.dto.EmployeeDto;
import com.warehouse_accounting.models.dto.ProjectDto;
import com.warehouse_accounting.models.dto.WarehouseDto;
import com.warehouse_accounting.services.interfaces.CompanyService;
import com.warehouse_accounting.services.interfaces.ContractService;
import com.warehouse_accounting.services.interfaces.ContractorService;
import com.warehouse_accounting.services.interfaces.DepartmentService;
import com.warehouse_accounting.services.interfaces.EmployeeService;
import com.warehouse_accounting.services.interfaces.ProjectService;
import com.warehouse_accounting.services.interfaces.WarehouseService;

import javax.imageio.IIOException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringComponent
@UIScope
public class SalesOrderComissionerFilter extends VerticalLayout {
    private CompanyService companyService;
    private ContractService contractService;
    private ContractorService contractorService;
    private ProjectService projectService;
    private WarehouseService warehouseService;
    private EmployeeService employeeService;
    private DepartmentService departmentService;

    public SalesOrderComissionerFilter(CompanyService companyService, ContractorService contractorService,
                                ContractService contractService,  ProjectService projectService,
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

        add(horizontalLayoutOne, horizontalLayoutTwo, horizontalLayoutThree, horizontalLayoutFour, horizontalLayoutFive);

        setVisible(false);
    }

    /**
     *
     * Это поле №1 - готово
     */
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

        List<CompanyDto> companyDtos = new ArrayList<>();
        try {
            companyDtos = companyService.getAll();
        }catch (IOException ioException){
            ioException.printStackTrace();
        }

        ComboBox<CompanyDto> companyDtosComboBox = new ComboBox<>("Товар или группа");
        companyDtosComboBox.setItems(companyDtos);
        companyDtosComboBox.setItemLabelGenerator(CompanyDto::getName);

        ComboBox<String> payment = new ComboBox<>("Проект");

        //todo проект, заполнить в выпадающий список классом проект
        payment.setItems("Оплачено", "Частично оплачено", "Не оплачено");

        List<ContractorDto> contractorDtos = contractorService.getAll();
        ComboBox<ContractorDto> contractorsCombo = new ComboBox<>("Контаргент");
        contractorsCombo.setItems(contractorDtos);
        contractorsCombo.setItemLabelGenerator(ContractorDto::getName);

        horizontalLayout.add(find, clear, bookmarks, settingButton, periodStart, periodEnd, companyDtosComboBox, payment, contractorsCombo);
        return horizontalLayout;
    }

    /**
     *
     * Это поле №2 - готово
     */
    private HorizontalLayout getHorizontalLayoutTwo(){
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        ComboBox<String> contractorGroup = new ComboBox<>("Группа контрагента");
        ComboBox<String> order = new ComboBox<>("Договор");
        ComboBox<String> ownerContractor = new ComboBox<>("Владелец контрагента");

        List<CompanyDto> companyDtos = null;
        try {
            companyDtos = companyService.getAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ComboBox<CompanyDto> organization = new ComboBox<>("Организация");
        organization.setItems(companyDtos);
        organization.setItemLabelGenerator(CompanyDto::getName);

        ComboBox<String> organizationCheck = new ComboBox<>("Счет организации");


        horizontalLayout.add(contractorGroup, order, ownerContractor, organization, organizationCheck);
        return horizontalLayout;
    }

    /**
     *
     * Это поле №3 - готово
     */
    private HorizontalLayout getHorizontalLayoutThree(){
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        ComboBox<String> typeDoc = new ComboBox<>("Тип документа", "Все", "Полученный отчет комиссионера", "Выданный отчеткомиссионера");
        ComboBox<String> status = new ComboBox<>("Статус");
        ComboBox<String> carriedOutCombo = new ComboBox<>("Проведено", "Да", "Нет");
        ComboBox<String> printedCombo = new ComboBox<>("Напечатано", "Да", "Нет");
        ComboBox<String> sentCombo = new ComboBox<>("Отправлено", "Да", "Нет");
        horizontalLayout.add(typeDoc, status, carriedOutCombo, printedCombo, sentCombo);

        return horizontalLayout;
    }

    /**
     *
     * Это поле №4
     */
    private HorizontalLayout getHorizontalLayoutFour(){
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        TextField deliveryAddress = new TextField("Адрес доставки");
        TextField shippingAddressComment = new TextField("Комментарий к адресу доставки");

        //ToDO Есть класс канал продаж нужно его подключить Не правильно сдела Делай как ниже в коментах
        ComboBox<String> salesChannel = new ComboBox<>("Канал продаж");

//        List<EmployeeDto> employeeDtos1 = employeeService.getAll();
//        ComboBox<EmployeeDto> employeeDtoComboBox1 = new ComboBox<>("Канал-продаж");
//        employeeDtoComboBox1.setItems(employeeDtos1);
//        employeeDtoComboBox1.setItemLabelGenerator(EmployeeDto::getLastName);

        List<EmployeeDto> employeeDtos = employeeService.getAll();
        ComboBox<EmployeeDto> employeeDtoComboBox = new ComboBox<>("Владелец-сотрудник");
        employeeDtoComboBox.setItems(employeeDtos);
        employeeDtoComboBox.setItemLabelGenerator(EmployeeDto::getLastName);

        List<DepartmentDto> departmentDtos = departmentService.getAll();
        ComboBox<DepartmentDto> departmentDtoComboBox = new ComboBox<>("Владелец-отдел");
        departmentDtoComboBox.setItems(departmentDtos);
        departmentDtoComboBox.setItemLabelGenerator(DepartmentDto::getName);

        ComboBox<String> generalAccess = new ComboBox<>("Общий доступ","Да", "Нет");
        DatePicker whenChangedStart = new DatePicker("Когда изменен: от");
        DatePicker whenChangedEnd = new DatePicker("до");

        horizontalLayout.add(salesChannel, employeeDtoComboBox, departmentDtoComboBox, generalAccess, whenChangedStart, whenChangedEnd);
        return horizontalLayout;
    }

    private HorizontalLayout getHorizontalLayoutFive(){
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        List<EmployeeDto> employeeDtos = employeeService.getAll();
        ComboBox<EmployeeDto> whoChange = new ComboBox<>("Кто изменил");
        whoChange.setItems(employeeDtos);
        whoChange.setItemLabelGenerator(EmployeeDto::getLastName);

        horizontalLayout.add(whoChange);
        return horizontalLayout;
    }
}
