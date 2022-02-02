package com.warehouse_accounting.components;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.purchases.Acceptances;
import com.warehouse_accounting.components.purchases.AccountsPayable;
import com.warehouse_accounting.components.purchases.PurchasesOrders;
import com.warehouse_accounting.components.purchases.Return;
import com.warehouse_accounting.components.purchases.grids.SupplierInvoiceGridLayout;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.warehouse_accounting.components.UtilView.subMenuTabs;

@Route(value = "purchases", layout = AppView.class)
@PageTitle("Закупки")
public class PurchasesSubMenuView extends VerticalLayout {

    private final Div pageContent = new Div();
    private PurchasesOrders purchasesOrders;
    private AccountsPayable accountsPayable;
    private Acceptances acceptances;
    private Return returns;

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
                    pageContent.add(initAccountsPayable(pageContent));
                    pageContent.add(SupplierInvoiceGridLayout.initSupplierInvoiceGrid());
                    break;
                case "Приемки":
                    pageContent.removeAll();
                    pageContent.add(initAcceptances(pageContent));
                    break;
                case "Возвраты поставщикам":
                    pageContent.removeAll();
                    pageContent.add(initReturn(pageContent));
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

    private AccountsPayable initAccountsPayable(Div pageContent){
        if (Objects.isNull(accountsPayable)){
            accountsPayable = new AccountsPayable(pageContent);
        }
        return accountsPayable;
    }

    private Acceptances initAcceptances(Div pageContent){
        if (Objects.isNull(acceptances)) {
            acceptances = new Acceptances(pageContent);
        }
        return acceptances;
    }

    private Return initReturn(Div pageContent){
        if (Objects.isNull(returns)) {
            returns = new Return(pageContent);
        }
        return returns;
    }
}

