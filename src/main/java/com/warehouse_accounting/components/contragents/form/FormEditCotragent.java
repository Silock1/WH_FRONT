package com.warehouse_accounting.components.contragents.form;




import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.contragents.ContragentsList;
import com.warehouse_accounting.models.dto.BankAccountDto;
import com.warehouse_accounting.models.dto.ContractorDto;
import com.warehouse_accounting.models.dto.dadataDto.Example2;
import com.warehouse_accounting.models.dto.ContractorFaceContactDto;
import com.warehouse_accounting.models.dto.LegalDetailDto;
import com.warehouse_accounting.services.interfaces.BankAccountService;
import com.warehouse_accounting.services.interfaces.ContractorGroupService;
import com.warehouse_accounting.services.interfaces.ContractorService;
import com.warehouse_accounting.services.interfaces.DadataService;
import com.warehouse_accounting.services.interfaces.TypeOfContractorService;

import java.util.ArrayList;
import java.util.List;


@SpringComponent
@UIScope
public class FormEditCotragent extends VerticalLayout {

    private ContractorGroupService contractorGroupService;
    private TypeOfContractorService typeOfContractorService;
    private ContractorService contractorService;
    private BankAccountService bankAccountService;
    private ContragentsList parent;
    private ContractorDto contractorDto;
    private DadataService dadata;
    private boolean newForm = false;

    private Button edit = new Button ("Изменить");
    private Button close = new Button ("Закрыть");
    // Поля для блока контрагент
    private TextField name;
    private ComboBox<String> status;
    private ComboBox<String> group;
    private TextField phone;
    private TextField fax;
    private TextField emil;
    private TextArea address;
    private TextArea commentToAddress;
    private TextArea comment;
    private TextField code;
    private TextField outerCode;

    // Поля для LegalDetais
    private TextField lastName;
    private TextField firstName;
    private TextField middleName;
    private TextArea addressLegal;
    private TextArea commentToAddressLegal;
    private TextField inn;
    private TextField okpo;
    private TextField ogrnip;
    private TextField kpp;
    private TextField numberOfTheCertificate;
    private DatePicker dateOfTheCertificate;
    private Select <String>typeOfContractor;

    // Формы для множественных внутренних обьектов.
    private List<FormBankAccauntInner> formsBankAccount;
    private List<FormForFaceContactInner> formsFacesContact;

