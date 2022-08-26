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
    }
}
