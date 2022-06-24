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
import com.warehouse_accounting.components.production.ProductionOrders;
import com.warehouse_accounting.components.production.forms.ProductionOrdersForm;
import com.warehouse_accounting.models.dto.ProductionOrderDto;
import com.warehouse_accounting.services.interfaces.ProductionOrderService;
import lombok.Data;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.Comparator;
import java.util.List;

@SpringComponent
@UIScope
@Log4j2
@Data
public class ProductionOrdersGridLayout extends HorizontalLayout {

    private final ProductionOrderService productionOrderService;
    @Getter
    private final Grid<ProductionOrderDto> technologicalMapDtoGrid = new Grid<>(ProductionOrderDto.class, false);
    private final ProductionOrders productionOrders;
    private List<ProductionOrderDto> productionOrderDtoList;


    public ProductionOrdersGridLayout(ProductionOrderService productionOrderService, ProductionOrders productionOrders) {
        this.productionOrderService = productionOrderService;
        this.productionOrders = productionOrders;


        Grid.Column<ProductionOrderDto> idColumn = technologicalMapDtoGrid.addColumn(ProductionOrderDto::getId).setHeader("Id");
        Grid.Column<ProductionOrderDto> numberColumn = technologicalMapDtoGrid.addColumn(ProductionOrderDto::getNumber).setHeader("Номер");
        Grid.Column<ProductionOrderDto> dateColumn = technologicalMapDtoGrid.addColumn(ProductionOrderDto::getDateTime).setHeader("Дата");
        Grid.Column<ProductionOrderDto> companyIdColumn = technologicalMapDtoGrid.addColumn(ProductionOrderDto::getCompanyId).setHeader("Id компании");
        Grid.Column<ProductionOrderDto> companyNameColumn = technologicalMapDtoGrid.addColumn(ProductionOrderDto::getCompanyName).setHeader("Название компании");
        Grid.Column<ProductionOrderDto> technologicalMapIdColumn = technologicalMapDtoGrid.addColumn(ProductionOrderDto::getTechnologicalMapId).setHeader("Id тех карты");
        Grid.Column<ProductionOrderDto> technologicalMapNameColumn = technologicalMapDtoGrid.addColumn(ProductionOrderDto::getTechnologicalMapName).setHeader("Название тех карты");
        Grid.Column<ProductionOrderDto> volumeColumn = technologicalMapDtoGrid.addColumn(ProductionOrderDto::getVolumeOfProduction).setHeader("Объем производства");
        Grid.Column<ProductionOrderDto> warehouseIdColumn = technologicalMapDtoGrid.addColumn(ProductionOrderDto::getWarehouseId).setHeader("Id склады");
        Grid.Column<ProductionOrderDto> warehouseNameColumn = technologicalMapDtoGrid.addColumn(ProductionOrderDto::getWarehouseName).setHeader("Название склада");
        Grid.Column<ProductionOrderDto> planeDateColumn = technologicalMapDtoGrid.addColumn(ProductionOrderDto::getPlanDate).setHeader("Плановая дата");
        Grid.Column<ProductionOrderDto> projectIdColumn = technologicalMapDtoGrid.addColumn(ProductionOrderDto::getProjectId).setHeader("Id проекта");
        Grid.Column<ProductionOrderDto> projectNameColumn = technologicalMapDtoGrid.addColumn(ProductionOrderDto::getProjectName).setHeader("Название проекта");
        Grid.Column<ProductionOrderDto> commentColumn = technologicalMapDtoGrid.addColumn(ProductionOrderDto::getComment).setHeader("Комментарий");


        technologicalMapDtoGrid.setSelectionMode(Grid.SelectionMode.MULTI); //

        productionOrderDtoList = productionOrderService.getAll();
        productionOrderDtoList.sort(Comparator.comparingLong(ProductionOrderDto::getId));
        technologicalMapDtoGrid.setItems(productionOrderDtoList);

        technologicalMapDtoGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        technologicalMapDtoGrid.addItemClickListener(event -> {
            productionOrders.getPageContent().removeAll();
            ProductionOrdersForm productionOrdersForm = new ProductionOrdersForm(
                    productionOrders,
                    new ProductionOrderDto(),
                    productionOrderService);
            productionOrders.getPageContent().add(productionOrdersForm);
        });

        Button menuButton = new Button(new Icon(VaadinIcon.COG));
        menuButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        ProductionOrdersGridLayout.ColumnToggleContextMenu columnToggleContextMenu = new ProductionOrdersGridLayout.ColumnToggleContextMenu(
                menuButton);
        columnToggleContextMenu.addColumnToggleItem("Id", idColumn);
        columnToggleContextMenu.addColumnToggleItem("Номер", numberColumn);
        columnToggleContextMenu.addColumnToggleItem("Дата", dateColumn);
        columnToggleContextMenu.addColumnToggleItem("Id компании", companyIdColumn);
        columnToggleContextMenu.addColumnToggleItem("Название компании", companyNameColumn);
        columnToggleContextMenu.addColumnToggleItem("Id тех карты", technologicalMapIdColumn);
        columnToggleContextMenu.addColumnToggleItem("Название тех карты", technologicalMapNameColumn);
        columnToggleContextMenu.addColumnToggleItem("Объем производства", volumeColumn);
        columnToggleContextMenu.addColumnToggleItem("Id склады", warehouseIdColumn);
        columnToggleContextMenu.addColumnToggleItem("Название склада", warehouseNameColumn);
        columnToggleContextMenu.addColumnToggleItem("Плановая дата", planeDateColumn);
        columnToggleContextMenu.addColumnToggleItem("Id проекта", projectIdColumn);
        columnToggleContextMenu.addColumnToggleItem("Название проекта", projectNameColumn);
        columnToggleContextMenu.addColumnToggleItem("Комментарий", commentColumn);


        Span title = new Span("Документы");
        title.getStyle().set("font-weight", "bold");
        HorizontalLayout headerLayout = new HorizontalLayout(menuButton);
        headerLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
        headerLayout.setFlexGrow(1);
        add(technologicalMapDtoGrid, headerLayout);
    }

    public void updateGrid() {
        productionOrderDtoList = productionOrderService.getAll();
        productionOrderDtoList.sort(Comparator.comparingLong(ProductionOrderDto::getId));
        technologicalMapDtoGrid.setItems(productionOrderDtoList);
    }

    private static class ColumnToggleContextMenu extends ContextMenu {
        public ColumnToggleContextMenu(Component target) {
            super(target);
            setOpenOnClick(true);
        }

        void addColumnToggleItem(String label, Grid.Column<ProductionOrderDto> column) {
            MenuItem menuItem = this.addItem(label, e -> {
                column.setVisible(e.getSource().isChecked());
            });
            menuItem.setCheckable(true);
            menuItem.setChecked(column.isVisible());
        }
    }
}

