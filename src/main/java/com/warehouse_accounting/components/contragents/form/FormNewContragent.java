package com.warehouse_accounting.components.contragents.form;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class FormNewContragent extends VerticalLayout {

    public FormNewContragent() {

        add(getGroupButton());
    }

    private HorizontalLayout getGroupButton() {
        HorizontalLayout controlGroupButton = new HorizontalLayout();
        Button createButton = new Button("Сохранить");


        controlGroupButton.add(createButton);
        return controlGroupButton;
    }

}
