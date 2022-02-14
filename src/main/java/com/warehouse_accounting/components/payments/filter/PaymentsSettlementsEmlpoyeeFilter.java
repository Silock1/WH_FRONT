package com.warehouse_accounting.components.payments.filter;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.warehouse_accounting.models.dto.ContractorDto;
import com.warehouse_accounting.models.dto.EmployeeDto;
import com.warehouse_accounting.models.dto.ProjectDto;
import com.warehouse_accounting.models.dto.SalesChannelDto;
import com.warehouse_accounting.services.interfaces.ContractorService;
import com.warehouse_accounting.services.interfaces.EmployeeService;
import com.warehouse_accounting.services.interfaces.ProjectService;
import com.warehouse_accounting.services.interfaces.SalesChannelsService;

import java.util.List;


public class PaymentsSettlementsEmlpoyeeFilter extends VerticalLayout {
        ProjectService projectService;
        ContractorService contractorService;
        SalesChannelsService salesChannelsService;
        EmployeeService employeeService;

    public PaymentsSettlementsEmlpoyeeFilter(ProjectService projectService, ContractorService contractorService,
                                             SalesChannelsService salesChannelsService, EmployeeService employeeService){
        this.projectService = projectService;
        this.contractorService = contractorService;
        this.salesChannelsService = salesChannelsService;
        this.employeeService = employeeService;
        HorizontalLayout horizontalLayoutOne = getHorizontalLayoutOne();
        HorizontalLayout horizontalLayoutTwo = getHorizontalLayoutTwo();
        add(horizontalLayoutOne, horizontalLayoutTwo);
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

        ComboBox<String> typeSelectNull = new ComboBox<>("Показать сотрудников с нулевыми оборотами", "Да", "Нет");

        List<ProjectDto> projectDtoList = projectService.getAll();
        ComboBox<ProjectDto> projectComboBox = new ComboBox<>("Проект");
        projectComboBox.setItems(projectDtoList);
        projectComboBox.setItemLabelGenerator(ProjectDto::getName);

        List<ContractorDto> contractorDtoList = contractorService.getAll();
        ComboBox<ContractorDto> contractorComboBox = new ComboBox<>("Организация");
        contractorComboBox.setItems(contractorDtoList);
        contractorComboBox.setItemLabelGenerator(ContractorDto::getName);

        List<EmployeeDto> employeeDtoList = employeeService.getAll();
        ComboBox<EmployeeDto> contractorGroupComboBox = new ComboBox<>("Сотрудник");
        contractorGroupComboBox.setItems(employeeDtoList);
        contractorGroupComboBox.setItemLabelGenerator(EmployeeDto::getLastName);


        horizontalLayout.add(find, clear, bookmarks, settingButton, periodStart, periodEnd, typeSelectNull,
                projectComboBox, contractorComboBox, contractorGroupComboBox);
        return horizontalLayout;
    }

    private HorizontalLayout getHorizontalLayoutTwo(){
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        List<SalesChannelDto> salesChannelDtoList = salesChannelsService.getAll();
        ComboBox<SalesChannelDto> salesChaneComboBox = new ComboBox<>("Канал продаж");
        salesChaneComboBox.setItems(salesChannelDtoList);
        salesChaneComboBox.setItemLabelGenerator(SalesChannelDto::getName);
        horizontalLayout.add(salesChaneComboBox);
        return horizontalLayout;
    }
}
