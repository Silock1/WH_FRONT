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
import com.vaadin.flow.component.html.Image;
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
import com.warehouse_accounting.services.impl.AddressServiceImpl;
import com.warehouse_accounting.services.interfaces.*;
import org.springframework.security.core.parameters.P;

import java.time.LocalDate;
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
    private AddressDto addressDto;
    private LegalDetailDto legalDetailDto = new LegalDetailDto();

    private final transient DadataService dadata;

    private boolean newForm = false;

    //???????? ?????? ?????????????? ??????????
    private Events events;

    private Button edit = new Button("??????????????????");
    private Button close = new Button("??????????????");

    private transient List<FormForFaceContactInner> formsFacesContact;

//    private final Binder<BankAccountDto> bankAccountDtoBinder = new Binder<>(BankAccountDto.class);

    private final Binder<ContractorDto> contractorDtoBinder = new Binder<>(ContractorDto.class);

    private final Binder<LegalDetailDto> legalDetailDtoBinder = new Binder<>(LegalDetailDto.class);

    private final Binder<AddressDto> addressDtoBinder = new Binder<>(AddressDto.class);

//    private final Binder<List<TypeOfPriceDto>> typeOfPriceDtoBinder = new Binder<>();
//
//    private final Binder<List<TypeOfContractorDto>> typeOfContractorDtoBinder = new Binder<>();
//
//    private final Binder<List<ContractorGroupDto>> contractorGroupDtoBinder = new Binder<>();

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

    boolean alreadyMakeUpdate = false;

    public void build() {
        removeAll();
        newForm = true;

//        if (alreadyMakeUpdate) {
//            try {
//                contractorDtoBinder.writeBean(contractorDto);
//            } catch (ValidationException e) {
//                throw new RuntimeException(e);
//            }
//        }

        add(createButton(contractorDto),
                getNameContragent(contractorDto),
                groupBlockLayout(contractorDto));
    }


    private HorizontalLayout createButton(ContractorDto contractorDto) {
        HorizontalLayout button = new HorizontalLayout();
        button.setAlignItems(Alignment.CENTER);
        String typeForm;
        typeForm = "??????????????????";
        edit = new Button(typeForm);
        edit.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        edit.addClickListener(e -> {

            // ?????????????????? ?????????????????? ???????????????????? ?????? ?????????????????? ?? ??????.
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

            if (!contractorDtoBinder.validate().isOk()) {
                //TODO ???????????? ?? ?????????????? Validation has failed for some fields,
                // ???????? ?????????????? ?? ?????????????? ??????????, ?? ?????????? ???????????????? ???????????????? ????????????
            }
            if (newForm) {
                try {
                    addressDtoBinder.writeBean(addressDto);
                    legalDetailDtoBinder.writeBean(legalDetailDto);
                    contractorDtoBinder.writeBean(contractorDto);

                    contractorService.create(contractorDto);
                } catch (ValidationException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                try {
                    //alreadyMakeUpdate = true;
                    addressDtoBinder.writeBean(addressDto);
                    legalDetailDtoBinder.writeBean(legalDetailDto);
                    contractorDtoBinder.writeBean(contractorDto);


                    contractorService.update(contractorDto);
                } catch (ValidationException ex) {
                    throw new RuntimeException(ex);
                }
            }

            removeAll();
            parent.showButtonEndGrid(true);

        });

        close = new Button("??????????????");
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
        TextField name = new TextField("????????????????????????");
        name.setMinWidth("300px");
        contractorDtoBinder.forField(name).asRequired("???????????????????????? ???? ???????????? ???????? ????????????!")
                .bind(ContractorDto::getName, ContractorDto::setName);

        if (!newForm) {
            contractorDtoBinder.readBean(contractorDto);
        }


        nameContragentLayout.add(name);
        return nameContragentLayout;
    }

    private VerticalLayout leftGroupFormLayout(ContractorDto contractorDto) {
        VerticalLayout leftLayout = new VerticalLayout();
        leftLayout.add(getContragentAccordion(contractorDto));
        leftLayout.add(getLegalDetailAccordion(contractorDto));
        //leftLayout.add(getAccessAccordion());
        leftLayout.add(getSalasEndPriceAccordion(contractorDto));
        leftLayout.setWidth("450px");
        return leftLayout;
    }

    private VerticalLayout rightGroupButtonLayout() {
        VerticalLayout rightLayout = new VerticalLayout();
        rightLayout.setWidth("500px");
        final String eventsName = "??????????????";
        final String tasksName = "????????????";
        final String documentsName = "??????????????????";
        final String filesName = "??????????";
        final String indicatorsName = "????????????????????";
        Tab details = new Tab(eventsName);
        Tab payment = new Tab(tasksName);
        Tab shipping = new Tab(documentsName);
        Tab files = new Tab(filesName);
        Tab indicators = new Tab(indicatorsName);
        Tabs tabs = new Tabs(details, payment, shipping, files, indicators);
        if (newForm) {
            details.setEnabled(false);
            payment.setEnabled(false);
            shipping.setEnabled(false);
            indicators.setEnabled(false);
            tabs.setSelectedTab(files);
        } else {
            tabs.setSelectedTab(details);
        }
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

    //???????? ?? ??????????????????????
    private AccordionPanel getContragentAccordion(ContractorDto contractorDto) {

        ComboBox<String> status = new ComboBox<>();
        ComboBox<ContractorGroupDto> group = new ComboBox<>();
        TextField phone = new TextField();
        TextField fax = new TextField();
        TextField email = new TextField();
        TextField comment = new TextField();
        TextField code = new TextField();
        TextField outerCode = new TextField();

        status.setItems("??????????", "?????????????? ??????????????????????", "????????????????????", "???????????? ??????????????????", "???????????? ???? ??????????????????");
        status.setValue("??????????");
        contractorDtoBinder.bind(status, ContractorDto::getStatus, ContractorDto::setStatus);

        group.setItems(contractorGroupService.getAll());
        group.setItemLabelGenerator(ContractorGroupDto::getName);
        contractorDtoBinder.forField(group).asRequired("?????????????? ????????????")
                .bind(ContractorDto::getContractorGroup, ContractorDto::setContractorGroup);
        group.setValue(contractorGroupService.getById(1L));

        contractorDtoBinder.forField(phone).bind(ContractorDto::getPhone, ContractorDto::setPhone);
        contractorDtoBinder.forField(fax).bind(ContractorDto::getFax, ContractorDto::setFax);
        contractorDtoBinder.forField(email).bind(ContractorDto::getEmail, ContractorDto::setEmail);

        addressDto = newForm? new AddressDto() : contractorDto.getAddress();

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
            contractorDto.setAddress(addressDto);
        }
        if (!newForm) {
            addressDtoBinder.readBean(addressDto);
            contractorDtoBinder.readBean(contractorDto);
        }

        FormLayout form = new FormLayout();
        form.addFormItem(status, "????????????");
        form.addFormItem(group, "????????????");
        form.addFormItem(phone, "??????????????");
        form.addFormItem(fax, "????????");
        form.addFormItem(email, "?????????????????????? ??????????");
        form.addFormItem(cityName, "??????????");
        form.addFormItem(streetName, "??????????");
        form.addFormItem(buildingName, "??????");
        form.addFormItem(office, "????????????????/????????");
        form.addFormItem(other, "????????????");
        form.addFormItem(comment, "??????????????????????");
        form.addFormItem(code, "??????");
        form.addFormItem(outerCode, "?????????????? ??????");

        Accordion accordion = new Accordion();
        AccordionPanel aboutContractor = accordion.add("O ??????????????????????", form);
        aboutContractor.addThemeVariants(DetailsVariant.FILLED);
        aboutContractor.setOpened(true);
        return aboutContractor;

    }

    // ???????? ????????????????
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
        Button addNewContBtn = new Button("???????????????? ??????????????");
        addNewContBtn.addClickListener(e -> {
            FormForFaceContactInner form = new FormForFaceContactInner();
            faceContactsSpace.add(form.getFormForFaceContact(null));
            formsFacesContact.add(form);
        });
        faceContactsSpace.add(addNewContBtn);
        Accordion accordion = new Accordion();
        AccordionPanel faceContacts = accordion.add("???????????????????? ????????", faceContactsSpace);
        faceContacts.addThemeVariants(DetailsVariant.FILLED);
        faceContacts.setOpened(true);
        return faceContacts;
    }

    //<???????? ??????????????????
    private AccordionPanel getLegalDetailAccordion(ContractorDto contractorDto) {

        legalDetailDto = newForm ? new LegalDetailDto() : contractorDto.getLegalDetail();

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
        legalDetailDtoBinder.forField(fullName).asRequired("?????????????? ???????????? ????????????????????????")
                .bind(LegalDetailDto::getFullName, LegalDetailDto::setFullName);
        legalDetailDtoBinder.forField(kpp).bind(LegalDetailDto::getKpp, LegalDetailDto::setKpp);
        legalDetailDtoBinder.forField(ogrnip).bind(LegalDetailDto::getOgrn, LegalDetailDto::setOgrn);
        legalDetailDtoBinder.forField(okpo).bind(LegalDetailDto::getOkpo, LegalDetailDto::setOkpo);

        if (newForm) {
            contractorDto.setLegalDetail(legalDetailDto);

        }
        if (!newForm) {
            legalDetailDtoBinder.readBean(legalDetailDto);
        }


        FormLayout formLayout = new FormLayout();
        formLayout.addFormItem(typeOfContractorDtoComboBox, "?????? ??????????????????????");
        formLayout.addFormItem(inn, "??????");
        formLayout.addFormItem(fullName, "???????????? ????????????????????????");
        formLayout.addFormItem(kpp, "??????");
        formLayout.addFormItem(ogrnip, "????????");
        formLayout.addFormItem(okpo, "????????");

        Button buttonToAddLegalDetail = new Button("???????????????? ?????????????????? ?? ??????????????????????");

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
        AccordionPanel legailDetails = accordion.add("??????????????????", formLayout);
        legailDetails.addContent(buttonToAddLegalDetail);
        legailDetails.addContent(getBankAccountSpace(contractorDto));
        legailDetails.addThemeVariants(DetailsVariant.FILLED);
        legailDetails.setOpened(true);
        return legailDetails;

    }

    // ???????? ???????????? ?? ????????
    private AccordionPanel getSalasEndPriceAccordion(ContractorDto contractorDto) {

        FormLayout formSalePrice = new FormLayout();

        ComboBox<TypeOfPriceDto> typeOfPriceDtoComboBox = new ComboBox<>();
        typeOfPriceDtoComboBox.setRequired(true);
        typeOfPriceDtoComboBox.setItems(typeOfPriceService.getAll());
        typeOfPriceDtoComboBox.setItemLabelGenerator(TypeOfPriceDto::getName);
        typeOfPriceDtoComboBox.setValue(typeOfPriceService.getById(1L));
        contractorDtoBinder.forField(typeOfPriceDtoComboBox)
                .bind(ContractorDto::getTypeOfPrice, ContractorDto::setTypeOfPrice);

        TextField numberDiscountCard = new TextField();
        contractorDtoBinder.forField(numberDiscountCard)
                .bind(ContractorDto::getNumberDiscountCard, ContractorDto::setNumberDiscountCard);
        if (!newForm) {
            contractorDtoBinder.readBean(contractorDto);
        }


        formSalePrice.addFormItem(typeOfPriceDtoComboBox, "????????");
        formSalePrice.addFormItem(numberDiscountCard, "?????????? ????????. ??????????");
        formSalePrice.setWidth("400px");
        Accordion accordion = new Accordion();
        AccordionPanel accordionPanel = accordion.add("???????????? ?? ????????", formSalePrice);
        accordionPanel.setOpened(true);
        accordionPanel.addThemeVariants(DetailsVariant.FILLED);
        return accordionPanel;

    }

    //???????? ????????????
    private AccordionPanel getAccessAccordion() {
        HorizontalLayout layout = new HorizontalLayout();
        Text text = new Text("?????? ???????? ???? ????????????");
        layout.add(text);
        Accordion accordion = new Accordion();
        AccordionPanel accordionPanel = accordion.add("????????????", layout);
        accordionPanel.addThemeVariants(DetailsVariant.FILLED);
        return accordionPanel;
    }

    // ?????????? ?????? ???????? ??????????????????
    private VerticalLayout getBankAccountSpace(ContractorDto contractorDto) {

        VerticalLayout bankAccountSpace = new VerticalLayout();

        Image buttonIcon = new Image("icons/plus.png", "Plus");
        buttonIcon.setWidth("14px");
        Button addNewBankAcc = new Button("?????????????????? ????????", buttonIcon);
        addNewBankAcc.addClickListener(e -> {
            try {
                bankAccountSpace.add(getFormForBankAccount(contractorDto));
            } catch (ValidationException ex) {
                throw new RuntimeException(ex);
            }
        });

        bankAccountSpace.add(addNewBankAcc);


        List<BankAccountDto> bankAccountDtoList = bankAccountService.getAll();
        if (!newForm) {
            contractorDtoBinder.readBean(contractorDto);
        }


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

        bankAccountDtoBinder2.forField(account).asRequired("?????????????????? ???????? ???? ?????????? ???????? ????????????")
                .bind(BankAccountDto::getAccount, BankAccountDto::setAccount);

        bankAccountDtoBinder2.forField(sortNumber).bind(BankAccountDto::getSortNumber, BankAccountDto::setSortNumber);

        bankAccountDtoBinder2.bind(mainAccount, BankAccountDto::getMainAccount, BankAccountDto::setMainAccount);


        bankAccountDtoBinder2.readBean(bankAccountDto);


        FormLayout formLayout = new FormLayout();
        formLayout.addFormItem(rcbic, "??????");
        formLayout.addFormItem(bank, "????????");
        formLayout.addFormItem(bankAddress, "??????????");
        formLayout.addFormItem(corespondentAccount, "Kopp. ????????");
        formLayout.addFormItem(account, "?????????????????? ????????");
        formLayout.addFormItem(sortNumber, "?????????????? ??????????????");
        formLayout.addFormItem(mainAccount, "???????????????? ????????");


        Button button = new Button("??????????????");
        button.addClickListener(e -> {
            main.setVisible(false);
        });


        Button button1 = new Button("?????????????????? ????????. ????????");
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

        bankAccountDtoBinder1.forField(account).asRequired("?????????????????? ???????? ???? ?????????? ???????? ????????????")
                .bind(BankAccountDto::getAccount, BankAccountDto::setAccount);

        bankAccountDtoBinder1.forField(sortNumber).bind(BankAccountDto::getSortNumber, BankAccountDto::setSortNumber);

        bankAccountDtoBinder1.bind(mainAccount, BankAccountDto::getMainAccount, BankAccountDto::setMainAccount);


        bankAccountDtoBinder1.readBean(bankAccountDto);


        FormLayout formLayout = new FormLayout();
        formLayout.addFormItem(rcbic, "??????");
        formLayout.addFormItem(bank, "????????");
        formLayout.addFormItem(bankAddress, "??????????");
        formLayout.addFormItem(corespondentAccount, "Kopp. ????????");
        formLayout.addFormItem(account, "?????????????????? ????????");
        formLayout.addFormItem(sortNumber, "?????????????? ??????????????");
        formLayout.addFormItem(mainAccount, "???????????????? ????????");


        Button button = new Button("?????????????? ????????. ????????");
        button.addClickListener(e -> {
            bankAccountService.deleteById(bankAccountDto.getId());
            main.setVisible(false);
        });


        Button button1 = new Button("???????????????? ????????. ????????");
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
