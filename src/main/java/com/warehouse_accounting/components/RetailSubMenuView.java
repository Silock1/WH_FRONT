package com.warehouse_accounting.components;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.retail.BonusTransaction;
import com.warehouse_accounting.components.retail.PointOfSales;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.warehouse_accounting.components.UtilView.subMenuTabs;

@Route(value = "retail", layout = AppView.class)
@PageTitle("Розница")
public class RetailSubMenuView extends VerticalLayout {
    private final Div pageContent = new Div();
    private PointOfSales pointOfSales;
    private final BonusTransaction bonusTransaction;

    public RetailSubMenuView(BonusTransaction bonusTransaction) {
        this.bonusTransaction = bonusTransaction;
        setSizeFull();
        pageContent.setSizeFull();
        pageContent.add(initPointOfSales(pageContent));
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
        Tabs subMenuTabs = subMenuTabs(retailMenuItems);
        subMenuTabs.addSelectedChangeListener(event -> {
            switch (event.getSelectedTab().getLabel()) {
                case "Точки продаж":
                    pageContent.removeAll();
                    pageContent.add(initPointOfSales(pageContent));
                    break;
                case "Смены":
                    pageContent.removeAll();
                    pageContent.add(new Span("Смены"));
                    break;
                case "Продажи":
                    pageContent.removeAll();
                    pageContent.add(new Span("Продажи"));
                    break;
                case "Возвраты":
                    pageContent.removeAll();
                    pageContent.add(new Span("Возвраты"));
                    break;
                case "Внесения":
                    pageContent.removeAll();
                    pageContent.add(new Span("Внесения"));
                    break;
                case "Выплаты":
                    pageContent.removeAll();
                    pageContent.add(new Span("Выплаты"));
                    break;
                case "Операции с баллами":
                    pageContent.removeAll();
                    pageContent.add(bonusTransaction);
                    break;
                case "Предоплаты":
                    pageContent.removeAll();
                    pageContent.add(new Span("Предоплаты"));
                    break;
                case "Возвраты предоплат":
                    pageContent.removeAll();
                    pageContent.add(new Span("Возвраты предоплат"));
                    break;
                case "Очередь облачных чеков":
                    pageContent.removeAll();
                    pageContent.add(new Span("Очередь облачных чеков"));
                    break;


            }
        });

        return subMenuTabs;
    }

    private PointOfSales initPointOfSales(Div pageContent) {
        if (Objects.isNull(pointOfSales)) {
            pointOfSales = new PointOfSales(pageContent);
        }
        return pointOfSales;
    }


}
