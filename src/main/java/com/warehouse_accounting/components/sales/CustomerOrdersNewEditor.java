package com.warehouse_accounting.components.sales;

import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.models.dto.CustomerOrderDto;
import com.warehouse_accounting.services.interfaces.CustomerOrderService;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class CustomerOrdersNewEditor extends VerticalLayout implements KeyNotifier {
    private final CustomerOrderService customerOrderService;

    private CustomerOrderDto customerOrderDto;

    @Autowired
    public CustomerOrdersNewEditor(CustomerOrderService customerOrderService) {
        this.customerOrderService = customerOrderService;
    }
}
