package com.warehouse_accounting.components.sales.forms.order.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

public class ColumnToggleContextMenu<T> extends ContextMenu {
    private Component target;

    public ColumnToggleContextMenu(Component target) {
        super(target);
        this.target = target;
        setOpenOnClick(true);
    }

    public ColumnToggleContextMenu() {
        this(new Button(new Icon(VaadinIcon.COG)));
        ((Button)target).addThemeVariants(ButtonVariant.LUMO_SMALL);
    }

    public ColumnToggleContextMenu addColumn(String label, Grid.Column<T> column) {
        MenuItem menuItem = addItem(label, e -> column.setVisible(e.getSource().isChecked()));
        menuItem.setCheckable(true);
        menuItem.setChecked(column.isVisible());
        return this;
    }

    public Component getTarget() {
        return target;
    }
}
