package com.warehouse_accounting.components.adjustments.grids;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.AppView;
import com.warehouse_accounting.components.adjustments.AdjustmentsView;
import com.warehouse_accounting.models.dto.AdjustmentsDto;
import com.warehouse_accounting.services.interfaces.AdjustmentsService;
import org.springframework.stereotype.Component;

@Component
@UIScope
@Route(value = "adjustmentsGridsLayout", layout = AppView.class)
public class AdjustmentsGridsLayout extends VerticalLayout{

    private HorizontalLayout horizontalToolPanelLayout = new HorizontalLayout();
    private Grid<AdjustmentsDto> adjustmentsDtoGrid = new Grid<>(AdjustmentsDto.class);
    private AdjustmentsService adjustmentsService;
    private Div grid = new Div();
    private AdjustmentsView adjustmentsView;

    public AdjustmentsGridsLayout(AdjustmentsService adjustmentsService, AdjustmentsView adjustmentsView) {
        this.adjustmentsView = adjustmentsView;
        this.adjustmentsService = adjustmentsService;
        grid.setSizeFull();
        add(adjustmentsDtoGrid());
    }

    private HorizontalLayout adjustmentsDtoGrid() {

        HorizontalLayout groupControl1 = new HorizontalLayout();
        adjustmentsDtoGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        adjustmentsDtoGrid.setItems(adjustmentsService.getAll());
        adjustmentsDtoGrid.setColumnOrder(adjustmentsDtoGrid.getColumnByKey("number").setHeader("№"),
                adjustmentsDtoGrid.getColumnByKey("dateTimeAdjustment").setHeader("Время"),
                adjustmentsDtoGrid.getColumnByKey("company").setHeader("Организация"),
                adjustmentsDtoGrid.getColumnByKey("contractor").setHeader("Контрагент"),
                adjustmentsDtoGrid.getColumnByKey("currentBalance").setHeader("Счет"),
                adjustmentsDtoGrid.getColumnByKey("totalBalance").setHeader("Касса"),
                adjustmentsDtoGrid.getColumnByKey("adjustmentAmount").setHeader("Сумма корректировки"),
                adjustmentsDtoGrid.getColumnByKey("comment").setHeader("Комментарий"),
                adjustmentsDtoGrid.getColumnByKey("whenChanged").setHeader("Когда изменен"),
                adjustmentsDtoGrid.getColumnByKey("type").setHeader("Кто изменил"));
        add(adjustmentsDtoGrid);
        return groupControl1;
    }
}
