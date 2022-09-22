package com.warehouse_accounting.components.userSubMenu;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.contragents.ContragentsList;
import com.warehouse_accounting.components.util.ColumnToggleContextMenu;
import com.warehouse_accounting.models.dto.ApplicationDto;
import com.warehouse_accounting.models.dto.ContractorDto;
import com.warehouse_accounting.services.interfaces.ApplicationService;

@SpringComponent
@UIScope
public class ApplicationsListGridLayout extends HorizontalLayout {

    private final ApplicationService applicationService;
    private Button settingButton = new Button(new Icon(VaadinIcon.COG));
    private Grid<ApplicationDto> applicationDtoGrid;
    private ApplicationsList parent;
    private ComboBox<String> monthFilter;

    public ApplicationsListGridLayout(ApplicationService applicationService) {
        this.applicationService = applicationService;
        applicationDtoGrid = initGrid();
        applicationDtoGrid.setItems(applicationService.getAll());
        add(applicationDtoGrid, settingButton);
        setSizeFull();
    }

    public void refreshData() {
        removeAll();
        applicationDtoGrid.setItems(applicationService.getAll());
        add(applicationDtoGrid, settingButton);
        setSizeFull();
    }

    private Grid<ApplicationDto> initGrid() {
        Grid<ApplicationDto> applicationDtoGrid1 = new Grid<>(ApplicationDto.class, false);

        applicationDtoGrid1.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        applicationDtoGrid1.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        applicationDtoGrid1.addThemeVariants(GridVariant.LUMO_COMPACT);
        applicationDtoGrid1.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
        applicationDtoGrid1.setHeightByRows(true);
        applicationDtoGrid1.setSelectionMode(Grid.SelectionMode.MULTI);

        Grid.Column<ApplicationDto> name = applicationDtoGrid1.addColumn(ApplicationDto::getName)
                .setHeader("Магазин").setSortable(true).setAutoWidth(true);
        Grid.Column<ApplicationDto> description = applicationDtoGrid1.addColumn(ApplicationDto::getDescription)
                .setHeader("Тип").setSortable(true).setAutoWidth(true).setKey("description");
//        Grid.Column<ApplicationDto> devMail = applicationDtoGrid1.addColumn(ApplicationDto::getDevMail)
//                .setHeader("Заказы").setSortable(true).setAutoWidth(true);
//        Grid.Column<ApplicationDto> developer = applicationDtoGrid1.addColumn(ApplicationDto::getDeveloper)
//                .setHeader("Остатки").setSortable(true).setAutoWidth(true);

        applicationDtoGrid1.addItemDoubleClickListener(event -> parent.editFormActivate(event.getItem()));

        settingButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        ColumnToggleContextMenu<ApplicationDto> columnToggleContextMenu = new ColumnToggleContextMenu<>(settingButton);

        columnToggleContextMenu.addColumnToggleItem("Магазин", name);
        columnToggleContextMenu.addColumnToggleItem("Тип", description);
//        columnToggleContextMenu.addColumnToggleItem("Заказы", devMail);
//        columnToggleContextMenu.addColumnToggleItem("Остатки", developer);

        HeaderRow headerRow = applicationDtoGrid1.appendHeaderRow();

        ComboBox<String> filter = new ComboBox<>();
        filter.addValueChangeListener(event -> this.onFilterChange());
        filter.setItems("ВКонтакте новый", "ВКонтакте старый");
        filter.setWidth("250px");
//        filter.setWidthFull();
        filter.setClearButtonVisible(true);
        headerRow.getCell(applicationDtoGrid1.getColumnByKey("description")).setComponent(filter);

        monthFilter = filter; //Временный фильтр, так как не знаю как сделать фильтр за пределами таблицы

        return applicationDtoGrid1;
    }

    public void setParent(ApplicationsList parent) {
        this.parent = parent;
    }

    private void onFilterChange() {

        ListDataProvider<ApplicationDto> listDataProvider =
                (ListDataProvider<ApplicationDto>) applicationDtoGrid.getDataProvider();
        // Since this will be the only active filter, it needs to account for all values of my filter fields
        listDataProvider.setFilter(item -> {
            boolean description = true;
            if (!monthFilter.isEmpty()) {
                description = item.getDescription().equals(monthFilter.getValue());
            }
            return description;
        });
    }
}
