package com.warehouse_accounting.components.goods.forms;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;


public class InvoiceForm extends VerticalLayout {

    private final Div parentLayer;
    private final Component returnLayer;

    public InvoiceForm(Div parentLayer, Component returnLayer) {
        this.parentLayer = parentLayer;
        this.returnLayer = returnLayer;
        VerticalLayout initTopButtons = initTopButtons();
        VerticalLayout initForms = initAllForms();
        add(initTopButtons,initForms);
    }

    private VerticalLayout initAllForms() {
        VerticalLayout verticalLayout = new VerticalLayout();

        //Forms
        HorizontalLayout formGroups = new HorizontalLayout();
        formGroups.add(forms1(), forms2());

        verticalLayout.add(formGroups);

        return verticalLayout;
    }

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

    private VerticalLayout initTopButtons(){
        VerticalLayout verticalLayout = new VerticalLayout();

        //Buttons
        Button save = new Button("Сохранить", e -> {
            //Создание и сохранение сущьности
            parentLayer.removeAll();
            parentLayer.add(returnLayer);
        });
        save.getStyle().set("marginTop","-0.1%");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS,ButtonVariant.LUMO_SMALL);

        Button close = new Button("Закрыть", e -> {
            parentLayer.removeAll();
            parentLayer.add(returnLayer);
        });
        close.getStyle().set("marginTop","-0.1%");
        close.addThemeVariants(ButtonVariant.LUMO_CONTRAST,ButtonVariant.LUMO_SMALL);

        MenuBar change = new MenuBar();
        change.addThemeVariants(MenuBarVariant.LUMO_CONTRAST,MenuBarVariant.LUMO_SMALL);
        MenuItem moveChange = change.addItem("Изменить");
        SubMenu subMenuChange = moveChange.getSubMenu();
        subMenuChange.addItem("Удалить");
        subMenuChange.addItem("Копировать");

        MenuBar create = new MenuBar();
        create.addThemeVariants(MenuBarVariant.LUMO_CONTRAST,MenuBarVariant.LUMO_SMALL);
        MenuItem moveCreate = create.addItem("Создать документ");
        SubMenu subMenuCreate = moveCreate.getSubMenu();
        subMenuCreate.addItem("Приемка");
        subMenuCreate.addItem("Исходящий платеж");
        subMenuCreate.addItem("Расходный ордер");

        MenuBar print = new MenuBar();
        print.addThemeVariants(MenuBarVariant.LUMO_CONTRAST,MenuBarVariant.LUMO_SMALL);
        MenuItem itemPrint = print.addItem(new Icon(VaadinIcon.PRINT));
        itemPrint.add(new Text("Печать"));
        SubMenu subMenuPrint = itemPrint.getSubMenu();
        subMenuPrint.addItem("Счет поставщика");
        subMenuPrint.addItem("Ценник (70х49,5 мм)");
        subMenuPrint.addItem("Термоэтикетка (58х40 мм)");
        subMenuPrint.addItem("Комплект...");
        subMenuPrint.addItem("Настроить...");

        MenuBar send = new MenuBar();
        send.addThemeVariants(MenuBarVariant.LUMO_CONTRAST,MenuBarVariant.LUMO_SMALL);
        MenuItem itemSend = send.addItem(new Icon(VaadinIcon.SHARE));
        itemSend.add(new Text("Отправить"));
        SubMenu subMenuSend = itemSend.getSubMenu();
        subMenuSend.addItem("Счет поставщика");
        subMenuSend.addItem("Комплект...");

        HorizontalLayout groupButton = new HorizontalLayout();
        groupButton.add(save,close,change,create,print,send);

        verticalLayout.add(groupButton);

        return verticalLayout;
    }
}
