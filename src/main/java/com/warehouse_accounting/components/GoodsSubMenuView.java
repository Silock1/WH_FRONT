package com.warehouse_accounting.components;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.goods.GoodsAndServiceView;
import com.warehouse_accounting.components.goods.Inventory;
import com.warehouse_accounting.components.goods.InternalOrderView;
import com.warehouse_accounting.components.goods.Posting;
import com.warehouse_accounting.components.goods.Remains;
import com.warehouse_accounting.components.goods.SerialNumbers;
import com.warehouse_accounting.components.goods.Turns;
import com.warehouse_accounting.components.goods.WriteOffs;
import com.warehouse_accounting.components.goods.filter.GoodsFilter;
import com.warehouse_accounting.components.goods.grids.movements.MovementView;
import com.warehouse_accounting.components.goods.grids.priceList.PriceList;
import com.warehouse_accounting.services.impl.SerialNumbersServiceImpl;
import com.warehouse_accounting.services.interfaces.ContractorService;
import com.warehouse_accounting.services.interfaces.DepartmentService;
import com.warehouse_accounting.services.interfaces.RemainsService;
import com.warehouse_accounting.services.interfaces.EmployeeService;
import com.warehouse_accounting.services.interfaces.ProductGroupService;
import com.warehouse_accounting.services.interfaces.ProductService;
import com.warehouse_accounting.services.interfaces.SerialNumbersService;
import com.warehouse_accounting.services.interfaces.TurnsService;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

import static com.warehouse_accounting.components.UtilView.subMenuTabs;



@Route(value = "goods", layout = AppView.class)
@PageTitle("Товары")
public class GoodsSubMenuView extends VerticalLayout {

    private final MovementView movementView;
    private final PriceList priceList;
    private final Posting posting;
    private final WriteOffs writeOffs;
    private final Remains remains;
    private final Inventory inventory;
    private final Turns turns;
    private final SerialNumbers serialNumbers;
    private GoodsAndServiceView goodsAndService;

    private final InternalOrderView internalOrderView;
    private final Div pageContent = new Div();

    private GoodsFilter goodsFilter;
    private final ProductService productService;
    private final EmployeeService employeeService;
    private final DepartmentService departmentService;
    private final ContractorService contractorService;
    private final ProductGroupService productGroupService;
    private final RemainsService remainsService;
    private final TurnsService turnsService;
    private final SerialNumbersService serialNumbersService;


    public GoodsSubMenuView(SerialNumbersService serialNumbersService, SerialNumbers serialNumbers,
                            TurnsService turnsService, Turns turns, PriceList priceList, Posting posting, Remains remains, MovementView movementView, GoodsAndServiceView goodsAndService,
                            GoodsFilter goodsFilter, ProductService productService, EmployeeService employeeService,
                            DepartmentService departmentService, ContractorService contractorService,
                            ProductGroupService productGroupService, WriteOffs writeOffs, RemainsService remainsService,
                            Inventory inventory, InternalOrderView internalOrderView) {
        this.serialNumbers = serialNumbers;
        this.serialNumbersService = serialNumbersService;
        this.turnsService = turnsService;
        this.turns = turns;
        this.priceList = priceList;
        this.posting = posting;
        this.remains = remains;
        this.movementView = movementView;
        this.writeOffs = writeOffs;
        this.remainsService = remainsService;
        this.inventory = inventory;
        this.goodsAndService = goodsAndService;
        this.goodsFilter = goodsFilter;
        this.productService = productService;
        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.contractorService = contractorService;
        this.productGroupService = productGroupService;
        this.internalOrderView = internalOrderView;
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
                "Обороты",
                "Сер. номера");
        Tabs subMenuTabs = subMenuTabs(goodsMenuItems);

        subMenuTabs.addSelectedChangeListener(event -> {
            switch (event.getSelectedTab().getLabel()) {
                case "Товары и услуги":
                    pageContent.removeAll();
                    pageContent.add(goodsAndService);
                    break;
                case "Оприходования":
                    pageContent.removeAll();
                    pageContent.add(posting);
                    break;
                case "Списания":
                    pageContent.removeAll();
                    pageContent.add(writeOffs); //списания
                    break;
                case "Инвентаризация":
                    pageContent.removeAll();
                    pageContent.add(inventory);
                    break;
                case "Внутренние заказы":
                    pageContent.removeAll();
                    pageContent.add(internalOrderView);
                    break;
                case "Перемещения":
                    pageContent.removeAll();
                    pageContent.add(movementView);
                    break;
                case "Прайс-листы":
                    pageContent.removeAll();
                    pageContent.add(priceList);
                    break;
                case "Остатки":
                    pageContent.removeAll();
                    pageContent.add(remains);
                    break;
                case "Обороты":
                    pageContent.removeAll();
                    pageContent.add(turns);
                    break;
                case "Сер. номера":
                    pageContent.removeAll();
                    pageContent.add(serialNumbers); //Добавил сер.номера
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

