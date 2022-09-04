package com.warehouse_accounting.components.contragents.form;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.PropertyId;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToBooleanConverter;
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.address.AddressForm;
import com.warehouse_accounting.components.contragents.ContragentsList;
import com.warehouse_accounting.components.contragents.form.form_edit.Events;
import com.warehouse_accounting.models.dto.AddressDto;
import com.warehouse_accounting.models.dto.BankAccountDto;
import com.warehouse_accounting.models.dto.ContractorDto;
import com.warehouse_accounting.models.dto.ContractorFaceContactDto;
import com.warehouse_accounting.models.dto.LegalDetailDto;
import com.warehouse_accounting.models.dto.dadataDto.Example2;
import com.warehouse_accounting.services.interfaces.AddressService;
import com.warehouse_accounting.services.interfaces.BankAccountService;
import com.warehouse_accounting.services.interfaces.CallService;
import com.warehouse_accounting.services.interfaces.ContractorService;
import com.warehouse_accounting.services.interfaces.DadataService;
import com.warehouse_accounting.services.interfaces.TypeOfPriceService;
import javafx.util.converter.BooleanStringConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@SpringComponent
@UIScope
public class FormEditCotragent extends VerticalLayout {
    private final ContractorService contractorService;
    private final transient BankAccountService bankAccountService;
    private final transient TypeOfPriceService typeOfPriceService;

    private final transient CallService callService;
    private ContragentsList parent;
    private ContractorDto contractorDto;
    private final transient DadataService dadata;
    private boolean newForm = false;

    //Поля для правого блока
    private Events events;


    private Button edit = new Button("Сохранить");
    private Button close = new Button("Закрыть");
    // Поля для блока контрагент
    private TextField name;
    private ComboBox<String> status;
    private ComboBox<String> group;
    private TextField phone;
    private TextField discountCard;
    private Select<String> typeOfPrice;
    private TextField fax;
    private TextField email;
    private AddressForm address;
    private TextArea comment;
    private TextField code;
    private TextField outerCode;

    // Поля для LegalDetails
    private TextField lastName;
    private TextField firstName;
    private TextField middleName;
    private AddressForm addressLegal;
    private TextField inn;
    private TextField okpo;
    private TextField ogrnip;
    private TextField kpp;
    private TextField numberOfTheCertificate;
    private DatePicker dateOfTheCertificate;
    private Select<String> typeOfContractor;

    // Формы для множественных внутренних обьектов.
//    private transient List<FormBankAccauntInner> formsBankAccount;

    private transient List<FormForFaceContactInner> formsFacesContact;

    private final Binder<BankAccountDto> bankAccountDtoBinder = new Binder<>(BankAccountDto.class);
    private final Binder<ContractorDto> contractorDtoBinder = new Binder<>(ContractorDto.class);


