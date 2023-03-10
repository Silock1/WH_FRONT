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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringComponent
@UIScope
public class SalesShipmentsFilter extends VerticalLayout {
    private CompanyService companyService;
    private ContractService contractService;
    private ContractorService contractorService;
    private ProjectService projectService;
    private WarehouseService warehouseService;
    private EmployeeService employeeService;
    private DepartmentService departmentService;

    public SalesShipmentsFilter(CompanyService companyService, ContractorService contractorService,
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
        HorizontalLayout horizontalLayoutSix = getHorizontalLayoutSix();
        HorizontalLayout horizontalLayoutSeven = getHorizontalLayoutSeven();

        add(horizontalLayoutOne, horizontalLayoutTwo, horizontalLayoutThree, horizontalLayoutFour, horizontalLayoutFive,
                horizontalLayoutSix, horizontalLayoutSeven);

        setVisible(false);
    }
    private HorizontalLayout getHorizontalLayoutOne(){
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        Button find = new Button("??????????");
        Button clear = new Button("????????????????");
        Button bookmarks = new Button(new Icon(VaadinIcon.BOOKMARK));
        Button settingButton = new Button(new Icon(VaadinIcon.COG));
        find.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        clear.addThemeVariants(ButtonVariant.LUMO_CONTRAST);


        DatePicker periodStart = new DatePicker("????????????");
        DatePicker periodEnd = new DatePicker("????");

        List<CompanyDto> companyDtos = new ArrayList<>();
        try {
            companyDtos = companyService.getAll();
        }catch (IOException ioException){
            ioException.printStackTrace();
        }

        ComboBox<CompanyDto> companyDtosComboBox = new ComboBox<>("??????????????????????????????");
        companyDtosComboBox.setItems(companyDtos);
        companyDtosComboBox.setItemLabelGenerator(CompanyDto::getName);

        ComboBox<String> payment = new ComboBox<>("????????????");

        payment.setItems("????????????????", "???????????????? ????????????????", "???? ????????????????");

        horizontalLayout.add(find, clear, bookmarks, settingButton, periodStart, periodEnd, companyDtosComboBox, payment);
        return horizontalLayout;
    }

    private HorizontalLayout getHorizontalLayoutTwo(){
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        ComboBox<String> productComboBox = new ComboBox<>("?????????? ?????? ????????????");
        ComboBox<String> typeReturn = new ComboBox<>("?????? ????????????????", "???????????????? ????????????????????", "?????? ??????????????????", "?????????????????? ????????????????????");

        List<WarehouseDto> warehouseDtos = warehouseService.getAll();
        ComboBox<WarehouseDto> warehouseDtoComboBox = new ComboBox<>("??????????");
        warehouseDtoComboBox.setItems(warehouseDtos);
        warehouseDtoComboBox.setItemLabelGenerator(WarehouseDto::getName);

        List<ProjectDto> projectDtos = projectService.getAll();
        ComboBox<ProjectDto> projectCombo = new ComboBox<>("????????????");
        projectCombo.setItems(projectDtos);
        projectCombo.setItemLabelGenerator(ProjectDto::getName);

        horizontalLayout.add(productComboBox, typeReturn, warehouseDtoComboBox, projectCombo);
        return horizontalLayout;
    }

    private HorizontalLayout getHorizontalLayoutThree(){
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        List<ContractorDto> contractorDtos = contractorService.getAll();
        ComboBox<ContractorDto> contractorsCombo = new ComboBox<>("????????????????????");
        contractorsCombo.setItems(contractorDtos);
        contractorsCombo.setItemLabelGenerator(ContractorDto::getName);

        ComboBox<String> groupContractors = new ComboBox<>("???????????? ??????????????????????");

        TextField counterPartyAccount = new TextField("???????? ??????????????????????");

        List<ContractDto> contractDtos = contractService.getAll();
        ComboBox<ContractDto> contractDtoComboBox = new ComboBox<>("??????????????");
        contractDtoComboBox.setItems(contractDtos);
        contractDtoComboBox.setItemLabelGenerator(ContractDto::getNumber);

        horizontalLayout.add(contractorsCombo, groupContractors, counterPartyAccount, contractDtoComboBox);
        return horizontalLayout;
    }

