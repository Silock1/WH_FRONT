package com.warehouse_accounting.components.user;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.user.settings.SettingsView;
import com.warehouse_accounting.models.dto.CountryDto;
import com.warehouse_accounting.models.dto.UnitsOfMeasureDto;
import com.warehouse_accounting.services.interfaces.CountryService;

import java.util.List;

@PageTitle("Настройки")
@Route(value = "country", layout = SettingsView.class)
public class SettingCountryView extends VerticalLayout {

    private final CountryService countryService;

    public SettingCountryView(CountryService countryService) {
        this.countryService = countryService;
        H2 tableName = new H2("Страны");
        Button addUnits = new Button("Страна");
        addUnits.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addUnits.addClickListener(e-> UI.getCurrent().navigate(SettingCountryAddView.class));
        HorizontalLayout header = new HorizontalLayout(tableName);
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.getThemeList().clear();

        Button delete = new Button("Удалить");
        delete.setEnabled(false);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        delete.getStyle().set("margin-inline-start", "auto");

        Grid<CountryDto> grid = new Grid<>(CountryDto.class, false);
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.addColumn(CountryDto::getShortName).setHeader("Краткое наименование");
        grid.addColumn(CountryDto::getLongName).setHeader("Полное наименование");
        grid.addColumn(CountryDto::getCode).setHeader("Цифровой код");
        grid.addColumn(CountryDto::getCodeOne).setHeader("Буквенный код(2)");
        grid.addColumn(CountryDto::getCodeTwo).setHeader("Буквенный код(3)");

        grid.addSelectionListener(selection -> {
            int size = selection.getAllSelectedItems().size();
            boolean isSingleSelection = size == 1;

            delete.setEnabled(size != 0);
        });

        HorizontalLayout footer = new HorizontalLayout(addUnits, delete);
        footer.getStyle().set("flex-wrap", "wrap");

        List<CountryDto> country = countryService.getAll();
        grid.setItems(country);
        setPadding(false);
        setAlignItems(Alignment.STRETCH);
        add(header, grid, footer);
    }
}
