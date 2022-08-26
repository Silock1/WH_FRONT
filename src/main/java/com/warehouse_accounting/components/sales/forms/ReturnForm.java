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

public class ReturnForm extends VerticalLayout {
    private final Div parentLayer;
    private final Component returnLayer;

    public ReturnForm(Div parentLayer, Component returnLayer) {
        this.parentLayer = parentLayer;
        this.returnLayer = returnLayer;
        createButtons();
        createDateLine();
        createColumns();
    }

    private void createColumns() {
    }

    private void createDateLine() {
    }

    private void createButtons() {
    }
}
