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
import com.warehouse_accounting.components.purchases.filter.AccountsPayableFilter;
import com.warehouse_accounting.components.purchases.grids.SupplierInvoiceGridLayout;
import com.warehouse_accounting.services.interfaces.CompanyService;
import com.warehouse_accounting.services.interfaces.ContractService;
import com.warehouse_accounting.services.interfaces.ContractorService;
import com.warehouse_accounting.services.interfaces.DepartmentService;
import com.warehouse_accounting.services.interfaces.EmployeeService;
import com.warehouse_accounting.services.interfaces.ProductService;
import com.warehouse_accounting.services.interfaces.ProjectService;
import com.warehouse_accounting.services.interfaces.WarehouseService;

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

    public PurchasesSubMenuView(AccountsPayableFilter accountsPayableFilter, WarehouseService warehouseService,
                                ContractService contractService, ContractorService contractorService,
                                ProjectService projectService, EmployeeService employeeService,
                                DepartmentService departmentService, ProductService productService,
                                CompanyService companyService) {
        this.accountsPayableFilter = accountsPayableFilter;
        this.warehouseService = warehouseService;
        this.contractService = contractService;
        this.contractorService = contractorService;
        this.projectService = projectService;
        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.productService = productService;
        this.companyService = companyService;
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
            accountsPayable = new AccountsPayable(pageContent, warehouseService, contractService, contractorService,
                    projectService, employeeService, departmentService, productService, companyService);
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

