package com.warehouse_accounting.components.payments.adjustments.grids;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.AppView;
import com.warehouse_accounting.components.payments.adjustments.AdjustmentsView;
import com.warehouse_accounting.components.util.ColumnToggleContextMenu;
import com.warehouse_accounting.models.dto.AdjustmentsDto;
import com.warehouse_accounting.services.interfaces.AdjustmentsService;
import org.springframework.stereotype.Component;

@Component
@UIScope
@Route(value = "adjustmentsGridsLayout", layout = AppView.class)
public class AdjustmentsGridsLayout extends VerticalLayout {

    private HorizontalLayout horizontalToolPanelLayout = new HorizontalLayout();
    private Grid<AdjustmentsDto> adjustmentsDtoGrid = new Grid<>(AdjustmentsDto.class);
    private AdjustmentsService adjustmentsService;
    private Div grid = new Div();

    private Button settingButton = new Button(new Icon(VaadinIcon.COG));
    private AdjustmentsView adjustmentsView;

    public AdjustmentsGridsLayout(AdjustmentsService adjustmentsService, AdjustmentsView adjustmentsView) {
        this.adjustmentsView = adjustmentsView;
        this.adjustmentsService = adjustmentsService;
        grid.setSizeFull();
        adjustmentsDtoGrid();
    }

    private HorizontalLayout adjustmentsDtoGrid() {

        HorizontalLayout groupControl1 = new HorizontalLayout();
        adjustmentsDtoGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        adjustmentsDtoGrid.setItems(adjustmentsService.getAll());

        Grid.Column<AdjustmentsDto> number = adjustmentsDtoGrid.getColumnByKey("number").setHeader("???");
        Grid.Column<AdjustmentsDto> date = adjustmentsDtoGrid.getColumnByKey("dateTimeAdjustment").setHeader("??????????");
        Grid.Column<AdjustmentsDto> org = adjustmentsDtoGrid.getColumnByKey("company").setHeader("??????????????????????");
        Grid.Column<AdjustmentsDto> contrAgent = adjustmentsDtoGrid.getColumnByKey("contractor").setHeader("????????????????????");
        Grid.Column<AdjustmentsDto> invoice = adjustmentsDtoGrid.getColumnByKey("currentBalance").setHeader("????????");
        Grid.Column<AdjustmentsDto> totalBalance = adjustmentsDtoGrid.getColumnByKey("totalBalance").setHeader("??????????");
        Grid.Column<AdjustmentsDto> adjustmentAmount = adjustmentsDtoGrid.getColumnByKey("adjustmentAmount").setHeader("?????????? ??????????????????????????");
        Grid.Column<AdjustmentsDto> comment = adjustmentsDtoGrid.getColumnByKey("comment").setHeader("??????????????????????");
        Grid.Column<AdjustmentsDto> whenChanged = adjustmentsDtoGrid.getColumnByKey("whenChanged").setHeader("?????????? ??????????????");
        Grid.Column<AdjustmentsDto> type = adjustmentsDtoGrid.getColumnByKey("type").setHeader("?????? ??????????????");
        adjustmentsDtoGrid.setColumnOrder(number, date, org, contrAgent, invoice, totalBalance, adjustmentAmount, comment, whenChanged, type);

        settingButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        ColumnToggleContextMenu<AdjustmentsDto> columnToggleContextMenu = new ColumnToggleContextMenu<>(settingButton);

        columnToggleContextMenu.addColumnToggleItem("???", number);
        columnToggleContextMenu.addColumnToggleItem("??????????", date);
        columnToggleContextMenu.addColumnToggleItem("??????????????????????", org);
        columnToggleContextMenu.addColumnToggleItem("????????????????????", contrAgent);
        columnToggleContextMenu.addColumnToggleItem("????????", invoice);
        columnToggleContextMenu.addColumnToggleItem("??????????", totalBalance);
        columnToggleContextMenu.addColumnToggleItem("?????????? ??????????????????????????", adjustmentAmount);
        columnToggleContextMenu.addColumnToggleItem("??????????????????????", comment);
        columnToggleContextMenu.addColumnToggleItem("?????????? ??????????????", whenChanged);
        columnToggleContextMenu.addColumnToggleItem("?????? ??????????????", type);

        groupControl1.add(adjustmentsDtoGrid, settingButton);
        adjustmentsDtoGrid.setHeightByRows(true);
        groupControl1.setWidthFull();

        add(groupControl1);
        return groupControl1;
    }
}
