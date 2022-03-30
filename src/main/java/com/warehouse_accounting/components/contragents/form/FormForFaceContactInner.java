package com.warehouse_accounting.components.contragents.form;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.warehouse_accounting.models.dto.ContractorFaceContactDto;

import java.lang.management.BufferPoolMXBean;


public class FormForFaceContactInner {

    private TextField allNames;
    private TextField position;
    private TextField phone;
    private TextField email;
    private TextField comment;

    private ContractorFaceContactDto faceContactDto;

    private boolean newFaceContact = false;
    private boolean deleted = false;

    public VerticalLayout getFormForFaceContact(ContractorFaceContactDto contactDto){
        if(contactDto == null) {
            contactDto = ContractorFaceContactDto.builder()
                    .allNames("")
                    .position("")
                    .phone("")
                    .email("")
                    .comment("").build();
            newFaceContact = true;
        }
        this.faceContactDto = contactDto;
        VerticalLayout main = new VerticalLayout();

        allNames = new TextField();
        if(contactDto.getAllNames() != null) allNames.setValue(contactDto.getAllNames());
        position = new TextField();
        if(contactDto.getPosition() != null) position.setValue(contactDto.getPosition());
        phone = new TextField();
        if(contactDto.getPhone() != null) phone.setValue(contactDto.getPhone());
        email = new TextField();
        if(contactDto.getEmail() != null) email.setValue(contactDto.getEmail());
        comment = new TextField();
        if(contactDto.getComment() != null) comment.setValue(contactDto.getComment());

        FormLayout form = new FormLayout();

        form.addFormItem(allNames, "ФИО");
        form.addFormItem(position, "Должность");
        form.addFormItem(phone, "Телефон");
        form.addFormItem(email, "Электронный адресс");
        form.addFormItem(comment, "Комментарий");

        Button delete = new Button("Удалить");
        delete.addClickListener(e->{
            main.setVisible(false);
            deleted = true;
        });

        main.add(delete, form);
        return main;
    }

    public ContractorFaceContactDto getContactFace(){
        faceContactDto.setAllNames(allNames.getValue());
        faceContactDto.setPosition(position.getValue());
        faceContactDto.setPhone(phone.getValue());
        faceContactDto.setEmail(email.getValue());
        faceContactDto.setComment(comment.getValue());
        return faceContactDto;
    }

    public boolean isDeleted() {
        return deleted;
    }
    public boolean isNewFaceContact() {
        return newFaceContact;
    }
    public FormForFaceContactInner() {
    }
}
