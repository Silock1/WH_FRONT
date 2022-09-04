//package com.warehouse_accounting.components.contragents.form;
//
//import com.vaadin.flow.component.Text;
//import com.vaadin.flow.component.button.Button;
//import com.vaadin.flow.component.checkbox.Checkbox;
//import com.vaadin.flow.component.formlayout.FormLayout;
//import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.component.textfield.TextArea;
//import com.vaadin.flow.component.textfield.TextField;
//import com.vaadin.flow.data.binder.Binder;
//import com.vaadin.flow.data.binder.ValidationException;
//import com.vaadin.flow.data.converter.StringToLongConverter;
//import com.warehouse_accounting.models.dto.BankAccountDto;
//import com.warehouse_accounting.models.dto.ContractorDto;
//import com.warehouse_accounting.services.impl.BankAccountServiceImpl;
//import com.warehouse_accounting.services.interfaces.BankAccountService;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//public class FormBankAccauntInner {
//
//    private TextField bic;
//
////    private Fie contractor;
//    private TextField bank;
//    private TextArea address;
//    private TextField correspondentAccount;
//    private TextField account;
//    private Checkbox checkbox;
//
//    private BankAccountDto bankAccountDto;
//    private BankAccountService bankAccountService;
//    private boolean newAccount = false;
//    private boolean deleted = false;
//
//    private Binder<BankAccountDto> bankAccountDtoBinder = new Binder<>(BankAccountDto.class);
//
//    public FormBankAccauntInner(BankAccountService bankAccountService, BankAccountDto bankAccountDto) {
//        this.bankAccountService = bankAccountService;
//    }
//
//    public boolean isDeleted() {
//        return deleted;
//    }
//
//    public boolean isNewAccount() {
//        return newAccount;
//    }
//
//    public VerticalLayout getFormForBankAccount(BankAccountDto bankAccountDto) throws ValidationException {
////        if(bankAccountDto == null){
////            bankAccountDto = BankAccountDto.builder()
////                    .rcbic("")
////                    .bank("")
////                    .correspondentAccount("")
////                    .account("")
////                    .mainAccount(false)
////                    .build();
////            newAccount = true;
////        }
//
//
////        this.bankAccountDto = bankAccountDto;
//        VerticalLayout main = new VerticalLayout();
//
//        Button button = new Button("Удалить");
//        button.addClickListener(e -> {
////            bankAccountDtoBinder.removeBean();
////            bankAccountService.deleteById(this.bankAccountDto.getId());
//            main.setVisible(false);
//            deleted = true;
//        });
//
//        Text text = new Text("Расчётный счёт");
//
//        bic = new TextField();
////        bankAccountDto.setRcbic(bic.getValue());
//
//        bankAccountDtoBinder.forField(bic).asRequired("БИК не должно быть пустым!")
//                .bind(BankAccountDto::getRcbic, BankAccountDto::setRcbic);
//        bic.setValue(bankAccountDto.getRcbic() == null ? " " : bankAccountDto.getRcbic());
//
//
//        TextField contractorId = new TextField("Contractor ID");
//
//        bankAccountDtoBinder.forField(contractorId)
//                .withConverter(new StringToLongConverter("error message"))
//                .bind(item -> item.getContractorDto().getId(), (item, value) -> item.getContractorDto().setId(value));
//
//        contractorId.setValue(bankAccountDto.getContractorDto() == null ?
//                " " : bankAccountDto.getContractorDto().toString());
//
////        if(bankAccountDto.getRcbic() != null) bic.setValue(bankAccountDto.getRcbic());
////        bank = new TextField();
////        if(bankAccountDto.getBank() != null)bank.setValue(bankAccountDto.getBank());
////        address = new TextField();
////        if(bankAccountDto.getAddress() != null) address.setValue(bankAccountDto.getAddress());
////        correspondentAccount = new TextField();
////        if(bankAccountDto.getCorrespondentAccount() != null) correspondentAccount.setValue(bankAccountDto.getCorrespondentAccount());
////        account = new TextField();
////        if(bankAccountDto.getAccount() != null)account.setValue(bankAccountDto.getAccount());
//        checkbox = new Checkbox();
//        checkbox.setLabel("Основной счёт");
////        if(bankAccountDto.getMainAccount() != null) checkbox.setValue(bankAccountDto.getMainAccount());
//
//        FormLayout formLayout = new FormLayout();
//        formLayout.addFormItem(bic, "БИК");
//        formLayout.addFormItem(contractorId, "Contractor id");
////        formLayout.addFormItem(bank, "Банк");
////        formLayout.addFormItem(address, "Адрес");
////        formLayout.addFormItem(correspondentAccount, "Kopp. счёт");
////        formLayout.addFormItem(account, "Расчётный счёт");
////        formLayout.addFormItem(checkbox, "");
//        main.add(button, text, formLayout);
//
//        return main;
//    }
//
//    public BankAccountDto getBankAccount() {
//        bankAccountDto.setRcbic(bic.getValue());
//        bankAccountDto.setBank(bank.getValue());
////        Address tmpAddress = new Address();
////        tmpAddress.setValue(address.getValue());
////        bankAccountDto.setAddress(tmpAddress); // temporary
//        bankAccountDto.setCorrespondentAccount(correspondentAccount.getValue());
//        bankAccountDto.setAccount(account.getValue());
//        bankAccountDto.setMainAccount(checkbox.getValue());
//        return bankAccountDto;
//    }
//}
