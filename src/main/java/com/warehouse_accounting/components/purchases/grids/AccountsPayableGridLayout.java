package com.warehouse_accounting.components.purchases.grids;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.warehouse_accounting.models.dto.InvoiceAccountsPayableDto;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class AccountsPayableGridLayout extends HorizontalLayout {
    private final TextField textField;

    private final Grid<InvoiceAccountsPayableDto> invoiceAccountsPayableDtoGrid =
            new Grid<>(InvoiceAccountsPayableDto.class, false);

    public AccountsPayableGridLayout(TextField textField){
        this.textField = textField;
        add(initInvoiceAccountsPayableGrid());
    }

    private Grid<InvoiceAccountsPayableDto> initInvoiceAccountsPayableGrid(){
        invoiceAccountsPayableDtoGrid.setColumns(getVisibleInvoiceColumn().keySet().toArray(String[]::new));
        invoiceAccountsPayableDtoGrid.setSelectionMode(Grid.SelectionMode.MULTI);

        getVisibleInvoiceColumn().forEach((key, value)-> invoiceAccountsPayableDtoGrid.getColumnByKey(key).setHeader(value));
        invoiceAccountsPayableDtoGrid.asMultiSelect().addSelectionListener(listener ->{
            int selectSize = listener.getAllSelectedItems().size();
            textField.setValue(String.valueOf(selectSize));
        });
        invoiceAccountsPayableDtoGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES);
        return invoiceAccountsPayableDtoGrid;
    }

    public Grid<InvoiceAccountsPayableDto> getInvoiceAccountsPayableDtoGrid(){
        return invoiceAccountsPayableDtoGrid;
    }

    private HashMap<String, String> getVisibleInvoiceColumn(){
        HashMap<String, String> fieldNameColumnName = new LinkedHashMap<>();
        fieldNameColumnName.put("invoiceDto.number","№");
        fieldNameColumnName.put("invoiceDto.invoiceDateTime", "Время");
        fieldNameColumnName.put("invoiceDto.invoiceAuthorLastName", "Контрагент");
        fieldNameColumnName.put("invoiceDto.companyName", "Организация");
        fieldNameColumnName.put("invoiceDto.warehouseName", "На склад");
        fieldNameColumnName.put("sum", "Сумма");
        fieldNameColumnName.put("dueDate", "Дата оплаты");
        fieldNameColumnName.put("invoiceDto.projectName", "Проект");
        fieldNameColumnName.put("invoiceDto.contractorName", "Договор");
        fieldNameColumnName.put("invoiceDto.comment", "Комментарий");
        return fieldNameColumnName;
    }

}
