package com.warehouse_accounting.components.contractors;

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
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;


import com.warehouse_accounting.components.contractors.grids.ContractorsGridLayout;
import com.warehouse_accounting.components.sales.grids.SalesGridLayout;
import com.warehouse_accounting.services.interfaces.CallService;

public class ContractorsOrders extends VerticalLayout {

    private ContractorsGridLayout contractorsGridLayout;
    private final TextField textFieldGridSelected = new TextField();
    private final Div parentLayer;

    public ContractorsOrders(Div parentLayer) {
        this.parentLayer = parentLayer;
        contractorsGridLayout = new ContractorsGridLayout((CallService) textFieldGridSelected);
        Div pageContent = new Div();
        pageContent.add(contractorsGridLayout);
        pageContent.setSizeFull();
        add(getGroupButtons(), pageContent);
    }

    private HorizontalLayout getGroupButtons() {
        HorizontalLayout groupControl = new HorizontalLayout();

        Button helpButton = new Button(new Icon(VaadinIcon.QUESTION_CIRCLE));
        helpButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);

        Label textProducts = new Label();
        textProducts.setText("Заказы покупателей");

        Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH));
        refreshButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);

        Button addFilterButton = new Button("Фильтр");
        addFilterButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        TextField searchField = new TextField();
        searchField.setPlaceholder("Номер или комментарий");
        searchField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        searchField.addInputListener(inputEvent -> {
        });

        groupControl.add(helpButton, textProducts, refreshButton,
                addFilterButton, searchField);
        setSizeFull();
        return groupControl;
    }

}
