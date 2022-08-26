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
import com.warehouse_accounting.components.sales.forms.InvoiceForm;
import com.warehouse_accounting.components.sales.grids.SalesInvoicesGridLayout;

/*
Продажи / возвраты покупателей
 */
public class CustomerReturns extends VerticalLayout {
    private SalesInvoicesGridLayout salesInvoicesGridLayout;
    private final TextField textFieldGridSelected = new TextField();
    private final Div parentLayer;

    public CustomerReturns(Div parentLayer) {
        this.parentLayer = parentLayer;
        salesInvoicesGridLayout = new SalesInvoicesGridLayout(textFieldGridSelected);
        Div pageContent = new Div();
        pageContent.setSizeFull();
        add(getGroupButtons(), pageContent);
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
            InvoiceForm invoiceForm = new InvoiceForm(parentLayer, this);
            parentLayer.removeAll();
            parentLayer.add(invoiceForm);

        });
    }
}
