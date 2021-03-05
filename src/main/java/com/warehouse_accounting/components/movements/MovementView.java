package com.warehouse_accounting.components.movements;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.AppView;
import com.warehouse_accounting.models.dto.MovementDto;
import com.warehouse_accounting.services.interfaces.MovementService;


@Route(value = "movement", layout = AppView.class)
public class MovementView extends VerticalLayout {

    private HorizontalLayout horizontalLayout = new HorizontalLayout();
    private MovementService movementService;
    private Grid<MovementDto> grid;

    public MovementView(MovementService movementService) {
        this.movementService = movementService;
        Div div = new Div();
        grid = new Grid<>(MovementDto.class);
        grid.setItems(movementService.getAll());

        grid.setColumnOrder(grid.getColumnByKey("id").setHeader("№"),
                grid.getColumnByKey("dateTime").setHeader("Время"),
                grid.getColumnByKey("warehouseFrom").setHeader("Со склада"),
                grid.getColumnByKey("warehouseTo").setHeader("На склад"),
                grid.getColumnByKey("company").setHeader("Организация"),
                grid.getColumnByKey("sum").setHeader("Сумма"),
                grid.getColumnByKey("moved").setHeader("Отправлено"),
                grid.getColumnByKey("printed").setHeader("Напечатано"),
                grid.getColumnByKey("comment").setHeader("Комментарий"));
        configToolPanel();
        add(horizontalLayout);
        add(grid);
    }

    private void configToolPanel() {
        Button helpButton = new Button(new Icon(VaadinIcon.QUESTION_CIRCLE));
        helpButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);

        Label text = new Label();
        text.setText("Перемещения");

        Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH));
        refreshButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);

        Button addMovementButton = new Button("Перемещение", new Icon(VaadinIcon.PLUS));
        addMovementButton.addThemeVariants(ButtonVariant.LUMO_SMALL);

        horizontalLayout.add(helpButton, text, refreshButton, addMovementButton);

    }
}
