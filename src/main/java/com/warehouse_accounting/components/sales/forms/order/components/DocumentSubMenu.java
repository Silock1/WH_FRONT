package com.warehouse_accounting.components.sales.forms.order.components;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;

public class DocumentSubMenu {
    private final MenuItem menuItem;

    public DocumentSubMenu(MenuItem menuItem){
        this.menuItem = menuItem;
    }

    public DocumentSubMenu addSubMenuItem(String title, ComponentEventListener<ClickEvent<MenuItem>> handler) {
        menuItem.getSubMenu().addItem(title, handler);
        return this;
    }

    public DocumentSubMenu addSubMenuItem(Component prefix, String title, ComponentEventListener<ClickEvent<MenuItem>> handler) {
        menuItem.getSubMenu().addItem(new Span(prefix, new Label(title)), handler);
        return this;
    }

    public DocumentSubMenu addSubMenuItem(Component control, ComponentEventListener<ClickEvent<MenuItem>> handler) {
        menuItem.getSubMenu().addItem(control, handler);
        return this;
    }

    public MenuItem getMenu() { return menuItem; }
}
