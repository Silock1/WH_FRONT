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
import com.warehouse_accounting.components.production.ProductionTasks;
import com.warehouse_accounting.components.production.forms.ProductionTasksForm;
import com.warehouse_accounting.components.util.ColumnToggleContextMenu;
import com.warehouse_accounting.models.dto.ProductionTasksDto;
import com.warehouse_accounting.services.interfaces.ContractorService;
import com.warehouse_accounting.services.interfaces.EmployeeService;
import com.warehouse_accounting.services.interfaces.ProductionTasksAdditionalFieldService;
import com.warehouse_accounting.services.interfaces.ProductionTasksService;
import com.warehouse_accounting.services.interfaces.WarehouseService;
import lombok.Getter;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@UIScope
@SpringComponent
public class ProductionTasksGridLayout extends HorizontalLayout {
    private final transient ProductionTasksService productionTasksService;
    private final transient ProductionTasksAdditionalFieldService productionTasksAdditionalFieldService;
    private final transient WarehouseService warehouseService;
    private transient List<ProductionTasksDto> productionTasksDtoList = new ArrayList<>();
    @Getter
    private final Grid<ProductionTasksDto> productionTasksDtoGrid =
            new Grid<>(ProductionTasksDto.class, false);

    private final ProductionTasks productionTasks;

    private final transient EmployeeService employeeService;
    private final transient ContractorService contractorService;

    public ProductionTasksGridLayout(ProductionTasksService productionTasksService,
                                     ProductionTasksAdditionalFieldService productionTasksAdditionalFieldService,
                                     WarehouseService warehouseService,
                                     @Lazy ProductionTasks productionTasks,
                                     EmployeeService employeeService,
                                     ContractorService contractorService) {
        this.productionTasksService = productionTasksService;
        this.productionTasksAdditionalFieldService = productionTasksAdditionalFieldService;
        this.warehouseService = warehouseService;
        this.productionTasks = productionTasks;
        this.employeeService = employeeService;
        this.contractorService = contractorService;

        initializingGrid();
    }

