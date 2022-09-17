package com.warehouse_accounting.components.contragents.form;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.contragents.ContractsOrder;
import com.warehouse_accounting.models.dto.CompanyDto;
import com.warehouse_accounting.models.dto.ContractDto;
import com.warehouse_accounting.models.dto.ContractorDto;
import com.warehouse_accounting.models.dto.ProductionTasksDto;
import com.warehouse_accounting.services.interfaces.CompanyService;
import com.warehouse_accounting.services.interfaces.ContractService;
import com.warehouse_accounting.services.interfaces.ContractorService;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;

@SpringComponent
@UIScope
public class FormForContract extends VerticalLayout {
    private ContractsOrder parent;

    private final ContractDto contractDto = new ContractDto();

    private Boolean newForm;

    private final ContractService contractService;

    private ContractorService contractorService;

    private CompanyService companyService;

    private final Binder<ContractDto> contractDtoBinder = new Binder<>(ContractDto.class);

    public FormForContract(ContractService contractService,
                           ContractorService contractorService,
                           CompanyService companyService) {
        this.contractService = contractService;
        this.contractorService = contractorService;
        this.companyService = companyService;
    }

    public void build(ContractDto contractDto) {
        removeAll();
        newForm = false;
        add(createButton(contractDto), mainForm(contractDto));
    }

    public void build() {
        removeAll();
        newForm = true;
        add(createButton(contractDto), mainForm(contractDto));
    }

