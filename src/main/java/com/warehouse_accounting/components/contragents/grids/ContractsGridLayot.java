package com.warehouse_accounting.components.contragents.grids;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.contragents.ContractsOrder;
import com.warehouse_accounting.models.dto.ContractDto;
import com.warehouse_accounting.services.interfaces.ContractService;

@SpringComponent
@UIScope
public class ContractsGridLayot extends HorizontalLayout {

    private final ContractService contractService;

    private Button settingButton = new Button(new Icon(VaadinIcon.COG_O));
    private Grid<ContractDto> contractDtoGrid;
    private ContractsOrder parent;

    public ContractsGridLayot(ContractService contractService) {
        this.contractService = contractService;
        contractDtoGrid = initGrid();
        contractDtoGrid.setItems(contractService.getAll());
        add(contractDtoGrid, settingButton);
        setSizeFull();
    }

    public void refreshDate() {
        removeAll();
        contractDtoGrid.setItems(contractService.getAll());
        add(contractDtoGrid, settingButton);
        setSizeFull();
    }

    private Grid<ContractDto> initGrid() {
        Grid<ContractDto> contractDtoGrid = new Grid<>(ContractDto.class, false);
        contractDtoGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        contractDtoGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        contractDtoGrid.addThemeVariants(GridVariant.LUMO_COMPACT);
        contractDtoGrid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
        contractDtoGrid.setHeightByRows(true);

        Grid.Column<ContractDto> numberColumn = contractDtoGrid.addColumn(ContractDto::getId).setHeader("Номер").setSortable(true).setAutoWidth(true);
        Grid.Column<ContractDto> codColumn = contractDtoGrid.addColumn(ContractDto::getNumber).setHeader("Код").setSortable(true).setAutoWidth(true);
        Grid.Column<ContractDto> dateColumn = contractDtoGrid.addColumn(ContractDto::getContractDate).setHeader("Время").setSortable(true).setAutoWidth(true);
        Grid.Column<ContractDto> contractorNameColumn = contractDtoGrid.addColumn(contractDto -> contractDto.getContractorDto().getName()).setHeader("Контрагент").setSortable(true).setAutoWidth(true);
        Grid.Column<ContractDto> companyNameColumn = contractDtoGrid.addColumn(contractDto -> contractDto.getCompanyDto().getName()).setHeader("Организация").setSortable(true).setAutoWidth(true);
        Grid.Column<ContractDto> amountColumn = contractDtoGrid.addColumn(ContractDto::getAmount).setHeader("Сумма").setSortable(true).setAutoWidth(true);
        Grid.Column<ContractDto> paidColumn = contractDtoGrid.addColumn(ContractDto::getPaid).setHeader("Оплачено").setSortable(true).setAutoWidth(true);
        Grid.Column<ContractDto> doneColumn = contractDtoGrid.addColumn(ContractDto::getDone).setHeader("Выполнено").setSortable(true).setAutoWidth(true);
        Grid.Column<ContractDto> isSharedAccessColumn = contractDtoGrid.addColumn(ContractDto::getIsSharedAccess).setHeader("Общий доступ").setSortable(true).setAutoWidth(true);
        Grid.Column<ContractDto> ownerDepartmentColumn = contractDtoGrid.addColumn(contractDto -> contractDto.getOwnerDepartment() != null ? contractDto.getOwnerDepartment().getName() : null).setHeader("Владелец отдел").setSortable(true).setAutoWidth(true);
        Grid.Column<ContractDto> ownerEmployeeColumn = contractDtoGrid.addColumn(contractDto -> contractDto.getOwnerEmployee() != null ? contractDto.getOwnerEmployee().getFirstName() : null).setHeader("Владелец сотрудник").setSortable(true).setAutoWidth(true);
        Grid.Column<ContractDto> isSendColumn = contractDtoGrid.addColumn(ContractDto::getIsSend).setHeader("Отправлено").setSortable(true).setAutoWidth(true);
        Grid.Column<ContractDto> isPrintColumn = contractDtoGrid.addColumn(ContractDto::getIsPrint).setHeader("Напечатано").setSortable(true).setAutoWidth(true);
        Grid.Column<ContractDto> whenChangeColumn = contractDtoGrid.addColumn(ContractDto::getWhenChange).setHeader("Когда изменен").setSortable(true).setAutoWidth(true);
        Grid.Column<ContractDto> whoChangeColumn = contractDtoGrid.addColumn(contractDto -> contractDto.getWhoChange() != null ? contractDto.getWhoChange().getFirstName() : null).setHeader("Кто изменил").setSortable(true).setAutoWidth(true);
        Grid.Column<ContractDto> commentColumn = contractDtoGrid.addColumn(ContractDto::getComment).setHeader("Комментарий").setSortable(true).setAutoWidth(true);

        contractDtoGrid.setSelectionMode(Grid.SelectionMode.MULTI);

        settingButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        ContractsGridLayot.ColumnToggleContextMenu columnToggleContextMenu = new ContractsGridLayot.ColumnToggleContextMenu(settingButton);
        columnToggleContextMenu.addColumnToggleItem("Номер", numberColumn);
        columnToggleContextMenu.addColumnToggleItem("Код", codColumn);
        columnToggleContextMenu.addColumnToggleItem("Время", dateColumn);
        columnToggleContextMenu.addColumnToggleItem("Контрагент", contractorNameColumn);
        columnToggleContextMenu.addColumnToggleItem("Организация", companyNameColumn);
        columnToggleContextMenu.addColumnToggleItem("Сумма", paidColumn);
        columnToggleContextMenu.addColumnToggleItem("Оплачено", doneColumn);
        columnToggleContextMenu.addColumnToggleItem("Выполнено", amountColumn);
        columnToggleContextMenu.addColumnToggleItem("Общий доступ", isSharedAccessColumn);
        columnToggleContextMenu.addColumnToggleItem("Владелец отдел", ownerDepartmentColumn);
        columnToggleContextMenu.addColumnToggleItem("Владелец сотрудник", ownerEmployeeColumn);
        columnToggleContextMenu.addColumnToggleItem("Отправлено", isSendColumn);
        columnToggleContextMenu.addColumnToggleItem("Напечатано", isPrintColumn);
        columnToggleContextMenu.addColumnToggleItem("Комментарий", commentColumn);
        columnToggleContextMenu.addColumnToggleItem("Когда изменен", whenChangeColumn);
        columnToggleContextMenu.addColumnToggleItem("Кто изменил", whoChangeColumn);
        return contractDtoGrid;
    }

    private static class ColumnToggleContextMenu extends ContextMenu {

        public ColumnToggleContextMenu(com.vaadin.flow.component.Component target) { //com.vaadin.flow.component.
            super(target);
            setOpenOnClick(true); //Если true то принимает "click"
        }

        void addColumnToggleItem(String label, Grid.Column<ContractDto> column) {
            MenuItem menuItem = this.addItem(label, e -> {
                column.setVisible(e.getSource().isChecked());
            });
            menuItem.setCheckable(true);
            menuItem.setChecked(column.isVisible());
        }
    }

    public void setParent(ContractsOrder parent) {
        this.parent = parent;
    }
}

