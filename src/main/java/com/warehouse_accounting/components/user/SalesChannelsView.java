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
import com.warehouse_accounting.models.dto.SalesChannelDto;
import com.warehouse_accounting.services.interfaces.SalesChannelsService;
import java.util.List;

@PageTitle("Настройки")
@Route(value = "saleschannel", layout = SettingsView.class)
public class SalesChannelsView extends VerticalLayout {

    private final SalesChannelsService salesChannelsService;

    public SalesChannelsView(SalesChannelsService salesChannelsService) {
        this.salesChannelsService = salesChannelsService;
        H2 tableName = new H2("Каналы поставок");
        Button addUnits = new Button("Добавить канал");
        addUnits.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addUnits.addClickListener(e-> UI.getCurrent().navigate(SalesChannelsAddView.class));
        HorizontalLayout header = new HorizontalLayout(tableName);
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.getThemeList().clear();

        Button delete = new Button("Удалить");
        delete.setEnabled(false);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        delete.getStyle().set("margin-inline-start", "auto");

        Grid<SalesChannelDto> grid = new Grid<>(SalesChannelDto.class, false);
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.addColumn(SalesChannelDto::getName).setHeader("Наименование");
        grid.addColumn(SalesChannelDto::getType).setHeader("Тип");
        grid.addColumn(SalesChannelDto::getDescription).setHeader("Описание");
        grid.addColumn(SalesChannelDto::getGeneralAccessC).setHeader("Общий доступ");
        grid.addColumn(SalesChannelDto::getOwnerDepartment).setHeader("Владелец-отдел");
        grid.addColumn(SalesChannelDto::getOwnerEmployee).setHeader("Владелец-сотрудник");
        grid.addColumn(SalesChannelDto::getWhenChanged).setHeader("Когда изменён");
        grid.addColumn(SalesChannelDto::getWhoChanged).setHeader("Кто изменил");
//        grid.addSelectionListener(selection -> {
//            int size = selection.getAllSelectedItems().size();
//            boolean isSingleSelection = size == 1;
//
//            delete.setEnabled(size != 0);
//        });

        HorizontalLayout footer = new HorizontalLayout(addUnits, delete);
        footer.getStyle().set("flex-wrap", "wrap");

        List<SalesChannelDto> people = salesChannelsService.getAll();
        grid.setItems(people);
        setPadding(false);
        setAlignItems(Alignment.STRETCH);
        add(header, grid, footer);
    }



}
