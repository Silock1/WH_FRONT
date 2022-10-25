package com.warehouse_accounting.components.retail.grids;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.warehouse_accounting.components.util.ColumnToggleContextMenu;
import com.warehouse_accounting.components.util.SilverButton;
import com.warehouse_accounting.models.dto.BonusTransactionDto;
import com.warehouse_accounting.services.interfaces.BonusTransactionService;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BonusTransactionGridLayout extends HorizontalLayout {
    private Grid<BonusTransactionDto> pointsGrid = new Grid<>(BonusTransactionDto.class, false);
    private BonusTransactionService service;

    public BonusTransactionGridLayout(BonusTransactionService service) {
        this.service = service;

        setSizeFull();
        add(
                tableContent()
        );

    }

    private HorizontalLayout tableContent() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        horizontalLayout.setSizeFull();
        pointsGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        pointsGrid.setHeightByRows(true);

        pointsGrid.setItems(service.getAll());
        pointsGrid.setSelectionMode(Grid.SelectionMode.MULTI);


        horizontalLayout.add(pointsGrid, settingButtonOperations());
        return horizontalLayout;
    }

    private Button settingButtonOperations() {
        SilverButton silverButton = new SilverButton();

        Grid.Column<BonusTransactionDto> idColumn = pointsGrid.addColumn(BonusTransactionDto::getId).setHeader("№").setSortable(true).setResizable(true);
        Grid.Column<BonusTransactionDto> dateCreateColumn = pointsGrid.addColumn(BonusTransactionDto::getCreated).setHeader("Дата создания").setSortable(true).setResizable(true);
        Grid.Column<BonusTransactionDto> operationColumn = pointsGrid.addColumn(BonusTransactionDto::getTransactionType).setHeader("Тип операции").setSortable(true).setResizable(true);
        Grid.Column<BonusTransactionDto> bonusColumn = pointsGrid.addColumn(BonusTransactionDto::getBonusValue).setHeader("Бонусные баллы").setSortable(true).setResizable(true);
        Grid.Column<BonusTransactionDto> statusColumn = pointsGrid.addColumn(BonusTransactionDto::getTransactionStatus).setHeader("Статус").setSortable(true).setResizable(true);
        Grid.Column<BonusTransactionDto> dateChargeColumn = pointsGrid.addColumn(BonusTransactionDto::getExecutionDate).setHeader("Дата начисления").setSortable(true).setResizable(true);
        Grid.Column<BonusTransactionDto> bonusProgramColumn = pointsGrid.addColumn(transaction -> transaction.getBonusProgramDto().getName()).setHeader("Бонусная программа").setSortable(true).setResizable(true);
        Grid.Column<BonusTransactionDto> contractorColumn = pointsGrid.addColumn(transaction -> transaction.getContractorDto().getName()).setHeader("Контрагент").setSortable(true).setResizable(true);
        Grid.Column<BonusTransactionDto> commentaryColumn = pointsGrid.addColumn(BonusTransactionDto::getComment).setHeader("Комментарий").setSortable(true).setResizable(true);
        Grid.Column<BonusTransactionDto> departmentColumn = pointsGrid.addColumn(transaction -> transaction.getDepartmentDto().getName()).setHeader("Владелец-отдел").setSortable(true).setResizable(true);
        Grid.Column<BonusTransactionDto> generalAccessColumn = pointsGrid.addColumn(BonusTransactionDto::isGeneralAccess).setHeader("Общий доступ").setSortable(true).setResizable(true);
        Grid.Column<BonusTransactionDto> ownerColumn = pointsGrid.addColumn(transaction -> transaction.getOwnerDto().getFirstName()).setHeader("Владелец-сотрудник").setSortable(true).setResizable(true);
        Grid.Column<BonusTransactionDto> dateChangedColumn = pointsGrid.addColumn(BonusTransactionDto::getDateChange).setHeader("Дата изменения").setSortable(true).setResizable(true);
        Grid.Column<BonusTransactionDto> employeeChangeColumn = pointsGrid.addColumn(transaction -> transaction.getOwnerChangedDto().getFirstName()).setHeader("Кто изменил").setSortable(true).setResizable(true);


        Button settingButton = silverButton.buttonSetting();
        ColumnToggleContextMenu<BonusTransactionDto> columnToggleContextMenu = new ColumnToggleContextMenu<>(settingButton);
        columnToggleContextMenu.addColumnToggleItem("№", idColumn);
        columnToggleContextMenu.addColumnToggleItem("Дата создания", dateCreateColumn);
        columnToggleContextMenu.addColumnToggleItem("Тип операции", operationColumn);
        columnToggleContextMenu.addColumnToggleItem("Бонусные баллы", bonusColumn);
        columnToggleContextMenu.addColumnToggleItem("Статус", statusColumn);
        columnToggleContextMenu.addColumnToggleItem("Дата начисления", dateChargeColumn);
        columnToggleContextMenu.addColumnToggleItem("Бонусная программа", bonusProgramColumn);
        columnToggleContextMenu.addColumnToggleItem("Контрагент", contractorColumn);
        columnToggleContextMenu.addColumnToggleItem("Комментарий", commentaryColumn);
        columnToggleContextMenu.addColumnToggleItem("Владелец-сотрудник", ownerColumn);
        columnToggleContextMenu.addColumnToggleItem("Общий доступ", generalAccessColumn);
        columnToggleContextMenu.addColumnToggleItem("Владелец-отдел", departmentColumn);
        columnToggleContextMenu.addColumnToggleItem("Дата изменения", dateChangedColumn);
        columnToggleContextMenu.addColumnToggleItem("Кто изменил", employeeChangeColumn);

        return settingButton;
    }


}
