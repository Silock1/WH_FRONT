package com.warehouse_accounting.components.purchases.grids;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.warehouse_accounting.models.dto.InvoiceProductDto;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class PurchasesGridLayout extends HorizontalLayout {

    private final TextField selectedTextField;
    //  private final Grid<InvoiceDto> invoiceOrdersDtoGrid = new Grid<>(InvoiceDto.class, false);
    private final Grid<InvoiceProductDto> invoiceProductOrdersDtoGrid = new Grid<>(InvoiceProductDto.class, false);

    public PurchasesGridLayout(TextField selectedTextField) {
        this.selectedTextField = selectedTextField;
        add(initInvoiceProductGrid());
    }

    private Grid<InvoiceProductDto> initInvoiceProductGrid() {
        invoiceProductOrdersDtoGrid.setColumns(getVisibleInvoiceColumn().keySet().toArray(String[]::new));
        invoiceProductOrdersDtoGrid.setSelectionMode(Grid.SelectionMode.MULTI);

        getVisibleInvoiceColumn().forEach((key, value) -> invoiceProductOrdersDtoGrid.getColumnByKey(key).setHeader(value));
        invoiceProductOrdersDtoGrid.asMultiSelect().addSelectionListener(listener -> {
            int selectSize = listener.getAllSelectedItems().size();
            selectedTextField.setValue(String.valueOf(selectSize));
        });
        invoiceProductOrdersDtoGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES);
        return invoiceProductOrdersDtoGrid;
    }

    public Grid<InvoiceProductDto> getInvoiceProductGrid() {
        return invoiceProductOrdersDtoGrid;
    }
    private HashMap<String, String> getVisibleInvoiceColumn() {
        HashMap<String, String> fieldNameColumnName = new LinkedHashMap<>();
        fieldNameColumnName.put("invoiceDto.number","№");
        fieldNameColumnName.put("invoiceDto.invoiceDateTime", "Время");
        fieldNameColumnName.put("invoiceDto.invoiceAuthorLastName", "Контрагент");
        fieldNameColumnName.put("invoiceDto.companyName", "Организация");
        fieldNameColumnName.put("sum", "Сумма");
        fieldNameColumnName.put("invoiceDto.projectName", "Проект");
        fieldNameColumnName.put("invoiceDto.warehouseName", "На склад");
        fieldNameColumnName.put("invoiceDto.contractorName", "Договор");
        fieldNameColumnName.put("invoiceDto.comment", "Комментарий");
        return fieldNameColumnName;
    }

}
