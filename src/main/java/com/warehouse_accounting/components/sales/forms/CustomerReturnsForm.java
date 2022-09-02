package com.warehouse_accounting.components.sales.forms;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class CustomerReturnsForm extends VerticalLayout {
    private final Div parentLayer;
    private final Component returnLayer;

    public CustomerReturnsForm(Div parentLayer, Component returnLayer) {
        this.parentLayer = parentLayer;
        this.returnLayer = returnLayer;
        createButtons();
        createDateLine();
        createColumns();
    }

    private void createColumns() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        VerticalLayout firstColumn = new VerticalLayout();
        firstColumn.setAlignItems(Alignment.END);
        VerticalLayout secondColumn = new VerticalLayout();
        secondColumn.setAlignItems(Alignment.END);

        HorizontalLayout dateLayout = new HorizontalLayout();
        Label dateLabel = new Label("Проект");
        DatePicker shippingDate = new DatePicker();
        dateLayout.add(dateLabel, shippingDate);

        firstColumn.add(createSubColumn("Организация", "company"),
                createSubColumn("Контрагент", "contractor"),
                createSubColumn("Проект", "project"));
        secondColumn.add(createSubColumn("Склад", "warehouse"),
                createSubColumn("Договор", "contract"),
                createSubColumn("Канал продаж", "salesChannel"));

        horizontalLayout.add(firstColumn, secondColumn);
        add(horizontalLayout);
    }

    private void createDateLine() {
        HorizontalLayout line = new HorizontalLayout();
        Label numberText = new Label();
        numberText.setText("Возврат покупателя №");
        TextField numberField = new TextField();
        Label fromText = new Label();
        fromText.setText(" от ");
        DatePicker createOrderDate = new DatePicker();

        Button customerReturnStatus = new Button("Статус");
        Checkbox accessCheckbox = new Checkbox();
        accessCheckbox.setLabel("Проведено");

        line.add(numberText, numberField, fromText, createOrderDate, customerReturnStatus, accessCheckbox);
        add(line);
    }

    private void createButtons() {
        HorizontalLayout buttons = new HorizontalLayout();
        Button saveButton = new Button("Сохранить", e -> {
            parentLayer.removeAll();
            parentLayer.add(returnLayer);
        });

        Button closeButton = new Button("Закрыть", e -> {
            parentLayer.removeAll();
            parentLayer.add(returnLayer);
        });

        HorizontalLayout editButton = getEditButton();
        HorizontalLayout createDocButton = getCreateDocButton();
        HorizontalLayout printButton = getPrintButton();
        Button shareButton = new Button("Отправить");

        buttons.add(saveButton, closeButton, editButton, createDocButton, printButton, shareButton);
        add(buttons);
    }

    private HorizontalLayout getEditButton(){
        Icon caretDownIcon = new Icon(VaadinIcon.CARET_DOWN);
        caretDownIcon.setSize("12px");

        MenuBar editMenuBar = new MenuBar();
        editMenuBar.addThemeVariants(MenuBarVariant.LUMO_SMALL);
        HorizontalLayout editItem = new HorizontalLayout(new Text("Изменить"), caretDownIcon);
        editItem.setSpacing(false);
        editItem.setAlignItems(Alignment.CENTER);

        MenuItem editMenu = editMenuBar.addItem(editItem);
        editMenu.getSubMenu().addItem("Удалить", menuItemClickEvent -> {
        }).getElement().setAttribute("disabled", true);

        HorizontalLayout groupEdit = new HorizontalLayout();
        groupEdit.add(editMenuBar);
        groupEdit.setSpacing(false);
        groupEdit.setAlignItems(Alignment.CENTER);
        return groupEdit;
    }

    private HorizontalLayout getCreateDocButton(){
        Icon caretDownIcon = new Icon(VaadinIcon.CARET_DOWN);
        caretDownIcon.setSize("12px");

        MenuBar createMenuBar = new MenuBar();
        createMenuBar.addThemeVariants(MenuBarVariant.LUMO_SMALL);
        HorizontalLayout editItem = new HorizontalLayout(new Text("Создать документ"), caretDownIcon);
        editItem.setSpacing(false);
        editItem.setAlignItems(Alignment.CENTER);

        MenuItem editMenu = createMenuBar.addItem(editItem);
        editMenu.getSubMenu().addItem("Исходящий платеж", menuItemClickEvent -> {
        });

        editMenu.getSubMenu().addItem("Расходный ордер", menuItemClickEvent -> {
        });

        editMenu.getSubMenu().addItem("Списание", menuItemClickEvent -> {
        });

        HorizontalLayout groupCreate = new HorizontalLayout();
        groupCreate.add(createMenuBar);
        groupCreate.setSpacing(false);
        groupCreate.setAlignItems(Alignment.CENTER);
        return groupCreate;
    }
    private HorizontalLayout getPrintButton(){
        MenuBar printMenuBar = new MenuBar();
        printMenuBar.addThemeVariants(MenuBarVariant.LUMO_SMALL);
        Icon printIcon = new Icon(VaadinIcon.PRINT);
        printIcon.setSize("12px");
        Icon caretDownIcon = new Icon(VaadinIcon.CARET_DOWN);
        caretDownIcon.setSize("12px");
        HorizontalLayout printItem = new HorizontalLayout(printIcon, new Text("  Печать"), caretDownIcon);
        printItem.setSpacing(false);
        printItem.setAlignItems(Alignment.CENTER);
        MenuItem print = printMenuBar.addItem(printItem);

        print.getSubMenu().addItem("Возврат покупателя", e -> {
        });

        print.getSubMenu().addItem("Комплект...", e -> {
        });

        print.getSubMenu().addItem("Настроить...", e -> {
        });

        HorizontalLayout groupPrint = new HorizontalLayout();
        groupPrint.add(printMenuBar);
        groupPrint.setSpacing(false);
        groupPrint.setAlignItems(Alignment.CENTER);
        return groupPrint;
    }

    private HorizontalLayout createSubColumn(String title, String className) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addClassName(className);
        Label label = new Label(title);
        TextField textField = new TextField();
        horizontalLayout.add(label, textField);
        return horizontalLayout;
    }
}
