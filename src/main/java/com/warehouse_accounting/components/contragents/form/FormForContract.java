package com.warehouse_accounting.components.contragents.form;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.contragents.ContractsOrder;
import com.warehouse_accounting.models.dto.CompanyDto;
import com.warehouse_accounting.models.dto.ContractDto;
import com.warehouse_accounting.models.dto.ContractorDto;
import com.warehouse_accounting.services.interfaces.CompanyService;
import com.warehouse_accounting.services.interfaces.ContractService;
import com.warehouse_accounting.services.interfaces.ContractorService;

import java.io.IOException;

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

        layoutForButtons.add(close, edit);
        layoutForButtons.setAlignItems(Alignment.CENTER);
        return layoutForButtons;

    }

    public FormLayout mainForm(ContractDto contractDto) {
        ComboBox<CompanyDto> companyDtoComboBox = new ComboBox<>();

        try {
            companyDtoComboBox.setItems(companyService.getAll());
            companyDtoComboBox.setItemLabelGenerator(CompanyDto::getName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        TextField code = new TextField();
        BigDecimalField amount = new BigDecimalField();
        TextField comment = new TextField();

        ComboBox<ContractorDto> contractorDtoComboBox = new ComboBox<>();
        contractorDtoComboBox.setItems(contractorService.getAll());
        contractorDtoComboBox.setItemLabelGenerator(ContractorDto::getName);

        ComboBox<String> reward = new ComboBox<>();
        reward.setItems("Процент от суммы продажи", "Не рассчитывать");


        contractDtoBinder.forField(companyDtoComboBox).bind(ContractDto::getCompany, ContractDto::setCompany);
        contractDtoBinder.forField(code).bind(ContractDto::getCode, ContractDto::setCode);
        contractDtoBinder.forField(amount).bind(ContractDto::getAmount, ContractDto::setAmount);
        contractDtoBinder.forField(comment).bind(ContractDto::getComment, ContractDto::setComment);
        contractDtoBinder.forField(contractorDtoComboBox).bind(ContractDto::getContractor, ContractDto::setContractor);
        contractDtoBinder.forField(reward).bind(ContractDto::getReward, ContractDto::setReward);

        if (!newForm) {
            contractDtoBinder.readBean(contractDto);
        }
        FormLayout formLayout = new FormLayout();
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0px", 1));

        formLayout.addFormItem(companyDtoComboBox, "Организация");
        formLayout.addFormItem(code, "Код");
        formLayout.addFormItem(amount, "Сумма договора");
        formLayout.addFormItem(comment, "Комментарий");
        formLayout.addFormItem(contractorDtoComboBox, "Контрагент");
        formLayout.addFormItem(reward, "Вознаграждение");

        return formLayout;
    }

    public void setParent(ContractsOrder parent) {
        this.parent = parent;
    }
}
