package com.warehouse_accounting.components.util;

import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.grid.Grid;

public class ColumnToggleContextMenu<T> extends ContextMenu {

    public ColumnToggleContextMenu(com.vaadin.flow.component.Component target) {
        super(target);
        setOpenOnClick(true);
    }

    public void addColumnToggleItem(String label, Grid.Column<T> column) {
        MenuItem menuItem = this.addItem(label, e -> {
            column.setVisible(e.getSource().isChecked());
        });
        menuItem.setCheckable(true);
        menuItem.setChecked(column.isVisible());
    }
}