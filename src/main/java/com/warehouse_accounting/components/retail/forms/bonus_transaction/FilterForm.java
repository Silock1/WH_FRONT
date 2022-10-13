package com.warehouse_accounting.components.retail.forms.bonus_transaction;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class FilterForm extends VerticalLayout {

    public FilterForm() {
        setVisible(false);
        add(lineOne(),lineTwo());

    }

    private HorizontalLayout lineOne() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.add(
                new Button("CLOSE", event -> this.setVisible(false)),
                new Button(),
                new Button(),
                new Button(),
                new Button(),
                new Input(),
                new Input(),
                new Input(),
                new Input(),
                new Input(),
                new Input(),
                new Input()

        );

        return layout;
    }

    private HorizontalLayout lineTwo() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.add(

                new Input(),
                new Input(),
                new Input(),
                new Input(),
                new Input()

        );

        return layout;
    }
}