    private HorizontalLayout createButton(ContractDto contractDto) {
        HorizontalLayout layoutForButtons = new HorizontalLayout();
        layoutForButtons.setAlignItems(Alignment.CENTER);
        String typeForm = "Сохранить";

        Button edit = new Button(typeForm);
        edit.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        edit.addClickListener(e -> {

            if (newForm) {
                try {
                    contractDto.setContractDate(null);
                    contractDtoBinder.writeBean(contractDto);
                    contractService.create(contractDto);
                } catch (ValidationException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                try {
                    contractDtoBinder.writeBean(contractDto);
                    contractService.update(contractDto);
                } catch (ValidationException ex) {
                    throw new RuntimeException(ex);
                }
            }

            removeAll();
            parent.showButtonEndGrid(true);

        });

        Button close = new Button("Закрыть");
        close.addClickListener(e1 -> {
            removeAll();
            parent.showButtonEndGrid(false);
        });

        layoutForButtons.add(edit, close);
        layoutForButtons.setAlignItems(Alignment.CENTER);
        return layoutForButtons;

    }

    public FormLayout mainForm(ContractDto contractDto) {

        HorizontalLayout layoutForNumberAndDate = new HorizontalLayout();
        Label label1 = new Label("Договор №");
        TextField number = new TextField();
        Label label2 = new Label("от");
        DateTimePicker datePickerField = new DateTimePicker();
        datePickerField.setValue(LocalDateTime.now());

        contractDtoBinder.forField(number).bind(ContractDto::getNumber, ContractDto::setNumber);
        contractDtoBinder.forField(datePickerField).bind(ContractDto::getContractDate, ContractDto::setContractDate);

        layoutForNumberAndDate.add(label1, number, label2, datePickerField);


        HorizontalLayout layoutForCompanyAndContractor = new HorizontalLayout();

        Div div = new Div();
        div.setWidth("130px");

        Div div1 = new Div();
        div1.setWidth("300px");

        Label label3 = new Label("Организация");
        ComboBox<CompanyDto> companyDtoComboBox = new ComboBox<>();
        try {
            companyDtoComboBox.setItems(companyService.getAll());
            companyDtoComboBox.setItemLabelGenerator(CompanyDto::getName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Label label4 = new Label("Контрагент");
        ComboBox<ContractorDto> contractorDtoComboBox = new ComboBox<>();
        contractorDtoComboBox.setItems(contractorService.getAll());
        contractorDtoComboBox.setItemLabelGenerator(ContractorDto::getName);
        contractDtoBinder.forField(companyDtoComboBox).asRequired("Поле должно быть заполнено")
                .bind(ContractDto::getCompany, ContractDto::setCompany);
        contractDtoBinder.forField(contractorDtoComboBox).asRequired("Поле должно быть заполнено")
                .bind(ContractDto::getContractor, ContractDto::setContractor);

        layoutForCompanyAndContractor.add(div1, label3, companyDtoComboBox, div, label4, contractorDtoComboBox);


        HorizontalLayout layoutForTypeOfContract = new HorizontalLayout();
        Div emptySpaceAtType = new Div();
        Label labelForType = new Label("Тип договора");
        emptySpaceAtType.setWidth("297px");

        ComboBox<String> comboBoxForType = new ComboBox<>();
        comboBoxForType.setItems("Договор комиссии", "Договор купли-продажи");
        comboBoxForType.setValue("Договор купли-продажи");

        ComboBox<String> comboBoxForReward = new ComboBox<>();
        comboBoxForReward.setItems("Процент от суммы продажи", "Не рассчитывать");
        comboBoxForReward.setValue("Не рассчитывать");

        IntegerField percentageOfTheSaleAmount = new IntegerField();
        percentageOfTheSaleAmount.setValue(0);
        Label percent = new Label("%");

        comboBoxForReward.addValueChangeListener(event -> {
            if (Objects.equals(event.getValue(), "Не рассчитывать")) {
                layoutForTypeOfContract.remove(percentageOfTheSaleAmount, percent);
                percentageOfTheSaleAmount.setValue(0);
            } else {
                layoutForTypeOfContract.add(percentageOfTheSaleAmount, percent);
            }
        });

        Label labelForReward = new Label("Вознаграждение");

        Div divForTypeOfContract = new Div();
        divForTypeOfContract.setWidth("100px");

        comboBoxForType.addValueChangeListener(event -> {
            if (Objects.equals(event.getValue(), "Договор комиссии")) {
                comboBoxForReward.setValue("Процент от суммы продажи");
                layoutForTypeOfContract.add(divForTypeOfContract, labelForReward,
                        comboBoxForReward, percentageOfTheSaleAmount, percent);
            } else {
                layoutForTypeOfContract.remove(divForTypeOfContract, labelForReward,
                        comboBoxForReward, percentageOfTheSaleAmount, percent);
            }
        });

        contractDtoBinder.forField(comboBoxForType).bind(ContractDto::getTypeOfContract, ContractDto::setTypeOfContract);
        contractDtoBinder.forField(comboBoxForReward).bind(ContractDto::getReward, ContractDto::setReward);
        contractDtoBinder.forField(percentageOfTheSaleAmount)
                .bind(ContractDto::getPercentageOfTheSaleAmount, ContractDto::setPercentageOfTheSaleAmount);

        layoutForTypeOfContract.add(emptySpaceAtType, labelForType, comboBoxForType);


        TextField code = new TextField();
        Label forCode = new Label("Код");
        BigDecimalField amount = new BigDecimalField();
        amount.setValue(BigDecimal.ZERO);
        Label forAmount = new Label("Сумма договора");
        TextArea comment = new TextArea();
        comment.getStyle().set("resize", "vertical");
        comment.getStyle().set("overflow", "auto");
        Label forComment = new Label("Комментарий");
        Div div2 = new Div();
        div2.setWidth("353px");
        Div div3 = new Div();
        div3.setWidth("283px");
        Div div4 = new Div();
        div4.setWidth("300px");


        ComboBox<String> reward = new ComboBox<>();
        reward.setItems("Процент от суммы продажи", "Не рассчитывать");

        HorizontalLayout layoutForCode = new HorizontalLayout();
        HorizontalLayout layoutForAmount = new HorizontalLayout();
        HorizontalLayout layoutForComment = new HorizontalLayout();

        contractDtoBinder.forField(code).bind(ContractDto::getCode, ContractDto::setCode);
        contractDtoBinder.forField(amount).bind(ContractDto::getAmount, ContractDto::setAmount);
        contractDtoBinder.forField(comment).bind(ContractDto::getComment, ContractDto::setComment);

        if (!newForm) {
            contractDtoBinder.readBean(contractDto);
        }

        layoutForCode.add(div2, forCode, code);
        layoutForAmount.add(div3, forAmount, amount);
        layoutForComment.add(div4, forComment, comment);

        FormLayout formLayout = new FormLayout();
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0px", 1));

        formLayout.add(layoutForNumberAndDate);
        formLayout.add(layoutForCompanyAndContractor);
        formLayout.add(layoutForTypeOfContract);
        formLayout.add(layoutForCode);
        formLayout.add(layoutForAmount);
        formLayout.add(layoutForComment);

        return formLayout;
    }

    public void setParent(ContractsOrder parent) {
        this.parent = parent;
    }
}
