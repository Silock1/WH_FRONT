package com.warehouse_accounting.components.contragents.form;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.contragents.ContragentsList;
import com.warehouse_accounting.components.contragents.form.form_edit.Events;
import com.warehouse_accounting.models.dto.AddressDto;
import com.warehouse_accounting.models.dto.BankAccountDto;
import com.warehouse_accounting.models.dto.ContractorDto;
import com.warehouse_accounting.models.dto.ContractorFaceContactDto;
import com.warehouse_accounting.models.dto.ContractorGroupDto;
import com.warehouse_accounting.models.dto.LegalDetailDto;
import com.warehouse_accounting.models.dto.TypeOfContractorDto;
import com.warehouse_accounting.models.dto.TypeOfPriceDto;
import com.warehouse_accounting.services.interfaces.BankAccountService;
import com.warehouse_accounting.services.interfaces.CallService;
import com.warehouse_accounting.services.interfaces.ContractorGroupService;
import com.warehouse_accounting.services.interfaces.ContractorService;
import com.warehouse_accounting.services.interfaces.DadataService;
import com.warehouse_accounting.services.interfaces.LegalDetailService;
import com.warehouse_accounting.services.interfaces.TypeOfContractorService;
import com.warehouse_accounting.services.interfaces.TypeOfPriceService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@SpringComponent
@UIScope
public class FormEditCotragent extends VerticalLayout {
    private final ContractorService contractorService;

    private LegalDetailService legalDetailService;

    private final ContractorGroupService contractorGroupService;

    private final BankAccountService bankAccountService;

    private final TypeOfPriceService typeOfPriceService;

    private final TypeOfContractorService typeOfContractorService;

    private final transient CallService callService;

    private ContragentsList parent;

    private final ContractorDto contractorDto = new ContractorDto();

    private final transient DadataService dadata;

    private boolean newForm = false;

    //Поля для правого блока
    private Events events;

    private Button edit = new Button("Сохранить");
    private Button close = new Button("Закрыть");

    private transient List<FormForFaceContactInner> formsFacesContact;

    private final Binder<BankAccountDto> bankAccountDtoBinder = new Binder<>(BankAccountDto.class);

    private final Binder<ContractorDto> contractorDtoBinder = new Binder<>(ContractorDto.class);

    private final Binder<LegalDetailDto> legalDetailDtoBinder = new Binder<>(LegalDetailDto.class);

    private final Binder<AddressDto> addressDtoBinder = new Binder<>(AddressDto.class);

    private final Binder<List<TypeOfPriceDto>> typeOfPriceDtoBinder = new Binder<>();

    private final Binder<List<TypeOfContractorDto>> typeOfContractorDtoBinder = new Binder<>();

    private final Binder<List<ContractorGroupDto>> contractorGroupDtoBinder = new Binder<>();

    public FormEditCotragent(ContractorService contractorService,
                             ContractorGroupService contractorGroupService,
                             DadataService dadata,
                             BankAccountService bankAccountService,
                             TypeOfPriceService typeOfPriceService,
                             TypeOfContractorService typeOfContractorService,
                             LegalDetailService legalDetailService,
                             CallService callService) {
        this.contractorService = contractorService;
        this.contractorGroupService = contractorGroupService;
        this.dadata = dadata;
        this.bankAccountService = bankAccountService;
        this.typeOfPriceService = typeOfPriceService;
        this.typeOfContractorService = typeOfContractorService;
        this.legalDetailService = legalDetailService;
        this.callService = callService;
    }


    public void build(ContractorDto contractorDto) {
        removeAll();
        newForm = false;
        contractorDtoBinder.readBean(contractorDto);
        add(createButton(contractorDto),
                getNameContragent(contractorDto),
                groupBlockLayout(contractorDto));
    }


    public void build() {
        removeAll();
        newForm = true;
        add(createButton(contractorDto),
                getNameContragent(contractorDto),
                groupBlockLayout(contractorDto));
    }


