package com.warehouse_accounting.components.production.grids;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.models.dto.TechnologicalOperationDto;
import com.warehouse_accounting.services.interfaces.TechnologicalOperationsService;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@SpringComponent
@UIScope
@Log4j2
public class TechnologicalOperationsGridLayout extends HorizontalLayout {
    private TechnologicalOperationsService productionOperationsService;
    private List<TechnologicalOperationDto> technologicalOperationDtoList = new ArrayList<>();

    @Getter
    private Grid<TechnologicalOperationDto> productionOperationsDtoGrid = new Grid<>(TechnologicalOperationDto.class, false);

    public TechnologicalOperationsGridLayout(TechnologicalOperationsService productionOperationsService) {
        this.productionOperationsService = productionOperationsService;
        initializingGrid();
    }

    public void initializingGrid () {
        Grid.Column<TechnologicalOperationDto> idColumn = productionOperationsDtoGrid.addColumn(TechnologicalOperationDto::getId).setHeader("№");
        Grid.Column<TechnologicalOperationDto> technologicalMapName = productionOperationsDtoGrid.addColumn(TechnologicalOperationDto::getTechnologicalMapName).setHeader("Технологическая карта");
        Grid.Column<TechnologicalOperationDto> technologicalMapId = productionOperationsDtoGrid.addColumn(TechnologicalOperationDto::getTechnologicalMapId).setHeader("TechnologicalMapId");
        technologicalMapId.setVisible(false);
        Grid.Column<TechnologicalOperationDto> volumeOfProduction = productionOperationsDtoGrid.addColumn(TechnologicalOperationDto::getVolumeOfProduction).setHeader("Объем производства");
        Grid.Column<TechnologicalOperationDto> warehouseForMaterialsName = productionOperationsDtoGrid.addColumn(TechnologicalOperationDto::getWarehouseForMaterialsName).setHeader("Склад для материалов");
        Grid.Column<TechnologicalOperationDto> warehouseForProductName = productionOperationsDtoGrid.addColumn(TechnologicalOperationDto::getWarehouseForProductName).setHeader("Склад для продукции");
//        Grid.Column<TechnologicalOperationDto> tasks = productionOperationsDtoGrid.addColumn(TechnologicalOperationDto::getTasks).setHeader("Tasks");

        productionOperationsDtoGrid.setSelectionMode(Grid.SelectionMode.MULTI);

        technologicalOperationDtoList = productionOperationsService.getAll();
        technologicalOperationDtoList.sort(Comparator.comparingLong(TechnologicalOperationDto::getId));
        productionOperationsDtoGrid.setItems(technologicalOperationDtoList);
        productionOperationsDtoGrid.setSelectionMode(Grid.SelectionMode.SINGLE);


        Button menuButton = new Button(new Icon(VaadinIcon.COG));
        menuButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        TechnologicalOperationsGridLayout.ColumnToggleContextMenu columnToggleContextMenu = new TechnologicalOperationsGridLayout.ColumnToggleContextMenu(
                menuButton);
        columnToggleContextMenu.addColumnToggleItem("Id", idColumn);
        columnToggleContextMenu.addColumnToggleItem("Технологическая карта", technologicalMapName);
        columnToggleContextMenu.addColumnToggleItem("Объем производства", volumeOfProduction);
        columnToggleContextMenu.addColumnToggleItem("Склад для материалов", warehouseForMaterialsName);
        columnToggleContextMenu.addColumnToggleItem("Склад для продукции", warehouseForProductName);
//        columnToggleContextMenu.addColumnToggleItem("Tasks", tasks);

        setSizeFull();
        add(productionOperationsDtoGrid);

    }
    public void updateGrid() {
        technologicalOperationDtoList = productionOperationsService.getAll();
        technologicalOperationDtoList.sort(Comparator.comparingLong(TechnologicalOperationDto::getId));
        productionOperationsDtoGrid.setItems(technologicalOperationDtoList);
    }

    private static class ColumnToggleContextMenu extends ContextMenu {
        public ColumnToggleContextMenu(Component target) {
            super(target);
            setOpenOnClick(true);
        }

        void addColumnToggleItem(String label, Grid.Column<TechnologicalOperationDto> column) {
            MenuItem menuItem = this.addItem(label, e -> {
                column.setVisible(e.getSource().isChecked());
            });
            menuItem.setCheckable(true);
            menuItem.setChecked(column.isVisible());
        }
    }
}
