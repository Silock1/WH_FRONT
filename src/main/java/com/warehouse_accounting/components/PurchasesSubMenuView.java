package com.warehouse_accounting.components;


import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Arrays;
import java.util.List;

import static com.warehouse_accounting.components.UtilView.subMenuTabs;

@Route(value = "purchases", layout = AppView.class)
@PageTitle("Закупки")
public class PurchasesSubMenuView extends VerticalLayout {

    private final Div pageContent = new Div();

    public PurchasesSubMenuView() {
        pageContent.setSizeFull();
        add(initSubMenu(), pageContent);
    }

    private Tabs initSubMenu() {
        List<String> purchasesMenuItems = Arrays.asList("Заказы поставщикам",
                "Счета поставщиков",
                "Приемки",
                "Возвраты поставщикам",
                "Счета-фактуры полученные",
                "Управление закупками");
        return subMenuTabs(purchasesMenuItems);
    }
}

