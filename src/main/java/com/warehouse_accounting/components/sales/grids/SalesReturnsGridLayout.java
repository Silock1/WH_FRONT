package com.warehouse_accounting.components.sales.grids;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.warehouse_accounting.models.dto.ReturnDto;

public class SalesReturnsGridLayout extends HorizontalLayout {

    private final TextField selectedTextField;
    private final Grid<ReturnDto> returnDtoGrid = new Grid<>(ReturnDto.class, false);

    public SalesReturnsGridLayout(TextField selectedTextField) {
        this.selectedTextField = selectedTextField;

        Grid.Column<ReturnDto> idColumn = returnDtoGrid.addColumn(ReturnDto::getId).setHeader("Id");
        Grid.Column<ReturnDto> returnDateTimeColumn = returnDtoGrid.addColumn(ReturnDto::getReturnDateTime).setHeader("Время");
        Grid.Column<ReturnDto> warehouseColumn = returnDtoGrid.addColumn(ReturnDto::getWarehouse).setHeader("Со склада");
        Grid.Column<ReturnDto> contractorColumn = returnDtoGrid.addColumn(ReturnDto::getContractor).setHeader("Контрагент");
        Grid.Column<ReturnDto> companyColumn = returnDtoGrid.addColumn(ReturnDto::getId).setHeader("Организация");
        Grid.Column<ReturnDto> contractColumn = returnDtoGrid.addColumn(ReturnDto::getId).setHeader("Id");
        Grid.Column<ReturnDto> projectColumn = returnDtoGrid.addColumn(ReturnDto::getId).setHeader("Id");
        Grid.Column<ReturnDto> filesColumn = returnDtoGrid.addColumn(ReturnDto::getId).setHeader("Id");
        Grid.Column<ReturnDto> tasksColumn  = returnDtoGrid.addColumn(ReturnDto::getId).setHeader("Id");
        Grid.Column<ReturnDto> productsColumn  = returnDtoGrid.addColumn(ReturnDto::getId).setHeader("Id");
        Grid.Column<ReturnDto> sumColumn = returnDtoGrid.addColumn(ReturnDto::getId).setHeader("Id");
        Grid.Column<ReturnDto> isSentColumn = returnDtoGrid.addColumn(ReturnDto::getId).setHeader("Id");
        Grid.Column<ReturnDto> isPrintedColumn = returnDtoGrid.addColumn(ReturnDto::getId).setHeader("Id");
        Grid.Column<ReturnDto> commentColumn = returnDtoGrid.addColumn(ReturnDto::getId).setHeader("Id");
    }
}
