package com.warehouse_accounting.components;

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

    public GoodsSubMenuView() {
        add(initSubMenu());
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
        return subMenuTabs;
    }
}
