package com.warehouse_accounting.components;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Arrays;
import java.util.List;

@Route(value = "retail", layout = AppView.class)
@PageTitle("Розница")
public class RetailSubMenuView extends VerticalLayout {
    private final Div pageContent = new Div();

    public RetailSubMenuView() {
        pageContent.setSizeFull();
        add(initSubMenu(), pageContent);
    }

    private Tabs initSubMenu() {
        List<String> retailMenuItems = Arrays.asList("Точки продаж",
                "Смены",
                "Продажи",
                "Возвраты",
                "Внесения",
                "Выплаты",
                "Операции с баллами",
                "Предоплаты",
                "Возвраты предоплат",
                "Очередь облачных чеков");
        Tabs subMenuTabs = new Tabs();
        for (String item : retailMenuItems) {
            Tab tab = new Tab();
            tab.addClassName("subMenuItem");
            tab.add(item);
            subMenuTabs.add(tab);
        }

        return subMenuTabs;
    }
}
