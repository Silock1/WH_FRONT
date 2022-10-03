package com.warehouse_accounting.components.retail;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.menubar.MenuBar;
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

        MenuBar menuOperation = silverButton.menuOperationButton("Операция");
        Button filter = silverButton.btnBlank("Фильтр");

        Span text = new Span("Операции с баллами");
        text.setClassName("title");

        Button helpButton = silverButton.helpButton();
        Button refreshButton = silverButton.refreshButton();


        Input searchField = new Input();

        searchField.setPlaceholder("Номер или комментарий");
        searchField.setWidth("200px");
        Input miniField = new Input();
        miniField.setWidth("25px");
        miniField.setValue("0");


        MenuBar edit = silverButton.menuEditButton("Изменить");


        toolbarLayout.add(

                helpButton,
                text,
                refreshButton,
                menuOperation,
                filter,
                searchField,
                miniField,
                edit

        );
        return toolbarLayout;
    }


    public HorizontalLayout tableContent() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSizeFull();
        pointsGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        pointsGrid.setSizeFull();
        pointsGrid.addColumn(OperationsWithPointsDto::getId).setHeader("№");
        pointsGrid.addColumn(OperationsWithPointsDto::getDateCreate).setHeader("Дата создания");
        pointsGrid.addColumn(OperationsWithPointsDto::getTypeOfOperation).setHeader("Тип операции");
        pointsGrid.addColumn(OperationsWithPointsDto::getBonusBalls).setHeader("Бонусные баллы");
        pointsGrid.addColumn(OperationsWithPointsDto::getStatus).setHeader("Статус");
        pointsGrid.addColumn(OperationsWithPointsDto::getDateProfit).setHeader("Дата начисления");
        pointsGrid.addColumn(OperationsWithPointsDto::getBonusProgram).setHeader("Бонусная программа");
        pointsGrid.addColumn(OperationsWithPointsDto::getContrAgent).setHeader("Контрагент");
        pointsGrid.addColumn(OperationsWithPointsDto::getCommentary).setHeader("Комментарий");


//        pointsGrid.setColumns("id",
//                "typeOfOperation",
//                "bonusBalls",
//                "status",
//                "date",
//                "bonusProgram",
//                "contrAgent",
//                "commentary");

        horizontalLayout.add(pointsGrid);
        return horizontalLayout;
    }


}