    private HorizontalLayout createButton(ContractorDto contractorDto) {
        HorizontalLayout button = new HorizontalLayout();
        button.setAlignItems(Alignment.CENTER);
        String typeForm;
        typeForm = "Сохранить";
        edit = new Button(typeForm);
        edit.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        edit.addClickListener(e -> {

            // Получение контактов аккаунттов или изменений в нём.
//                ContractorFaceContactDto contact;
//                for (FormForFaceContactInner form3 : formsFacesContact) {
//                    contact = form3.getContactFace();
//                    if (!form3.isDeleted() && form3.isNewFaceContact()) {
//                        contractorDto.getContacts().add(contact);
//                    }
//                    if (!form3.isDeleted() && !form3.isNewFaceContact()) {
//                        for (ContractorFaceContactDto contactOld : contractorDto.getContacts()) {
//                            if (Objects.equals(contactOld.getId(), contact.getId())) {
//                                contactOld.setAllNames(contact.getAllNames());
//                                contactOld.setPosition(contact.getPosition());
//                                contactOld.setPhone(contactOld.getPhone());
//                                contactOld.setEmail(contact.getEmail());
//                                contactOld.setComment(contact.getComment());
//                            }
//                        }
//                    }
//                }

            if (newForm) {
                try {
                    contractorDtoBinder.writeBean(contractorDto);
                    contractorService.create(contractorDto);
                } catch (ValidationException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                try {
                    contractorDtoBinder.writeBean(contractorDto);
                    contractorService.update(contractorDto);
                } catch (ValidationException ex) {
                    throw new RuntimeException(ex);
                }
            }

            removeAll();
            parent.showButtonEndGrid(true);
//        }

        });
        close = new Button("Закрыть");
        close.addClickListener(e1 -> {
            removeAll();
            parent.showButtonEndGrid(false);
        });
        button.add(close, edit);
        button.setAlignItems(Alignment.CENTER);
        return button;
    }

    private HorizontalLayout getNameContragent(ContractorDto contractorDto) {
        HorizontalLayout nameContragentLayout = new HorizontalLayout();
        TextField name = new TextField("Наименование");
        name.setMinWidth("300px");
        contractorDtoBinder.forField(name).asRequired("Наименование не должно быть пустым!")
                .bind(ContractorDto::getName, ContractorDto::setName);
        contractorDtoBinder.readBean(contractorDto);

        nameContragentLayout.add(name);

        return nameContragentLayout;
    }

    private VerticalLayout leftGroupFormLayout(ContractorDto contractorDto) {
        VerticalLayout leftLayout = new VerticalLayout();
        leftLayout.add(getContragentAccordion(contractorDto));
        if (!newForm) {
            leftLayout.add(getLegalDetailAccordion(contractorDto));
            leftLayout.add(getSalasEndPriceAccordion(contractorDto));
            leftLayout.add(getAccessAccordion());
        }
        leftLayout.setWidth("450px");
        return leftLayout;
    }

    private VerticalLayout rightGroupButtonLayout() {
        VerticalLayout rightLayout = new VerticalLayout();
        rightLayout.setWidth("500px");
        final String eventsName = "События";
        final String tasksName = "Задачи";
        final String documentsName = "Документы";
        final String filesName = "Файлы";
        final String indicatorsName = "Показатели";
        Tab details = new Tab(eventsName);
        Tab payment = new Tab(tasksName);
        Tab shipping = new Tab(documentsName);
        Tab files = new Tab(filesName);
        Tab indicators = new Tab(indicatorsName);
        Tabs tabs = new Tabs(details, payment, shipping, files, indicators);
        Div tabContent = new Div();
        tabContent.setSizeFull();
        tabs.addSelectedChangeListener(event -> {
            switch (event.getSelectedTab().getLabel()) {
                case eventsName:
                    tabContent.removeAll();
                    tabContent.add(events);
                    break;
                case tasksName:
                    tabContent.removeAll();
                    tabContent.add(new Span(tasksName));
                    break;
                case documentsName:
                    tabContent.removeAll();
                    tabContent.add(new Span(documentsName));
                    break;
                case filesName:
                    tabContent.removeAll();
                    tabContent.add(new Span(filesName));
                    break;
                case indicatorsName:
                    tabContent.removeAll();
                    tabContent.add(new Span(indicatorsName));
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + event.getSelectedTab().getLabel());
            }
        });
        rightLayout.add(tabs, tabContent);
        return rightLayout;
    }

    private HorizontalLayout groupBlockLayout(ContractorDto contractorDto) {
        HorizontalLayout blockLayout = new HorizontalLayout();
        blockLayout.add(leftGroupFormLayout(contractorDto), rightGroupButtonLayout());
        return blockLayout;
    }

