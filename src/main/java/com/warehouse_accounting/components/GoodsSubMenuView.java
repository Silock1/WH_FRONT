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

@Route(value = "goods", layout = AppView.class)
@PageTitle("Товары")
public class GoodsSubMenuView extends VerticalLayout {

    private final Div pageContent = new Div();

    public GoodsSubMenuView() {
        add(initSubMenu());
        add(pageContent);
    }

    private Tabs initSubMenu() {
        List<String> goodsMenuItems = Arrays.asList("Товары и услуги",
                "Оприходования",
                "Списания",
                "Инвентаризация",
                "Внутренние заказы",
                "Перемещения",
                "Прайс-листы",
                "Остатки",
                "Обороты");
        Tabs subMenuTabs = new Tabs();
        for (String item : goodsMenuItems) {
            Tab tab = new Tab();
            tab.addClassName("subMenuItem");
            tab.add(item);
            subMenuTabs.add(tab);
        }
        subMenuTabs.addSelectedChangeListener(event -> {
            switch (event.getSelectedTab().getLabel()) {
                case "Товары и услуги":
                    pageContent.removeAll();
                    pageContent.add(new Span("Товары и услуги"));
                    break;
                case "Оприходования":
                    pageContent.removeAll();
                    pageContent.add(new Span("Оприходования"));
                    break;
                case "Списания":
                    pageContent.removeAll();
                    pageContent.add(new Span("Списания"));
                    break;
                case "Инвентаризация":
                    pageContent.removeAll();
                    pageContent.add(new Span("Инвентаризация"));
                    break;
                case "Внутренние заказы":
                    pageContent.removeAll();
                    pageContent.add(new Span("Внутренние заказы"));
                    break;
                case "Перемещения":
                    pageContent.removeAll();
                    pageContent.add(new Span("Перемещения"));
                    break;
                case "Прайс-листы":
                    pageContent.removeAll();
                    pageContent.add(new Span("Прайс-листы"));
                    break;
                case "Остатки":
                    pageContent.removeAll();
                    pageContent.add(new Span("Остатки"));
                    break;
                case "Обороты":
                    pageContent.removeAll();
                    pageContent.add(new Span("Обороты"));
                    break;
            }
        });
        return subMenuTabs;
    }
}