    public FormEditCotragent(ContractorService contractorService,
                             DadataService dadata,
                             BankAccountService bankAccountService,
                             TypeOfPriceService typeOfPriceService,
                             CallService callService,
                             AddressForm address,
                             AddressForm addressLegal) {
        this.contractorService = contractorService;
        this.dadata = dadata;
        this.bankAccountService = bankAccountService;
        this.typeOfPriceService = typeOfPriceService;
        this.callService = callService;
        this.address = address;
        this.addressLegal = addressLegal;
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
//
//        ContractorService contractorService = new ContractorServiceImpl("/api/contractors", retrofit);
//        contractorDto = new ContractorDto();
//        contractorDto.setName("Первый");
//        contractorDto.setStatus("1");
//        contractorDto.setCode("1");
//        contractorDto.setComment("1");
//        contractorDto.setEmail("f");
//        contractorDto.setFax("f");
//        contractorDto.setOuterCode("f");
//
//
////            contractorDto.setCheckboxEnabled(checkboxEnabled.getValue());
////            contractorDto.setName(namePointSales.getValue());
////            contractorDto.setCashiers(formCashiers.getValue());
//        contractorService.create(contractorDto);


//
//        contractorDto = contractorService.getById(contractorDto.getId());
//        if(contractorDto.getBankAccountDtos() == null)contractorDto.setBankAccountDtos(new ArrayList<>());
//        if(contractorDto.getContacts() == null) contractorDto.setContacts(new ArrayList<>());
//        this.contractorDto = contractorDto;
//        this.events = new Events(callService, contractorDto);
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
            // получение Основные данные аккаунта
//            contractorDto.setName(name.getValue());
//            contractorDto.setStatus(status.getValue());
//            contractorDto.setContractorGroupName(group.getValue());
//            contractorDto.setPhone(phone.getValue());
//            contractorDto.setFax(fax.getValue());
//            contractorDto.setEmail(email.getValue());
//            contractorDto.setAddress(address.getValue());
//            contractorDto.setComment(comment.getValue());
//            contractorDto.setCode(code.getValue());

// МОЙ НОВЫЙ КОД
//            contractorDto.setNumberDiscountCard(discountCard.getValue());
//            contractorDto.setTypeOfPriceId(typeOfPrice.getValue().equals("Оптовая") ? 2L : 1L);
//            contractorDto.setTypeOfPriceName(typeOfPrice.getValue());
//            contractorDto.getTypeOfPrice().setName(typeOfPrice.getValue());
//            contractorDto.getTypeOfPrice()
//                    .setSortNumber(typeOfPrice.getValue().equals("Оптовая") ? "2" : "1");
//
//            if (outerCode.getValue().equals("")) {
//                contractorDto.setOuterCode("Generate");
//            } else {
//                contractorDto.setOuterCode(outerCode.getValue());
//            }

            // получение Данные LegalDetails
//            contractorDto.getLegalDetailDto().setLastName(lastName.getValue());
//            contractorDto.getLegalDetailDto().setFirstName(firstName.getValue());
//            contractorDto.getLegalDetailDto().setMiddleName(middleName.getValue());
//            contractorDto.getLegalDetailDto().setAddress(addressLegal.getValue());
//            contractorDto.getLegalDetailDto().setInn(inn.getValue());
//            contractorDto.getLegalDetailDto().setOkpo(okpo.getValue());
//            contractorDto.getLegalDetailDto().setOgrnip(ogrnip.getValue());
//            contractorDto.getLegalDetailDto().setKpp(kpp.getValue());
//            contractorDto.getLegalDetailDto().setNumberOfTheCertificate(numberOfTheCertificate.getValue());
//            contractorDto.getLegalDetailDto().setDateOfTheCertificate(dateOfTheCertificate.getValue());
//            contractorDto.getLegalDetailDto().setTypeOfContractorName(typeOfContractor.getValue());


            // получение Данные LegalDetails
//                contractorDto.getLegalDetailDto().setLastName(lastName.getValue());
//                contractorDto.getLegalDetailDto().setFirstName(firstName.getValue());
//                contractorDto.getLegalDetailDto().setMiddleName(middleName.getValue());
//                contractorDto.getLegalDetailDto().setAddress(addressLegal.getValue());
//                contractorDto.getLegalDetailDto().setInn(inn.getValue());
//                contractorDto.getLegalDetailDto().setOkpo(okpo.getValue());
//                contractorDto.getLegalDetailDto().setOgrnip(ogrnip.getValue());
//                contractorDto.getLegalDetailDto().setKpp(kpp.getValue());
//                contractorDto.getLegalDetailDto().setNumberOfTheCertificate(numberOfTheCertificate.getValue());
//                contractorDto.getLegalDetailDto().setDateOfTheCertificate(dateOfTheCertificate.getValue());
//                contractorDto.getLegalDetailDto().setTypeOfContractorName(typeOfContractor.getValue());


//            Retrofit retrofit1 = new Retrofit.Builder()
//                    .baseUrl("http://localhost:4446")
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//


//                BankAccountDto accountDto1;
//                for (FormBankAccauntInner form2 : formsBankAccount) {
//                    bankAccountDto = form2.getBankAccount();
//                    if (!form2.isDeleted() && form2.isNewAccount()) {
//                        contractorDto.getBankAccountDtos().add(bankAccountDto);
//                    }
//                    if (!form2.isDeleted() && !form2.isNewAccount()) {
//                        for (BankAccountDto bankAccountOld : contractorDto.getBankAccountDtos()) {
//                            if (Objects.equals(bankAccountOld.getId(), accountDto1.getId())) {
//                                bankAccountOld.setRcbic(accountDto1.getRcbic());
//                                contractorDto.setName(name.getValue());
//                                bankAccountOld.setBank(accountDto1.getBank());
//                                bankAccountOld.setAddress(accountDto1.getAddress());
//                                bankAccountOld.setCorrespondentAccount(accountDto1.getCorrespondentAccount());
//                                bankAccountOld.setAccount(accountDto1.getAccount());
//                                bankAccountOld.setMainAccount(accountDto1.getMainAccount());
//                                bankAccountOld.setSortNumber(accountDto1.getSortNumber());
//                            }
//                        }
//                    }
//                }

//            final ComboBox<ContractorDto> contractorField = new ComboBox<>("Поставщик", contractorService.getAll());
//            contractorField.setItemLabelGenerator(ContractorDto::getName);
//            contractorField.setAllowCustomValue(false);
//            contractorField.addValueChangeListener(event -> bankAccountDto.setContractorDto(event.getValue()));
//            contractorField.setValue(contractorService.getById(1L));
//            contractorField.setVisible(true);

//            bankAccountService.create(bankAccountDto);


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
                } catch (ValidationException ex) {
                    throw new RuntimeException(ex);
                }
                contractorService.create(contractorDto);
            } else {
                try {
//                    if (wantToCreateBankAccount) {
//                        bankAccountDtoBinder.writeBean(bankAccountDto);
//                        bankAccountService.create(bankAccountDto);
//                    }
//                    bankAccountDtoBinder.writeBean(bankAccountDto);
//
//                    List<BankAccountDto> bankAccountDtoList = new ArrayList<>();
//
//                    bankAccountDtoList.add(bankAccountDto);
//
//                    contractorDto.setBankAccountDtos(bankAccountDtoList);

//                    contractorDtoBinder.readBean(contractorDto);

                    contractorDtoBinder.writeBean(contractorDto);
                    contractorService.update(contractorDto);
                } catch (ValidationException ex) {
                    throw new RuntimeException(ex);
                }
            }
            //Удобно в тестах для отладки пока оставить.
            System.out.println("Отправил:" + contractorDto);

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
//        name.setValue(contractorDto.getName() == null ? " " : contractorDto.getName());


