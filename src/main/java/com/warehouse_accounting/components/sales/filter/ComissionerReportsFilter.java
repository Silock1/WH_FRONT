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

        add(horizontalLayoutOne, horizontalLayoutTwo, horizontalLayoutThree, horizontalLayoutFour);

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

        ComboBox<String> productComboBox = new ComboBox<>("?????????? ?????? ????????????");

        List<ProjectDto> projectDtos = projectService.getAll();
        ComboBox<ProjectDto> projectCombo = new ComboBox<>("????????????");
        projectCombo.setItems(projectDtos);
        projectCombo.setItemLabelGenerator(ProjectDto::getName);

        List<ContractorDto> contractorDtos = contractorService.getAll();
        ComboBox<ContractorDto> contractorsCombo = new ComboBox<>("????????????????????");
        contractorsCombo.setItems(contractorDtos);
        contractorsCombo.setItemLabelGenerator(ContractorDto::getName);

        ComboBox<String> groupContractors = new ComboBox<>("???????????? ??????????????????????");

        horizontalLayout.add(find, clear, bookmarks, settingButton, periodStart, periodEnd, productComboBox, projectCombo, contractorsCombo, groupContractors);
        return horizontalLayout;
    }

    private HorizontalLayout getHorizontalLayoutTwo(){
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        List<ContractDto> contractDtos = contractService.getAll();
        ComboBox<ContractDto> contractDtoComboBox = new ComboBox<>("??????????????");
        contractDtoComboBox.setItems(contractDtos);
        contractDtoComboBox.setItemLabelGenerator(ContractDto::getNumber);

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

        ComboBox<String> documentTypeCombo = new ComboBox<>("?????? ??????????????????", "??????", "???????????????????? ?????????? ????????????????????????", "???????????????? ?????????? ????????????????????????");

        ComboBox<String> statusCombo = new ComboBox<>("????????????", "??????????", "??????????????????????", "????????????", "????????????????", "??????????????????", "??????????????", "??????????????");

        horizontalLayout.add(contractDtoComboBox, employeeDtoComboBox, companyDtoComboBox, organizationAccount, documentTypeCombo, statusCombo);
        return horizontalLayout;
    }

    private HorizontalLayout getHorizontalLayoutThree(){
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        ComboBox<String> carriedOutCombo = new ComboBox<>("??????????????????", "????", "??????");

        ComboBox<String> printedCombo = new ComboBox<>("????????????????????", "????", "??????");

        ComboBox<String> sentCombo = new ComboBox<>("????????????????????", "????", "??????");

        ComboBox<String> salesChannel = new ComboBox<>("?????????? ????????????");

        List<EmployeeDto> employeeDtos = employeeService.getAll();
        ComboBox<EmployeeDto> employeeDtoComboBox = new ComboBox<>("????????????????-??????????????????");
        employeeDtoComboBox.setItems(employeeDtos);
        employeeDtoComboBox.setItemLabelGenerator(EmployeeDto::getLastName);

        List<DepartmentDto> departmentDtos = departmentService.getAll();
        ComboBox<DepartmentDto> departmentDtoComboBox = new ComboBox<>("????????????????-??????????");
        departmentDtoComboBox.setItems(departmentDtos);
        departmentDtoComboBox.setItemLabelGenerator(DepartmentDto::getName);

        horizontalLayout.add(carriedOutCombo, printedCombo, sentCombo, salesChannel, employeeDtoComboBox, departmentDtoComboBox);
        return horizontalLayout;
    }

    private HorizontalLayout getHorizontalLayoutFour(){
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        ComboBox<String> generalAccess = new ComboBox<>("?????????? ????????????","????", "??????");
        DatePicker whenChangedStart = new DatePicker("?????????? ??????????????: ????");
        DatePicker whenChangedEnd = new DatePicker("????");

        List<EmployeeDto> employeeDtos = employeeService.getAll();
        ComboBox<EmployeeDto> employeeDtoComboBox = new ComboBox<>("?????? ??????????????");
        employeeDtoComboBox.setItems(employeeDtos);
        employeeDtoComboBox.setItemLabelGenerator(EmployeeDto::getLastName);


        horizontalLayout.add(generalAccess, employeeDtoComboBox, whenChangedStart, whenChangedEnd, employeeDtoComboBox);
        return horizontalLayout;
    }

}
