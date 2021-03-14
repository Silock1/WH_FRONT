package com.warehouse_accounting.components;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.goods.GoodsAndServiceView;
import com.warehouse_accounting.components.movements.MovementView;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Arrays;
import java.util.List;

@PageTitle("Товары")
@Route(value = "goods", layout = AppView.class)
@PreserveOnRefresh
public class GoodsSubMenuView extends VerticalLayout {

    private final MovementView movementView;
    private final GoodsAndServiceView goodsAndService;
    private final Div pageContent;

    public GoodsSubMenuView(@Qualifier("mainLayer") Div pageContent, MovementView movementView, GoodsAndServiceView goodsAndService) {
        this.movementView = movementView;
        this.goodsAndService = goodsAndService;
        this.pageContent = pageContent;
        this.pageContent.removeAll();
        pageContent.add(goodsAndService);
        pageContent.setSizeFull();
        add(initSubMenu(), pageContent);
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
                    pageContent.add(goodsAndService);
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
                    pageContent.add(movementView);
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