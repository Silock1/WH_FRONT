package com.warehouse_accounting.components.sales.forms.order.components;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.warehouse_accounting.models.dto.*;
import com.warehouse_accounting.services.interfaces.*;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

// Связанные дтошки InvoiceDto, InvoiceProductDto, ContractorDto, ProductDto, Companydto, AddressDto(нет её)

// todo: Баг с начальным заполнением поля даты отгрузки: не устанавливается текущее значение.

// todo: надо добавить такие контролы к полям
//      new Icon(VaadinIcon.PENCIL); - создание нового покупателя, организации и т.п. (организация, контрагент, склад)
//      new Icon(VaadinIcon.PLUS); - добавить к списку (канал продаж, договор, проект)
//      реализовать создание договора (доступно только поле выбора контрагента) (contract, Контрагенты -- Договоры)
//      реализовать создание проекта
//      реализовать форму редактирования адреса и само редактирование. Поле адреса доставки подсказывает адреса.
//      создать форму канала продаж (канал продаж +)

// todo: поля Организация и Контрагент должны быть заполнены для выполнения действий
// todo: поле "Органицация" должно заполняться текущим пользователем?

// todo: поле рублёвого баланса должно быть изначально скрыто (dummy тоже придётся скрывать)

@Log4j2
@CssImport("./css/order_details.css")
public class OrderDetails extends HorizontalLayout {
    private final CompanyService companyService;
    private final ContractorService contractorService;
    private final WarehouseService warehouseService;
    private final ContractService contractService;
    private final ProjectService projectService;
    private final InvoiceDto invoice;
    private List<CompanyDto> companies;
    private List<ContractorDto> contractors;

    public OrderDetails(CompanyService companyService, ContractorService contractorService,
                        WarehouseService warehouseService, ContractService contractService,
                        ProjectService projectService,  InvoiceDto invoiceDto) throws IOException {

        this.companyService = companyService;
        this.contractorService = contractorService;
        this.warehouseService = warehouseService;
        this.contractService = contractService;
        this.projectService = projectService;
        this.invoice = invoiceDto;

        contractors = contractorService.getAll();
        companies = companyService.getAll();

        Span dummy = new Span(".");
        dummy.setClassName("dtb-empty");

        VerticalLayout leftCaptions = new VerticalLayout();
        leftCaptions.add(
                new Span("Организация"),
                new Span("Контрагент"),
                dummy,
                new Span("План. дата отгрузки"),
                new Span("Канал продаж")
        );

        ComboBox<CompanyDto> company = new ComboBox<>();
        company.setClearButtonVisible(true);
        company.setItems(companies);
        company.setItemLabelGenerator(CompanyDto::getName);
        company.setRequired(true);
        company.addValueChangeListener(event -> {
            CompanyDto companyDto = event.getValue();
            invoice.setCompanyId(companyDto.getId());
            invoice.setCompanyName(companyDto.getName());
        });

        ComboBox<ContractDto> contract = new ComboBox<>();
        contract.setEnabled(false);
        contract.setClearButtonVisible(true);
        List<ContractDto> contracts = contractService.getAll();
        contract.setItems(contracts);
        contract.setItemLabelGenerator(ContractDto::getNumber);
        contract.addValueChangeListener(event -> {
            contract.setEnabled(true);
            ContractDto contractDto = event.getValue();
            invoice.setContractId(contractDto.getId());
            invoice.setContractNumber(contractDto.getNumber());
            invoice.setContractDate(contractDto.getContractDate());
        });

        ComboBox<ContractorDto> agent = new ComboBox<>();
        agent.setClearButtonVisible(true);
        agent.setItems(contractors);
        agent.setItemLabelGenerator(ContractorDto::getName);
        agent.setRequired(true);
        agent.addValueChangeListener(event -> {
            contract.setEnabled(true);
            ContractorDto contractorDto = event.getValue();
            invoice.setContractorId(contractorDto.getId());
            invoice.setContractorName(contractorDto.getName());
        });

        Label balance = new Label("Баланс: 0,00 руб"); // todo: это должно быть изначально скрыто

        DatePicker unloadDate = new DatePicker(LocalDate.now());
        invoice.setContractDate(LocalDate.now());
        unloadDate.addValueChangeListener(event -> invoice.setContractDate(event.getValue()));

        ComboBox<String> channel = new ComboBox<>(); // todo: нет такого поля в InvoiceDto

        VerticalLayout leftFields = new VerticalLayout(
                new HorizontalLayout(company),
                new HorizontalLayout(agent),
                new HorizontalLayout(balance),
                new HorizontalLayout(unloadDate),
                new HorizontalLayout(channel)
        );

        VerticalLayout rightCaptions = new VerticalLayout();
        rightCaptions.add(
                new Span("Склад"),
                new Span("Договор"),
                new Span("Проект")
        );

        ComboBox<WarehouseDto> warehouse = new ComboBox<>();
        warehouse.setClearButtonVisible(true);
        List<WarehouseDto> warehouses = warehouseService.getAll();
        warehouse.setItems(warehouses);
        warehouse.setItemLabelGenerator(WarehouseDto::getName);
        warehouse.addValueChangeListener(event -> {
            WarehouseDto warehouseDto = event.getValue();
            invoice.setWarehouseId(warehouseDto.getId());
            invoice.setWarehouseName(warehouseDto.getName());
        });

        ComboBox<ProjectDto> project = new ComboBox<>();
        project.setClearButtonVisible(true);
        List<ProjectDto> projects = projectService.getAll();
        project.setItems(projects);
        project.setItemLabelGenerator(ProjectDto::getName);
        project.addValueChangeListener(event -> {
            ProjectDto projectDto = event.getValue();
            invoice.setProjectId(projectDto.getId());
            invoice.setProjectName(projectDto.getName());
        });

        VerticalLayout rightFields = new VerticalLayout();
        rightFields.add(warehouse, contract, project);

        HorizontalLayout address = new HorizontalLayout(new VerticalLayout(new Span("address stub")), new VerticalLayout(new TextArea()));

        add(new HorizontalLayout(leftCaptions, leftFields), new HorizontalLayout(rightCaptions, rightFields), address);
    }

    public InvoiceDto getInvoice() {
        return invoice;
    }
}
