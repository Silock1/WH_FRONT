package com.warehouse_accounting.components.address;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;

public class AddressView extends VerticalLayout {

    public AddressView(String title) {
        TextArea fullAddress = new TextArea(title);
    }
}
