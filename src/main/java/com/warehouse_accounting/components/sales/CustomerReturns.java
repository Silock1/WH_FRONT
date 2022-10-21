package com.warehouse_accounting.components.sales;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.warehouse_accounting.components.sales.filter.CustomerReturnsFilter;
import com.warehouse_accounting.components.sales.forms.CustomerReturnsForm;
import com.warehouse_accounting.components.sales.grids.CustomerReturnsGridLayout;

/*
Продажи / возвраты покупателей
 */
public class CustomerReturns extends VerticalLayout {
    private CustomerReturnsGridLayout customerReturnsGridLayout;
    private final TextField textFieldGridSelected = new TextField();
    private final Div parentLayer;
    private final CustomerReturnsFilter customerReturnsFilter;

    public CustomerReturns(Div parentLayer, CustomerReturnsFilter customerReturnsFilter) {
        this.customerReturnsFilter = customerReturnsFilter;
        this.parentLayer = parentLayer;
        customerReturnsGridLayout = new CustomerReturnsGridLayout(textFieldGridSelected);
        Div pageContent = new Div();
        pageContent.add(customerReturnsGridLayout);
        pageContent.setSizeFull();
        add(getGroupButtons(), customerReturnsFilter, pageContent);
    }

    private HorizontalLayout getGroupButtons() {
        HorizontalLayout groupControl = new HorizontalLayout();

        Button helpButton = new Button(new Icon(VaadinIcon.QUESTION_CIRCLE));
        helpButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);

        Label textProducts = new Label();
        textProducts.setText("Возвраты покупателей");

        Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH));
        refreshButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);

        Button addOrderButton = new Button("Возврат", new Icon(VaadinIcon.PLUS));
        addOrderButton.addThemeVariants(ButtonVariant.LUMO_SMALL);

        addOrderButton.addClickListener(buttonClickEvent -> {
            CustomerReturnsForm returnForm = new CustomerReturnsForm(parentLayer, this);
            parentLayer.removeAll();
            parentLayer.add(returnForm);
        });

        Button addFilterButton = new Button("Фильтр");
        addFilterButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        addFilterButton.addClickListener(e ->
                {
                    System.out.println("КНОПКА НАЖАТА");
                    customerReturnsFilter.setVisible(!customerReturnsFilter.isVisible());
                }
        );

        TextField searchField = new TextField();
        searchField.setPlaceholder("Номер или комментарий");
        searchField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        searchField.addInputListener(inputEvent -> {

        });

        HorizontalLayout editMenuBar = getEditMenuBar();
        HorizontalLayout statusMenuBar = getStatusMenuBar();
        HorizontalLayout printMenuBar = getPrintMenuBar();

        Button setting = new Button(new Icon(VaadinIcon.COG));
        setting.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        setting.addClickListener(event -> {

        });

        groupControl.add(helpButton, textProducts, refreshButton, addOrderButton,
                addFilterButton, searchField, editMenuBar, statusMenuBar, printMenuBar, setting);
        setSizeFull();
        return groupControl;
    }

    private HorizontalLayout getEditMenuBar() {
        Icon caretDownIcon = new Icon(VaadinIcon.CARET_DOWN);
        caretDownIcon.setSize("12px");
        textFieldGridSelected.setReadOnly(true);
        textFieldGridSelected.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        textFieldGridSelected.setWidth("30px");
        textFieldGridSelected.setValue("0");

        MenuBar editMenuBar = new MenuBar();
        editMenuBar.addThemeVariants(MenuBarVariant.LUMO_SMALL);
        HorizontalLayout editItem = new HorizontalLayout(new Text("Изменить"), caretDownIcon);
        editItem.setSpacing(false);
        editItem.setAlignItems(Alignment.CENTER);

        MenuItem editMenu = editMenuBar.addItem(editItem);
        editMenu.getSubMenu().addItem("Удалить", menuItemClickEvent -> {

        }).getElement().setAttribute("disabled", true);

        editMenu.getSubMenu().addItem("Массовое редактирование", menuItemClickEvent -> {

        });

        editMenu.getSubMenu().addItem("Провести", menuItemClickEvent -> {

        }).getElement().setAttribute("disabled", true);

        editMenu.getSubMenu().addItem("Снять проведение", menuItemClickEvent -> {

        }).getElement().setAttribute("disabled", true);

        HorizontalLayout groupEdit = new HorizontalLayout();
        groupEdit.add(textFieldGridSelected, editMenuBar);
        groupEdit.setSpacing(false);
        groupEdit.setAlignItems(Alignment.CENTER);
        return groupEdit;
    }

    private HorizontalLayout getStatusMenuBar() {
        Icon caretDownIcon = new Icon(VaadinIcon.CARET_DOWN);
        caretDownIcon.setSize("12px");
        textFieldGridSelected.setReadOnly(true);
        textFieldGridSelected.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        textFieldGridSelected.setWidth("30px");
        textFieldGridSelected.setValue("0");

        MenuBar statusMenuBar = new MenuBar();
        statusMenuBar.addThemeVariants(MenuBarVariant.LUMO_SMALL);
        HorizontalLayout horizontalLayout = new HorizontalLayout(new Text("Статус"), caretDownIcon);
        horizontalLayout.setSpacing(false);
        horizontalLayout.setAlignItems(Alignment.CENTER);

        MenuItem statusItem = statusMenuBar.addItem(horizontalLayout);

        statusItem.getSubMenu().addItem("Настроить...", e -> {

        });
        HorizontalLayout groupStatus = new HorizontalLayout();
        groupStatus.add(statusMenuBar);
        groupStatus.setSpacing(false);
        groupStatus.setAlignItems(Alignment.CENTER);
        return groupStatus;
    }

    private HorizontalLayout getPrintMenuBar() {
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

        print.getSubMenu().addItem("Список возвратов", e -> {

        });

        print.getSubMenu().addItem("Возврат покупателя", e -> {

        }).getElement().setAttribute("disabled", true);

        print.getSubMenu().addItem("Комплект...", e -> {

        }).getElement().setAttribute("disabled", true);

        print.getSubMenu().addItem("Настроить...", e -> {
        });

        HorizontalLayout groupPrint = new HorizontalLayout();
        groupPrint.add(printMenuBar);
        groupPrint.setSpacing(false);
        groupPrint.setAlignItems(Alignment.CENTER);
        return groupPrint;
    }
}
