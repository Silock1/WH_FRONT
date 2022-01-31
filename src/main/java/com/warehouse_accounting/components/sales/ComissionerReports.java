package com.warehouse_accounting.components.sales;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.warehouse_accounting.components.sales.forms.InvoiceForm;
import com.warehouse_accounting.components.sales.grids.ComissionerReportsGridLayout;

public class ComissionerReports extends VerticalLayout {

    private final ComissionerReportsGridLayout reportsGridLayout;
//    private final TextField textFieldGridSelected = new TextField();
    private final Div parentLayer;

    public ComissionerReports(Div parentLayer) {
        this.parentLayer = parentLayer;
        reportsGridLayout = new ComissionerReportsGridLayout();
        Div pageContent = new Div();
        pageContent.add(reportsGridLayout);
        pageContent.setSizeFull();
        add(buttonsGroup(), pageContent);
    }

    private HorizontalLayout buttonsGroup() {
        HorizontalLayout groupControl = new HorizontalLayout();

        Button helpButton = new Button(new Icon(VaadinIcon.QUESTION_CIRCLE));
        helpButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);

        Label textProducts = new Label();
        textProducts.setText("Счета покупателям");

        Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH));
        refreshButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);

        Button addOrderButton = new Button("Счет", new Icon(VaadinIcon.PLUS));
        addOrderButton.addThemeVariants(ButtonVariant.LUMO_SMALL);

        addOrderButton.addClickListener(buttonClickEvent -> {
            InvoiceForm invoiceForm = new InvoiceForm(parentLayer, this);
            parentLayer.removeAll();
            parentLayer.add(invoiceForm);

        });



        Button addFilterButton = new Button("Фильтр");
        addFilterButton.addThemeVariants(ButtonVariant.LUMO_SMALL);

        TextField searchField = new TextField();
        searchField.setPlaceholder("Номер или комментарий");
        searchField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        searchField.addInputListener(inputEvent -> {

        });

//        HorizontalLayout editMenuBar = getEditMenuBar();
//        HorizontalLayout statusMenuBar = getStatusMenuBar();
//        HorizontalLayout createMenuBar = getCreateMenuBar();
//        HorizontalLayout printMenuBar = getPrintMenuBar();
//
//        groupControl.add(helpButton, textProducts, refreshButton, addOrderButton,
//            addFilterButton, searchField, editMenuBar, statusMenuBar, createMenuBar, printMenuBar);
        setSizeFull();
        return groupControl;
    }


}
