package com.warehouse_accounting.components.goods.forms;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class InvoiceForm extends VerticalLayout {

    private final Div parentLayer;
    private final Component returnLayer;

    public InvoiceForm(Div parentLayer, Component returnLayer) {
        this.parentLayer = parentLayer;
        this.returnLayer = returnLayer;
        VerticalLayout forms = initForms();
        add(forms);
    }

    private VerticalLayout initForms() {
        VerticalLayout verticalLayout = new VerticalLayout();

        //Buttons
        Button save = new Button("Сохранить", e -> {
            //Создание и сохранение сущьности
            parentLayer.removeAll();
            parentLayer.add(returnLayer);
        });

        Button close = new Button("Закрыть", e -> {
            parentLayer.removeAll();
            parentLayer.add(returnLayer);
        });

        HorizontalLayout groupButton = new HorizontalLayout();
        groupButton.setHeightFull();
        groupButton.add(save, close);
        verticalLayout.add(groupButton);

        //Forms
        HorizontalLayout formGroups = new HorizontalLayout();
        formGroups.add(forms1(), forms2());

        verticalLayout.add(formGroups);

        return verticalLayout;
    } //Init Form

    private VerticalLayout forms1() {
        VerticalLayout verticalLayout = new VerticalLayout();

        //Fields
        //Organization
        FormLayout formOrganization = new FormLayout();
        TextField fieldOrganization = new TextField();
        formOrganization.addFormItem(fieldOrganization,"Организация");
        formOrganization.setResponsiveSteps(new FormLayout.ResponsiveStep("25em", 1));

        //ContrAgent
        FormLayout formContrAgent = new FormLayout();
        TextField fieldContrAgent = new TextField();
        formContrAgent.addFormItem(fieldContrAgent,"Контрагент");
        formContrAgent.setResponsiveSteps(new FormLayout.ResponsiveStep("25em", 1));

        //DatePay
        FormLayout formDatePay = new FormLayout();
        DatePicker datePickerPay = new DatePicker();
        formDatePay.addFormItem(datePickerPay,"Дата оплаты");
        formDatePay.setResponsiveSteps(new FormLayout.ResponsiveStep("25em", 1));

        //IncomingNumber
        FormLayout formIncomingNumber = new FormLayout();
        TextField fieldIncomingNumber = new TextField();
        formIncomingNumber.addFormItem(fieldIncomingNumber,"Входящий номер");
        formIncomingNumber.setResponsiveSteps(new FormLayout.ResponsiveStep("25em", 1));

        verticalLayout.add(formOrganization,formContrAgent,formDatePay,formIncomingNumber);

        return verticalLayout;
    }

    private VerticalLayout forms2() {
        VerticalLayout verticalLayout = new VerticalLayout();

        //Warehouse
        FormLayout formWarehouse = new FormLayout();
        TextField fieldWarehouse = new TextField();
        formWarehouse.addFormItem(fieldWarehouse,"Склад");
        formWarehouse.setResponsiveSteps(new FormLayout.ResponsiveStep("25em", 1));

        //Contract
        FormLayout formContract = new FormLayout();
        TextField fieldContract = new TextField();
        formContract.addFormItem(fieldContract,"Договор");
        formContract.setResponsiveSteps(new FormLayout.ResponsiveStep("25em", 1));

        //Project
        FormLayout formProject = new FormLayout();
        TextField fieldProject = new TextField();
        formProject.addFormItem(fieldProject,"Проект");
        formProject.setResponsiveSteps(new FormLayout.ResponsiveStep("25em", 1));

        verticalLayout.add(formWarehouse,formContract,formProject);

        return verticalLayout;
    }

}
