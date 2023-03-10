package com.warehouse_accounting.components.goods.grids;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.AppView;
import com.warehouse_accounting.components.goods.InternalOrderView;
import com.warehouse_accounting.components.util.ColumnToggleContextMenu;
import com.warehouse_accounting.models.dto.InternalOrderDto;
import com.warehouse_accounting.services.interfaces.InternalOrderService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@UIScope
@Component
@Qualifier("internalOrderGridLayout")
@Route(value = "internalOrderGridLayout", layout = AppView.class)
public class InternalOrderGridLayout extends HorizontalLayout {

    private final InternalOrderService internalOrderService;

    private Button settingButton = new Button(new Icon(VaadinIcon.COG));
    private Grid<InternalOrderDto> internalOrderDtoGrid;

    private InternalOrderView parent;

    public InternalOrderGridLayout(InternalOrderService internalOrderService) {
        this.internalOrderService = internalOrderService;
        internalOrderDtoGrid = initGrid();
        internalOrderDtoGrid.setItems(internalOrderService.getAll());
        add(internalOrderDtoGrid, settingButton);
        setSizeFull();
    }

    public void refreshDate() {
        removeAll();
        internalOrderDtoGrid.setItems(internalOrderService.getAll());
        add(internalOrderDtoGrid, settingButton);
        setSizeFull();
    }

    private Grid<InternalOrderDto> initGrid() {
        Grid<InternalOrderDto> orderDtoGrid = new Grid<>(InternalOrderDto.class, false);

        Grid.Column<InternalOrderDto> numberColumn = orderDtoGrid.addColumn(InternalOrderDto::getNumber)
                .setHeader("???").setSortable(true).setAutoWidth(true);
        Grid.Column<InternalOrderDto> dateColumn = orderDtoGrid.addColumn(InternalOrderDto::getLocalDateTime)
                .setHeader("??????????").setSortable(true).setAutoWidth(true);
        Grid.Column<InternalOrderDto> companyNameColumn = orderDtoGrid.addColumn(internalOrderDto ->
                        internalOrderDto.getOrganization() != null ? internalOrderDto.getOrganization().getName() : null)
                .setHeader("??????????????????????").setSortable(true).setAutoWidth(true);
        Grid.Column<InternalOrderDto> sumColumn = orderDtoGrid.addColumn(InternalOrderDto::getSum)
                .setHeader("??????????").setSortable(true).setAutoWidth(true);
        Grid.Column<InternalOrderDto> shippedColumn = orderDtoGrid.addColumn(InternalOrderDto::getShipped)
                .setHeader("??????????????????").setSortable(true).setAutoWidth(true);
        Grid.Column<InternalOrderDto> projectNameColumn = orderDtoGrid.addColumn(internalOrderDto ->
                        internalOrderDto.getProjectDto() != null ? internalOrderDto.getProjectDto().getName() : null)
                .setHeader("????????????").setSortable(true).setAutoWidth(true);
        Grid.Column<InternalOrderDto> isSharedAccessColumn = orderDtoGrid.addColumn(InternalOrderDto::getIsSharedAccess)
                .setHeader("?????????? ????????????").setSortable(true).setAutoWidth(true);
        Grid.Column<InternalOrderDto> ownerDepartmentColumn = orderDtoGrid.addColumn(internalOrderDto ->
                        internalOrderDto.getOwnerDepartment() != null ? internalOrderDto.getOwnerDepartment().getName() : null)
                .setHeader("???????????????? ??????????").setSortable(true).setAutoWidth(true);
        Grid.Column<InternalOrderDto> ownerEmployeeColumn = orderDtoGrid.addColumn(internalOrderDto ->
                        internalOrderDto.getOwnerEmployee() != null ? internalOrderDto.getOwnerEmployee().getFirstName() : null)
                .setHeader("???????????????? ??????????????????").setSortable(true).setAutoWidth(true);
        Grid.Column<InternalOrderDto> isSendColumn = orderDtoGrid.addColumn(InternalOrderDto::getSent)
                .setHeader("????????????????????").setSortable(true).setAutoWidth(true);
        Grid.Column<InternalOrderDto> isPrintColumn = orderDtoGrid.addColumn(InternalOrderDto::getPrint)
                .setHeader("????????????????????").setSortable(true).setAutoWidth(true);
        Grid.Column<InternalOrderDto> commentColumn = orderDtoGrid.addColumn(InternalOrderDto::getComment)
                .setHeader("??????????????????????").setSortable(true).setAutoWidth(true);
        Grid.Column<InternalOrderDto> whenChangeColumn = orderDtoGrid.addColumn(InternalOrderDto::getWhenChanged)
                .setHeader("?????????? ??????????????").setSortable(true).setAutoWidth(true);
        Grid.Column<InternalOrderDto> whoChangeColumn = orderDtoGrid.addColumn(internalOrderDto ->
                        internalOrderDto.getWhoChanged() != null ? internalOrderDto.getWhoChanged().getFirstName() : null)
                .setHeader("?????? ??????????????").setSortable(true).setAutoWidth(true);

        orderDtoGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        settingButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        ColumnToggleContextMenu<InternalOrderDto> columnToggleContextMenu = new ColumnToggleContextMenu<>(settingButton);

        columnToggleContextMenu.addColumnToggleItem("???", numberColumn);
        columnToggleContextMenu.addColumnToggleItem("??????????", dateColumn);
        columnToggleContextMenu.addColumnToggleItem("??????????????????????", companyNameColumn);
        columnToggleContextMenu.addColumnToggleItem("??????????", sumColumn);
        columnToggleContextMenu.addColumnToggleItem("??????????????????", shippedColumn);
        columnToggleContextMenu.addColumnToggleItem("????????????", projectNameColumn);
        columnToggleContextMenu.addColumnToggleItem("?????????? ????????????", isSharedAccessColumn);
        columnToggleContextMenu.addColumnToggleItem("???????????????? ??????????", ownerDepartmentColumn);
        columnToggleContextMenu.addColumnToggleItem("???????????????? ??????????????????", ownerEmployeeColumn);
        columnToggleContextMenu.addColumnToggleItem("????????????????????", isSendColumn);
        columnToggleContextMenu.addColumnToggleItem("????????????????????", isPrintColumn);
        columnToggleContextMenu.addColumnToggleItem("??????????????????????", commentColumn);
        columnToggleContextMenu.addColumnToggleItem("?????????? ??????????????", whenChangeColumn);
        columnToggleContextMenu.addColumnToggleItem("?????? ??????????????", whoChangeColumn);

        orderDtoGrid.setSizeFull();
        orderDtoGrid.setHeightByRows(true);
        return orderDtoGrid;
    }

    public void setParent(InternalOrderView parent) {
        this.parent = parent;
    }

    public Grid<InternalOrderDto> getInternalOrderDtoGrid() {
        return internalOrderDtoGrid;
    }
}
