package com.warehouse_accounting.components.retail;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.util.SilverButton;
import com.warehouse_accounting.models.dto.OperationsWithPointsDto;

/**
 * Операции с баллами (Розница/Операции с баллами)
 **/

@Route("operationsWIthPoints")
@CssImport(value = "./css/application.css")
public class OperationsWithPoints extends VerticalLayout {
    Grid<OperationsWithPointsDto> pointsGrid = new Grid<>(OperationsWithPointsDto.class, false);

    public OperationsWithPoints() {
        setSizeFull();
        add(toolBar(),
                tableContent()
        );


    }

    private HorizontalLayout toolBar() {
        HorizontalLayout toolbarLayout = new HorizontalLayout();
//        toolbarLayout.setClassName("borderCheck");
        SilverButton silverButton = new SilverButton();


        toolbarLayout.setWidthFull();
        toolbarLayout.setAlignItems(Alignment.CENTER);

        Button button1 = silverButton.btnPLusBlue("Операция");
        Button button2 = silverButton.btnBlank("Фильтр");
        Button button3 = silverButton.btnPlusYellow("Изменить");
        Button button4 = silverButton.btnPLusBlue("Контрагенты");
        Button button5 = silverButton.btnBlank("Проверка");
        MenuBar origina = silverButton.defaultBar();

        Span text = new Span("Операции с баллами");
        text.setClassName("title");

        Button helpButton = silverButton.helpButton();
        Button refreshButton = silverButton.refreshButton();


        MenuBar operation = silverButton.menuBarButton();




        toolbarLayout.add(

                helpButton,
                text,
                refreshButton,
                button1,
                button2,

                button4,

                operation,
                button3,
                button5,
                origina
        );
        return toolbarLayout;
    }


    public HorizontalLayout tableContent() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSizeFull();
        pointsGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
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


