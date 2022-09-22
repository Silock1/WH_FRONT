//package com.warehouse_accounting.components.userSubMenu;
//
//import com.vaadin.flow.component.button.Button;
//import com.vaadin.flow.component.button.ButtonVariant;
//import com.vaadin.flow.component.combobox.ComboBox;
//import com.vaadin.flow.component.grid.Grid;
//import com.vaadin.flow.component.grid.HeaderRow;
//import com.vaadin.flow.component.icon.Icon;
//import com.vaadin.flow.component.icon.VaadinIcon;
//import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
//import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.data.provider.ListDataProvider;
//import com.vaadin.flow.spring.annotation.SpringComponent;
//import com.vaadin.flow.spring.annotation.UIScope;
//import com.warehouse_accounting.models.dto.ApplicationDto;
//import com.warehouse_accounting.services.interfaces.ApplicationService;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//@SpringComponent
//@UIScope
//public class ApplicationsFilterLayout extends VerticalLayout {
//    Button find = new Button("Найти");
//    Button clear = new Button("Очистить");
//    Button settingButton = new Button(new Icon(VaadinIcon.COG));
//    Button bookmarks = new Button(new Icon(VaadinIcon.BOOKMARK));
//    private ComboBox<String> monthFilter;
//    private ApplicationService applicationService;
//
//    private final Grid<ApplicationDto> grid = new Grid<>(ApplicationDto.class, false);
//
//    public ApplicationsFilterLayout() {
////        grid.setItems(applicationService.getAll());
////
////        HeaderRow headerRow = grid.appendHeaderRow();
//////
//////        List<String> list = new ArrayList<>();
//////        list.add("ВКонтакте новый");
//////        list.add("ВКонтакте старый");
////        ComboBox<String> filter = new ComboBox<>();
////        filter.addValueChangeListener(event -> this.onFilterChange());
//////        filter.setItemLabelGenerator(itemLabelGenerator);
////        filter.setItems("ВКонтакте новый", "ВКонтакте старый");
////        filter.setWidthFull();
////        filter.setClearButtonVisible(true);
////        headerRow.getCell(grid.getColumnByKey("description")).setComponent(filter);
////
////        monthFilter = filter;
////
////        find.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
////
////        HorizontalLayout horizontalLayout_one = new HorizontalLayout(find, clear, settingButton,
////                bookmarks, monthFilter);
////
////        setVisible(false);
//
//    }
//
////    private <T> ComboBox<T> gridComboBoxFilter(String columnKey, HeaderRow headerRow, List<T> items) {
////        ComboBox<T> filter = new ComboBox<>();
////        filter.addValueChangeListener(event -> this.onFilterChange());
//////        filter.setItemLabelGenerator(itemLabelGenerator);
////        filter.setItems(items);
////        filter.setWidthFull();
////        filter.setClearButtonVisible(true);
////        headerRow.getCell(grid.getColumnByKey(columnKey)).setComponent(filter);
////        return filter;
////    }
//
//    private void prepareFilterFields() {
//        HeaderRow headerRow = grid.appendHeaderRow();
////
////        List<String> list = new ArrayList<>();
////        list.add("ВКонтакте новый");
////        list.add("ВКонтакте старый");
//        ComboBox<String> filter = new ComboBox<>();
//        filter.addValueChangeListener(event -> this.onFilterChange());
////        filter.setItemLabelGenerator(itemLabelGenerator);
//        filter.setItems("ВКонтакте новый", "ВКонтакте старый");
//        filter.setWidthFull();
//        filter.setClearButtonVisible(true);
//        headerRow.getCell(grid.getColumnByKey("description")).setComponent(filter);
//
//        monthFilter = filter;
//
//    }
//
//
//    private void onFilterChange() {
//
//        ListDataProvider<ApplicationDto> listDataProvider = (ListDataProvider<ApplicationDto>) grid.getDataProvider();
//        // Since this will be the only active filter, it needs to account for all values of my filter fields
//        listDataProvider.setFilter(item -> {
//            boolean description = true;
//            if (!monthFilter.isEmpty()) {
//                description = item.getDescription().equals(monthFilter.getValue());
//            }
//            return description;
//        });
//    }
//}
