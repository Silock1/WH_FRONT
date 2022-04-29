package com.warehouse_accounting.components.purchases.grids;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import com.warehouse_accounting.models.dto.FacturienDto;

import java.util.HashMap;
import java.util.LinkedHashMap;

/*
Счета-фактуры полученные
 */
public class FacturienGridLayout extends VerticalLayout {

    private final TextField textField;
    Grid<FacturienDto> facturienDtoGrid = new Grid<>(FacturienDto.class, false);

    public FacturienGridLayout(TextField textField) {
        this.textField = textField;
        add(initFacturienGrid());
    }

    public Grid<FacturienDto> initFacturienGrid(){
        facturienDtoGrid.setColumns(getVisibleFacturienColumn().keySet().toArray(String[]::new));
        facturienDtoGrid.setSelectionMode(Grid.SelectionMode.MULTI);

        getVisibleFacturienColumn().forEach((key, value) -> facturienDtoGrid.getColumnByKey(key).setHeader(value));
        facturienDtoGrid.asMultiSelect().addSelectionListener(listener -> {
            int selectSize = listener.getAllSelectedItems().size();
            textField.setValue(String.valueOf(selectSize));
        });

        facturienDtoGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES);

        return facturienDtoGrid;
    }

    public Grid<FacturienDto> getFacturienDtoGrid() {
        return facturienDtoGrid;
    }

    private HashMap<String, String> getVisibleFacturienColumn() {
        HashMap<String, String> fieldNameColumnName = new LinkedHashMap<>();

        fieldNameColumnName.put("invoiceNumber","№");
        fieldNameColumnName.put("incomingTime", "Время");
        fieldNameColumnName.put("contrAgent", "Контрагент");
        fieldNameColumnName.put("organization", "Организация");
        fieldNameColumnName.put("sum", "Сумма");
        fieldNameColumnName.put("incomingNumber", "Входящий номер");
        fieldNameColumnName.put("incomingDate", "Входящая дата");
        fieldNameColumnName.put("sent", "Отправлено");
        fieldNameColumnName.put("printed", "Напечатано");
        fieldNameColumnName.put("addComment", "Комментарий");
        return fieldNameColumnName;
    }















}
