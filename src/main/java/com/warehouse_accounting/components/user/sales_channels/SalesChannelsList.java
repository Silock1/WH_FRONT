package com.warehouse_accounting.components.user.sales_channels;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.user.sales_channels.forms.SalesChannelsAddView;
import com.warehouse_accounting.components.user.settings.SettingsView;
import com.warehouse_accounting.components.util.ColumnToggleContextMenu;
import com.warehouse_accounting.models.dto.SalesChannelDto;
import com.warehouse_accounting.services.interfaces.SalesChannelsService;

import java.util.List;

@PageTitle("Настройки")
@Route(value = "saleschannel", layout = SettingsView.class)
public class SalesChannelsList extends VerticalLayout {

    private Grid<SalesChannelDto> grid;

    public SalesChannelsList(SalesChannelsService salesChannelsService) {

        H2 tableName = new H2("Каналы продаж");

        HorizontalLayout header = new HorizontalLayout();
        header.setAlignItems(FlexComponent.Alignment.CENTER);

        Button delete = new Button("Удалить");
        delete.setEnabled(false);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        delete.getStyle().set("margin-inline-start", "auto");

        grid = new Grid<>(SalesChannelDto.class, false);
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        Grid.Column<SalesChannelDto> name = grid.addColumn(SalesChannelDto::getName).setHeader("Наименование");
        Grid.Column<SalesChannelDto> type = grid.addColumn(SalesChannelDto::getType).setHeader("Тип");
        Grid.Column<SalesChannelDto> description = grid.addColumn(SalesChannelDto::getDescription).setHeader("Описание");
        grid.addSelectionListener(selection -> delete.setEnabled(selection.getAllSelectedItems().size() != 0));

        delete.addClickListener(buttonClickEvent -> {
            grid.getSelectedItems().forEach(selected -> salesChannelsService.deleteById(selected.getId()));
            grid.setItems(salesChannelsService.getAll());
        });

        List<SalesChannelDto> channels = salesChannelsService.getAll();
        grid.setItems(channels);

        Label textChannels = new Label();
        textChannels.setText("Каналы продаж");

        Button addChannels = new Button("Добавить канал", new Icon(VaadinIcon.PLUS));
        addChannels.addThemeVariants(ButtonVariant.LUMO_SMALL);
        addChannels.addClickListener(e -> UI.getCurrent().navigate(SalesChannelsAddView.class));

        Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH));
        refreshButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
        refreshButton.addClickListener(buttonClickEvent -> grid.setItems(salesChannelsService.getAll()));

        setPadding(false);
        setAlignItems(Alignment.STRETCH);

        Button menuButton = new Button(new Icon(VaadinIcon.COG));
        menuButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        ColumnToggleContextMenu<SalesChannelDto> columnToggleContextMenu = new ColumnToggleContextMenu<>(menuButton);

        columnToggleContextMenu.addColumnToggleItem("Наименование", name);
        columnToggleContextMenu.addColumnToggleItem("Тип", type);
        columnToggleContextMenu.addColumnToggleItem("Описание", description);

        header.add(tableName, refreshButton, addChannels, delete);

        HorizontalLayout gridAndCog = new HorizontalLayout();
        gridAndCog.add(grid, menuButton);

        add(header, gridAndCog);
    }
}
