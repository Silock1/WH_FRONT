package com.warehouse_accounting.components;

import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;

import java.util.List;

//Утилитный класс для методов, которые создают элементы страницы (таблицы, табы, окна и т.д.)

public class UtilView {

// Метод  для создания набора табов из переданного листа.
//
    public static Tabs subMenuTabs(List<String> menuItems) {
        Tabs subMenuTabs = new Tabs();
        for (String item : menuItems) {
            Tab tab = new Tab();
            tab.addClassName("subMenuItem");
            tab.add(item);
            subMenuTabs.add(tab);
        }
        return subMenuTabs;
    }
}