//        TextField nameField = new TextField();
//        nameField.setLabel("Наименование");
//        nameField.setMinWidth("300px");
//        productionProcessTechnologyDtoBinder
//                .forField(nameField)
//                .asRequired("Наименование не должно быть пустым!")
//                .bind(ProductionProcessTechnologyDto::getName, ProductionProcessTechnologyDto::setName);
//        nameField.setValue(productionProcessTechnologyDto.getName() == null ? "" : productionProcessTechnologyDto.getName());


//        name.setWidth("450px");

//        name.setValue(contractorDto.getName());
//        if (contractorDto.getName() != null) name.setValue(contractorDto.getName());

        nameContragentLayout.add(name);

        return nameContragentLayout;
    }

    private VerticalLayout leftGroupFormLayout(ContractorDto contractorDto) {
        VerticalLayout leftLayout = new VerticalLayout();
        leftLayout.add(getContragentAccordion(contractorDto),
//                getFaceContactAccordion(),
                getLegalDetailAccordion(contractorDto),
//                getSalasEndPriceAccordion(),
                getAccessAccordion());
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

//        FormLayout main = new FormLayout();

        ComboBox<String> status = new ComboBox<>();
        TextField phone = new TextField();
        TextField fax = new TextField();
        TextField email = new TextField();
        TextField comment = new TextField();
        TextField code = new TextField();
        TextField outerCode = new TextField();

        status.setItems("Новый", "Выслано предложение", "Переговоры", "Сделка заключена", "Сделка не заключена");
        status.setValue("Новый");
        contractorDtoBinder.bind(status, ContractorDto::getStatus, ContractorDto::setStatus);

        contractorDtoBinder.forField(phone).bind(ContractorDto::getPhone, ContractorDto::setPhone);
        phone.setValue(contractorDto.getPhone() == null ? " " : contractorDto.getPhone());

        contractorDtoBinder.forField(fax).bind(ContractorDto::getFax, ContractorDto::setFax);
        fax.setValue(contractorDto.getFax() == null ? " " : contractorDto.getFax());

        contractorDtoBinder.forField(email).bind(ContractorDto::getEmail, ContractorDto::setEmail);
        email.setValue(contractorDto.getEmail() == null ? " " : contractorDto.getEmail());

        contractorDtoBinder.forField(comment).bind(ContractorDto::getComment, ContractorDto::setComment);
        comment.setValue(contractorDto.getComment() == null ? " " : contractorDto.getComment());

        contractorDtoBinder.forField(code).bind(ContractorDto::getCode, ContractorDto::setCode);
        code.setValue(contractorDto.getCode() == null ? " " : contractorDto.getCode());

        contractorDtoBinder.forField(outerCode).bind(ContractorDto::getOuterCode, ContractorDto::setOuterCode);
        outerCode.setValue(contractorDto.getOuterCode() == null ? " " : contractorDto.getOuterCode());

        contractorDtoBinder.readBean(contractorDto);

        FormLayout form = new FormLayout();
        form.addFormItem(status, "Статус");
//        form.addFormItem(group, "Группы");
        form.addFormItem(phone, "Телефон");
        form.addFormItem(fax, "Факс");
        form.addFormItem(email, "Электронная почта");
//        form.addFormItem("Фактический адрес", address);
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
        if (contractorDto.getLegalDetailDto() == null) contractorDto.setLegalDetailDto(new LegalDetailDto());

        VerticalLayout main = new VerticalLayout();
        VerticalLayout forms = new VerticalLayout();
        // НАстроить дефолтный фокус
        typeOfContractor = new Select<>();
        typeOfContractor.setWidth("350px");
        typeOfContractor.setItems("Физическое лицо", "Юридическое лицо", "Индивидуальный предприниматель");
        typeOfContractor.setEmptySelectionCaption("Тип контрагента");

        if (contractorDto.getLegalDetailDto().getTypeOfContractorName() != null) {
            forms.add(getFormForContractorType(contractorDto.getLegalDetailDto().getTypeOfContractorName(), contractorDto));
            typeOfContractor.setValue(contractorDto.getLegalDetailDto().getTypeOfContractorName());
        } else {
            forms.add(getFormForContractorType("Юридическое лицо", contractorDto));
            typeOfContractor.setValue("Юридическое лицо");
        }
        typeOfContractor.addFocusListener(e -> {
            if (typeOfContractor.getValue().equals("Физическое лицо")) {
                forms.removeAll();
                forms.add(getFormForContractorType("Физическое лицо", contractorDto));
            }
            if (typeOfContractor.getValue().equals("Индивидуальный предприниматель")) {
                forms.removeAll();
                forms.add(getFormForContractorType("Индивидуальный предприниматель", contractorDto));
            }
            if (typeOfContractor.getValue().equals("Юридическое лицо")) {
                forms.removeAll();
                forms.add(getFormForContractorType("Юридическое лицо", contractorDto));
            }
        });

        main.add(typeOfContractor, forms);
        Accordion accordion = new Accordion();
        AccordionPanel legailDetails = accordion.add("Реквизиты", main);
        legailDetails.addThemeVariants(DetailsVariant.FILLED);
        legailDetails.setOpened(true);
        return legailDetails;
    }

    // Блок Скидки и цены