    //Блок о контрагенте
    private AccordionPanel getContragentAccordion(ContractorDto contractorDto) {

        ComboBox<String> status = new ComboBox<>();
        ComboBox<ContractorGroupDto> group = new ComboBox<>();
        TextField phone = new TextField();
        TextField fax = new TextField();
        TextField email = new TextField();
        TextField comment = new TextField();
        TextField code = new TextField();
        TextField outerCode = new TextField();

        status.setItems("Новый", "Выслано предложение", "Переговоры", "Сделка заключена", "Сделка не заключена");
        status.setValue("Новый");
        contractorDtoBinder.bind(status, ContractorDto::getStatus, ContractorDto::setStatus);

        group.setItems(contractorGroupService.getAll());
        group.setItemLabelGenerator(ContractorGroupDto::getName);
        contractorDtoBinder.forField(group).asRequired("Укажите группу")
                .bind(ContractorDto::getContractorGroup, ContractorDto::setContractorGroup);
        group.setValue(contractorGroupService.getById(1L));

        contractorDtoBinder.forField(phone).bind(ContractorDto::getPhone, ContractorDto::setPhone);
        contractorDtoBinder.forField(fax).bind(ContractorDto::getFax, ContractorDto::setFax);
        contractorDtoBinder.forField(email).bind(ContractorDto::getEmail, ContractorDto::setEmail);

        AddressDto addressDto = contractorDto.getAddress();

        TextField cityName = new TextField();
        TextField streetName = new TextField();
        TextField buildingName = new TextField();
        TextField office = new TextField();
        TextField other = new TextField();


        addressDtoBinder.forField(cityName).bind(AddressDto::getCityName, AddressDto::setCityName);
        addressDtoBinder.forField(streetName).bind(AddressDto::getStreetName, AddressDto::setStreetName);
        addressDtoBinder.forField(buildingName).bind(AddressDto::getBuildingName, AddressDto::setBuildingName);
        addressDtoBinder.forField(office).bind(AddressDto::getOffice, AddressDto::setOffice);
        addressDtoBinder.forField(other).bind(AddressDto::getOther, AddressDto::setOther);


        contractorDtoBinder.forField(comment).bind(ContractorDto::getComment, ContractorDto::setComment);
        contractorDtoBinder.forField(code).bind(ContractorDto::getCode, ContractorDto::setCode);
        contractorDtoBinder.forField(outerCode).bind(ContractorDto::getOuterCode, ContractorDto::setOuterCode);

        if (newForm) {
            AddressDto addressDto1 = new AddressDto();
            addressDtoBinder.readBean(addressDto1);
            LegalDetailDto legalDetailDto = new LegalDetailDto();
            legalDetailDtoBinder.readBean(legalDetailDto);
            contractorDto.setAddress(addressDto1);
            contractorDto.setLegalDetail(legalDetailDto);
        }

        contractorDtoBinder.readBean(contractorDto);

        if (!newForm) {
            addressDtoBinder.readBean(addressDto);
        }


        Button buttonToAddActualAddress = new Button("Обновить адрес");
        buttonToAddActualAddress.setDisableOnClick(false);
        buttonToAddActualAddress.addClickListener(e -> {
            try {
                addressDtoBinder.writeBean(addressDto);
            } catch (ValidationException ex) {
                throw new RuntimeException(ex);
            }
            contractorService.update(contractorDto);
        });


        FormLayout form = new FormLayout();
        form.addFormItem(status, "Статус");
        form.addFormItem(group, "Группы");
        form.addFormItem(phone, "Телефон");
        form.addFormItem(fax, "Факс");
        form.addFormItem(email, "Электронная почта");

        if (!newForm) {
            form.addFormItem(cityName, "Город");
            form.addFormItem(streetName, "Улица");
            form.addFormItem(buildingName, "Дом");
            form.addFormItem(office, "Квартира/Офис");
            form.addFormItem(other, "Другое");
            form.add(buttonToAddActualAddress);
        }

        form.addFormItem(comment, "Комментарий");
        form.addFormItem(code, "Код");
        form.addFormItem(outerCode, "Внешний код");

        Accordion accordion = new Accordion();
        AccordionPanel aboutContractor = accordion.add("O контрагенте", form);
        aboutContractor.addThemeVariants(DetailsVariant.FILLED);
        aboutContractor.setOpened(true);
        return aboutContractor;

    }

