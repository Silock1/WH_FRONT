package com.warehouse_accounting.components.adjustments.adjustmentButton;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class AccountBalances extends VerticalLayout {

    private final Div parentLayer ;
    private final Component returnLayer;

    public AccountBalances(Div parentLayer, Component returnLayer) {
        this.parentLayer = parentLayer;
        this.returnLayer = returnLayer;

        VerticalLayout form = initForm();
        add(form);
    }

    private VerticalLayout initForm() {
        VerticalLayout verticalLayout = new VerticalLayout();

        Button save = new Button("Сохранить", e -> {
            //TODO повод поработать этот функционал
        });

        Button close = new Button("Закрыть", e -> {
            //TODO повод поработать этот функционал
        });

        MenuBar accountBalances = new MenuBar();

        MenuItem create = accountBalances.addItem("Изменить");
        create.add(new Icon(VaadinIcon.CARET_DOWN));

        SubMenu creteList = create.getSubMenu();
        MenuItem accountBalance = creteList.addItem("Удалить");
        accountBalance.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });

        MenuItem print = accountBalances.addItem(new Icon(VaadinIcon.PRINT));
        print.add("Печать");
        print.add(new Icon(VaadinIcon.CARET_DOWN));

        SubMenu printSubMenu = print.getSubMenu();
        MenuItem set = printSubMenu.addItem("Комплект...");
        set.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });

        MenuItem settings = printSubMenu.addItem("Настроить...");
        settings.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });

        MenuItem toSend = accountBalances.addItem(new Icon(VaadinIcon.ENVELOPE));
        toSend.add("Отправить");
        toSend.add(new Icon(VaadinIcon.CARET_DOWN));

        SubMenu toSendSubMenu = toSend.getSubMenu();
        MenuItem set1 = toSendSubMenu.addItem("Комплект...");
        set1.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });

        HorizontalLayout groupButton = new HorizontalLayout();
        groupButton.setHeightFull();
        groupButton.add(save,close, accountBalances);

        FormLayout form = getFormLayout();
        HorizontalLayout groupForm = new HorizontalLayout();
        groupForm.add(form);
        verticalLayout.add(groupButton, groupForm);
        return verticalLayout;
    }

    private FormLayout getFormLayout() {
        FormLayout form = new FormLayout();
        TextField nameField = new TextField();
        nameField.setLabel("Корректировка остатков на счете №");
        nameField.setPlaceholder("");
        TextField codField = new TextField();
        codField.setLabel("");
        codField.setPlaceholder("");
        TextField articleField = new TextField();
        articleField.setLabel("Организация");
        articleField.setPlaceholder("");

        form.add(nameField, codField, articleField);

        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("25em", 1),
                new FormLayout.ResponsiveStep("10em", 2),
                new FormLayout.ResponsiveStep("40em", 3));
        return form;
    }
}
