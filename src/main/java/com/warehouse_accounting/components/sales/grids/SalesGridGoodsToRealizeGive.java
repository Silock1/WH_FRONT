package com.warehouse_accounting.components.sales.grids;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.warehouse_accounting.models.dto.CustomerGoodsToRealizeGiveDto;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class SalesGridGoodsToRealizeGive extends HorizontalLayout {

    private final TextField selectedTextField;
    private final Grid<CustomerGoodsToRealizeGiveDto> customerGoodsToRealizeDtoGrid = new Grid<>(CustomerGoodsToRealizeGiveDto.class, false);

    public SalesGridGoodsToRealizeGive(TextField selectedTextField) {
        this.selectedTextField = selectedTextField;
        add(initGrid());
    }

    private Grid<CustomerGoodsToRealizeGiveDto> initGrid() {
        customerGoodsToRealizeDtoGrid.setColumns(getVisibleColumn().keySet().toArray(String[]::new));
        customerGoodsToRealizeDtoGrid.setSelectionMode(Grid.SelectionMode.MULTI);

        getVisibleColumn().forEach((key, value) -> customerGoodsToRealizeDtoGrid.getColumnByKey(key).setHeader(value));
        customerGoodsToRealizeDtoGrid.asMultiSelect().addSelectionListener(listener -> {
            int selectSize = listener.getAllSelectedItems().size();
            selectedTextField.setValue(String.valueOf(selectSize));
        });
        customerGoodsToRealizeDtoGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES);
        return customerGoodsToRealizeDtoGrid;
    }

    public Grid<CustomerGoodsToRealizeGiveDto> getProductGrid() {
        return customerGoodsToRealizeDtoGrid;
    }

    private HashMap<String, String> getVisibleColumn() {
        HashMap<String, String> fieldNameColumnName = new LinkedHashMap<>();
        fieldNameColumnName.put("productsDto", "Наименование");
        fieldNameColumnName.put("id", "Код");
        fieldNameColumnName.put("article", "Артикул");
        fieldNameColumnName.put("unit", "Ед. изм.");
        fieldNameColumnName.put("give", "Передано");
        fieldNameColumnName.put("quantity", "Кол-во");
        fieldNameColumnName.put("amount", "Сумма");
        fieldNameColumnName.put("arrive", "Вернули");
        fieldNameColumnName.put("remains", "Остаток");

        return fieldNameColumnName;
    }
}