    // Блок Контакты
    private AccordionPanel getFaceContactAccordion() {
        formsFacesContact = new ArrayList<>();
        VerticalLayout faceContactsSpace = new VerticalLayout();
        if (contractorDto.getContacts() != null) {
            for (ContractorFaceContactDto contact : contractorDto.getContacts()) {
                FormForFaceContactInner form = new FormForFaceContactInner();
                faceContactsSpace.add(form.getFormForFaceContact(contact));
                formsFacesContact.add(form);
            }
        }
        Button addNewContBtn = new Button("Добавить контакт");
        addNewContBtn.addClickListener(e -> {
            FormForFaceContactInner form = new FormForFaceContactInner();
            faceContactsSpace.add(form.getFormForFaceContact(null));
            formsFacesContact.add(form);
        });
        faceContactsSpace.add(addNewContBtn);
        Accordion accordion = new Accordion();
        AccordionPanel faceContacts = accordion.add("Контактные лица", faceContactsSpace);
        faceContacts.addThemeVariants(DetailsVariant.FILLED);
        faceContacts.setOpened(true);
        return faceContacts;
    }

    //<Блок реквизиты
    private AccordionPanel getLegalDetailAccordion(ContractorDto contractorDto) {

        LegalDetailDto legalDetailDto = contractorDto.getLegalDetail();

        ComboBox<TypeOfContractorDto> typeOfContractorDtoComboBox = new ComboBox<>();
        typeOfContractorDtoComboBox.setWidth("350px");
        typeOfContractorDtoComboBox.setItems(typeOfContractorService.getAll());
        typeOfContractorDtoComboBox.setItemLabelGenerator(TypeOfContractorDto::getName);
        typeOfContractorDtoComboBox.setRequired(true);

        typeOfContractorDtoComboBox.setItems(typeOfContractorService.getAll());
        typeOfContractorDtoComboBox.setValue(typeOfContractorService.getById(1L));
        legalDetailDtoBinder.forField(typeOfContractorDtoComboBox)
                .bind(LegalDetailDto::getTypeOfContractor, LegalDetailDto::setTypeOfContractor);

        TextField inn = new TextField();
        TextField fullName = new TextField();
        TextField kpp = new TextField();
        TextField ogrnip = new TextField();
        TextField okpo = new TextField();

        legalDetailDtoBinder.forField(inn).bind(LegalDetailDto::getInn, LegalDetailDto::setInn);
        legalDetailDtoBinder.forField(fullName).asRequired("Укажите полное наименование")
                .bind(LegalDetailDto::getFullName, LegalDetailDto::setFullName);
        legalDetailDtoBinder.forField(kpp).bind(LegalDetailDto::getKpp, LegalDetailDto::setKpp);
        legalDetailDtoBinder.forField(ogrnip).bind(LegalDetailDto::getOgrn, LegalDetailDto::setOgrn);
        legalDetailDtoBinder.forField(okpo).bind(LegalDetailDto::getOkpo, LegalDetailDto::setOkpo);


        legalDetailDtoBinder.readBean(legalDetailDto);


        FormLayout formLayout = new FormLayout();
        formLayout.addFormItem(typeOfContractorDtoComboBox, "Тип контрагента");
        formLayout.addFormItem(inn, "ИНН");
        formLayout.addFormItem(fullName, "Полное наименование");
        formLayout.addFormItem(kpp, "КПП");
        formLayout.addFormItem(ogrnip, "ОГРН");
        formLayout.addFormItem(okpo, "ОКПО");

        Button buttonToAddLegalDetail = new Button("Обновить реквизиты к контрагенту");

        buttonToAddLegalDetail.setDisableOnClick(false);

        buttonToAddLegalDetail.addClickListener(e -> {
            try {
                legalDetailDtoBinder.writeBean(legalDetailDto);
            } catch (ValidationException ex) {
                throw new RuntimeException(ex);
            }
            contractorService.update(contractorDto);
        });


        Accordion accordion = new Accordion();
        AccordionPanel legailDetails = accordion.add("Реквизиты", formLayout);
        legailDetails.addContent(buttonToAddLegalDetail);
        legailDetails.addContent(getBankAccountSpace(contractorDto));
        legailDetails.addThemeVariants(DetailsVariant.FILLED);
        legailDetails.setOpened(true);
        return legailDetails;

    }

