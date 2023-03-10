package com.warehouse_accounting.components.contragents.grids;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.contragents.ContractsOrder;
import com.warehouse_accounting.components.util.ColumnToggleContextMenu;
import com.warehouse_accounting.models.dto.ContractDto;
import com.warehouse_accounting.services.interfaces.ContractService;

@SpringComponent
@UIScope
public class ContractsGridLayout extends HorizontalLayout {

    private final ContractService contractService;

    private final Button settingButton = new Button(new Icon(VaadinIcon.COG));
    private final Grid<ContractDto> contractDtoGrid;
    private ContractsOrder parent;

    public ContractsGridLayout(ContractService contractService) {
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

        Grid.Column<ContractDto> numberColumn =
                contractDtoGrid.addColumn(ContractDto::getNumber).setHeader("??????????")
                        .setSortable(true).setAutoWidth(true);

        Grid.Column<ContractDto> codColumn =
                contractDtoGrid.addColumn(ContractDto::getCode).setHeader("??????")
                        .setSortable(true).setAutoWidth(true);

        Grid.Column<ContractDto> dateColumn =
                contractDtoGrid.addColumn(ContractDto::getContractDate).setHeader("??????????")
                        .setSortable(true).setAutoWidth(true);

        Grid.Column<ContractDto> contractorNameColumn =
                contractDtoGrid.addColumn(contractDto -> contractDto.getContractor().getName()).setHeader("????????????????????")
                        .setSortable(true).setAutoWidth(true);

        Grid.Column<ContractDto> companyNameColumn =
                contractDtoGrid.addColumn(contractDto -> contractDto.getCompany().getName()).setHeader("??????????????????????")
                        .setSortable(true).setAutoWidth(true);

        Grid.Column<ContractDto> amountColumn =
                contractDtoGrid.addColumn(ContractDto::getAmount).setHeader("??????????")
                        .setSortable(true).setAutoWidth(true);
//        Grid.Column<ContractDto> paidColumn = contractDtoGrid.addColumn(ContractDto::getPaid).setHeader("????????????????").setSortable(true).setAutoWidth(true);
//        Grid.Column<ContractDto> doneColumn = contractDtoGrid.addColumn(ContractDto::getDone).setHeader("??????????????????").setSortable(true).setAutoWidth(true);
//        Grid.Column<ContractDto> isSharedAccessColumn = contractDtoGrid.addColumn(ContractDto::getIsSharedAccess).setHeader("?????????? ????????????").setSortable(true).setAutoWidth(true);
//        Grid.Column<ContractDto> ownerDepartmentColumn = contractDtoGrid.addColumn(contractDto -> contractDto.getOwnerDepartment() != null ? contractDto.getOwnerDepartment().getName() : null).setHeader("???????????????? ??????????").setSortable(true).setAutoWidth(true);
//        Grid.Column<ContractDto> ownerEmployeeColumn = contractDtoGrid.addColumn(contractDto -> contractDto.getOwnerEmployee() != null ? contractDto.getOwnerEmployee().getFirstName() : null).setHeader("???????????????? ??????????????????").setSortable(true).setAutoWidth(true);
//        Grid.Column<ContractDto> isSendColumn = contractDtoGrid.addColumn(ContractDto::getIsSend).setHeader("????????????????????").setSortable(true).setAutoWidth(true);
//        Grid.Column<ContractDto> isPrintColumn = contractDtoGrid.addColumn(ContractDto::getIsPrint).setHeader("????????????????????").setSortable(true).setAutoWidth(true);
//        Grid.Column<ContractDto> whenChangeColumn = contractDtoGrid.addColumn(ContractDto::getWhenChange).setHeader("?????????? ??????????????").setSortable(true).setAutoWidth(true);
//        Grid.Column<ContractDto> whoChangeColumn = contractDtoGrid.addColumn(contractDto -> contractDto.getWhoChange() != null ? contractDto.getWhoChange().getFirstName() : null).setHeader("?????? ??????????????").setSortable(true).setAutoWidth(true);
        Grid.Column<ContractDto> commentColumn =
                contractDtoGrid.addColumn(ContractDto::getComment).setHeader("??????????????????????")
                        .setSortable(true).setAutoWidth(true);

        contractDtoGrid.addItemDoubleClickListener(event -> parent.editFormActivate(event.getItem()));

        contractDtoGrid.setSelectionMode(Grid.SelectionMode.MULTI);

        settingButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        ColumnToggleContextMenu<ContractDto> columnToggleContextMenu = new ColumnToggleContextMenu<>(settingButton);

        columnToggleContextMenu.addColumnToggleItem("??????????", numberColumn);
        columnToggleContextMenu.addColumnToggleItem("??????", codColumn);
        columnToggleContextMenu.addColumnToggleItem("??????????", dateColumn);
        columnToggleContextMenu.addColumnToggleItem("????????????????????", contractorNameColumn);
        columnToggleContextMenu.addColumnToggleItem("??????????????????????", companyNameColumn);
//        columnToggleContextMenu.addColumnToggleItem("??????????", paidColumn);
//        columnToggleContextMenu.addColumnToggleItem("????????????????", doneColumn);
//        columnToggleContextMenu.addColumnToggleItem("??????????????????", amountColumn);
//        columnToggleContextMenu.addColumnToggleItem("?????????? ????????????", isSharedAccessColumn);
//        columnToggleContextMenu.addColumnToggleItem("???????????????? ??????????", ownerDepartmentColumn);
//        columnToggleContextMenu.addColumnToggleItem("???????????????? ??????????????????", ownerEmployeeColumn);
//        columnToggleContextMenu.addColumnToggleItem("????????????????????", isSendColumn);
//        columnToggleContextMenu.addColumnToggleItem("????????????????????", isPrintColumn);
        columnToggleContextMenu.addColumnToggleItem("??????????", amountColumn);
        columnToggleContextMenu.addColumnToggleItem("??????????????????????", commentColumn);
//        columnToggleContextMenu.addColumnToggleItem("?????????? ??????????????", whenChangeColumn);
//        columnToggleContextMenu.addColumnToggleItem("?????? ??????????????", whoChangeColumn);
        return contractDtoGrid;
    }

    public void setParent(ContractsOrder parent) {
        this.parent = parent;
    }
}

