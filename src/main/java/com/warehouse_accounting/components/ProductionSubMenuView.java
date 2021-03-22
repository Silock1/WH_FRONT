package com.warehouse_accounting.components;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.movements.MovementView;

import java.util.Arrays;
import java.util.List;

@Route(value = "production", layout = AppView.class)
@PageTitle("Производство")
public class ProductionSubMenuView extends VerticalLayout {

    private final MovementView movementView;
    private final Div pageContent = new Div();

    public ProductionSubMenuView(MovementView movementView) {
        this.movementView = movementView;
        pageContent.setSizeFull();
        add(initSubMenu(), pageContent);
    }

    private Tabs initSubMenu() {
        List<String> goodsMenuItems = Arrays.asList("Тех. карты",
                "Заказы на производство",
                "Тех. операции");
        Tabs subMenuTabs = new Tabs();
        for (String item : goodsMenuItems) {
            Tab tab = new Tab();
            tab.addClassName("subMenuItem");
            tab.add(item);
            subMenuTabs.add(tab);
        }
        subMenuTabs.addSelectedChangeListener(event -> {
            switch (event.getSelectedTab().getLabel()) {
                case "Тех. карты":
                    pageContent.removeAll();
                    pageContent.add(new Span("Тех. карты"));
                    break;
                case "Заказы на производство":
                    pageContent.removeAll();
                    pageContent.add(new Span("Заказы на производство"));
                    break;
                case "Тех. операции":
                    pageContent.removeAll();
                    pageContent.add(new Span("Тех. операции"));
                    break;
            }
        });
        return subMenuTabs;
    }
}
