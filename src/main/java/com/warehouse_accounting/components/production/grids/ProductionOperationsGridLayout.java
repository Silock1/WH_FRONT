package com.warehouse_accounting.components.production.grids;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.models.dto.ProductionOperationsDto;
import com.warehouse_accounting.services.interfaces.ProductionOperationsService;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@UIScope
@SpringComponent
public class ProductionOperationsGridLayout extends HorizontalLayout {
    private ProductionOperationsService productionOperationsService;
    private List<ProductionOperationsDto> productionOperationsDtoList = new ArrayList<>();

    @Getter
    private Grid<ProductionOperationsDto> productionOperationsDtoGrid = new Grid<>(ProductionOperationsDto.class, false);

    public ProductionOperationsGridLayout(ProductionOperationsService productionOperationsService) {
        this.productionOperationsService = productionOperationsService;
        initializingGrid();
    }

    public void initializingGrid () {
        Grid.Column<ProductionOperationsDto> idColumn = productionOperationsDtoGrid.addColumn(ProductionOperationsDto::getId).setHeader("Id");
        idColumn.setVisible(false);
        Grid.Column<ProductionOperationsDto> companyName = productionOperationsDtoGrid.addColumn(ProductionOperationsDto::getCompanyName).setHeader("Company Name");
        Grid.Column<ProductionOperationsDto> projectName = productionOperationsDtoGrid.addColumn(ProductionOperationsDto::getProjectName).setHeader("ProjectName");
        Grid.Column<ProductionOperationsDto> technologicalMapName = productionOperationsDtoGrid.addColumn(ProductionOperationsDto::getTechnologicalMapName).setHeader("TechnologicalMapName");
        Grid.Column<ProductionOperationsDto> technologicalMapId = productionOperationsDtoGrid.addColumn(ProductionOperationsDto::getTechnologicalMapId).setHeader("TechnologicalMapId");
        technologicalMapId.setVisible(false);
        Grid.Column<ProductionOperationsDto> warehouseForMaterialsName = productionOperationsDtoGrid.addColumn(ProductionOperationsDto::getWarehouseForMaterialsName).setHeader("WarehouseForMaterialsName");
        Grid.Column<ProductionOperationsDto> warehouseForProductName = productionOperationsDtoGrid.addColumn(ProductionOperationsDto::getWarehouseForProductName).setHeader("WarehouseForProductName");
        Grid.Column<ProductionOperationsDto> tasks = productionOperationsDtoGrid.addColumn(ProductionOperationsDto::getTasks).setHeader("Tasks");

        productionOperationsDtoList = productionOperationsService.getAll();
        productionOperationsDtoList.sort(Comparator.comparingLong(ProductionOperationsDto::getId));
        productionOperationsDtoGrid.setItems(productionOperationsDtoList);

        productionOperationsDtoGrid.setSelectionMode(Grid.SelectionMode.SINGLE);

    }
}
