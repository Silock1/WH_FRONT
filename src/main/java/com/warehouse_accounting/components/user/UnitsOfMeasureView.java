package com.warehouse_accounting.components.user;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.user.settings.SettingsView;
import com.warehouse_accounting.components.util.ColumnToggleContextMenu;
import com.warehouse_accounting.models.dto.UnitsOfMeasureDto;
import com.warehouse_accounting.services.interfaces.UnitsOfMeasureService;

import java.util.List;

@PageTitle("Настройки")
@Route(value = "uom", layout = SettingsView.class)
public class UnitsOfMeasureView extends VerticalLayout {

    private final UnitsOfMeasureService uomService;

    public UnitsOfMeasureView(UnitsOfMeasureService uomService) {
        this.uomService = uomService;
        H2 tableName = new H2("Единицы измерения");
        Button addUnits = new Button("Добавить единицу");
        addUnits.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addUnits.addClickListener(e -> UI.getCurrent().navigate(UnitsOfMeasureAddView.class));
        HorizontalLayout header = new HorizontalLayout(tableName);
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.getThemeList().clear();

        Button delete = new Button("Удалить");
        delete.setEnabled(false);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        delete.getStyle().set("margin-inline-start", "auto");

        Grid<UnitsOfMeasureDto> grid = new Grid<>(UnitsOfMeasureDto.class, false);
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        Grid.Column<UnitsOfMeasureDto> type = grid.addColumn(UnitsOfMeasureDto::getType).setHeader("Тип");
        Grid.Column<UnitsOfMeasureDto> shortName = grid.addColumn(UnitsOfMeasureDto::getName).setHeader("Краткое наименование");
        Grid.Column<UnitsOfMeasureDto> fullName = grid.addColumn(UnitsOfMeasureDto::getFullName).setHeader("Полное наименование");
        Grid.Column<UnitsOfMeasureDto> digitCode = grid.addColumn(UnitsOfMeasureDto::getCode).setHeader("Цифровой код");

        Button menuButton = new Button(new Icon(VaadinIcon.COG));
        menuButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        ColumnToggleContextMenu<UnitsOfMeasureDto> columnToggleContextMenu = new ColumnToggleContextMenu<>(menuButton);

        columnToggleContextMenu.addColumnToggleItem("Тип", type);
        columnToggleContextMenu.addColumnToggleItem("Краткое наименование", shortName);
        columnToggleContextMenu.addColumnToggleItem("Полное наименование", fullName);
        columnToggleContextMenu.addColumnToggleItem("Цифровой код", digitCode);

        grid.addSelectionListener(selection -> {
            int size = selection.getAllSelectedItems().size();
            boolean isSingleSelection = size == 1;

            delete.setEnabled(size != 0);
        });

        delete.addClickListener(buttonClickEvent -> {
            grid.getSelectedItems().forEach(selected -> {
                uomService.deleteById(selected.getId());
            });
            grid.setItems(uomService.getAll());
        });

        HorizontalLayout footer = new HorizontalLayout(addUnits, delete);
        footer.getStyle().set("flex-wrap", "wrap");

        List<UnitsOfMeasureDto> people = uomService.getAll();
        grid.setItems(people);
        setPadding(false);
        setAlignItems(Alignment.STRETCH);

        HorizontalLayout gridAndCog = new HorizontalLayout();
        gridAndCog.add(grid, menuButton);

        add(header, footer, gridAndCog);
    }
}
