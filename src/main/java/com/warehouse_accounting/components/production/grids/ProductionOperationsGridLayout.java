package com.warehouse_accounting.components.production.grids;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.production.ProductionOperations;
import com.warehouse_accounting.models.dto.ProductionOperationsDto;
import com.warehouse_accounting.models.dto.ProductionProcessTechnologyDto;
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


        Button menuButton = new Button(new Icon(VaadinIcon.COG));
        menuButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        ProductionOperationsGridLayout.ColumnToggleContextMenu columnToggleContextMenu = new ProductionOperationsGridLayout.ColumnToggleContextMenu(
                menuButton);
        columnToggleContextMenu.addColumnToggleItem("Id", idColumn);
        columnToggleContextMenu.addColumnToggleItem("Company Name", companyName);
        columnToggleContextMenu.addColumnToggleItem("ProjectName", projectName);

        columnToggleContextMenu.addColumnToggleItem("TechnologicalMapName", technologicalMapName);
        columnToggleContextMenu.addColumnToggleItem("TechnologicalMapId", warehouseForMaterialsName);
        columnToggleContextMenu.addColumnToggleItem("WarehouseForMaterialsName", warehouseForProductName);
        columnToggleContextMenu.addColumnToggleItem("Tasks", tasks);

        setSizeFull();
        add(productionOperationsDtoGrid);

    }
    public void updateGrid() {
        productionOperationsDtoList = productionOperationsService.getAll();
        productionOperationsDtoList.sort(Comparator.comparingLong(ProductionOperationsDto::getId));
        productionOperationsDtoGrid.getDataProvider().refreshAll();
        productionOperationsDtoGrid.setItems(productionOperationsDtoList);
        productionOperationsDtoGrid.getDataProvider().refreshAll();
    }

    private static class ColumnToggleContextMenu extends ContextMenu {
        public ColumnToggleContextMenu(Component target) {
            super(target);
            setOpenOnClick(true);
        }

        void addColumnToggleItem(String label, Grid.Column<ProductionOperationsDto> column) {
            MenuItem menuItem = this.addItem(label, e -> {
                column.setVisible(e.getSource().isChecked());
            });
            menuItem.setCheckable(true);
            menuItem.setChecked(column.isVisible());
        }
    }
}
