package com.warehouse_accounting.components.purchases.grids;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.warehouse_accounting.models.dto.SupplierInvoiceDto;
import com.warehouse_accounting.services.impl.SupplierInvoiceServiceImpl;
import com.warehouse_accounting.services.interfaces.SupplierInvoiceService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SupplierInvoiceGridLayout extends HorizontalLayout {

    public static Grid<SupplierInvoiceDto> initSupplierInvoiceGrid(){

        Grid<SupplierInvoiceDto> supplierInvoiceDtoGrid = new Grid<>(SupplierInvoiceDto.class, false);

        supplierInvoiceDtoGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES );
        supplierInvoiceDtoGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        supplierInvoiceDtoGrid.addThemeVariants(GridVariant.LUMO_COMPACT);
        supplierInvoiceDtoGrid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
        supplierInvoiceDtoGrid.setHeightByRows(true);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:4446")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SupplierInvoiceService supplierInvoiceService = new SupplierInvoiceServiceImpl("/api/supplier_invoices",retrofit);

        supplierInvoiceDtoGrid.addColumn(SupplierInvoiceDto::getId).setHeader("ID").setSortable(true).setAutoWidth(true);
        supplierInvoiceDtoGrid.addColumn(SupplierInvoiceDto::getInvoiceNumber).setHeader("Счет поставщика №").setSortable(true).setAutoWidth(true);
        supplierInvoiceDtoGrid.addColumn(SupplierInvoiceDto::getDateInvoiceNumber).setHeader("От даты").setSortable(true).setAutoWidth(true);
        supplierInvoiceDtoGrid.addColumn(SupplierInvoiceDto::getOrganization).setHeader("Организация").setSortable(true).setAutoWidth(true);
        supplierInvoiceDtoGrid.addColumn(SupplierInvoiceDto::getWarehouse).setHeader("Склад").setSortable(true).setAutoWidth(true);
        supplierInvoiceDtoGrid.addColumn(SupplierInvoiceDto::getContrAgent).setHeader("Контрагент").setSortable(true).setAutoWidth(true);
        supplierInvoiceDtoGrid.addColumn(SupplierInvoiceDto::getContract).setHeader("Договор").setSortable(true).setAutoWidth(true);
        supplierInvoiceDtoGrid.addColumn(SupplierInvoiceDto::getDatePay).setHeader("Дата оплаты").setSortable(true).setAutoWidth(true);
        supplierInvoiceDtoGrid.addColumn(SupplierInvoiceDto::getProject).setHeader("Проект").setSortable(true).setAutoWidth(true);
        supplierInvoiceDtoGrid.addColumn(SupplierInvoiceDto::getIncomingNumber).setHeader("Входящий номер").setSortable(true).setAutoWidth(true);
        supplierInvoiceDtoGrid.addColumn(SupplierInvoiceDto::getDateIncomingNumber).setHeader("От даты").setSortable(true).setAutoWidth(true);
        supplierInvoiceDtoGrid.addColumn(SupplierInvoiceDto::getAddPosition).setHeader("Наименование позиции").setSortable(true).setAutoWidth(true);
        supplierInvoiceDtoGrid.addColumn(SupplierInvoiceDto::getAddComment).setHeader("Комментарий").setSortable(true).setAutoWidth(true);

        supplierInvoiceDtoGrid.setItems(supplierInvoiceService.getAll());

        return supplierInvoiceDtoGrid;
    }
}
