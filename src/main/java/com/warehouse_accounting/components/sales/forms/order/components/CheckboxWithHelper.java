package com.warehouse_accounting.components.sales.forms.order.components;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.shared.Registration;

public class CheckboxWithHelper extends Span {
    private Checkbox checkbox = new Checkbox();
    private Icon icon = new Icon(VaadinIcon.QUESTION_CIRCLE_O);
    private String helpText;

    public CheckboxWithHelper() {
        icon.addClassName("dtb-question");
        checkbox.addClassName("dtb-flag");
        addClassName("dtb-baseline");
        add(icon, checkbox);
    }

    public CheckboxWithHelper(String labelText) {
        this();
        checkbox.setLabel(labelText);
    }

    public CheckboxWithHelper(String labelText, String helpText) {
        this(labelText);
        icon.addClickListener(event -> Notification.show("Not Implemented (" + helpText + ")"));
    }

    public Registration addValueChangeListener(HasValue.ValueChangeListener<? super AbstractField.ComponentValueChangeEvent<Checkbox, Boolean>> listener) {
        return checkbox.addValueChangeListener(listener);
    }

    public Boolean getValue() { return checkbox.getValue(); }

    public void setValue(Boolean value) { checkbox.setValue(value); }

    public void setEnabled(Boolean enable) { checkbox.setEnabled(enable); }
}
