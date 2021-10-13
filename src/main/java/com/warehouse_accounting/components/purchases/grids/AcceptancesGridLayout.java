package com.warehouse_accounting.components.purchases.grids;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.warehouse_accounting.models.dto.AcceptanceProductDto;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class AcceptancesGridLayout extends HorizontalLayout {
    private final TextField textField;

    private final Grid<AcceptanceProductDto> acceptanceProductDtoGrid = new Grid<>(AcceptanceProductDto.class, false);

    public AcceptancesGridLayout(TextField textField) {
        this.textField = textField;
        add(initAcceptanceGrid());
    }

    private Grid<AcceptanceProductDto> initAcceptanceGrid(){
        acceptanceProductDtoGrid.setColumns(getVisibleAcceptancesColumn().keySet().toArray(String[]::new));
        acceptanceProductDtoGrid.setSelectionMode(Grid.SelectionMode.MULTI);

        getVisibleAcceptancesColumn().forEach((key, value) -> acceptanceProductDtoGrid.getColumnByKey(key).setHeader(value));
        acceptanceProductDtoGrid.asMultiSelect().addSelectionListener(listener -> {
            int selectSize = listener.getAllSelectedItems().size();
            textField.setValue(String.valueOf(selectSize));
        });

        acceptanceProductDtoGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES);

        return acceptanceProductDtoGrid;
    }

    public Grid<AcceptanceProductDto> getAcceptancesDtoGrid(){
        return acceptanceProductDtoGrid;
    }

    private HashMap<String, String> getVisibleAcceptancesColumn(){
        HashMap<String, String> fieldNameColumnName = new LinkedHashMap<>();

        fieldNameColumnName.put("acceptancesDto.number","№");
        fieldNameColumnName.put("acceptancesDto.acceptanceDateTime", "Время");
        fieldNameColumnName.put("acceptancesDto.warehouseName", "На склад");
        fieldNameColumnName.put("acceptancesDto.acceptanceAuthorLastName", "Контрагент");
        fieldNameColumnName.put("acceptancesDto.companyName", "Организация");
        fieldNameColumnName.put("sum", "Сумма");
        fieldNameColumnName.put("sumPaid", "Оплачено");
        fieldNameColumnName.put("incomingDate", "Входящая дата");
        fieldNameColumnName.put("acceptancesDto.comment", "Комментарий");
        return fieldNameColumnName;
    }
}