    private void initializingGrid() {

        Grid.Column<ProductionTasksDto> idColumn =
                productionTasksDtoGrid.addColumn(ProductionTasksDto::getId).setHeader("Id");
        idColumn.setVisible(false);
        Grid.Column<ProductionTasksDto> taskIdColumn =
                productionTasksDtoGrid.addColumn(ProductionTasksDto::getTaskId).setHeader("???");
        Grid.Column<ProductionTasksDto> timeColumn =
                productionTasksDtoGrid.addColumn(ProductionTasksDto::getDateOfCreate).setHeader("??????????");
        Grid.Column<ProductionTasksDto> organizationColumn =
                productionTasksDtoGrid.addColumn(ProductionTasksDto::getOrganization).setHeader("??????????????????????");
        Grid.Column<ProductionTasksDto> plannedDateColumn =
                productionTasksDtoGrid.addColumn(ProductionTasksDto::getPlannedDate)
                        .setHeader("????????. ???????? ????????????????????????");
        plannedDateColumn.setVisible(false);
        Grid.Column<ProductionTasksDto> materialWarehouseColumn =
                productionTasksDtoGrid.addColumn(ProductionTasksDto::getMaterialWarehouseName)
                        .setHeader("?????????? ?????? ????????????????????");
        materialWarehouseColumn.setVisible(false);
        Grid.Column<ProductionTasksDto> productionWarehouseColumn =
                productionTasksDtoGrid.addColumn(ProductionTasksDto::getProductionWarehouseName)
                        .setHeader("?????????? ?????? ??????????????????");
        productionWarehouseColumn.setVisible(false);
        Grid.Column<ProductionTasksDto> startColumn =
                productionTasksDtoGrid.addColumn(ProductionTasksDto::getDateOfStart).setHeader("???????????? ????????????????????????");
        Grid.Column<ProductionTasksDto> endColumn =
                productionTasksDtoGrid.addColumn(ProductionTasksDto::getDateOfEnd).setHeader("???????????????????? ????????????????????????");
        Grid.Column<ProductionTasksDto> accessColumn =
                productionTasksDtoGrid.addColumn(ProductionTasksDto::getIsAccessed).setHeader("?????????? ????????????");
        accessColumn.setVisible(false);
        Grid.Column<ProductionTasksDto> ownerDepartmentColumn =
                productionTasksDtoGrid.addColumn(ProductionTasksDto::getOwnerDepartmentName)
                        .setHeader("????????????????-??????????");
        ownerDepartmentColumn.setVisible(false);
        Grid.Column<ProductionTasksDto> ownerEmployeeColumn =
                productionTasksDtoGrid.addColumn(ProductionTasksDto::getOwnerEmployeeName)
                        .setHeader("????????????????-??????????????????");
        ownerEmployeeColumn.setVisible(false);
        Grid.Column<ProductionTasksDto> sendColumn =
                productionTasksDtoGrid.addColumn(ProductionTasksDto::getDateOfSend).setHeader("????????????????????");
        Grid.Column<ProductionTasksDto> printColumn =
                productionTasksDtoGrid.addColumn(ProductionTasksDto::getDateOfPrint).setHeader("????????????????????");
        Grid.Column<ProductionTasksDto> descriptionColumn =
                productionTasksDtoGrid.addColumn(ProductionTasksDto::getDescription).setHeader("??????????????????????");
        Grid.Column<ProductionTasksDto> editColumn =
                productionTasksDtoGrid.addColumn(ProductionTasksDto::getDateOfEdit).setHeader("?????????? ??????????????");
        editColumn.setVisible(false);
        Grid.Column<ProductionTasksDto> editEmployeeColumn =
                productionTasksDtoGrid.addColumn(ProductionTasksDto::getEditEmployeeName).setHeader("?????? ??????????????");
        editEmployeeColumn.setVisible(false);

        productionTasksDtoList = productionTasksService.getAll();
        productionTasksDtoList.sort(Comparator.comparingLong(ProductionTasksDto::getId));
        productionTasksDtoGrid.setItems(productionTasksDtoList);
        for (String customField : productionTasksDtoList.get(0).getAdditionalFieldsNames()) {
            productionTasksDtoGrid.addColumn( productionTasksDto ->
                    productionTasksAdditionalFieldService.getById(productionTasksDto.getAdditionalFieldsIds()
                                    .get(productionTasksDto.getAdditionalFieldsNames().indexOf(customField)))
                            .getProperty().get("value")
            ).setHeader(customField).setKey(customField);
        }

        productionTasksDtoGrid.setSelectionMode(Grid.SelectionMode.SINGLE);

        productionTasksDtoGrid.addItemDoubleClickListener(click -> productionTasks.add(new ProductionTasksForm(
                productionTasks,
                ((ProductionTasksDto) productionTasksDtoGrid.getSelectedItems().toArray()[0]),
                productionTasksService,
                productionTasksAdditionalFieldService,
                warehouseService,employeeService, contractorService
                )));

        Button menuButton = new Button(new Icon(VaadinIcon.COG));
        menuButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        ColumnToggleContextMenu<ProductionTasksDto> columnToggleContextMenu = new ColumnToggleContextMenu<>(menuButton);

        columnToggleContextMenu.addColumnToggleItem("Id", idColumn);
        columnToggleContextMenu.addColumnToggleItem("??????????", taskIdColumn);
        columnToggleContextMenu.addColumnToggleItem("??????????", timeColumn);
        columnToggleContextMenu.addColumnToggleItem("??????????????????????", organizationColumn);
        columnToggleContextMenu.addColumnToggleItem("????????. ???????? ????????????????????????", plannedDateColumn);
        columnToggleContextMenu.addColumnToggleItem("?????????? ?????? ????????????????????", materialWarehouseColumn);
        columnToggleContextMenu.addColumnToggleItem("?????????? ?????? ??????????????????", productionWarehouseColumn);
        columnToggleContextMenu.addColumnToggleItem("???????????? ????????????????????????", startColumn);
        columnToggleContextMenu.addColumnToggleItem("???????????????????? ????????????????????????", endColumn);
        columnToggleContextMenu.addColumnToggleItem("?????????? ????????????", accessColumn);
        columnToggleContextMenu.addColumnToggleItem("????????????????????", sendColumn);
        columnToggleContextMenu.addColumnToggleItem("????????????????????", printColumn);
        columnToggleContextMenu.addColumnToggleItem("??????????????????????", descriptionColumn);
        columnToggleContextMenu.addColumnToggleItem("?????????? ??????????????", editColumn);
        columnToggleContextMenu.addColumnToggleItem("?????? ??????????????", editEmployeeColumn);

        Span title = new Span("??????????????????");
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
