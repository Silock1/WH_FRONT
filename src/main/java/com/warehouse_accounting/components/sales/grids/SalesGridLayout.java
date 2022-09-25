package com.warehouse_accounting.components.sales.grids;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.warehouse_accounting.components.util.ColumnToggleContextMenu;
import com.warehouse_accounting.models.dto.CustomerOrderDto;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class SalesGridLayout extends HorizontalLayout {

    private final TextField selectedTextField;

    private Button settingButton = new Button(new Icon(VaadinIcon.COG));
    private final Grid<CustomerOrderDto> customerOrderDtoGrid = new Grid<>(CustomerOrderDto.class, false);

    public SalesGridLayout(TextField selectedTextField) {
        this.selectedTextField = selectedTextField;
        add(initGrid(), settingButton);
    }

    private Grid<CustomerOrderDto> initGrid() {
        customerOrderDtoGrid.setColumns(getVisibleColumn().keySet().toArray(String[]::new));
        customerOrderDtoGrid.setSelectionMode(Grid.SelectionMode.MULTI);

        settingButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        ColumnToggleContextMenu<CustomerOrderDto> columnToggleContextMenu = new ColumnToggleContextMenu<>(settingButton);


        getVisibleColumn().forEach((key, value) -> {
            Grid.Column<CustomerOrderDto> customerOrderDtoColumn = customerOrderDtoGrid.getColumnByKey(key).setHeader(value);
            columnToggleContextMenu.addColumnToggleItem(value, customerOrderDtoColumn);
        });
        customerOrderDtoGrid.asMultiSelect().addSelectionListener(listener -> {
            int selectSize = listener.getAllSelectedItems().size();
            selectedTextField.setValue(String.valueOf(selectSize));
        });
        customerOrderDtoGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES);
        return customerOrderDtoGrid;
    }

    public Grid<CustomerOrderDto> getProductGrid() {
        return customerOrderDtoGrid;
    }

    private HashMap<String, String> getVisibleColumn() {
        HashMap<String, String> fieldNameColumnName = new LinkedHashMap<>();
        fieldNameColumnName.put("docNumber", "№");
        fieldNameColumnName.put("date", "Время");
        fieldNameColumnName.put("contrAgentName", "Контрагент");
        fieldNameColumnName.put("companyName", "Организация");
        fieldNameColumnName.put("sum", "Сумма");
        fieldNameColumnName.put("isPaid", "Оплачено");
        fieldNameColumnName.put("warehouseFromName", "Со склада");
        fieldNameColumnName.put("deliveryAddressFull", "Адрес доставки");
        fieldNameColumnName.put("comments", "Комментарий");
        return fieldNameColumnName;
    }
}
