package com.warehouse_accounting.components.retail;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.models.dto.OperationsWithPointsDto;

/**
 * Операции с баллами (Розница/Операции с баллами)
 **/

@Route("operationsWIthPoints")
public class OperationsWithPoints extends VerticalLayout {
    Grid<OperationsWithPointsDto> pointsGrid = new Grid<>(OperationsWithPointsDto.class, false);

    public OperationsWithPoints() {
//        configureTable();
        setSizeFull();
//        pointsGrid.setSizeFull();
//        pointsGrid.setColumns("id",
//                "number",
//                "typeOfOperation",
//                "bonusBalls",
//                "status",
//                "date",
//                "bonusProgram",
//                "contrAgent",
//                "commentary");
//
//
         add(tableContent());
//        add(pointsGrid);
//        add(new Span("TEST"));

    }



    public HorizontalLayout tableContent() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSizeFull();
//        pointsGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES);
        pointsGrid.setSizeFull();
        pointsGrid.setColumns("id",
                "number",
                "typeOfOperation",
                "bonusBalls",
                "status",
                "date",
                "bonusProgram",
                "contrAgent",
                "commentary");
//        Image image = new Image("icons/2.png", "keks");
//        Button button = new Button(image);
//        horizontalLayout.add(button);
        horizontalLayout.add(pointsGrid);
        return horizontalLayout;
    }

    public void configureTable() {








    }

}
