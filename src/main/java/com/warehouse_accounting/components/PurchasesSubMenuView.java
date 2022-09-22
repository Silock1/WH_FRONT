package com.warehouse_accounting.components;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.purchases.*;
import com.warehouse_accounting.components.purchases.filter.AccountsPayableFilter;
import com.warehouse_accounting.components.purchases.grids.IncomingVoices;
import com.warehouse_accounting.services.interfaces.*;
import org.springframework.beans.factory.annotation.Qualifier;

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

    private AccountsPayableFilter accountsPayableFilter;
    private final WarehouseService warehouseService;
    private final ContractService contractService;
    private final ContractorService contractorService;
    private final ProjectService projectService;
    private final EmployeeService employeeService;
    private final DepartmentService departmentService;
    private final ProductService productService;
    private final CompanyService companyService;
    private final InvoicesReceived invoicesReceived;
    private final IncomingVoices incomingVoices;
    private final IncomingVoicesService incomingVoicesService;

    private final AcceptanceService acceptanceService;
    private StatusService statusService;

    public PurchasesSubMenuView(WarehouseService warehouseService,IncomingVoicesService incomingVoicesService,
                                IncomingVoices incomingVoices,
                                ContractService contractService, ContractorService contractorService,
                                ProjectService projectService, EmployeeService employeeService,
                                DepartmentService departmentService, ProductService productService,
                                CompanyService companyService, @Qualifier("invoicesReceived") InvoicesReceived invoicesReceived,
                                AcceptanceService acceptanceService, StatusService statusService) {
        this.warehouseService = warehouseService;
        this.incomingVoices = incomingVoices;
        this.incomingVoicesService = incomingVoicesService;
        this.contractService = contractService;
        this.contractorService = contractorService;
        this.projectService = projectService;
        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.productService = productService;
        this.companyService = companyService;
        this.invoicesReceived = invoicesReceived;
        this.acceptanceService = acceptanceService;
        this.statusService = statusService;
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
                "Управление закупками",
                "Входящие накладные ЕГАИС");
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
                    pageContent.add(invoicesReceived);
                    break;
                case "Счета-фактуры выданные":
                    pageContent.removeAll();
                    pageContent.add(new Span("Счета-фактуры выданные"));
                    break;
                case "Управление закупками":
                    pageContent.removeAll();
                    pageContent.add(new Span("Управление закупками"));
                    break;
                case "Входящие накладные ЕГАИС":
                    pageContent.removeAll();
                    pageContent.add((incomingVoices));
                    break;
            }
        });
        return subMenuTabs;
    }

    private PurchasesOrders initPurchasesOrders(Div pageContent) {
        if (Objects.isNull(purchasesOrders)) {
            purchasesOrders = new PurchasesOrders(pageContent);
        }
        return purchasesOrders;
    }

    private AccountsPayable initAccountsPayable(Div pageContent) {
        if (Objects.isNull(accountsPayable)) {
            accountsPayable = new AccountsPayable(pageContent, accountsPayableFilter, statusService, warehouseService, contractService, contractorService,
                    projectService, employeeService, departmentService,
                    productService, companyService);        }
        return accountsPayable;
    }


    private Acceptances initAcceptances(Div pageContent) {
        if (Objects.isNull(acceptances)) {
            acceptances = new Acceptances(pageContent, acceptanceService);
        }
        return acceptances;
    }

    private Return initReturn(Div pageContent) {
        if (Objects.isNull(returns)) {
            returns = new Return(pageContent);
        }
        return returns;
    }

}

