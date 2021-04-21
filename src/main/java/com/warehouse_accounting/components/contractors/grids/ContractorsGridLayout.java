package com.warehouse_accounting.components.contractors.grids;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.warehouse_accounting.models.dto.CallDto;
import com.warehouse_accounting.models.dto.CustomerOrdersDto;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class ContractorsGridLayout extends HorizontalLayout {

    private final TextField selectedTextField;
    private final Grid<CallDto> callDtoGrid = new Grid<>(CallDto.class, false);

    public ContractorsGridLayout(TextField selectedTextField) {
        this.selectedTextField = selectedTextField;
        add(initGrid());
    }

    private Grid<CallDto> initGrid() {
        callDtoGrid.setColumns(getVisibleColumn().keySet().toArray(String[]::new));
//        customerOrdersDtoGrid.setSelectionMode(Grid.SelectionMode.MULTI);

        getVisibleColumn().forEach((key, value) -> callDtoGrid.getColumnByKey(key).setHeader(value));
//        customerOrdersDtoGrid.asMultiSelect().addSelectionListener(listener -> {
//            int selectSize = listener.getAllSelectedItems().size();
//            selectedTextField.setValue(String.valueOf(selectSize));
//        });
        callDtoGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES);
        return callDtoGrid;
    }

//    public Grid<CallDto> getProductGrid() {
//        return callDtoGrid;
//    }

    private HashMap<String, String> getVisibleColumn() {
        HashMap<String, String> fieldNameColumnName = new LinkedHashMap<>();
//        fieldNameColumnName.put("id", "Id");
        fieldNameColumnName.put("callTime", "Время");
        fieldNameColumnName.put("type", "Тип");
        fieldNameColumnName.put("number", "Номер");
        fieldNameColumnName.put("contractorName", "Контрагент");
        fieldNameColumnName.put("employeeWhoCalledName", "Сотрудник");
        fieldNameColumnName.put("callDuration", "Разговор, сек");
        fieldNameColumnName.put("comment", "Комментарий");
        fieldNameColumnName.put("callRecord", "Запись");
        fieldNameColumnName.put("whenChanged", "Когда изменен");
        fieldNameColumnName.put("employeeWhoChangedName", "Кто изменил");
        return fieldNameColumnName;
    }
}
