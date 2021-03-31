package com.warehouse_accounting.components;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Arrays;
import java.util.List;

//private final Div pageContent = new Div();
//
//public ProductionSubMenuView() {
//        pageContent.setSizeFull();
//        add(initSubMenu(), pageContent);
//        }
//
//private Tabs initSubMenu() {
//        List<String> goodsMenuItems = Arrays.asList("Тех. карты",
//        "Заказы на производство",
//        "Тех. операции");
//        Tabs subMenuTabs = new Tabs();
//        for (String item : goodsMenuItems) {
//        Tab tab = new Tab();
//        tab.addClassName("subMenuItem");
//        tab.add(item);
//        subMenuTabs.add(tab);
//        }
//        subMenuTabs.addSelectedChangeListener(event -> {
//        switch (event.getSelectedTab().getLabel()) {
//        case "Тех. карты":
//        pageContent.removeAll();
//        pageContent.add(new Span("Тех. карты"));
//        break;
//        case "Заказы на производство":
//        pageContent.removeAll();
//        pageContent.add(new Span("Заказы на производство"));
//        break;
//        case "Тех. операции":
//        pageContent.removeAll();
//        pageContent.add(new Span("Тех. операции"));
//        break;
//        }
//        });
//        return subMenuTabs;
//        }
@Route(value = "money", layout = AppView.class)
@PageTitle("Деньги")
public class Money extends VerticalLayout {

    private final Div pageContent = new Div();

    public Money() {
        pageContent.setSizeFull();
        add(initSubMeny(), pageContent);
    }

    private Tabs initSubMeny() {
        List<String> moneyMenuItems = Arrays.asList("Платежи",
                "Движение денежных средств",
                "Прибыли и убытки",
                "Взаиморасчеты",
                "Корректировки");
        Tabs subMenuTabs = new Tabs();
        for (String item : moneyMenuItems) {
            Tab tab = new Tab();
            tab.addClassName("subMenuItem");
            tab.add(item);
            subMenuTabs.add(tab);
        }
        subMenuTabs.addSelectedChangeListener(event -> {
        switch (event.getSelectedTab().getLabel()) {
        case "Платежи":
        pageContent.removeAll();
        pageContent.add(new Span("Платежи"));
        break;
        case "Движение денежных средств":
        pageContent.removeAll();
        pageContent.add(new Span("Движение денежных средств"));
        break;
        case "Прибыли и убытки":
            pageContent.removeAll();
            pageContent.add(new Span("Прибыли и убытки"));
        break;
        case "Взаиморасчеты":
            pageContent.removeAll();
            pageContent.add(new Span("Взаиморасчеты"));
        break;
        case "Корректировки":
            pageContent.removeAll();
            pageContent.add(new Span("Корректировки"));
        break;
        }
        });
        return subMenuTabs;
        }
}