    // Блок Скидки и цены
    private AccordionPanel getSalasEndPriceAccordion(ContractorDto contractorDto) {

        FormLayout formSalePrice = new FormLayout();

        ComboBox<TypeOfPriceDto> typeOfPriceDtoComboBox = new ComboBox<>();
        typeOfPriceDtoComboBox.setRequired(true);
        typeOfPriceDtoComboBox.setItems(typeOfPriceService.getAll());
        typeOfPriceDtoComboBox.setItemLabelGenerator(TypeOfPriceDto::getName);
        contractorDtoBinder.forField(typeOfPriceDtoComboBox)
                .bind(ContractorDto::getTypeOfPrice, ContractorDto::setTypeOfPrice);

        TextField numberDiscountCard = new TextField();
        contractorDtoBinder.forField(numberDiscountCard)
                .bind(ContractorDto::getNumberDiscountCard, ContractorDto::setNumberDiscountCard);

        contractorDtoBinder.readBean(contractorDto);

        formSalePrice.addFormItem(typeOfPriceDtoComboBox, "Цены");
        formSalePrice.addFormItem(numberDiscountCard, "Номер диск. карта");
        formSalePrice.setWidth("400px");
        Accordion accordion = new Accordion();
        AccordionPanel accordionPanel = accordion.add("Скидки и цены", formSalePrice);
        accordionPanel.setOpened(true);
        accordionPanel.addThemeVariants(DetailsVariant.FILLED);
        return accordionPanel;

    }

    //БЛок Доступ
    private AccordionPanel getAccessAccordion() {
        HorizontalLayout layout = new HorizontalLayout();
        Text text = new Text("Тут пока не готово");
        layout.add(text);
        Accordion accordion = new Accordion();
        AccordionPanel accordionPanel = accordion.add("Доступ", layout);
        accordionPanel.addThemeVariants(DetailsVariant.FILLED);
        return accordionPanel;
    }

