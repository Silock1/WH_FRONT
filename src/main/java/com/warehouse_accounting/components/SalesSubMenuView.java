package com.warehouse_accounting.components;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.sales.CustomerGoodsToRealize;
import com.warehouse_accounting.components.sales.CustomerInvoices;
import com.warehouse_accounting.components.sales.CustomerOrders;
import com.warehouse_accounting.components.sales.filter.GoodsToRealizeFilter;
import com.warehouse_accounting.components.sales.filter.SalesShipmentsFilter;
import com.warehouse_accounting.services.interfaces.GoodsToRealizeGetService;
import com.warehouse_accounting.services.interfaces.GoodsToRealizeGiveService;
import com.warehouse_accounting.components.sales.Shipments;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.warehouse_accounting.components.UtilView.subMenuTabs;

@Route(value = "sales", layout = AppView.class)
@PageTitle("Продажи")
public class SalesSubMenuView extends VerticalLayout {

    private final Div pageContent = new Div();
    private CustomerOrders customerOrders;
    private CustomerGoodsToRealize customerGoodsToRealize;
    private final GoodsToRealizeFilter filterLayout;

    private GoodsToRealizeGiveService goodsToRealizeGiveService;
    private GoodsToRealizeGetService goodsToRealizeGetService;
    private CustomerInvoices customerInvoices;
    private Shipments shipments;

    public SalesSubMenuView(GoodsToRealizeFilter filterLayout, GoodsToRealizeGiveService goodsToRealizeGiveService,
                            GoodsToRealizeGetService goodsToRealizeGetService) {

        pageContent.setSizeFull();
        this.goodsToRealizeGetService = goodsToRealizeGetService;
        this.goodsToRealizeGiveService = goodsToRealizeGiveService;
        this.filterLayout = filterLayout;
        pageContent.add(initCustomerOrders(pageContent));
        add(initSubMenu(), pageContent);
    }

    private Tabs initSubMenu() {
        List<String> saleMenuItems = Arrays.asList("Заказы покупателей",
                "Счета покупателям",
                "Отгрузки",
                "Отчеты комиссионера",
                "Возвраты покупателей",
                "Счета-фактуры выданные",
                "Прибыльность",
                "Товары на реализации",
                "Воронка продаж");
        Tabs subMenuTabs = subMenuTabs(saleMenuItems);

        subMenuTabs.addSelectedChangeListener(event -> {
            switch (event.getSelectedTab().getLabel()) {
                case "Заказы покупателей":
                    pageContent.removeAll();
                    pageContent.add(initCustomerOrders(pageContent));
                    break;
                case "Счета покупателям":
                    pageContent.removeAll();
                    pageContent.add(initCustomerInvoices(pageContent));
                    break;
                case "Отгрузки":
                    pageContent.removeAll();
                    pageContent.add(initShipments(pageContent));
                    break;
                case "Отчеты комиссионера":
                    pageContent.removeAll();
                    pageContent.add(new Span("Отчеты комиссионера"));
                    break;
                case "Возвраты покупателей":
                    pageContent.removeAll();
                    pageContent.add(new Span("Возвраты покупателей"));
                    break;
                case "Счета-фактуры выданные":
                    pageContent.removeAll();
                    pageContent.add(new Span("Счета-фактуры выданные"));
                    break;
                case "Прибыльность":
                    pageContent.removeAll();
                    pageContent.add(new Span("Прибыльность"));
                    break;
                case "Товары на реализации":
                    pageContent.removeAll();
                    pageContent.add(initCustomerGoodsToRealize(filterLayout, goodsToRealizeGetService, goodsToRealizeGiveService));
                    break;
                case "Воронка продаж":
                    pageContent.removeAll();
                    pageContent.add(new Span("Воронка продаж"));
                    break;
            }
        });
        return subMenuTabs;
    }
    private CustomerOrders initCustomerOrders(Div pageContent){
        if (Objects.isNull(customerOrders)) {
            customerOrders = new CustomerOrders(pageContent);
        }
        return customerOrders;
    }

    private CustomerGoodsToRealize initCustomerGoodsToRealize(GoodsToRealizeFilter filterLayout, GoodsToRealizeGetService goodsToRealizeGetService, GoodsToRealizeGiveService goodsToRealizeGiveService){
        if (Objects.isNull(customerGoodsToRealize)) {
            customerGoodsToRealize = new CustomerGoodsToRealize(filterLayout, goodsToRealizeGetService, goodsToRealizeGiveService);
        }
        return customerGoodsToRealize;
    }

    private Shipments initShipments(Div pageContent) {
        if (Objects.isNull(shipments)) {
            shipments = new Shipments(pageContent);
        }
        return shipments;
    }
    private CustomerInvoices initCustomerInvoices(Div pageContent){
        if (Objects.isNull(customerInvoices)) {
            customerInvoices = new CustomerInvoices(pageContent);
        }
        return customerInvoices;
    }
}
