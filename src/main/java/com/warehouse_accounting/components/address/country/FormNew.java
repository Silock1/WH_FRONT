package com.warehouse_accounting.components.address.country;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.warehouse_accounting.services.interfaces.CountryService;

public class FormNew extends VerticalLayout {

    private final CountryService service;

    public FormNew(CountryService service) {
        this.service = service;

        Dialog dialog = new Dialog();

        FormLayout form = new FormLayout();

        TextField shortName = new TextField();
        form.addFormItem(shortName, "Краткое наименование");

        TextField longName = new TextField();
        form.addFormItem(longName, "Полное наименование");

        TextField code = new TextField();
        form.addFormItem(code, "Цифровой код");

        TextField codeOne = new TextField();
        form.addFormItem(codeOne, "Буквенный код(2)");

        TextField codeTwo = new TextField();
        form.addFormItem(codeTwo, "Буквенный код(3)");
    }

    private HorizontalLayout getButtonGroup() {
        Button createButton = new Button("Создать");
        Button cancelButton = new Button("Отмена");

        return new HorizontalLayout(
                createButton,
                cancelButton
        );
    }
}
