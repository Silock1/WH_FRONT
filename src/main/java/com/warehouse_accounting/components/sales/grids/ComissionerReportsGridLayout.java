package com.warehouse_accounting.components.sales.grids;

import com.vaadin.flow.component.Component;
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
import com.warehouse_accounting.models.dto.ComissionerReportsDto;
import com.warehouse_accounting.models.dto.InvoiceDto;

public class ComissionerReportsGridLayout extends HorizontalLayout {

    private final Grid<ComissionerReportsDto> dtoGrid = new Grid<>(ComissionerReportsDto.class, false);

    public ComissionerReportsGridLayout() {

        Grid.Column<ComissionerReportsDto> docTypeColumn = dtoGrid.addColumn(ComissionerReportsDto::getDocType).setHeader("Тип документа");
        Grid.Column<ComissionerReportsDto> numberColumn = dtoGrid.addColumn(ComissionerReportsDto::getNumber).setHeader("№");
        Grid.Column<ComissionerReportsDto> timeColumn = dtoGrid.addColumn(ComissionerReportsDto::getTime).setHeader("Время");
        Grid.Column<ComissionerReportsDto> organizationColumn = dtoGrid.addColumn(ComissionerReportsDto::getOrganization).setHeader("Организация");
        Grid.Column<ComissionerReportsDto> organizationAccountColumn = dtoGrid.addColumn(ComissionerReportsDto::getOrganizationAccount).setHeader("Счёт организации");
        Grid.Column<ComissionerReportsDto> contractorColumn = dtoGrid.addColumn(ComissionerReportsDto::getContractor).setHeader("Контрагент");
        Grid.Column<ComissionerReportsDto> contractorAccountColumn = dtoGrid.addColumn(ComissionerReportsDto::getContractorAccount).setHeader("Счёт контрагента");
        Grid.Column<ComissionerReportsDto> sumColumn = dtoGrid.addColumn(ComissionerReportsDto::getSum).setHeader("Сумма");
        Grid.Column<ComissionerReportsDto> projectColumn = dtoGrid.addColumn(ComissionerReportsDto::getProject).setHeader("Проект");
        Grid.Column<ComissionerReportsDto> contractColumn = dtoGrid.addColumn(ComissionerReportsDto::getContract).setHeader("Договор");
        Grid.Column<ComissionerReportsDto> sumOfRewardColumn = dtoGrid.addColumn(ComissionerReportsDto::getSumOfReward).setHeader("Сумма вознаграждения");
        Grid.Column<ComissionerReportsDto> sumOfCommittentColumn = dtoGrid.addColumn(ComissionerReportsDto::getSumOfCommittent).setHeader("Сумма комитента");
        Grid.Column<ComissionerReportsDto> paidColumn = dtoGrid.addColumn(ComissionerReportsDto::getPaid).setHeader("Оплачено");
        Grid.Column<ComissionerReportsDto> remainsToPayColumn = dtoGrid.addColumn(ComissionerReportsDto::getRemainsToPay).setHeader("Осталось оплатить");
        Grid.Column<ComissionerReportsDto> startOfPeriodColumn = dtoGrid.addColumn(ComissionerReportsDto::getStartOfPeriod).setHeader("Начало периода");
        Grid.Column<ComissionerReportsDto> endOfPeriodColumn = dtoGrid.addColumn(ComissionerReportsDto::getEndOfPeriod).setHeader("Конец периода");
        Grid.Column<ComissionerReportsDto> incomingNumberColumn = dtoGrid.addColumn(ComissionerReportsDto::getIncomingNumber).setHeader("Входящий номер");
        Grid.Column<ComissionerReportsDto> incomingDateColumn = dtoGrid.addColumn(ComissionerReportsDto::getIncomingDate).setHeader("Входящая дата");
        Grid.Column<ComissionerReportsDto> salesChannelColumn = dtoGrid.addColumn(ComissionerReportsDto::getSalesChannel).setHeader("Канал продаж");
        Grid.Column<ComissionerReportsDto> generalAccessColumn = dtoGrid.addColumn(ComissionerReportsDto::getGeneralAccess).setHeader("Общий доступ");
        Grid.Column<ComissionerReportsDto> ownerDepartmentColumn = dtoGrid.addColumn(ComissionerReportsDto::getOwnerDepartment).setHeader("Владелец-отдел");
        Grid.Column<ComissionerReportsDto> ownerEmployeeColumn = dtoGrid.addColumn(ComissionerReportsDto::getOwnerEmployee).setHeader("Владелец-сотрудник");
        Grid.Column<ComissionerReportsDto> statusColumn = dtoGrid.addColumn(ComissionerReportsDto::getStatus).setHeader("Статус");
        Grid.Column<ComissionerReportsDto> sentColumn = dtoGrid.addColumn(ComissionerReportsDto::getSent).setHeader("Отправлено");
        Grid.Column<ComissionerReportsDto> printedColumn = dtoGrid.addColumn(ComissionerReportsDto::getPrinted).setHeader("Напечатано");
        Grid.Column<ComissionerReportsDto> commentaryColumn = dtoGrid.addColumn(ComissionerReportsDto::getCommentary).setHeader("Комментарий");
        Grid.Column<ComissionerReportsDto> whenChangedColumn = dtoGrid.addColumn(ComissionerReportsDto::getWhenChanged).setHeader("Когда изменён");
        Grid.Column<ComissionerReportsDto> whoChangedColumn = dtoGrid.addColumn(ComissionerReportsDto::getWhoChanged).setHeader("Кто изменил");

        dtoGrid.setSelectionMode(Grid.SelectionMode.MULTI);

        Button menuButton = new Button(new Icon(VaadinIcon.COG_O));
        menuButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        ColumnToggleContextMenu columnToggleContextMenu = new ColumnToggleContextMenu(menuButton);
        columnToggleContextMenu.addColumnToggleItem("Тип документа", docTypeColumn);
        columnToggleContextMenu.addColumnToggleItem("№", numberColumn);
        columnToggleContextMenu.addColumnToggleItem("Время", timeColumn);
        columnToggleContextMenu.addColumnToggleItem("Организация", organizationColumn);
        columnToggleContextMenu.addColumnToggleItem("Счёт организации", organizationAccountColumn);
        columnToggleContextMenu.addColumnToggleItem("Контрагент", contractorColumn);
        columnToggleContextMenu.addColumnToggleItem("Счёт контрагента", contractorAccountColumn);
        columnToggleContextMenu.addColumnToggleItem("Сумма", sumColumn);
        columnToggleContextMenu.addColumnToggleItem("Проект", projectColumn);
        columnToggleContextMenu.addColumnToggleItem("Договор", contractColumn);
        columnToggleContextMenu.addColumnToggleItem("Сумма вознаграждения", sumOfRewardColumn);
        columnToggleContextMenu.addColumnToggleItem("Сумма комитента", sumOfCommittentColumn);
        columnToggleContextMenu.addColumnToggleItem("Оплачено", paidColumn);
        columnToggleContextMenu.addColumnToggleItem("Осталось оплатить", remainsToPayColumn);
        columnToggleContextMenu.addColumnToggleItem("Начало периода", startOfPeriodColumn);
        columnToggleContextMenu.addColumnToggleItem("Конец периода", endOfPeriodColumn);
        columnToggleContextMenu.addColumnToggleItem("Входящий номер", incomingNumberColumn);
        columnToggleContextMenu.addColumnToggleItem("Входящая дата", incomingDateColumn);
        columnToggleContextMenu.addColumnToggleItem("Канал продаж", salesChannelColumn);
        columnToggleContextMenu.addColumnToggleItem("Общий доступ", generalAccessColumn);
        columnToggleContextMenu.addColumnToggleItem("Владелец-отдел", ownerDepartmentColumn);
        columnToggleContextMenu.addColumnToggleItem("Владелец-сотрудник", ownerEmployeeColumn);
        columnToggleContextMenu.addColumnToggleItem("Статус", statusColumn);
        columnToggleContextMenu.addColumnToggleItem("Отправлено", sentColumn);
        columnToggleContextMenu.addColumnToggleItem("Напечатано", printedColumn);
        columnToggleContextMenu.addColumnToggleItem("Комментарий", commentaryColumn);
        columnToggleContextMenu.addColumnToggleItem("Когда изменён", whenChangedColumn);
        columnToggleContextMenu.addColumnToggleItem("Кто изменил", whoChangedColumn);


        dtoGrid.getColumns().forEach(col -> col.setAutoWidth(true));

//        Span title = new Span("Документы");
//        title.getStyle().set("font-weight", "bold");
        HorizontalLayout headerLayout = new HorizontalLayout(menuButton);
        headerLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
        headerLayout.setFlexGrow(2);
        add(dtoGrid, headerLayout);
    }

    private static class ColumnToggleContextMenu extends ContextMenu {

        public ColumnToggleContextMenu(Component target) {
            super(target);
            setOpenOnClick(true);
        }

        void addColumnToggleItem(String label, Grid.Column<ComissionerReportsDto> column) {
            MenuItem menuItem = this.addItem(label, e -> {
                column.setVisible(e.getSource().isChecked());
            });
            menuItem.setCheckable(true);
            menuItem.setChecked(column.isVisible());
        }
    }
}
