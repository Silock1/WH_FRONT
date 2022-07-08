package com.warehouse_accounting.components;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.payments.adjustments.AdjustmentsView;
import com.warehouse_accounting.components.payments.PaymentsSettlementsView;
import com.warehouse_accounting.components.payments.PaymentsView;

import java.util.Arrays;
import java.util.List;

import static com.warehouse_accounting.components.UtilView.subMenuTabs;

@Route(value = "money", layout = AppView.class)
@PageTitle("Платежи")
public class MoneySubMenuView extends VerticalLayout {

    private final Div pageContent = new Div();
    private final PaymentsView paymentsView;
    private final AdjustmentsView adjustmentsView;
    private final PaymentsSettlementsView paymentsSettlementsView;

    public MoneySubMenuView(PaymentsView paymentsView, AdjustmentsView adjustmentsView
            , PaymentsSettlementsView paymentsSettlementsView
    ) {
        this.paymentsView = paymentsView;
        this.adjustmentsView = adjustmentsView;
        this.paymentsSettlementsView = paymentsSettlementsView;
        pageContent.setSizeFull();
        add(initSubMenu(), pageContent);
    }

    private Tabs initSubMenu() {
        List<String> moneyMenuItems = Arrays.asList("Платежи",
                "Движение денежных средств",
                "Прибыли и убытки",
                "Взаиморасчеты",
                "Корректировки");

        Tabs subMenuTabs = subMenuTabs(moneyMenuItems);

        subMenuTabs.addSelectedChangeListener(event -> {
            switch (event.getSelectedTab().getLabel()) {
                case "Платежи":
                    pageContent.removeAll();
                    pageContent.add(paymentsView);
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
                    pageContent.add(paymentsSettlementsView);
//                    pageContent.add(new Span("Взаиморасчеты"));
                    break;
                case "Корректировки":
                    pageContent.removeAll();
                    pageContent.add(adjustmentsView);
                    break;
            }
        });
        return subMenuTabs;
    }
}
