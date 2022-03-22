package com.warehouse_accounting.components;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.goods.GoodsAndServiceView;
import com.warehouse_accounting.components.goods.filter.GoodsFilter;
import com.warehouse_accounting.components.movements.MovementView;
import com.warehouse_accounting.components.tasks.Tasks;
import com.warehouse_accounting.services.interfaces.ContractorService;
import com.warehouse_accounting.services.interfaces.DepartmentService;
import com.warehouse_accounting.services.interfaces.EmployeeService;
import com.warehouse_accounting.services.interfaces.ProductGroupService;
import com.warehouse_accounting.services.interfaces.ProductService;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.warehouse_accounting.components.UtilView.subMenuTabs;



@Route(value = "goods", layout = AppView.class)
@PageTitle("Товары")
public class GoodsSubMenuView extends VerticalLayout {

    private final MovementView movementView;
    private GoodsAndServiceView goodsAndService;
    private final Div pageContent = new Div();

    private GoodsFilter goodsFilter;
    private final ProductService productService;
    private final EmployeeService employeeService;
    private final DepartmentService departmentService;
    private final ContractorService contractorService;
    private final ProductGroupService productGroupService;


    public GoodsSubMenuView(MovementView movementView, GoodsAndServiceView goodsAndService, GoodsFilter goodsFilter, ProductService productService, EmployeeService employeeService, DepartmentService departmentService, ContractorService contractorService, ProductGroupService productGroupService) {
        this.movementView = movementView;
        this.goodsAndService = goodsAndService;
        this.goodsFilter = goodsFilter;
        this.productService = productService;
        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.contractorService = contractorService;
        this.productGroupService = productGroupService;
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
        Tabs subMenuTabs = subMenuTabs(goodsMenuItems);

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

    @PostConstruct
    public void setMainDiv(){
        goodsAndService.setMainDiv(pageContent);
    }

}