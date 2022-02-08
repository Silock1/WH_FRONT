package com.warehouse_accounting.components.sales.grids;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.warehouse_accounting.models.dto.InvoiceDto;
import com.warehouse_accounting.services.interfaces.InvoiceService;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class SalesInvoicesGridLayout extends HorizontalLayout {

    private final TextField selectedTextField;
    private final Grid<InvoiceDto> invoiceDtoGrid = new Grid<>(InvoiceDto.class, false);

    public SalesInvoicesGridLayout(TextField selectedTextField) {
        this.selectedTextField = selectedTextField;

        Grid.Column<InvoiceDto> idColumn = invoiceDtoGrid.addColumn(InvoiceDto::getId).setHeader("Id");
        Grid.Column<InvoiceDto> numberColumn = invoiceDtoGrid.addColumn(InvoiceDto::getNumber).setHeader("Номер");
        Grid.Column<InvoiceDto> invoiceDateTimeColumn = invoiceDtoGrid.addColumn(InvoiceDto::getInvoiceDateTime).setHeader("Время");
        Grid.Column<InvoiceDto> typeColumn = invoiceDtoGrid.addColumn(InvoiceDto::getType).setHeader("Напечатано");
        Grid.Column<InvoiceDto> invoiceAuthorIdColumn = invoiceDtoGrid.addColumn(InvoiceDto::getInvoiceAuthorId).setHeader("Id Автора");
        Grid.Column<InvoiceDto> invoiceAuthorLastNameColumn = invoiceDtoGrid.addColumn(InvoiceDto::getInvoiceAuthorLastName).setHeader("Фамилие Автора");
        Grid.Column<InvoiceDto> companyIdColumn = invoiceDtoGrid.addColumn(InvoiceDto::getCompanyId).setHeader("Id компании");
        Grid.Column<InvoiceDto> companyNameColumn = invoiceDtoGrid.addColumn(InvoiceDto::getCompanyName).setHeader("Компания");
        Grid.Column<InvoiceDto> projectIdColumn = invoiceDtoGrid.addColumn(InvoiceDto::getProjectId).setHeader("ID проекта");
        Grid.Column<InvoiceDto> projectNameColumn = invoiceDtoGrid.addColumn(InvoiceDto::getProjectName).setHeader("Проект");
        Grid.Column<InvoiceDto> warehouseIdColumn = invoiceDtoGrid.addColumn(InvoiceDto::getWarehouseId).setHeader("warehouseId");
        Grid.Column<InvoiceDto> warehouseNameColumn = invoiceDtoGrid.addColumn(InvoiceDto::getWarehouseName).setHeader("warehouseName");
        Grid.Column<InvoiceDto> commentColumn = invoiceDtoGrid.addColumn(InvoiceDto::getComment).setHeader("Коментраии");
        Grid.Column<InvoiceDto> contractorIdColumn = invoiceDtoGrid.addColumn(InvoiceDto::getContractorId).setHeader("ID Контрагента");
        Grid.Column<InvoiceDto> contractorNameColumn = invoiceDtoGrid.addColumn(InvoiceDto::getContractorName).setHeader("Контрагент");
        Grid.Column<InvoiceDto> contractIdColumn = invoiceDtoGrid.addColumn(InvoiceDto::getContractId).setHeader("ID Контракт");
        Grid.Column<InvoiceDto> contractNumberColumn = invoiceDtoGrid.addColumn(InvoiceDto::getContractNumber).setHeader("Контракта");
        Grid.Column<InvoiceDto> contractDateColumn = invoiceDtoGrid.addColumn(InvoiceDto::getContractDate).setHeader("Дата Контракта");
        Grid.Column<InvoiceDto> editsColumn = invoiceDtoGrid.addColumn(InvoiceDto::getEdits).setHeader("Редактировано");

        invoiceDtoGrid.setSelectionMode(Grid.SelectionMode.MULTI); //

        Button menuButton = new Button(new Icon(VaadinIcon.COG));
        menuButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        ColumnToggleContextMenu columnToggleContextMenu = new ColumnToggleContextMenu(
                menuButton);
        columnToggleContextMenu.addColumnToggleItem("Id", idColumn);
        columnToggleContextMenu.addColumnToggleItem("Номер", numberColumn);
        columnToggleContextMenu.addColumnToggleItem("Время", invoiceDateTimeColumn);
        columnToggleContextMenu.addColumnToggleItem("Напечатано", typeColumn);
        columnToggleContextMenu.addColumnToggleItem("Id Автора", invoiceAuthorIdColumn);
        columnToggleContextMenu.addColumnToggleItem("Фамилие Автора", invoiceAuthorLastNameColumn);
        columnToggleContextMenu.addColumnToggleItem("Id компании", companyIdColumn);
        columnToggleContextMenu.addColumnToggleItem("Компания", companyNameColumn);
        columnToggleContextMenu.addColumnToggleItem("ID проекта", projectIdColumn);
        columnToggleContextMenu.addColumnToggleItem("Проект", projectNameColumn);
        columnToggleContextMenu.addColumnToggleItem("warehouseId", warehouseIdColumn);
        columnToggleContextMenu.addColumnToggleItem("warehouseName", warehouseNameColumn);
        columnToggleContextMenu.addColumnToggleItem("Коментраии", commentColumn);
        columnToggleContextMenu.addColumnToggleItem("ID Контрагента", contractorIdColumn);
        columnToggleContextMenu.addColumnToggleItem("Контрагент", contractorNameColumn);
        columnToggleContextMenu.addColumnToggleItem("ID Контракт", contractIdColumn);
        columnToggleContextMenu.addColumnToggleItem("Контракта", contractNumberColumn);
        columnToggleContextMenu.addColumnToggleItem("Дата Контракта", contractDateColumn);
        columnToggleContextMenu.addColumnToggleItem("Редактировано", editsColumn);


//        List<InvoiceDto> invoces = invoiceService.getAll();
//        invoiceDtoGrid.setItems(invoces);

        Span title = new Span("Документы");
        title.getStyle().set("font-weight", "bold");
        HorizontalLayout headerLayout = new HorizontalLayout(menuButton);
        headerLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
        headerLayout.setFlexGrow(1);
        add(invoiceDtoGrid, headerLayout);
    }

    private static class ColumnToggleContextMenu extends ContextMenu {
        public ColumnToggleContextMenu(Component target) {
            super(target);
            setOpenOnClick(true);
        }

        void addColumnToggleItem(String label, Grid.Column<InvoiceDto> column) {
            MenuItem menuItem = this.addItem(label, e -> {
                column.setVisible(e.getSource().isChecked());
            });
            menuItem.setCheckable(true);
            menuItem.setChecked(column.isVisible());
        }
    }

}