    public FormEditCotragent(ContractorService contractorService, DadataService dadata, TypeOfContractorService typeOfContractorService, ContractorGroupService contractorGroupService, BankAccountService bankAccountService) {
        this.contractorService = contractorService;
        this.typeOfContractorService = typeOfContractorService;
        this.contractorGroupService = contractorGroupService;
        this.dadata = dadata;
        this.bankAccountService = bankAccountService;
    }
    public void bild(ContractorDto contractorDto){
        removeAll();
        // Проверяем на null понимаем new или edit форма.
        if(contractorDto == null){
            newForm = true;
            contractorDto = ContractorDto.builder()
                    .name("")
                    .status("")
                    .contractorGroupName("")
                    .fax("")
                    .email("")
                    .address("")
                    .commentToAddress("")
                    .comment("")
                    .code("")
                    .outerCode("")
                    .build();
            }
        else {
            contractorDto = contractorService.getById(contractorDto.getId());
            newForm = false;
            System.out.println("Получил:" + contractorDto);
        }
        if(contractorDto.getBankAccountDtos() == null)contractorDto.setBankAccountDtos(new ArrayList<>());
        if(contractorDto.getContacts() == null) contractorDto.setContacts(new ArrayList<>());
        this.contractorDto = contractorDto;
        add(createButton(contractorDto), getNameContragent(contractorDto),groupBlockLayout());
    }
    private HorizontalLayout createButton(ContractorDto contractorDto){
        HorizontalLayout button = new HorizontalLayout();
        button.setAlignItems(Alignment.CENTER);
        String typeForm;
        typeForm = newForm ? "Cоздать" : "Изменить";
        edit = new Button(typeForm);
        edit.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        edit.addClickListener(e->{

                // получение Основные данные аккаунта
                contractorDto.setName(name.getValue());
                contractorDto.setStatus(status.getValue());
                contractorDto.setContractorGroupName(group.getValue());
                contractorDto.setPhone(phone.getValue());
                contractorDto.setFax(fax.getValue());
                contractorDto.setEmail(emil.getValue());
                contractorDto.setAddress(address.getValue());
                contractorDto.setCommentToAddress(commentToAddress.getValue());
                contractorDto.setComment(comment.getValue());
                contractorDto.setCode(code.getValue());
                if(outerCode.getValue().equals("")){
                    contractorDto.setOuterCode("Generate");
                }else {
                    contractorDto.setOuterCode(outerCode.getValue());
                }
                // получение Данные LegalDetails
                contractorDto.getLegalDetailDto().setLastName(lastName.getValue());
                contractorDto.getLegalDetailDto().setFirstName(firstName.getValue());
                contractorDto.getLegalDetailDto().setMiddleName(middleName.getValue());
                contractorDto.getLegalDetailDto().setAddress(addressLegal.getValue());
                contractorDto.getLegalDetailDto().setCommentToAddress(commentToAddressLegal.getValue());
                contractorDto.getLegalDetailDto().setInn(inn.getValue());
                contractorDto.getLegalDetailDto().setOkpo(okpo.getValue());
                contractorDto.getLegalDetailDto().setOgrnip(ogrnip.getValue());
                contractorDto.getLegalDetailDto().setKpp(kpp.getValue());
                contractorDto.getLegalDetailDto().setNumberOfTheCertificate(numberOfTheCertificate.getValue());
                contractorDto.getLegalDetailDto().setDateOfTheCertificate(dateOfTheCertificate.getValue());
                contractorDto.getLegalDetailDto().setTypeOfContractorName(typeOfContractor.getValue());


                BankAccountDto accountDto;
                for(FormBankAccauntInner form : formsBankAccount){
                    accountDto = form.getBankAccount();
                    if(!form.isDeleted() && form.isNewAccount()){
                        contractorDto.getBankAccountDtos().add(accountDto);
                    }
                    if(!form.isDeleted() && !form.isNewAccount()){
                        for(BankAccountDto bankAccountOld : contractorDto.getBankAccountDtos()){
                            if(bankAccountOld.getId() == accountDto.getId()){
                                bankAccountOld.setRcbic(accountDto.getRcbic());
                                bankAccountOld.setBank(accountDto.getBank());
                                bankAccountOld.setAddress(accountDto.getAddress());
                                bankAccountOld.setCorrespondentAccount(accountDto.getCorrespondentAccount());
                                bankAccountOld.setAccount(accountDto.getAccount());
                                bankAccountOld.setMainAccount(accountDto.getMainAccount());
                                bankAccountOld.setSortNumber(accountDto.getSortNumber());
                            }
                        }
                    }
                }
           // Получение контактов аккаунттов или изменений в нём.
            ContractorFaceContactDto contact;
            for(FormForFaceContactInner form: formsFacesContact ){
                contact = form.getContactFace();
                if(!form.isDeleted() && form.isNewFaceContact()){
                    contractorDto.getContacts().add(contact);
                }
                if(!form.isDeleted() && !form.isNewFaceContact()){
                    for(ContractorFaceContactDto contactOld : contractorDto.getContacts()){
                        if(contactOld.getId() == contact.getId()){
                            contactOld.setAllNames(contact.getAllNames());
                            contactOld.setPosition(contact.getPosition());
                            contactOld.setPhone(contactOld.getPhone());
                            contactOld.setEmail(contact.getEmail());
                            contactOld.setComment(contact.getComment());
                        }
                    }
                }
            }
            if(newForm){
                contractorService.create(contractorDto);
            }else {
                contractorService.update(contractorDto);
            }
            //Удобно в тестах для отладки пока оставить.
            System.out.println("Отправил:" + contractorDto);

            removeAll();
            parent.showButtonEndGrid(true);
        });
        close = new Button("Закрыть");
        close.addClickListener(e -> {
            removeAll();
            parent.showButtonEndGrid(false);
        });
        button.add(close,edit);
        button.setAlignItems(Alignment.CENTER);
        return button;
    }
    private HorizontalLayout getNameContragent(ContractorDto contractorDto) {
        HorizontalLayout nameContragentLayout = new HorizontalLayout();
        name = new TextField("Наименование");

        name.setWidth("450px");
        if(contractorDto.getName()!= null)name.setValue(contractorDto.getName());
        nameContragentLayout.add(name);
        return nameContragentLayout;
    }
    private VerticalLayout leftGroupFormLayout() {
        VerticalLayout leftLayout = new VerticalLayout();
        leftLayout.add(getContragentAccordion(), getFaceContactAccordion(),getLegаlDetailAccordion(),getSalasEndPriceAccordion(), getAccessAccordion());
        leftLayout.setWidth("450px");
      return leftLayout;
    }
    private VerticalLayout rightGroupButtonLayout() {
        VerticalLayout rightLayout = new VerticalLayout();
            rightLayout.setWidth("500px");
        Tab details = new Tab("События");
        Tab payment = new Tab("Задачи");
        Tab shipping = new Tab("Документы");
        Tab files = new Tab("Файлы");
        Tab indicators = new Tab("Показатели");
        Tabs tabs = new Tabs(details, payment, shipping, files, indicators);
        rightLayout.add(tabs);
        return rightLayout;
    }
    private HorizontalLayout groupBlockLayout() {
        HorizontalLayout blockLayout = new HorizontalLayout();
        blockLayout.add(leftGroupFormLayout(), rightGroupButtonLayout());
        return blockLayout;
    }
    //Блок о контрагенте
    private AccordionPanel getContragentAccordion(){
        FormLayout main = new FormLayout();

        status = new ComboBox<>();
        status.setItems("Новый", "Выслано предложение","Переговоры", "Сделка заключена", "Сделка не заключена");
        if(contractorDto.getStatus()!= null) status.setValue(contractorDto.getStatus());
        group = new ComboBox<>();
        group.setItems("Основные", "Мелкий опт","Розница");
        phone = new TextField();
        if(contractorDto.getPhone() != null) phone.setValue(contractorDto.getPhone());
        fax = new TextField();
        if(contractorDto.getFax() != null) fax.setValue(contractorDto.getFax());
        emil = new TextField();
        if(contractorDto.getEmail() != null) emil.setValue(contractorDto.getEmail());
        address = new TextArea();
        if(contractorDto.getAddress() != null) address.setValue(contractorDto.getAddress());
        commentToAddress = new TextArea();
        if(contractorDto.getCommentToAddress() != null) commentToAddress.setValue(contractorDto.getCommentToAddress());
        comment = new TextArea();
        if(contractorDto.getComment() != null) comment.setValue(contractorDto.getComment());
        code = new TextField();
        if(contractorDto.getCode() != null) code.setValue(contractorDto.getCode());
        outerCode = new TextField();
        if(contractorDto.getCode() != null) outerCode.setValue(contractorDto.getOuterCode());

        FormLayout form = new FormLayout();
        form.addFormItem(status, "Статус");
        form.addFormItem(group, "Группы");
        form.addFormItem(phone, "Телефон");
        form.addFormItem(fax, "Факс");
        form.addFormItem(emil, "Электронная почта");
        form.addFormItem(address, "Адресс");
        form.addFormItem(commentToAddress, "Комментарий к Адресу");
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
    private AccordionPanel getFaceContactAccordion(){
        formsFacesContact = new ArrayList<>();
        VerticalLayout faceContactsSpace = new VerticalLayout();
        if(contractorDto.getContacts() != null){
            for(ContractorFaceContactDto contact : contractorDto.getContacts()){
                FormForFaceContactInner form = new FormForFaceContactInner();
                faceContactsSpace.add(form.getFormForFaceContact(contact));
                formsFacesContact.add(form);
            }
        }
        Button addNewContBtn = new Button("Добавить контакт");
        addNewContBtn.addClickListener(e->{
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
    private AccordionPanel getLegаlDetailAccordion(){
        if(contractorDto.getLegalDetailDto() == null) contractorDto.setLegalDetailDto(new LegalDetailDto());

        VerticalLayout main = new VerticalLayout();
        VerticalLayout forms = new VerticalLayout();
        // НАстроить дефолтный фокус
        typeOfContractor = new Select<>();
        typeOfContractor.setWidth("350px");
        typeOfContractor.setItems("Физическое лицо","Юридическое лицо", "Индивидуальный предприниматель");
        typeOfContractor.setEmptySelectionCaption("Тип контрагента");

        if (contractorDto.getLegalDetailDto().getTypeOfContractorName() !=null) {
            forms.add(getFormForContractorType(contractorDto.getLegalDetailDto().getTypeOfContractorName()));
            typeOfContractor.setValue(contractorDto.getLegalDetailDto().getTypeOfContractorName());
        }else {
            forms.add(getFormForContractorType("Юридическое лицо"));
            typeOfContractor.setValue("Юридическое лицо");
        }
        typeOfContractor.addFocusListener(e ->{
           if(typeOfContractor.getValue().equals("Физическое лицо")){
               forms.removeAll();
               forms.add(getFormForContractorType("Физическое лицо"));
           }
           if (typeOfContractor.getValue().equals("Индивидуальный предприниматель")) {
               forms.removeAll();
               forms.add(getFormForContractorType("Индивидуальный предприниматель"));
           }
            if (typeOfContractor.getValue().equals("Юридическое лицо")) {
                forms.removeAll();
                forms.add(getFormForContractorType("Юридическое лицо"));
            }
        });

        main.add(typeOfContractor, forms);
        Accordion accordion = new Accordion();
        AccordionPanel legailDetails = accordion.add("Реквезиты", main);
        legailDetails.addThemeVariants(DetailsVariant.FILLED);
        legailDetails.setOpened(true);
        return legailDetails;
    }
    // Блок Скидки и цены
    private AccordionPanel getSalasEndPriceAccordion(){
        HorizontalLayout layout = new HorizontalLayout();
        Text text = new Text("Тут пока не готово");
        layout.add(text);
        Accordion accordion = new Accordion();
        AccordionPanel accordionPanel = accordion.add("Скидки и цены", layout);
        accordionPanel.addThemeVariants(DetailsVariant.FILLED);
        return accordionPanel;
    }
    //БЛок Доступ
    private AccordionPanel getAccessAccordion(){
        HorizontalLayout layout = new HorizontalLayout();
        Text text = new Text("Тут пока не готово");
        layout.add(text);
        Accordion accordion = new Accordion();
        AccordionPanel accordionPanel = accordion.add("Доступ", layout);
        accordionPanel.addThemeVariants(DetailsVariant.FILLED);
        return accordionPanel;
    }

    // Форма lrgalDetail
    private VerticalLayout getFormForContractorType(String type){
        if(contractorDto.getLegalDetailDto() == null){
            contractorDto.setLegalDetailDto(LegalDetailDto.builder()
                    .lastName("")
                    .firstName("")
                    .middleName("")
                    .address("")
                    .commentToAddress("")
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
        if(contractorDto.getLegalDetailDto().getLastName() != null) lastName.setValue(contractorDto.getLegalDetailDto().getLastName());
        firstName = new TextField();
        if(contractorDto.getLegalDetailDto().getFirstName() != null) firstName.setValue(contractorDto.getLegalDetailDto().getFirstName());
        middleName = new TextField();
        if(contractorDto.getLegalDetailDto().getMiddleName() != null) middleName.setValue(contractorDto.getLegalDetailDto().getMiddleName());
        addressLegal = new TextArea();
        if(contractorDto.getLegalDetailDto().getAddress() != null) addressLegal.setValue(contractorDto.getLegalDetailDto().getAddress());
        commentToAddressLegal = new TextArea();
        if(contractorDto.getLegalDetailDto().getCommentToAddress() != null) commentToAddressLegal.setValue(contractorDto.getLegalDetailDto().getCommentToAddress());
        inn = new TextField();
        if(contractorDto.getLegalDetailDto().getInn() != null) inn.setValue(contractorDto.getLegalDetailDto().getInn());
        okpo = new TextField();
        if(contractorDto.getLegalDetailDto().getOkpo() != null) okpo.setValue(contractorDto.getLegalDetailDto().getOkpo());
        ogrnip = new TextField();
        if(contractorDto.getLegalDetailDto().getOgrnip() != null) ogrnip.setValue(contractorDto.getLegalDetailDto().getOgrnip());
        kpp = new TextField();
        if(contractorDto.getLegalDetailDto().getKpp() != null) kpp.setValue(contractorDto.getLegalDetailDto().getKpp());
        numberOfTheCertificate = new TextField();
        if(contractorDto.getLegalDetailDto().getNumberOfTheCertificate() != null) numberOfTheCertificate.setValue(contractorDto.getLegalDetailDto().getNumberOfTheCertificate());
        dateOfTheCertificate = new DatePicker("Date");
        if(contractorDto.getLegalDetailDto().getDateOfTheCertificate() != null) dateOfTheCertificate.setValue(contractorDto.getLegalDetailDto().getDateOfTheCertificate());

        switch (type) {
            case "Индивидуальный предприниматель":
            formLayout.addFormItem(inn, "ИНН");
            formLayout.addFormItem(lastName, "Фамилия");
            formLayout.addFormItem(firstName, "Имя");
            formLayout.addFormItem(middleName, "Отчество");
            formLayout.addFormItem(addressLegal, "Адрес регистрации");
            formLayout.addFormItem(commentToAddressLegal, "Комментарий к аресу");
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
            formLayout.addFormItem(addressLegal, "Адрес регистрации");
            formLayout.addFormItem(commentToAddressLegal, "Комментарий к аресу");
            break;

            case "Юридическое лицо":
            formLayout.addFormItem(inn, "ИНН");
            formLayout.addFormItem(firstName, "Полное наименование");
            formLayout.addFormItem(addressLegal, "Юридический адрес");
            formLayout.addFormItem(commentToAddressLegal, "Комментарий к аресу");
            formLayout.addFormItem(kpp, "КПП");
            formLayout.addFormItem(ogrnip, "ОГРН");
            formLayout.addFormItem(okpo, "ОКПО");
            break;
        }
        VerticalLayout buttins = new VerticalLayout();
        Button button = new Button("Запросить по ИНН");
        button.addClickListener(e->{
            Example2 example2 = dadata.getExample(inn.getValue());

            if (example2.getSuggestions().size() == 0) {
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
                        addressLegal.setValue(example2.getSuggestions().get(0).getData().getAddress().getUnrestrictedValue());
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
                        addressLegal.setValue(example2.getSuggestions().get(0).getData().getAddress().getUnrestrictedValue());
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
        main.add(formLayout,buttins);
        superMain.add(main);
        superMain.add(getBankAccountSpace());
        return superMain;
    }

    private void showError(String message){
        Notification notification = new Notification();
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        Div text = new Div(new Text(message));
        Button closeButton = new Button(new Icon("lumo", "cross"));
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        closeButton.getElement().setAttribute("aria-label", "Close");
        closeButton.addClickListener(event -> {
            notification.close();
        });
        HorizontalLayout layout = new HorizontalLayout(text, closeButton);
        layout.setAlignItems(Alignment.CENTER);
        notification.add(layout);
        notification.open();
    }
    // Формы для банк Аккаунтов
    private VerticalLayout getBankAccountSpace(){
        formsBankAccount = new ArrayList<>();
        VerticalLayout bankAccountSpace = new VerticalLayout();
        if(contractorDto.getBankAccountDtos() != null) {
            for (BankAccountDto bankAccountDto : contractorDto.getBankAccountDtos()) {
                FormBankAccauntInner formBankAccauntInner = new FormBankAccauntInner(bankAccountService);
                bankAccountSpace.add(formBankAccauntInner.getFormForBankAccount(bankAccountDto));
                formsBankAccount.add(formBankAccauntInner);
            }
        }
        Button addNewBankAcc = new Button("Добавить счёт");
        addNewBankAcc.addClickListener(e->{
            FormBankAccauntInner form = new FormBankAccauntInner(bankAccountService);
            bankAccountSpace.add(form.getFormForBankAccount(null));
            formsBankAccount.add(form);
        });
        bankAccountSpace.add(addNewBankAcc);
        return bankAccountSpace;
    }
    public void setParent(ContragentsList parent) {
        this.parent = parent;
    }
}

