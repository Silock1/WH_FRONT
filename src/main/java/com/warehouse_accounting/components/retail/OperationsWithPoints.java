package com.warehouse_accounting.components.retail;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.util.SilverButton;
import com.warehouse_accounting.models.dto.OperationsWithPointsDto;

/**
 * Операции с баллами (Розница/Операции с баллами)
 **/

@Route("operationsWIthPoints")
public class OperationsWithPoints extends VerticalLayout {
    Grid<OperationsWithPointsDto> pointsGrid = new Grid<>(OperationsWithPointsDto.class, false);

    public OperationsWithPoints() {
        setSizeFull();
        add(    buttons(),
                tableContent()
        );


    }

    private HorizontalLayout buttons() {
        HorizontalLayout buttons = new HorizontalLayout();
        SilverButton silverButton = new SilverButton();
        Button button1 = silverButton.btnPLusBlue("Операция");
        Button button2 = silverButton.btnBlank("Фильтр");
        Button button3 = silverButton.btnPlusYellow("Изменить");
        Button button4 = silverButton.btnPLusBlue("Контрагенты");
        Button button5 = silverButton.btnBlank("Проверка");


        buttons.add(
                button1,
                button2,
                button3,
                button4,
                button5
        );
        return buttons;
    }


    public HorizontalLayout tableContent() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSizeFull();
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

        horizontalLayout.add(pointsGrid);
        return horizontalLayout;
    }


}


