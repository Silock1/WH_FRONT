package com.warehouse_accounting.components.production.grids;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.util.ColumnToggleContextMenu;
import com.warehouse_accounting.models.dto.ProductionTasksDto;
import com.warehouse_accounting.services.interfaces.ProductionTasksService;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@UIScope
@SpringComponent
public class ProductionTasksGridLayout extends HorizontalLayout {
    private final transient ProductionTasksService productionTasksService;
    private transient List<ProductionTasksDto> productionTasksDtoList = new ArrayList<>();
    @Getter
    private final Grid<ProductionTasksDto> productionTasksDtoGrid =
            new Grid<>(ProductionTasksDto.class, false);

    public ProductionTasksGridLayout(ProductionTasksService productionTasksService) {
        this.productionTasksService = productionTasksService;

        initializingGrid();
    }

    private void initializingGrid() {

        Grid.Column<ProductionTasksDto> idColumn =
                productionTasksDtoGrid.addColumn(ProductionTasksDto::getId).setHeader("Id");
        idColumn.setVisible(false);
        Grid.Column<ProductionTasksDto> taskIdColumn =
                productionTasksDtoGrid.addColumn(ProductionTasksDto::getTaskId).setHeader("№");
        Grid.Column<ProductionTasksDto> timeColumn =
                productionTasksDtoGrid.addColumn(ProductionTasksDto::getDateOfCreate).setHeader("Время");
        Grid.Column<ProductionTasksDto> organizationColumn =
                productionTasksDtoGrid.addColumn(ProductionTasksDto::getOrganization).setHeader("Организация");
        Grid.Column<ProductionTasksDto> plannedDateColumn =
                productionTasksDtoGrid.addColumn(ProductionTasksDto::getPlannedDate)
                        .setHeader("План. дата производства");
        plannedDateColumn.setVisible(false);
        Grid.Column<ProductionTasksDto> materialWarehouseColumn =
                productionTasksDtoGrid.addColumn(ProductionTasksDto::getMaterialWarehouseName)
                        .setHeader("Склад для материалов");
        materialWarehouseColumn.setVisible(false);
        Grid.Column<ProductionTasksDto> productionWarehouseColumn =
                productionTasksDtoGrid.addColumn(ProductionTasksDto::getProductionWarehouseName)
                        .setHeader("Склад для продукции");
        productionWarehouseColumn.setVisible(false);
        Grid.Column<ProductionTasksDto> startColumn =
                productionTasksDtoGrid.addColumn(ProductionTasksDto::getDateOfStart).setHeader("Начало производства");
        Grid.Column<ProductionTasksDto> endColumn =
                productionTasksDtoGrid.addColumn(ProductionTasksDto::getDateOfEnd).setHeader("Завершение производства");
        Grid.Column<ProductionTasksDto> accessColumn =
                productionTasksDtoGrid.addColumn(ProductionTasksDto::getIsAccessed).setHeader("Общий доступ");
        accessColumn.setVisible(false);
        Grid.Column<ProductionTasksDto> ownerDepartmentColumn =
                productionTasksDtoGrid.addColumn(ProductionTasksDto::getOwnerDepartmentName)
                        .setHeader("Владелец-отдел");
        ownerDepartmentColumn.setVisible(false);
        Grid.Column<ProductionTasksDto> ownerEmployeeColumn =
                productionTasksDtoGrid.addColumn(ProductionTasksDto::getOwnerEmployeeName)
                        .setHeader("Владелец-сотрудник");
        ownerEmployeeColumn.setVisible(false);
        Grid.Column<ProductionTasksDto> sendColumn =
                productionTasksDtoGrid.addColumn(ProductionTasksDto::getDateOfSend).setHeader("Отправлено");
        Grid.Column<ProductionTasksDto> printColumn =
                productionTasksDtoGrid.addColumn(ProductionTasksDto::getDateOfPrint).setHeader("Напечатано");
        Grid.Column<ProductionTasksDto> descriptionColumn =
                productionTasksDtoGrid.addColumn(ProductionTasksDto::getDescription).setHeader("Комментарий");
        Grid.Column<ProductionTasksDto> editColumn =
                productionTasksDtoGrid.addColumn(ProductionTasksDto::getDateOfEdit).setHeader("Когда изменен");
        editColumn.setVisible(false);
        Grid.Column<ProductionTasksDto> editEmployeeColumn =
                productionTasksDtoGrid.addColumn(ProductionTasksDto::getEditEmployeeName).setHeader("Кто изменил");
        editEmployeeColumn.setVisible(false);

        productionTasksDtoList = productionTasksService.getAll();
        productionTasksDtoList.sort(Comparator.comparingLong(ProductionTasksDto::getId));
        productionTasksDtoGrid.setItems(productionTasksDtoList);

        productionTasksDtoGrid.setSelectionMode(Grid.SelectionMode.SINGLE);

        Button menuButton = new Button(new Icon(VaadinIcon.COG));
        menuButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        ColumnToggleContextMenu<ProductionTasksDto> columnToggleContextMenu = new ColumnToggleContextMenu<>(menuButton);

        columnToggleContextMenu.addColumnToggleItem("Id", idColumn);
        columnToggleContextMenu.addColumnToggleItem("Номер", taskIdColumn);
        columnToggleContextMenu.addColumnToggleItem("Время", timeColumn);
        columnToggleContextMenu.addColumnToggleItem("Организация", organizationColumn);
        columnToggleContextMenu.addColumnToggleItem("План. дата производства", plannedDateColumn);
        columnToggleContextMenu.addColumnToggleItem("Склад для материалов", materialWarehouseColumn);
        columnToggleContextMenu.addColumnToggleItem("Склад для продукции", productionWarehouseColumn);
        columnToggleContextMenu.addColumnToggleItem("Начало производства", startColumn);
        columnToggleContextMenu.addColumnToggleItem("Завершение производства", endColumn);
        columnToggleContextMenu.addColumnToggleItem("Общий доступ", accessColumn);
        columnToggleContextMenu.addColumnToggleItem("Отправлено", sendColumn);
        columnToggleContextMenu.addColumnToggleItem("Напечатано", printColumn);
        columnToggleContextMenu.addColumnToggleItem("Комментарий", descriptionColumn);
        columnToggleContextMenu.addColumnToggleItem("Когда изменен", editColumn);
        columnToggleContextMenu.addColumnToggleItem("Кто изменил", editEmployeeColumn);

        Span title = new Span("Документы");
        title.getStyle().set("font-weight", "bold");
        HorizontalLayout headerLayout = new HorizontalLayout(menuButton);
        headerLayout.setAlignItems(Alignment.BASELINE);
        headerLayout.setFlexGrow(1);

        setSizeFull();
        add(productionTasksDtoGrid, headerLayout);
    }

    public void updateGrid() {
        productionTasksDtoList = productionTasksService.getAll();
        productionTasksDtoList.sort(Comparator.comparingLong(ProductionTasksDto::getId));
        productionTasksDtoGrid.getDataProvider().refreshAll();
        productionTasksDtoGrid.setItems(productionTasksDtoList);
        productionTasksDtoGrid.getDataProvider().refreshAll();
    }
}
