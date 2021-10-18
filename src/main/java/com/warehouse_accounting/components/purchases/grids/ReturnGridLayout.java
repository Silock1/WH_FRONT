package com.warehouse_accounting.components.purchases.grids;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.warehouse_accounting.models.dto.ReturnDto;

import java.util.HashMap;
import java.util.LinkedHashMap;


public class ReturnGridLayout extends HorizontalLayout{
    private final TextField textField;
    private final Grid<ReturnDto> returnDtoGrid = new Grid<>(ReturnDto.class, false);
    public ReturnGridLayout(TextField textField) {
        this.textField = textField;
        add(initReturnGrid());
    }
    private Grid<ReturnDto> initReturnGrid(){
        returnDtoGrid.setColumns(getVisibleReturnColumn().keySet().toArray(String[]::new));
        returnDtoGrid.setSelectionMode(Grid.SelectionMode.MULTI);

        getVisibleReturnColumn().forEach((key, value) -> returnDtoGrid.getColumnByKey(key).setHeader(value));
        returnDtoGrid.asMultiSelect().addSelectionListener(listener -> {
            int selectSize = listener.getAllSelectedItems().size();
            textField.setValue(String.valueOf(selectSize));
        });

        returnDtoGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES);

        return returnDtoGrid;
    }

    public Grid<ReturnDto> getReturnDtoGrid() {
        return returnDtoGrid;
    }

    private HashMap<String, String> getVisibleReturnColumn() {
        HashMap<String, String> fieldNameColumnName = new LinkedHashMap<>();

        fieldNameColumnName.put("number","№");
        fieldNameColumnName.put("returnDateTime", "Время");
        fieldNameColumnName.put("warehouseName", "На склад");
        fieldNameColumnName.put("authorLastName", "Контрагент");
        fieldNameColumnName.put("companyName", "Организация");
        fieldNameColumnName.put("comment", "Комментарий");
        return fieldNameColumnName;
    }


}
