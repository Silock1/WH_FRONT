package com.warehouse_accounting.components;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.address.AddressForm;
import com.warehouse_accounting.components.sales.ComissionerReports;
import com.warehouse_accounting.components.sales.CustomerGoodsToRealize;
import com.warehouse_accounting.components.sales.CustomerInvoices;
import com.warehouse_accounting.components.sales.CustomerOrders;
import com.warehouse_accounting.components.sales.CustomerReturns;
import com.warehouse_accounting.components.sales.Shipments;
import com.warehouse_accounting.components.sales.filter.ComissionerReportsFilter;
import com.warehouse_accounting.components.sales.filter.CustomerOrdersFilter;
import com.warehouse_accounting.components.sales.filter.CustomerReturnsFilter;
import com.warehouse_accounting.components.sales.filter.GoodsToRealizeFilter;
import com.warehouse_accounting.components.sales.filter.SalesShipmentsFilter;
import com.warehouse_accounting.services.interfaces.*;

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
    private final SalesShipmentsFilter salesShipmentsFilter;
    private final CustomerOrdersFilter customerOrdersFilter;
    private final GoodsToRealizeFilter goodsToRealizeFilter;

    private GoodsToRealizeGiveService goodsToRealizeGiveService;
    private GoodsToRealizeGetService goodsToRealizeGetService;
    private CustomerInvoices customerInvoices;
    private Shipments shipments;
    private ComissionerReports comissionerReports;
    private CustomerReturns customerReturns;


    private CompanyService companyService;
    private ContractorService contractorService;
    private ContractService contractService;
    private ProjectService projectService;
    private WarehouseService warehouseService;
    private DepartmentService departmentService;
    private EmployeeService employeeService;
    private final ProductService productService;
    private final CustomerOrderService customerOrderService;
    private final SalesChannelsService channelsService;
    private final AddressForm addressForm;
    private final AddressService addressService;

    public SalesSubMenuView(SalesShipmentsFilter salesShipmentsFilter,
                            CustomerOrdersFilter customerOrdersFilter, GoodsToRealizeGiveService goodsToRealizeGiveService,
                            GoodsToRealizeGetService goodsToRealizeGetService, CompanyService companyService,
                            ContractorService contractorService, ContractService contractService, ProjectService projectService,
                            WarehouseService warehouseService, DepartmentService departmentService, EmployeeService employeeService,
                            GoodsToRealizeFilter filterLayout, GoodsToRealizeFilter goodsToRealizeFilter,
                            ProductService productService, CustomerOrderService customerOrderService, SalesChannelsService channelsService,
                            AddressForm addressForm, AddressService addressService) {
        this.goodsToRealizeFilter = goodsToRealizeFilter;
        this.customerOrdersFilter = customerOrdersFilter;
        this.companyService = companyService;
        this.contractorService = contractorService;
        this.contractService = contractService;
        this.projectService = projectService;
        this.warehouseService = warehouseService;
        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.salesShipmentsFilter = salesShipmentsFilter;
        this.productService = productService;
        this.customerOrderService = customerOrderService;
        this.channelsService = channelsService;
        this.addressForm = addressForm;
        this.addressService = addressService;

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
                    pageContent.add(initComissionerReports(pageContent));
                    break;

                case "Возвраты покупателей":
                    pageContent.removeAll();
                    // Сделать и Добавить представление инициализации возврата покупателей
                    pageContent.add(initCustomerReturns(pageContent));
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
            customerOrders = new CustomerOrders(pageContent, companyService, contractorService, contractService,
                    projectService, warehouseService, employeeService, departmentService, productService, customerOrderService,
                    channelsService, addressForm, addressService);
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
            shipments = new Shipments(pageContent, companyService, contractorService, contractService,
                    projectService, warehouseService, departmentService, employeeService);
        }
        return shipments;
    }

    private CustomerInvoices initCustomerInvoices(Div pageContent){
        if (Objects.isNull(customerInvoices)) {
            customerInvoices = new CustomerInvoices(pageContent);
        }
        return customerInvoices;
    }

    private ComissionerReports initComissionerReports(Div pageContent){
        if (Objects.isNull(comissionerReports)) {
            comissionerReports = new ComissionerReports(/*pageContent,*/ companyService, contractorService, contractService,
                    projectService, warehouseService, departmentService, employeeService, new ComissionerReportsFilter(companyService, contractorService, contractService,
                    projectService, warehouseService, employeeService, departmentService));
        }
        return comissionerReports;
    }

    private CustomerReturns initCustomerReturns(Div pageContent){
        if (Objects.isNull(customerReturns)) {
            customerReturns = new CustomerReturns(pageContent, new CustomerReturnsFilter(companyService, contractorService, contractService,
                    projectService, warehouseService, employeeService, departmentService));
        }
        return customerReturns;
    }
}
