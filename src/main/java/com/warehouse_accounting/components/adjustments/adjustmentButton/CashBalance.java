package com.warehouse_accounting.components.adjustments.adjustmentButton;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class CashBalance extends VerticalLayout{

    private final Div parentLayer ;
    private final Component returnLayer;

    public CashBalance(Div parentLayer, Component returnLayer) {
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
        FormLayout form1 = getFormLayout1();
        HorizontalLayout groupForm = new HorizontalLayout();
        HorizontalLayout groupForm1 = new HorizontalLayout();

        groupForm.add(form);
        groupForm1.add(form1);
        verticalLayout.add(groupButton, groupForm ,groupForm1);


        return verticalLayout;
    }

    private FormLayout getFormLayout() {
        FormLayout form = new FormLayout();

        Label l1 = new Label("Корректировка остатков в кассе №");

        TextField nameField = new TextField();
        nameField.setPlaceholder("");
        nameField.setWidth("10%");

        Label l2 = new Label("от");

        TextField codField = new TextField();
        codField.setLabel("");
        codField.setPlaceholder("");

        form.add(l1, nameField, l2 ,codField);

            form.setResponsiveSteps(
                    new FormLayout.ResponsiveStep("120em", 1),
                    new FormLayout.ResponsiveStep("50em", 2),
                    new FormLayout.ResponsiveStep("50em", 3),
                    new FormLayout.ResponsiveStep("50em", 4));
        return form;
    }

    private FormLayout getFormLayout1() {
        FormLayout form1 = new FormLayout();

        Label l1 = new Label("Организация");
        l1.setWidth("10%");

        TextField nameField = new TextField();
        nameField.setPlaceholder("");

        Label l2 = new Label("Текущий остаток");
        l2.setWidth("10%");

        TextField codField = new TextField();
        codField.setLabel("");

        Label l3 = new Label("Сумма корректировки");
        l3.setWidth("10%");

        TextField nameField1 = new TextField();
        nameField1.setPlaceholder("");

        Label l4 = new Label("Итоговый остаток");
        l4.setWidth("10%");

        TextField codField1 = new TextField();
        codField1.setLabel("");

        form1.add(l1, nameField, l2 ,codField , l3 ,nameField1 ,l4 ,codField1);


        return form1;
    }
}