//    private AccordionPanel getSalasEndPriceAccordion() {
//        HorizontalLayout layout = new HorizontalLayout();
//        FormLayout formSalePrice = new FormLayout();
//        typeOfPrice = new Select<>();
//        typeOfPrice.setItems(typeOfPriceService.getAll()
//                .stream().map(TypeOfPriceDto::getName)
//                .collect(Collectors.toList()));
//        typeOfPrice.setEmptySelectionCaption("Тип цены");
//        if (contractorDto.getTypeOfPrice() == null) {
//            contractorDto.setTypeOfPrice(TypeOfPriceDto.builder()
//                    .name("")
//                    .sortNumber("")
//                    .build());
//        }
//
//        if (contractorDto.getTypeOfPriceName() != null) {
//            typeOfPrice.setValue(contractorDto.getTypeOfPriceName());
//        }
//        discountCard = new TextField();
//        if (contractorDto.getNumberDiscountCard() != null)
//            discountCard.setValue(contractorDto.getNumberDiscountCard());
//
//        formSalePrice.addFormItem(typeOfPrice, "Цены");
//        formSalePrice.addFormItem(discountCard, "Дисконтная карта");
//        formSalePrice.setWidth("400px");
//        Accordion accordion = new Accordion();
//        AccordionPanel accordionPanel = accordion.add("Скидки и цены", layout);
//        accordionPanel.addThemeVariants(DetailsVariant.FILLED);
//        return accordionPanel;
//
//    }

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

    // Форма lrgalDetail
    private VerticalLayout getFormForContractorType(String type, ContractorDto contractorDto) {
        if (contractorDto.getLegalDetailDto() == null) {
            contractorDto.setLegalDetailDto(LegalDetailDto.builder()
                    .lastName("")
                    .firstName("")
                    .middleName("")
                    .address(new AddressDto())
                    .inn("")
                    .okpo("")
                    .ogrnip("")
                    .kpp("")
                    .numberOfTheCertificate("")
                    .build());
        }
        VerticalLayout superMain = new VerticalLayout();
        HorizontalLayout main = new HorizontalLayout();
        FormLayout formLayout = new FormLayout();
        formLayout.setWidth("250px");

        lastName = new TextField();
        if (contractorDto.getLegalDetailDto().getLastName() != null)
            lastName.setValue(contractorDto.getLegalDetailDto().getLastName());
        firstName = new TextField();
        if (contractorDto.getLegalDetailDto().getFirstName() != null)
            firstName.setValue(contractorDto.getLegalDetailDto().getFirstName());
        middleName = new TextField();
        if (contractorDto.getLegalDetailDto().getMiddleName() != null)
            middleName.setValue(contractorDto.getLegalDetailDto().getMiddleName());
        if (contractorDto.getLegalDetailDto().getAddress() != null)
            addressLegal.setValue(contractorDto.getLegalDetailDto().getAddress());
        inn = new TextField();
        if (contractorDto.getLegalDetailDto().getInn() != null)
            inn.setValue(contractorDto.getLegalDetailDto().getInn());
        okpo = new TextField();
        if (contractorDto.getLegalDetailDto().getOkpo() != null)
            okpo.setValue(contractorDto.getLegalDetailDto().getOkpo());
        ogrnip = new TextField();
        if (contractorDto.getLegalDetailDto().getOgrnip() != null)
            ogrnip.setValue(contractorDto.getLegalDetailDto().getOgrnip());
        kpp = new TextField();
        if (contractorDto.getLegalDetailDto().getKpp() != null)
            kpp.setValue(contractorDto.getLegalDetailDto().getKpp());
        numberOfTheCertificate = new TextField();
        if (contractorDto.getLegalDetailDto().getNumberOfTheCertificate() != null)
            numberOfTheCertificate.setValue(contractorDto.getLegalDetailDto().getNumberOfTheCertificate());
        dateOfTheCertificate = new DatePicker("Date");
        if (contractorDto.getLegalDetailDto().getDateOfTheCertificate() != null)
            dateOfTheCertificate.setValue(contractorDto.getLegalDetailDto().getDateOfTheCertificate());

        switch (type) {
            case "Индивидуальный предприниматель":
                formLayout.addFormItem(inn, "ИНН");
                formLayout.addFormItem(lastName, "Фамилия");
                formLayout.addFormItem(firstName, "Имя");
                formLayout.addFormItem(middleName, "Отчество");
                addressLegal.addToForm("Адрес регистрации", formLayout);
                formLayout.addFormItem(okpo, "ОКПО");
                formLayout.addFormItem(ogrnip, "ОГРНИП");
                formLayout.addFormItem(numberOfTheCertificate, "Номер свидетельства");
                formLayout.addFormItem(dateOfTheCertificate, "Дата свидетельства");
                break;

            case "Физическое лицо":
                formLayout.addFormItem(inn, "ИНН");
                formLayout.addFormItem(lastName, "Фамилия");
                formLayout.addFormItem(firstName, "Имя");
                formLayout.addFormItem(middleName, "Отчество");
                addressLegal.addToForm("Адрес регистрации", formLayout);
                break;

            case "Юридическое лицо":
                formLayout.addFormItem(inn, "ИНН");
                formLayout.addFormItem(firstName, "Полное наименование");
                addressLegal.addToForm("Адрес регистрации", formLayout);
                formLayout.addFormItem(kpp, "КПП");
                formLayout.addFormItem(ogrnip, "ОГРН");
                formLayout.addFormItem(okpo, "ОКПО");
                break;
        }
        VerticalLayout buttins = new VerticalLayout();
        Button button = new Button("Запросить по ИНН");
        button.addClickListener(e -> {
            Example2 example2 = dadata.getExample(inn.getValue());

            if (example2.getSuggestions().isEmpty()) {
                showError("По данному ИНН ничего не найдено");
            } else {
                if (typeOfContractor.getValue().equals("Индивидуальный предприниматель")) {
                    if (example2.getSuggestions().get(0).getData().getType().equals("LEGAL")) {
                        showError("Для данного ИНН нужно выбрать тип: Юридическое лицо");
                        contractorDto.getLegalDetailDto().setInn(inn.getValue());
                    } else {
                        lastName.setValue(example2.getSuggestions().get(0).getData().getFio().getSurname());
                        firstName.setValue(example2.getSuggestions().get(0).getData().getFio().getName());
                        middleName.setValue(example2.getSuggestions().get(0).getData().getFio().getPatronymic());
                        //нужно исправить, ошибочно указан адрес из dadataDto, нужен AddressDto
                        //addressLegal.setValue(example2.getSuggestions().get(0).getData().getAddress().getUnrestrictedValue());
                        okpo.setValue(example2.getSuggestions().get(0).getData().getOkpo());
                        ogrnip.setValue(example2.getSuggestions().get(0).getData().getOgrn());
                    }
                } else if (typeOfContractor.getValue().equals("Юридическое лицо")) {
                    if (example2.getSuggestions().get(0).getData().getType().equals("INDIVIDUAL")) {
                        showError("Для данного ИНН нужно выбрать тип: Индивидуальный предприниматель");
                        contractorDto.getLegalDetailDto().setInn(inn.getValue());
                    } else {
                        firstName.setValue(example2.getSuggestions().get(0).getData().getName().getShortWithOpf());
                        kpp.setValue(example2.getSuggestions().get(0).getData().getKpp());
                        ////нужно исправить, ошибочно указан адрес из dadataDto, нужен AddressDto
                        //addressLegal.setValue(example2.getSuggestions().get(0).getData().getAddress().getUnrestrictedValue());
                        ogrnip.setValue(example2.getSuggestions().get(0).getData().getOgrn());
                        okpo.setValue(example2.getSuggestions().get(0).getData().getOkpo());
                    }
                } else {
                    showError("Для физ. лиц функция недоступна.");
                }
            }
        });
        Button button1 = new Button("Адреса");
        buttins.setWidth("100px");
        buttins.add(button, button1);
        main.add(formLayout, buttins);
        superMain.add(main);
        if (!newForm) {
            superMain.add(getBankAccountSpace(contractorDto));
        }
        return superMain;
    }

    private void showError(String message) {
        Notification notification = new Notification();
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        Div text = new Div(new Text(message));
        Button closeButton = new Button(new Icon("lumo", "cross"));
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        closeButton.getElement().setAttribute("aria-label", "Close");
        closeButton.addClickListener(event -> notification.close());
        HorizontalLayout layout = new HorizontalLayout(text, closeButton);
        layout.setAlignItems(Alignment.CENTER);
        notification.add(layout);
        notification.open();
    }

    // Формы для банк Аккаунтов
    private VerticalLayout getBankAccountSpace(ContractorDto contractorDto) {

        VerticalLayout bankAccountSpace = new VerticalLayout();

        Button addNewBankAcc = new Button("Добавить счёт");

        addNewBankAcc.addClickListener(e -> {
            try {
                wantToCreateBankAccount = true;
//                List<BankAccountDto> bankAccountDtoList = new ArrayList<>();

                bankAccountSpace.add(getFormForBankAccount(contractorDto));
            } catch (ValidationException ex) {
                throw new RuntimeException(ex);
            }
        });

        bankAccountSpace.add(addNewBankAcc);

        //попытка получить все банк аккаунты принадлежащее изменяемому контрагенту
//        if (!newForm) {
//            List<BankAccountDto> bankAccountDtoList = bankAccountService.getAll();
//
//            List<BankAccountDto> filteredList = bankAccountDtoList.stream()
//                    .filter(bankAccountDto1 -> {
//                        bankAccountDtoBinder.readBean(bankAccountDto1);
//                        if (bankAccountDto1.getContractor() == contractorDto) {
//                            try {
//                                bankAccountSpace.add(getFormForBankAccount(contractorDto, bankAccountDto1));
//                                return true;
//                            } catch (ValidationException ex) {
//                                throw new RuntimeException(ex);
//                            }
//                        }
//                        return false;
//                    })
//                    .collect(Collectors.toList());
//        }
        return bankAccountSpace;
    }

    public void setParent(ContragentsList parent) {
        this.parent = parent;
    }

    private boolean wantToCreateBankAccount = false;

    private boolean newAccount = false;
    private boolean deleted = false;

    public boolean isDeleted() {
        return deleted;
    }

    public boolean isNewAccount() {
        return newAccount;
    }

    private AddressService addressService;


    private final Binder<AddressDto> addressDtoBinder = new Binder<>(AddressDto.class);

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

        Text text = new Text("Расчетный счет");


        BankAccountDto bankAccountDto = new BankAccountDto();
        bankAccountDto.setContractor(contractorDto);


        bankAccountDtoBinder.forField(rcbic).bind(BankAccountDto::getRcbic, BankAccountDto::setRcbic);
        rcbic.setValue(bankAccountDto.getRcbic() == null ? " " : bankAccountDto.getRcbic());

        bankAccountDtoBinder.forField(bank).bind(BankAccountDto::getBank, BankAccountDto::setBank);
        bank.setValue(bankAccountDto.getBank() == null ? " " : bankAccountDto.getBank());

        bankAccountDtoBinder.forField(bankAddress).bind(BankAccountDto::getBankAddress, BankAccountDto::setBankAddress);
        bankAddress.setValue(bankAccountDto.getBankAddress() == null ? " " : bankAccountDto.getBankAddress());

        bankAccountDtoBinder.forField(corespondentAccount)
                .bind(BankAccountDto::getCorrespondentAccount, BankAccountDto::setCorrespondentAccount);
        corespondentAccount.setValue(bankAccountDto
                .getCorrespondentAccount() == null ? " " : bankAccountDto.getCorrespondentAccount());

        bankAccountDtoBinder.forField(account).asRequired("Расчетный счёт не может быть пустым")
                .bind(BankAccountDto::getAccount, BankAccountDto::setAccount);
        account.setValue(bankAccountDto.getAccount() == null ? " " : bankAccountDto.getAccount());

        bankAccountDtoBinder.forField(sortNumber).bind(BankAccountDto::getSortNumber, BankAccountDto::setSortNumber);
        sortNumber.setValue(bankAccountDto.getSortNumber() == null ? " " : bankAccountDto.getSortNumber());

        bankAccountDtoBinder.bind(mainAccount, BankAccountDto::getMainAccount, BankAccountDto::setMainAccount);
        mainAccount.setValue(false);


        contractorDtoBinder.readBean(contractorDto);

        bankAccountDtoBinder.readBean(bankAccountDto);


        FormLayout formLayout = new FormLayout();
        formLayout.addFormItem(rcbic, "БИК");
        formLayout.addFormItem(bank, "Банк");
        formLayout.addFormItem(bankAddress, "Адрес");
        formLayout.addFormItem(corespondentAccount, "Kopp. счет");
        formLayout.addFormItem(account, "Расчетный счет");
        formLayout.addFormItem(sortNumber, "Текущий остаток");
        formLayout.addFormItem(mainAccount, "Основной счет");


        Button button = new Button("Удалить");
        button.addClickListener(e -> {
            main.setVisible(false);
            deleted = true;
        });


        Button button1 = new Button("Привязать банк. счет");
        button1.setDisableOnClick(true);
        button1.addClickListener(e -> {
            try {
                bankAccountDtoBinder.writeBean(bankAccountDto);
            } catch (ValidationException ex) {
                throw new RuntimeException(ex);
            }
            bankAccountService.create(bankAccountDto);
            main.setVisible(true);
        });

        main.add(button, text, formLayout, button1);

        return main;
    }

//    public BankAccountDto getBankAccount() {
//        bankAccountDto.setRcbic(bic.getValue());
//        bankAccountDto.setBank(bank.getValue());
////        Address tmpAddress = new Address();
////        tmpAddress.setValue(address.getValue());
////        bankAccountDto.setAddress(tmpAddress); // temporary
////            bankAccountDto.setCorrespondentAccount(correspondentAccount.getValue());
//        bankAccountDto.setAccount(account.getValue());
//        bankAccountDto.setMainAccount(checkbox.getValue());
//        return bankAccountDto;
//    }


}
