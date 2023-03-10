//package com.warehouse_accounting.components.contragents.form;
//
//import com.vaadin.flow.component.Text;
//import com.vaadin.flow.component.button.Button;
//import com.vaadin.flow.component.button.ButtonVariant;
//import com.vaadin.flow.component.checkbox.Checkbox;
//import com.vaadin.flow.component.combobox.ComboBox;
//import com.vaadin.flow.component.formlayout.FormLayout;
//import com.vaadin.flow.component.icon.Icon;
//import com.vaadin.flow.component.icon.VaadinIcon;
//import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
//import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.component.tabs.Tab;
//import com.vaadin.flow.component.tabs.Tabs;
//import com.vaadin.flow.component.textfield.TextArea;
//import com.vaadin.flow.component.textfield.TextField;
//import com.vaadin.flow.spring.annotation.SpringComponent;
//import com.vaadin.flow.spring.annotation.UIScope;
//import com.warehouse_accounting.components.address.AddressForm;
//import com.warehouse_accounting.components.contragents.ContragentsList;
//import com.warehouse_accounting.components.contragents.form.form_edit.Events;
//import com.warehouse_accounting.models.dto.AddressDto;
//import com.warehouse_accounting.models.dto.ContractorDto;
//import com.warehouse_accounting.models.dto.ContractorGroupDto;
//import com.warehouse_accounting.services.impl.ContractorServiceImpl;
//import com.warehouse_accounting.services.interfaces.CallService;
//import com.warehouse_accounting.services.interfaces.ContractorService;
//import org.springframework.beans.factory.annotation.Autowired;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//import java.util.ArrayList;
//
//
//@SpringComponent
//@UIScope
//public class FormNewContragent extends VerticalLayout {
//    private TextField nameContragent;
//    private ComboBox<String> statusContragent;
//    private ComboBox<String> groupContragent;
//    private TextField telTextField;
//    private TextField faxTextField;
//    private TextField emailTextField;
//    private AddressForm actualAddressForm;
//    private TextArea commentTextArea;
//    private ComboBox<String> typeEmployee;
//    private TextField innEmployee;
//    private AddressForm legalAddressForm;
//    private TextField kppEmployee;
//    private TextField ogrnEmployee;
//    private TextField okpoEmployee;
//    private ComboBox<String> price;
//    private TextField discountCard;
//    private ComboBox<String> employee;
//    private ComboBox<String> departmentEmployee;
//    private boolean newForm = false;
//    private final transient ContractorService contractorService;
//    private ContragentsList contragentsList;
//    private Events events;
//    private transient ContractorDto contractorDto;
//    private final transient CallService callService;
//
//    @Autowired
//    public FormNewContragent(
//            AddressForm actualAddressForm,
//            AddressForm legalAddressForm,
//            ContractorService contractorService, CallService callService) {
//        this.actualAddressForm = actualAddressForm;
//        this.legalAddressForm = legalAddressForm;
//        this.contractorService = contractorService;
//        this.callService = callService;
//    }
//
//    public void refres (){
//        removeAll();
//        add(getGroupButton(), getNameContragent(), groupBlockLayout());
//    }
//    private HorizontalLayout getGroupButton() {
//        HorizontalLayout controlGroupButton = new HorizontalLayout();
//        Button createButton = new Button("??????????????????", e ->{
//            Retrofit retrofit = new Retrofit.Builder()
//                    .baseUrl("http://localhost:4446")
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//            ContractorService contractorService = new ContractorServiceImpl("/api/contractors", retrofit);
//            ContractorDto contractorDto = new ContractorDto();
//            contractorDto.setName(nameContragent.getValue());
//            contractorDto.setPhone(telTextField.getValue());
//            contractorDto.setFax(faxTextField.getValue());
//            contractorDto.setEmail(emailTextField.getValue());
//            contractorDto.setAddress(actualAddressForm.getValue());
//            contractorDto.setComment(commentTextArea.getValue());
//            contractorDto.setNumberDiscountCard(discountCard.getValue());
//            contractorDto.setLegalDetailAddress(legalAddressForm.getValue());
//            contractorDto.setLegalDetailInn(innEmployee.getValue());
//            contractorDto.setLegalDetailKpp(kppEmployee.getValue());
//            contractorDto.setLegalDetailTypeOfContractorName(typeEmployee.getValue());
//            contractorDto.setContractorGroupName(groupContragent.getValue());
//            contractorDto.setContractorGroup(ContractorGroupDto.builder().build());
//
//            contractorService.create(contractorDto);
//            removeAll();
//            contragentsList.showButtonEndGrid(true);
//        });
//        Button closeButton = new Button("??????????????", e -> {
//            removeAll();
//            contragentsList.showButtonEndGrid(false);
//        });
//        controlGroupButton.add(createButton, closeButton);
//        return controlGroupButton;
//    }
//    private HorizontalLayout getNameContragent() {
//        HorizontalLayout nameContragentLayout = new HorizontalLayout();
//        nameContragent = new TextField("????????????????????????");
//        nameContragent.setWidth("550px");
//        nameContragentLayout.add(nameContragent);
//        return nameContragentLayout;
//    }
//    private HorizontalLayout groupBlockLayout() {
//        HorizontalLayout blockLayout = new HorizontalLayout();
//        blockLayout.add(leftGroupButtonLayout(), rightGroupButtonLayout());
//        return blockLayout;
//    }
//    private HorizontalLayout leftGroupButtonLayout() {
//        HorizontalLayout leftLayout = new HorizontalLayout();
//        VerticalLayout verticalLayout = new VerticalLayout();
//        FormLayout formContragent = new FormLayout();
//
//        Text textContragent = new Text("?? ??????????????????????");
//        statusContragent = new ComboBox<>();
//        statusContragent.setItems("??????????", "?????????????? ??????????????????????");
//        groupContragent = new ComboBox<>();
//        groupContragent.setItems("????????????????", "???????????? ??????");
//        telTextField = new TextField();
//        faxTextField = new TextField();
//        emailTextField = new TextField();
//        commentTextArea = new TextArea();
//
//        formContragent.addFormItem(statusContragent, "????????????");
//        formContragent.addFormItem(groupContragent, "????????????");
//        formContragent.addFormItem(telTextField,"??????????????");
//        formContragent.addFormItem(faxTextField,"????????");
//        formContragent.addFormItem(emailTextField,"E-mail");
//        actualAddressForm.addToForm("?????????????????????? ??????????", formContragent);
//        formContragent.addFormItem(commentTextArea, "??????????????????????");
//        formContragent.setWidth("400px");
//        /*
//        ???????????????????? ????????
//         */
//
//        Text textContactContragent = new Text("???????????????????? ????????");
//
//        FormLayout formLayout = new FormLayout();
//        TextField revenue = new TextField();
//        formLayout.addFormItem(revenue, "?????????????? ??????????");
//        /*
//        ??????????????????
//         */
//
//        Text textRequisites = new Text("??????????????????");
//        FormLayout formRequisites = new FormLayout();
//        typeEmployee = new ComboBox<>();
//        typeEmployee.setItems("??????????????????", "????????????????????");
//        innEmployee = new TextField();
//        TextField fullNameEmployee = new TextField();
//        kppEmployee = new TextField();
//        ogrnEmployee = new TextField();
//        okpoEmployee = new TextField();
//        Button paymentAccount = new Button(new Icon(VaadinIcon.PLUS));
//        paymentAccount.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//
//        formRequisites.addFormItem(typeEmployee, "?????? ??????????????????????");
//        formRequisites.addFormItem(innEmployee, "??????");
//        formRequisites.addFormItem(fullNameEmployee, "???????????? ????????????????????????");
//        legalAddressForm.addToForm("?????????????????????? ??????????", formRequisites);
//        formRequisites.addFormItem(kppEmployee, "??????");
//        formRequisites.addFormItem(ogrnEmployee, "????????");
//        formRequisites.addFormItem(okpoEmployee, "????????");
//        formRequisites.addFormItem(paymentAccount, "?????????????????? ????????");
//        formRequisites.setWidth("400px");
//
//        /*
//        ???????????? ?? ????????
//         */
//        Text saleAndPrice = new Text("???????????? ?? ????????");
//        FormLayout formSalePrice = new FormLayout();
//        price = new ComboBox<>();
//        price.setItems("??????????????", "????????????????????????");
//        discountCard = new TextField();
//        formSalePrice.addFormItem(price, "????????");
//        formSalePrice.addFormItem(discountCard, "???????????????????? ??????????");
//        formSalePrice.setWidth("400px");
//
//        Text access = new Text("????????????");
//        FormLayout accessForm = new FormLayout();
//        employee = new ComboBox<>();
//        employee.setItems("???????????? ???????? ??????????????????","?????????? ?????????? ??????????????????");
//        departmentEmployee = new ComboBox<>();
//        departmentEmployee.setItems("?????????????????????? ????????","?????????????????? ????????????");
//        Checkbox generalAccess = new Checkbox();
//
//        accessForm.addFormItem(employee, "??????????????????");
//        accessForm.addFormItem(departmentEmployee,"??????????");
//        accessForm.addFormItem(generalAccess, "?????????? ????????????");
//        accessForm.setWidth("400px");
//
//        verticalLayout.add(textContragent, formContragent, textContactContragent, formLayout, textRequisites,
//                formRequisites, saleAndPrice,formSalePrice, access, accessForm);
//        leftLayout.add(verticalLayout);
//        return leftLayout;
//    }
//
//    /*
//    ?????????????? ?? ????????????
//     */
//    private VerticalLayout rightGroupButtonLayout() {
//        VerticalLayout rightLayout = new VerticalLayout();
//
//        Tab details = new Tab("??????????????");
//        Tab payment = new Tab("????????????");
//        Tab shipping = new Tab("??????????????????");
//        Tab files = new Tab("??????????");
//        Tab indicators = new Tab("????????????????????");
//        Tabs tabs = new Tabs(details, payment, shipping, files, indicators);
//        rightLayout.add(tabs);
//        return rightLayout;
//    }
//    public void setContragentsList(ContragentsList contragentsList) {
//        this.contragentsList = contragentsList;
//    }
//
//    public void buildForNew(ContractorDto contractorDto){
//        removeAll();
//        // ?????????????????? ???? null ???????????????? new ?????? edit ??????????.
//        if(contractorDto == null){
//            newForm = true;
//            contractorDto = ContractorDto.builder()
//                    .name("")
//                    .status("")
//                    .contractorGroupName("")
//                    .fax("")
//                    .email("")
//                    .address(new AddressDto())
//                    .comment("")
//                    .code("")
//                    .outerCode("")
//                    .build();
//        }
//        else {
//            contractorDto = contractorService.getById(contractorDto.getId());
//            newForm = false;
//            System.out.println("??????????????:" + contractorDto);
//        }
//        if(contractorDto.getBankAccountDtos() == null)contractorDto.setBankAccountDtos(new ArrayList<>());
//        if(contractorDto.getContacts() == null) contractorDto.setContacts(new ArrayList<>());
//        this.contractorDto = contractorDto;
//        this.events = new Events(callService, contractorDto);
//        add(createButton(contractorDto), getNameContragent(contractorDto),groupBlockLayout());
//    }
//
//}
