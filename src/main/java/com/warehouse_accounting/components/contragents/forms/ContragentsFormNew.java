package com.warehouse_accounting.components.contragents.forms;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class ContragentsFormNew extends VerticalLayout {

    private final Div parentLayer;
    private final Component returnLayer;

    public ContragentsFormNew(Div parentLayer, Component returnLayer) {
        this.parentLayer = parentLayer;
        this.returnLayer = returnLayer;
        VerticalLayout form = initForm();
        add(form);
    }

    private VerticalLayout initForm() {
        VerticalLayout verticalLayout = new VerticalLayout();
        Button save = new Button("Сохранить");
        Button close = new Button("Закрыть");

        HorizontalLayout groupButton = new HorizontalLayout();
        groupButton.setHeightFull();
        groupButton.add(save, close);

        FormLayout form = getFormLayout();
        HorizontalLayout groupForm = new HorizontalLayout();
        groupForm.add(form);
        verticalLayout.add(groupButton, groupForm);

        return verticalLayout;
    }

    private FormLayout getFormLayout() {
        FormLayout form = new FormLayout();
        TextField nameField = new TextField();
        nameField.setLabel("Наименование");

        form.add(nameField);
        return form;
    }


}
