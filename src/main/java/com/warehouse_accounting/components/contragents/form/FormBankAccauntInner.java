package com.warehouse_accounting.components.contragents.form;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.warehouse_accounting.models.dto.BankAccountDto;
import com.warehouse_accounting.services.interfaces.BankAccountService;

public class FormBankAccauntInner {

    private TextField bic;
    private TextField bank;
    private TextArea address;
    private TextField correspondentAccount;
    private TextField account;
    private Checkbox checkbox;

    private BankAccountDto bankAccountDto;
    private BankAccountService bankAccountService;
    private boolean newAccount = false;
    private boolean deleted = false;

    public FormBankAccauntInner(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public boolean isNewAccount() {
        return newAccount;
    }

    public VerticalLayout getFormForBankAccount(BankAccountDto bankAccountDto){
        if(bankAccountDto == null){
            bankAccountDto = BankAccountDto.builder()
                    .rcbic("")
                    .bank("")
                    .correspondentAccount("")
                    .account("")
                    .mainAccount(false)
                    .build();
            newAccount = true;
        }
        this.bankAccountDto = bankAccountDto;
        VerticalLayout main = new VerticalLayout();
        Button button = new Button("Удалить");
        button.addClickListener(e ->{
            bankAccountService.deleteById(this.bankAccountDto.getId());
            main.setVisible(false);
            deleted = true;
        });

        Text text = new Text("Расчётный счёт");
        bic = new TextField();
        if(bankAccountDto.getRcbic() != null) bic.setValue(bankAccountDto.getRcbic());
        bank = new TextField();
        if(bankAccountDto.getBank() != null)bank.setValue(bankAccountDto.getBank());
//        address = new TextField();
//        if(bankAccountDto.getAddress() != null) address.setValue(bankAccountDto.getAddress());
        correspondentAccount = new TextField();
        if(bankAccountDto.getCorrespondentAccount() != null) correspondentAccount.setValue(bankAccountDto.getCorrespondentAccount());
        account = new TextField();
        if(bankAccountDto.getAccount() != null)account.setValue(bankAccountDto.getAccount());
        checkbox = new Checkbox();
        checkbox.setLabel("Основной счёт");
        if(bankAccountDto.getMainAccount() != null) checkbox.setValue(bankAccountDto.getMainAccount());

        FormLayout formLayout = new FormLayout();
        formLayout.addFormItem(bic, "БИК");
        formLayout.addFormItem(bank, "Банк");
        formLayout.addFormItem(address, "Адрес");
        formLayout.addFormItem(correspondentAccount, "Kopp. счёт");
        formLayout.addFormItem(account, "Расчётный счёт");
        formLayout.addFormItem(checkbox, "");
        main.add(button,text,formLayout);

        return main;
    }
    public BankAccountDto getBankAccount(){
        bankAccountDto.setRcbic(bic.getValue());
        bankAccountDto.setBank(bank.getValue());
        bankAccountDto.setAddress(address.getValue());
        bankAccountDto.setCorrespondentAccount(correspondentAccount.getValue());
        bankAccountDto.setAccount(account.getValue());
        bankAccountDto.setMainAccount(checkbox.getValue());
        return bankAccountDto;
    }
}
