package com.warehouse_accounting.components.production.grids;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.models.dto.ProductionProcessTechnologyDto;

@UIScope
@SpringComponent
public class ProductionProcessTechnologyGridLayout extends HorizontalLayout {
    private final Grid<ProductionProcessTechnologyDto> productionProcessTechnologyDtoGrid = new Grid<>(ProductionProcessTechnologyDto.class, false);

    public ProductionProcessTechnologyGridLayout() {
        add(productionProcessTechnologyDtoGrid);
    }


}
