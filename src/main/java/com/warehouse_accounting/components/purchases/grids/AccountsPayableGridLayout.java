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
import com.warehouse_accounting.models.dto.InvoiceAccountsPayableDto;

import java.util.HashMap;
import java.util.LinkedHashMap;


public class AccountsPayableGridLayout extends HorizontalLayout {
    private final TextField textField;

    private Button settingButton = new Button(new Icon(VaadinIcon.COG_O));
    private final Grid<InvoiceAccountsPayableDto> invoiceAccountsPayableDtoGrid = new Grid<>(InvoiceAccountsPayableDto.class, false);

    public AccountsPayableGridLayout(TextField textField) {
        this.textField = textField;
        add(initInvoiceAccountsPayableGrid(), settingButton);
    }

    private Grid<InvoiceAccountsPayableDto> initInvoiceAccountsPayableGrid() {
        invoiceAccountsPayableDtoGrid.setColumns(getVisibleInvoiceColumn().keySet().toArray(String[]::new));
        invoiceAccountsPayableDtoGrid.setSelectionMode(Grid.SelectionMode.MULTI);

        settingButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        ColumnToggleContextMenu<InvoiceAccountsPayableDto> columnToggleContextMenu = new ColumnToggleContextMenu<>(settingButton);

        getVisibleInvoiceColumn().forEach((key, value) -> {
            Grid.Column<InvoiceAccountsPayableDto> invoiceAccountsPayableDtoColumn = invoiceAccountsPayableDtoGrid.getColumnByKey(key).setHeader(value);
            columnToggleContextMenu.addColumnToggleItem(value, invoiceAccountsPayableDtoColumn);
        });
        invoiceAccountsPayableDtoGrid.asMultiSelect().addSelectionListener(listener -> {
            int selectSize = listener.getAllSelectedItems().size();
            textField.setValue(String.valueOf(selectSize));
        });
        invoiceAccountsPayableDtoGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES);
        return invoiceAccountsPayableDtoGrid;
    }

    public Grid<InvoiceAccountsPayableDto> getInvoiceAccountsPayableDtoGrid() {
        return invoiceAccountsPayableDtoGrid;
    }

    private HashMap<String, String> getVisibleInvoiceColumn() {
        HashMap<String, String> fieldNameColumnName = new LinkedHashMap<>();
        fieldNameColumnName.put("invoiceDto.number", "???");
        fieldNameColumnName.put("invoiceDto.invoiceDateTime", "??????????");
        fieldNameColumnName.put("invoiceDto.invoiceAuthorLastName", "????????????????????");
        fieldNameColumnName.put("invoiceDto.companyName", "??????????????????????");
        fieldNameColumnName.put("invoiceDto.warehouseName", "???? ??????????");
        fieldNameColumnName.put("sum", "??????????");
        fieldNameColumnName.put("dueDate", "???????? ????????????");
        fieldNameColumnName.put("invoiceDto.projectName", "????????????");
        fieldNameColumnName.put("invoiceDto.contractorName", "??????????????");
        fieldNameColumnName.put("invoiceDto.comment", "??????????????????????");
        return fieldNameColumnName;
    }
}
