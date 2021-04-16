package com.warehouse_accounting.components;


import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.purchases.PurchasesOrders;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.warehouse_accounting.components.UtilView.subMenuTabs;

@Route(value = "purchases", layout = AppView.class)
@PageTitle("Закупки")
public class PurchasesSubMenuView extends VerticalLayout {

    private final Div pageContent = new Div();
    private PurchasesOrders purchasesOrders;

    public PurchasesSubMenuView() {
        pageContent.setSizeFull();
        pageContent.add(initPurchasesOrders(pageContent));
        add(initSubMenu(), pageContent);
    }

    private Tabs initSubMenu() {
        List<String> purchasesMenuItems = Arrays.asList("Заказы поставщикам",
                "Счета поставщиков",
                "Приемки",
                "Возвраты поставщикам",
                "Счета-фактуры полученные",
                "Управление закупками");
        Tabs subMenuTabs = subMenuTabs(purchasesMenuItems);

        subMenuTabs.addSelectedChangeListener(event -> {
            switch (event.getSelectedTab().getLabel()) {
                case "Заказы поставщикам":
                    pageContent.removeAll();
                    pageContent.add(initPurchasesOrders(pageContent));
                    break;
                case "Счета поставщиков":
                    pageContent.removeAll();
                    pageContent.add(new Span("Счета поставщиков"));
                    break;
                case "Приемки":
                    pageContent.removeAll();
                    pageContent.add(new Span("Приемки"));
                    break;
                case "Возвраты поставщикам":
                    pageContent.removeAll();
                    pageContent.add(new Span("Возвраты поставщикам"));
                    break;
                case "Счета-фактуры полученные":
                    pageContent.removeAll();
                    pageContent.add(new Span("Счета-фактуры полученные"));
                    break;
                case "Счета-фактуры выданные":
                    pageContent.removeAll();
                    pageContent.add(new Span("Счета-фактуры выданные"));
                    break;
                case "Управление закупками":
                    pageContent.removeAll();
                    pageContent.add(new Span("Управление закупками"));
                    break;
            }
        });
        return subMenuTabs;
    }

    private PurchasesOrders initPurchasesOrders(Div pageContent){
        if (Objects.isNull(purchasesOrders)) {
            purchasesOrders = new PurchasesOrders(pageContent);
        }
        return purchasesOrders;
    }
}

