package com.warehouse_accounting.components;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.production.ProductionOperations;
import com.warehouse_accounting.components.production.ProductionProcessTechnology;
import com.warehouse_accounting.components.production.ProductionSteps;
import com.warehouse_accounting.components.production.ProductionTasks;
import com.warehouse_accounting.components.production.ProductionOrders;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.warehouse_accounting.components.UtilView.subMenuTabs;

@Route(value = "production", layout = AppView.class)
@PageTitle("Производство")
public class ProductionSubMenuView extends VerticalLayout {


    private final Div pageContent = new Div();
    private final ProductionTasks productionTasks;
    private final ProductionSteps productionSteps;
    private final ProductionProcessTechnology productionProcessTechnology;
    private final ProductionOperations productionOperations;

    private final ProductionOrders productionOrders;

    public ProductionSubMenuView(ProductionOrders productionOrders,
                                 ProductionTasks productionTasks,
                                 ProductionSteps productionSteps,
                                 ProductionProcessTechnology productionProcessTechnology,
                                 ProductionOperations productionOperations) {
        this.productionOrders = productionOrders;
        this.productionOperations = productionOperations;
        this.productionTasks = productionTasks;
        this.productionSteps = productionSteps;
        this.productionProcessTechnology = productionProcessTechnology;

        pageContent.setSizeFull();
        add(initSubMenu(), pageContent);
    }

    private Tabs initSubMenu() {
        List<String> goodsMenuItems = Arrays.asList("Тех. карты",
                "Заказы на производство",
                "Тех. операции", "Производственные задания", "Техпроцессы", "Этапы");
        Tabs subMenuTabs = subMenuTabs(goodsMenuItems);
        subMenuTabs.addSelectedChangeListener(event -> {
            switch (event.getSelectedTab().getLabel()) {
                case "Тех. карты":
                    pageContent.removeAll();
                    pageContent.add(new Span("Тех. карты"));
                    break;
                case "Заказы на производство":
                    pageContent.removeAll();
                    pageContent.add(productionOrders);
                    break;
                case "Тех. операции":
                    pageContent.removeAll();
                    pageContent.add(productionOperations);
                    break;
                case "Производственные задания":
                    pageContent.removeAll();
                    pageContent.add(productionTasks);
                    break;
                case "Техпроцессы":
                    pageContent.removeAll();
                    pageContent.add(productionProcessTechnology);
                    break;
                case "Этапы":
                    pageContent.removeAll();
                    productionSteps.setParentLayer(pageContent);
                    pageContent.add(productionSteps);
                    break;
            }
        });
        return subMenuTabs;
    }

    @PostConstruct
    public void initBean() {
        productionSteps.setParentLayer(pageContent);
    }

//    private ProductionSteps initProductionSteps(Div pageContent){
//        if (Objects.isNull(productionSteps)) {
//            productionSteps = new ProductionSteps();
//        }
//        return productionSteps;
//    }
}
