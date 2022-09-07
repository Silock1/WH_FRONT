package com.warehouse_accounting.components.sales.grids;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.warehouse_accounting.components.util.ColumnToggleContextMenu;
import com.warehouse_accounting.models.dto.CustomerReturnsDto;

public class CustomerReturnsGridLayout extends HorizontalLayout {

    private final TextField selectedTextField;
    private final Grid<CustomerReturnsDto> customerReturnsDtoGrid = new Grid<>(CustomerReturnsDto.class, false);

    public CustomerReturnsGridLayout(TextField selectedTextField) {
        this.selectedTextField = selectedTextField;

        Grid.Column<CustomerReturnsDto> idColumn = customerReturnsDtoGrid.addColumn(CustomerReturnsDto::getId).setHeader("Id");
        Grid.Column<CustomerReturnsDto> returnDateTimeColumn = customerReturnsDtoGrid.addColumn(CustomerReturnsDto::getReturnDateTime).setHeader("Время");
        Grid.Column<CustomerReturnsDto> warehouseColumn = customerReturnsDtoGrid.addColumn(CustomerReturnsDto::getWarehouse).setHeader("На склад");
        Grid.Column<CustomerReturnsDto> contractorColumn = customerReturnsDtoGrid.addColumn(CustomerReturnsDto::getContractor).setHeader("Контрагент");
        Grid.Column<CustomerReturnsDto> companyColumn = customerReturnsDtoGrid.addColumn(CustomerReturnsDto::getCompany).setHeader("Организация");
        Grid.Column<CustomerReturnsDto> contractColumn = customerReturnsDtoGrid.addColumn(CustomerReturnsDto::getId).setHeader("Договор");
        Grid.Column<CustomerReturnsDto> projectColumn = customerReturnsDtoGrid.addColumn(CustomerReturnsDto::getId).setHeader("Проект");
        Grid.Column<CustomerReturnsDto> filesColumn = customerReturnsDtoGrid.addColumn(CustomerReturnsDto::getId).setHeader("Файлы");
        Grid.Column<CustomerReturnsDto> tasksColumn  = customerReturnsDtoGrid.addColumn(CustomerReturnsDto::getId).setHeader("Задачи");
        Grid.Column<CustomerReturnsDto> productsColumn  = customerReturnsDtoGrid.addColumn(CustomerReturnsDto::getProducts).setHeader("Товары");
        Grid.Column<CustomerReturnsDto> sumColumn = customerReturnsDtoGrid.addColumn(CustomerReturnsDto::getSum).setHeader("Сумма");
        Grid.Column<CustomerReturnsDto> isSentColumn = customerReturnsDtoGrid.addColumn(CustomerReturnsDto::isSent).setHeader("Отправлено");
        Grid.Column<CustomerReturnsDto> isPrintedColumn = customerReturnsDtoGrid.addColumn(CustomerReturnsDto::isPrinted).setHeader("Напечатано");
        Grid.Column<CustomerReturnsDto> commentColumn = customerReturnsDtoGrid.addColumn(CustomerReturnsDto::getComment).setHeader("Комментарий");

        customerReturnsDtoGrid.setSelectionMode(Grid.SelectionMode.MULTI);

        Button menuButton = new Button(new Icon(VaadinIcon.COG));
        menuButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        ColumnToggleContextMenu<CustomerReturnsDto> columnToggleContextMenu = new ColumnToggleContextMenu<>(menuButton);

        columnToggleContextMenu.addColumnToggleItem("Id", idColumn);
        columnToggleContextMenu.addColumnToggleItem("Время", returnDateTimeColumn);
        columnToggleContextMenu.addColumnToggleItem("На склад", warehouseColumn);
        columnToggleContextMenu.addColumnToggleItem("Контрагент", contractorColumn);
        columnToggleContextMenu.addColumnToggleItem("Организация", companyColumn);
        columnToggleContextMenu.addColumnToggleItem("Договор", contractColumn);
        columnToggleContextMenu.addColumnToggleItem("Проект", projectColumn);
        columnToggleContextMenu.addColumnToggleItem("Файлы", filesColumn);
        columnToggleContextMenu.addColumnToggleItem("Задачи", tasksColumn);
        columnToggleContextMenu.addColumnToggleItem("Товары", productsColumn);
        columnToggleContextMenu.addColumnToggleItem("Сумма", sumColumn);
        columnToggleContextMenu.addColumnToggleItem("Отправлено", isSentColumn);
        columnToggleContextMenu.addColumnToggleItem("Напечатано", isPrintedColumn);
        columnToggleContextMenu.addColumnToggleItem("Комментарий", commentColumn);

        Span title = new Span("Документы");
        title.getStyle().set("font-weight", "bold");
        HorizontalLayout headerLayout = new HorizontalLayout(menuButton);
        headerLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
        headerLayout.setFlexGrow(1);
        add(customerReturnsDtoGrid, headerLayout);
    }
}
