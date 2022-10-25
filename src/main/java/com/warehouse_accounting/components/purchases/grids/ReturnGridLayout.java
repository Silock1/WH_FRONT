package com.warehouse_accounting.components.purchases.grids;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.warehouse_accounting.components.util.ColumnToggleContextMenu;
import com.warehouse_accounting.models.dto.ReturnDto;

import java.util.HashMap;
import java.util.LinkedHashMap;


public class ReturnGridLayout extends HorizontalLayout {
    private final TextField textField;

    private Button settingButton = new Button(new Icon(VaadinIcon.COG));
    private final Grid<ReturnDto> returnDtoGrid = new Grid<>(ReturnDto.class, false);

    public ReturnGridLayout(TextField textField) {
        this.textField = textField;
        add(initReturnGrid(), settingButton);
    }

    private Grid<ReturnDto> initReturnGrid() {
        returnDtoGrid.setColumns(getVisibleReturnColumn().keySet().toArray(String[]::new));
        returnDtoGrid.setSelectionMode(Grid.SelectionMode.MULTI);

        settingButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        ColumnToggleContextMenu<ReturnDto> columnToggleContextMenu = new ColumnToggleContextMenu<>(settingButton);

        getVisibleReturnColumn().forEach((key, value) -> {
            Grid.Column<ReturnDto> returnDtoColumn = returnDtoGrid.getColumnByKey(key).setHeader(value);
            columnToggleContextMenu.addColumnToggleItem(value, returnDtoColumn);
        });
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

        fieldNameColumnName.put("number", "№");
        fieldNameColumnName.put("returnDateTime", "Время");
        fieldNameColumnName.put("warehouse", "На склад");
        fieldNameColumnName.put("contractor", "Контрагент");
        fieldNameColumnName.put("company", "Организация");
        fieldNameColumnName.put("comment", "Комментарий");
        return fieldNameColumnName;
    }


}
