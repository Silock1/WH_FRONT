package com.warehouse_accounting.components.contragents;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
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
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.contragents.grids.ContragentsListGridLayout;
import com.warehouse_accounting.components.sales.grids.SalesGridLayout;

/*
Контрагенты
 */
@SpringComponent
@UIScope
public class ContragentsList extends VerticalLayout {

    private ContragentsListGridLayout contragentsListGridLayout;
//    private final TextField textFieldGridSelected = new TextField();

    public ContragentsList(ContragentsListGridLayout contragentsListGridLayout) {
        this.contragentsListGridLayout=contragentsListGridLayout;

        add(getGroupButtons());

    }

    private HorizontalLayout getGroupButtons() {

        HorizontalLayout controlButton = new HorizontalLayout();

        Button helpButton = new Button(new Icon(VaadinIcon.QUESTION_CIRCLE));
        helpButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);

        Label textLabel = new Label("Контрагенты");

        Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH));
        refreshButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);

        Button addContragent = new Button(("Контрагент"), new Icon(VaadinIcon.PLUS));
        addContragent.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button filter = new Button("Фильтр");
        filter.addThemeVariants(ButtonVariant.LUMO_SMALL);

        TextField textField = new TextField();
        textField.setPlaceholder("Наим, тел, email, событие, коммент");
        textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        textField.setWidth("210px");

        MenuBar menuBar = new MenuBar();
        menuBar.addThemeVariants(MenuBarVariant.LUMO_ICON, MenuBarVariant.LUMO_CONTRAST);
        TextField count = new TextField();
        count.setValue("0");
        count.setWidth("20px");
        count.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        menuBar.addItem(count);

        Icon caretDownEdit = new Icon(VaadinIcon.CARET_DOWN);
        caretDownEdit.setSize("13px");
        Icon caretDownStatus = new Icon(VaadinIcon.CARET_DOWN);
        caretDownStatus.setSize("13px");

        HorizontalLayout editVision = new HorizontalLayout(new Text("Изменить"), caretDownEdit);
        editVision.setSpacing(false);
        MenuItem edit = menuBar.addItem(editVision);
        SubMenu editSubMenu = edit.getSubMenu();
        editSubMenu.addItem("Удалить").onEnabledStateChanged(false);
        editSubMenu.addItem("Копировать").onEnabledStateChanged(false);
        editSubMenu.addItem("Массовое редактирование");
        editSubMenu.addItem("Поместить в архив").onEnabledStateChanged(false);
        editSubMenu.addItem("Извлечь из архива").onEnabledStateChanged(false);


        controlButton.add(textLabel, refreshButton, addContragent, filter, textField);
        setSizeFull();
        return controlButton;
    }


}