    // Формы для банк Аккаунтов
    private VerticalLayout getBankAccountSpace(ContractorDto contractorDto) {

        VerticalLayout bankAccountSpace = new VerticalLayout();

        Button addNewBankAcc = new Button("+ Расчетный счет");

        addNewBankAcc.addClickListener(e -> {
            try {
                bankAccountSpace.add(getFormForBankAccount(contractorDto));
            } catch (ValidationException ex) {
                throw new RuntimeException(ex);
            }
        });

        bankAccountSpace.add(addNewBankAcc);


        List<BankAccountDto> bankAccountDtoList = bankAccountService.getAll();

        contractorDtoBinder.readBean(contractorDto);

        List<BankAccountDto> filteredList = bankAccountDtoList.stream()
                .filter(bankAccountDto1 -> {
                    if (bankAccountDto1.getContractor() != null &&
                            Objects.equals(contractorDto.getId(), bankAccountDto1.getContractor().getId())) {
                        try {
                            bankAccountSpace
                                    .add(getFormForAlreadyExistsBankAccounts(contractorDto, bankAccountDto1));
                            return true;
                        } catch (ValidationException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    return false;
                })
                .collect(Collectors.toList());


        return bankAccountSpace;
    }

    public VerticalLayout getFormForBankAccount(ContractorDto contractorDto)
            throws ValidationException {
        VerticalLayout main = new VerticalLayout();

        TextField rcbic = new TextField();
        TextField bank = new TextField();
        TextField bankAddress = new TextField();
        TextField corespondentAccount = new TextField();
        TextField account = new TextField();
        Checkbox mainAccount = new Checkbox();
        TextField sortNumber = new TextField();

        Binder<BankAccountDto> bankAccountDtoBinder2 = new Binder<>(BankAccountDto.class);

        BankAccountDto bankAccountDto = new BankAccountDto();
        bankAccountDto.setContractor(contractorDto);


        bankAccountDtoBinder2.forField(rcbic).bind(BankAccountDto::getRcbic, BankAccountDto::setRcbic);

        bankAccountDtoBinder2.forField(bank).bind(BankAccountDto::getBank, BankAccountDto::setBank);

        bankAccountDtoBinder2.forField(bankAddress).bind(BankAccountDto::getBankAddress, BankAccountDto::setBankAddress);

        bankAccountDtoBinder2.forField(corespondentAccount)
                .bind(BankAccountDto::getCorrespondentAccount, BankAccountDto::setCorrespondentAccount);

        bankAccountDtoBinder2.forField(account).asRequired("Расчетный счёт не может быть пустым")
                .bind(BankAccountDto::getAccount, BankAccountDto::setAccount);

        bankAccountDtoBinder2.forField(sortNumber).bind(BankAccountDto::getSortNumber, BankAccountDto::setSortNumber);

        bankAccountDtoBinder2.bind(mainAccount, BankAccountDto::getMainAccount, BankAccountDto::setMainAccount);


        bankAccountDtoBinder2.readBean(bankAccountDto);


        FormLayout formLayout = new FormLayout();
        formLayout.addFormItem(rcbic, "БИК");
        formLayout.addFormItem(bank, "Банк");
        formLayout.addFormItem(bankAddress, "Адрес");
        formLayout.addFormItem(corespondentAccount, "Kopp. счет");
        formLayout.addFormItem(account, "Расчетный счет");
        formLayout.addFormItem(sortNumber, "Текущий остаток");
        formLayout.addFormItem(mainAccount, "Основной счет");


        Button button = new Button("Закрыть");
        button.addClickListener(e -> {
            main.setVisible(false);
        });


        Button button1 = new Button("Привязать банк. счет");
        button1.setDisableOnClick(true);
        button1.addClickListener(e -> {
            try {
                bankAccountDtoBinder2.writeBean(bankAccountDto);
            } catch (ValidationException ex) {
                throw new RuntimeException(ex);
            }
            bankAccountService.create(bankAccountDto);
            main.setVisible(true);
        });

        main.add(button, formLayout, button1);

        return main;
    }

    public VerticalLayout getFormForAlreadyExistsBankAccounts(ContractorDto contractorDto, BankAccountDto bankAccountDto)
            throws ValidationException {
        VerticalLayout main = new VerticalLayout();

        TextField rcbic = new TextField();
        TextField bank = new TextField();
        TextField bankAddress = new TextField();
        TextField corespondentAccount = new TextField();
        TextField account = new TextField();
        Checkbox mainAccount = new Checkbox();
        TextField sortNumber = new TextField();

        Binder<BankAccountDto> bankAccountDtoBinder1 = new Binder<>(BankAccountDto.class);

        bankAccountDto.setContractor(contractorDto);

        bankAccountDtoBinder1.forField(rcbic).bind(BankAccountDto::getRcbic, BankAccountDto::setRcbic);

        bankAccountDtoBinder1.forField(bank).bind(BankAccountDto::getBank, BankAccountDto::setBank);

        bankAccountDtoBinder1.forField(bankAddress).bind(BankAccountDto::getBankAddress, BankAccountDto::setBankAddress);

        bankAccountDtoBinder1.forField(corespondentAccount)
                .bind(BankAccountDto::getCorrespondentAccount, BankAccountDto::setCorrespondentAccount);

        bankAccountDtoBinder1.forField(account).asRequired("Расчетный счёт не может быть пустым")
                .bind(BankAccountDto::getAccount, BankAccountDto::setAccount);

        bankAccountDtoBinder1.forField(sortNumber).bind(BankAccountDto::getSortNumber, BankAccountDto::setSortNumber);

        bankAccountDtoBinder1.bind(mainAccount, BankAccountDto::getMainAccount, BankAccountDto::setMainAccount);


        bankAccountDtoBinder1.readBean(bankAccountDto);


        FormLayout formLayout = new FormLayout();
        formLayout.addFormItem(rcbic, "БИК");
        formLayout.addFormItem(bank, "Банк");
        formLayout.addFormItem(bankAddress, "Адрес");
        formLayout.addFormItem(corespondentAccount, "Kopp. счет");
        formLayout.addFormItem(account, "Расчетный счет");
        formLayout.addFormItem(sortNumber, "Текущий остаток");
        formLayout.addFormItem(mainAccount, "Основной счет");


        Button button = new Button("Удалить банк. счёт");
        button.addClickListener(e -> {
            bankAccountService.deleteById(bankAccountDto.getId());
            main.setVisible(false);
        });


        Button button1 = new Button("Обновить банк. счет");
        button1.setDisableOnClick(false);
        button1.addClickListener(e -> {
            try {
                bankAccountDtoBinder1.writeBean(bankAccountDto);
            } catch (ValidationException ex) {
                throw new RuntimeException(ex);
            }
            bankAccountService.update(bankAccountDto);
            main.setVisible(true);
        });

        main.add(button, formLayout, button1);

        return main;
    }

    public void setParent(ContragentsList parent) {
        this.parent = parent;
    }

}
