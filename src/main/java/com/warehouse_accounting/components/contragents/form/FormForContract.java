package com.warehouse_accounting.components.contragents.form;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
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
import com.warehouse_accounting.services.interfaces.CompanyService;
import com.warehouse_accounting.services.interfaces.ContractService;
import com.warehouse_accounting.services.interfaces.ContractorService;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@SpringComponent
@UIScope
public class FormForContract extends VerticalLayout {
    private ContractsOrder parent;

    private final ContractDto contractDto = new ContractDto();

    private Boolean newForm;

    private final ContractService contractService;

    private final ContractorService contractorService;

    private final CompanyService companyService;

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


        //меню-бар который идёт после кнопки закрыть
        MenuBar menuBar = new MenuBar();
        menuBar.addThemeVariants(MenuBarVariant.LUMO_ICON, MenuBarVariant.LUMO_CONTRAST);

        Icon caretDownEdit = new Icon(VaadinIcon.CARET_DOWN);
        caretDownEdit.setSize("13px");

        HorizontalLayout editVision = new HorizontalLayout(new Text("Изменить"), caretDownEdit);
        editVision.setSpacing(false);

        MenuItem editItem = menuBar.addItem(editVision);
        SubMenu editSubMenu = editItem.getSubMenu();
        editSubMenu.addItem("Удалить").addClickListener(event -> {
            contractDto.setEnabled(false);
            try {
                contractDtoBinder.writeBean(contractDto);
            } catch (ValidationException e) {
                throw new RuntimeException(e);
            }
            contractService.update(contractDto);
            //удаление происходит не физическое, а выставляется флаг false в столбец enabled таблицы contracts
            removeAll();
            parent.showButtonEndGrid(true);
        });
        editSubMenu.addItem("Копировать").onEnabledStateChanged(false);


        layoutForButtons.add(edit, close, menuBar);
        layoutForButtons.setAlignItems(Alignment.CENTER);
        return layoutForButtons;

    }

    public FormLayout mainForm(ContractDto contractDto) {

        //макет для номера и даты
        HorizontalLayout layoutForNumberAndDate = new HorizontalLayout();
        Label label1 = new Label("Договор №");
        TextField number = new TextField();
        Label label2 = new Label("от");
        DatePicker datePickerField = new DatePicker();
        datePickerField.setValue(LocalDate.now());

        contractDtoBinder.forField(number).bind(ContractDto::getNumber, ContractDto::setNumber);
        contractDtoBinder.forField(datePickerField).bind(ContractDto::getContractDate, ContractDto::setContractDate);

        layoutForNumberAndDate.add(label1, number, label2, datePickerField);


        //макет для контрагента и огранизации
        HorizontalLayout layoutForCompanyAndContractor = new HorizontalLayout();

        Div div = new Div();
        div.setWidth("130px");

        Div div1 = new Div();
        div1.setWidth("160px");

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


        //макет для типа договора
        HorizontalLayout layoutForTypeOfContract = new HorizontalLayout();
        Div divForType = new Div();
        Label labelForType = new Label("Тип договора");
        divForType.setWidth("157px");

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
                percentageOfTheSaleAmount.setValue(0);
                layoutForTypeOfContract.remove(percentageOfTheSaleAmount, percent);
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
                percentageOfTheSaleAmount.setValue(0); //обнуление процента если был выбран договор купли-продажи
                comboBoxForReward.setValue("Не рассчитывать"); //выставляется значение чтобы не появлялся филд для процента
                layoutForTypeOfContract.remove(divForTypeOfContract, labelForReward,
                        comboBoxForReward, percentageOfTheSaleAmount, percent);
            }
        });

        contractDtoBinder.forField(comboBoxForType).bind(ContractDto::getTypeOfContract, ContractDto::setTypeOfContract);
        contractDtoBinder.forField(comboBoxForReward).bind(ContractDto::getReward, ContractDto::setReward);
        contractDtoBinder.forField(percentageOfTheSaleAmount)
                .bind(ContractDto::getPercentageOfTheSaleAmount, ContractDto::setPercentageOfTheSaleAmount);

        layoutForTypeOfContract.add(divForType, labelForType, comboBoxForType);

        //всё что идёт после типа договора
        TextField code = new TextField();
        Label forCode = new Label("Код");
        BigDecimalField amount = new BigDecimalField();
        amount.setValue(BigDecimal.ZERO);
        Label forAmount = new Label("Сумма договора");
        TextArea comment = new TextArea();
        comment.getStyle().set("resize", "vertical");
        comment.getStyle().set("overflow", "auto");
        Label forComment = new Label("Комментарий");
        Div divForCode = new Div();
        divForCode.setWidth("213px");
        Div divForAmount = new Div();
        divForAmount.setWidth("143px");
        Div divForComment = new Div();
        divForComment.setWidth("160px");


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

        layoutForCode.add(divForCode, forCode, code);
        layoutForAmount.add(divForAmount, forAmount, amount);
        layoutForComment.add(divForComment, forComment, comment);

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