    private HorizontalLayout getHorizontalLayoutFour(){
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        List<EmployeeDto> employeeDtos = employeeService.getAll();
        ComboBox<EmployeeDto> employeeDtoComboBox = new ComboBox<>("???????????????? ??????????????????????");
        employeeDtoComboBox.setItems(employeeDtos);
        employeeDtoComboBox.setItemLabelGenerator(EmployeeDto::getLastName);

        List<CompanyDto> companyDtos = new ArrayList<>();

        try {
            companyDtos = companyService.getAll();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ComboBox<CompanyDto> companyDtoComboBox = new ComboBox<>("??????????????????????");
        companyDtoComboBox.setItems(companyDtos);
        companyDtoComboBox.setItemLabelGenerator(CompanyDto::getName);

        TextField organizationAccount = new TextField("???????? ??????????????????????");
        ComboBox<String> statusCombo = new ComboBox<>("????????????");

        horizontalLayout.add(employeeDtoComboBox, companyDtoComboBox, organizationAccount, statusCombo);
        return horizontalLayout;
    }

    private HorizontalLayout getHorizontalLayoutFive(){
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        ComboBox<String> carriedOutCombo = new ComboBox<>("??????????????????", "????", "??????");
        ComboBox<String> printedCombo = new ComboBox<>("????????????????????", "????", "??????");
        ComboBox<String> sentCombo = new ComboBox<>("????????????????????", "????", "??????");
        ComboBox<String> salesChannel = new ComboBox<>("?????????? ????????????");
        horizontalLayout.add(carriedOutCombo, printedCombo, sentCombo, salesChannel);

        return horizontalLayout;
    }

    private HorizontalLayout getHorizontalLayoutSix(){
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        TextField deliveryAddress = new TextField("?????????? ????????????????");
        TextField shippingAddressComment = new TextField("?????????????????????? ?? ???????????? ????????????????");

        List<EmployeeDto> employeeDtos = employeeService.getAll();
        ComboBox<EmployeeDto> employeeDtoComboBox = new ComboBox<>("????????????????-??????????????????");
        employeeDtoComboBox.setItems(employeeDtos);
        employeeDtoComboBox.setItemLabelGenerator(EmployeeDto::getLastName);

        List<DepartmentDto> departmentDtos = departmentService.getAll();
        ComboBox<DepartmentDto> departmentDtoComboBox = new ComboBox<>("????????????????-??????????");
        departmentDtoComboBox.setItems(departmentDtos);
        departmentDtoComboBox.setItemLabelGenerator(DepartmentDto::getName);


        horizontalLayout.add(deliveryAddress, shippingAddressComment,employeeDtoComboBox,
                departmentDtoComboBox);
        return horizontalLayout;
    }

    private HorizontalLayout getHorizontalLayoutSeven(){
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        ComboBox<String> generalAccess = new ComboBox<>("?????????? ????????????","????", "??????");
        DatePicker whenChangedStart = new DatePicker("?????????? ??????????????: ????");
        DatePicker whenChangedEnd = new DatePicker("????");

        List<EmployeeDto> employeeDtos = employeeService.getAll();
        ComboBox<EmployeeDto> employeeDtoComboBox = new ComboBox<>("?????? ??????????????");
        employeeDtoComboBox.setItems(employeeDtos);
        employeeDtoComboBox.setItemLabelGenerator(EmployeeDto::getLastName);

        horizontalLayout.add(generalAccess, whenChangedStart, whenChangedEnd, employeeDtoComboBox);
        return horizontalLayout;
    }
}
