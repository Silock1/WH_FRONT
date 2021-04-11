package com.warehouse_accounting.components.purchases.grids;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.warehouse_accounting.models.dto.InvoiceDto;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class PurchasesGridLayout extends HorizontalLayout {

    private final TextField selectedTextField;
    private final Grid<InvoiceDto> purchasesOrdersDtoGrid = new Grid<>(InvoiceDto.class, false);

    public PurchasesGridLayout(TextField selectedTextField) {
        this.selectedTextField = selectedTextField;
        add(initGrid());
    }

    private Grid<InvoiceDto> initGrid() {
        purchasesOrdersDtoGrid.setColumns(getVisibleColumn().keySet().toArray(String[]::new));
        purchasesOrdersDtoGrid.setSelectionMode(Grid.SelectionMode.MULTI);

        getVisibleColumn().forEach((key, value) -> purchasesOrdersDtoGrid.getColumnByKey(key).setHeader(value));
        purchasesOrdersDtoGrid.asMultiSelect().addSelectionListener(listener -> {
            int selectSize = listener.getAllSelectedItems().size();
            selectedTextField.setValue(String.valueOf(selectSize));
        });
        purchasesOrdersDtoGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES);
        return purchasesOrdersDtoGrid;
    }

    public Grid<InvoiceDto> getProductGrid() {
        return purchasesOrdersDtoGrid;
    }

    private HashMap<String, String> getVisibleColumn() {
        HashMap<String, String> fieldNameColumnName = new LinkedHashMap<>();
        fieldNameColumnName.put("id", "Id");
        fieldNameColumnName.put("customer", "Контрагент");
        fieldNameColumnName.put("summa", "Сумма");
        fieldNameColumnName.put("bill", "Выставлено счетов");
        fieldNameColumnName.put("summaPaid", "Оплачено");
        fieldNameColumnName.put("shipped", "Отгружено");
        fieldNameColumnName.put("reserved", "Зарезервировано");
        fieldNameColumnName.put("status", "Статус");
        fieldNameColumnName.put("sent", "Отправлено");
        fieldNameColumnName.put("print", "Напечатано");
        fieldNameColumnName.put("comment", "Комментарий");
        return fieldNameColumnName;
    }
}
