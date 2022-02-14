package com.warehouse_accounting.components.payments.filter;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.models.dto.ContractorDto;
import com.warehouse_accounting.models.dto.ContractorGroupDto;
import com.warehouse_accounting.models.dto.PaymentsDto;
import com.warehouse_accounting.models.dto.ProjectDto;
import com.warehouse_accounting.services.interfaces.ContractService;
import com.warehouse_accounting.services.interfaces.ContractorGroupService;
import com.warehouse_accounting.services.interfaces.ContractorService;
import com.warehouse_accounting.services.interfaces.PaymentsService;
import com.warehouse_accounting.services.interfaces.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;




public class PaymentsSettlementsCompanyFilter extends VerticalLayout {
//    private Grid<PaymentsDto> grid = new Grid<>(PaymentsDto.class);
        private ProjectService projectService;
        private ContractorService contractorService;
        private ContractorGroupService contractorGroupService;

    public PaymentsSettlementsCompanyFilter(ProjectService projectService, ContractorService contractorService,
                                            ContractorGroupService contractorGroupService){
        this.projectService = projectService;
        this.contractorService = contractorService;
        this.contractorGroupService = contractorGroupService;

        HorizontalLayout horizontalLayoutOne = getHorizontalLayoutOne();
        add(horizontalLayoutOne);
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

        ComboBox<String> typeSelectNull = new ComboBox<>("Показать клиентов с нулевыми оборотами", "Да", "Нет");

        List<ProjectDto> projectDtoList = projectService.getAll();
        ComboBox<ProjectDto> projectComboBox = new ComboBox<>("Проект");
        projectComboBox.setItems(projectDtoList);
        projectComboBox.setItemLabelGenerator(ProjectDto::getName);

        List<ContractorDto> contractorDtoList = contractorService.getAll();
        ComboBox<ContractorDto> contractorComboBox = new ComboBox<>("Контрагент");
        contractorComboBox.setItems(contractorDtoList);
        contractorComboBox.setItemLabelGenerator(ContractorDto::getName);

        List<ContractorGroupDto> contractorGroupDtoList = contractorGroupService.getAll();
        ComboBox<ContractorGroupDto> contractorGroupComboBox = new ComboBox<>("Группа контрагента");
        contractorGroupComboBox.setItems(contractorGroupDtoList);
        contractorGroupComboBox.setItemLabelGenerator(ContractorGroupDto::getName);

        horizontalLayout.add(find, clear, bookmarks, settingButton, periodStart, periodEnd, typeSelectNull,
                projectComboBox, contractorComboBox, contractorGroupComboBox);
        return horizontalLayout;
    }

}
