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

        Grid.Column<ContractDto> numberColumn = contractDtoGrid.addColumn(ContractDto::getNumber).setHeader("Номер").setSortable(true).setAutoWidth(true);
        Grid.Column<ContractDto> dateColumn = contractDtoGrid.addColumn(ContractDto::getContractDate).setHeader("Время").setSortable(true).setAutoWidth(true);
        Grid.Column<ContractDto> contractorNameColumn = contractDtoGrid.addColumn(contractDto -> contractDto.getContractorDto().getName()).setHeader("Контрагент").setSortable(true).setAutoWidth(true);
        Grid.Column<ContractDto> companyNameColumn = contractDtoGrid.addColumn(contractDto -> contractDto.getCompanyDto().getName()).setHeader("Организация").setSortable(true).setAutoWidth(true);
        Grid.Column<ContractDto> amountColumn = contractDtoGrid.addColumn(ContractDto::getAmount).setHeader("Сумма").setSortable(true).setAutoWidth(true);
        Grid.Column<ContractDto> commentColumn = contractDtoGrid.addColumn(ContractDto::getComment).setHeader("Комментарий").setSortable(true).setAutoWidth(true);

        contractDtoGrid.setSelectionMode(Grid.SelectionMode.MULTI);

        settingButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        ContractsGridLayot.ColumnToggleContextMenu columnToggleContextMenu = new ContractsGridLayot.ColumnToggleContextMenu(settingButton);
        columnToggleContextMenu.addColumnToggleItem("Номер", numberColumn);
        columnToggleContextMenu.addColumnToggleItem("Время", dateColumn);
        columnToggleContextMenu.addColumnToggleItem("Контрагент", contractorNameColumn);
        columnToggleContextMenu.addColumnToggleItem("Организация", companyNameColumn);
        columnToggleContextMenu.addColumnToggleItem("Сумма", amountColumn);
        columnToggleContextMenu.addColumnToggleItem("Комментарий", commentColumn);
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

