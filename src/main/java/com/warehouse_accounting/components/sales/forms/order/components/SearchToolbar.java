package com.warehouse_accounting.components.sales.forms.order.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.warehouse_accounting.models.dto.ProductDto;
import com.warehouse_accounting.services.interfaces.ProductService;

// todo: Реализовать добавление из справочника (с остальным пока неопределённо).

public class SearchToolbar extends HorizontalLayout {
    public SearchToolbar(ProductService productService, OrderGrid productGrid) {
        ComboBox<ProductDto> search = new ComboBox<>();
        search.setPlaceholder("Добавить товар по названию, артикулу или коду");
        search.setItemLabelGenerator(ProductDto::getName);
        search.setItems(
                (product, request) -> product.getName().contains(request) || product.getArticul().contains(request),
                productService.getAll()
        );
        search.addValueChangeListener(event -> {
            productGrid.addProduct(event.getValue());
        });

        Button catalog = new Button("Добавить из справочника");
        catalog.addClickListener(event -> Notification.show("Not Implemented"));

        Button checkComplect = new Button("Проверить комплектацию");
        checkComplect.addClickListener(event -> Notification.show("Not Implemented"));

        Button importOrder = new Button("Импорт");
        importOrder.addClickListener(event -> Notification.show("Not Implemented"));

        add(search, catalog, checkComplect, importOrder);
    }
}
