package com.warehouse_accounting.components.indiCators.grids;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.AppView;
import com.warehouse_accounting.models.dto.RecycleBinDto;
import com.warehouse_accounting.services.interfaces.RecycleBinService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;

@Component
@UIScope
@Route(value = "recycleBin", layout = AppView.class)
public class RecycleBinGridLayout extends VerticalLayout {

    private HorizontalLayout horizontalToolPanelLayout = new HorizontalLayout();
    private Grid<RecycleBinDto> grid = new Grid<>(RecycleBinDto.class);
    private RecycleBinService recycleBinService;

    public RecycleBinGridLayout(RecycleBinService recycleBinService) {
        this.recycleBinService = recycleBinService;
        horizontalToolPanelLayout.setAlignItems(Alignment.CENTER);
        grid.setItems(recycleBinService.getAll());
        add(horizontalToolPanelLayout);
        configToolPanel();
        add(grid());
    }

    private Grid<RecycleBinDto> grid(){
        grid.setColumns(getVisibleInvoiceColumn().keySet().toArray(String[]::new));
        grid.setSelectionMode(Grid.SelectionMode.MULTI);

        getVisibleInvoiceColumn().forEach((key, value)-> grid.getColumnByKey(key).setHeader(value));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES);
        return grid;
    }

    private HashMap<String, String> getVisibleInvoiceColumn(){
        HashMap<String, String> fieldNameColumnName = new LinkedHashMap<>();
        fieldNameColumnName.put("company.sortNumber","Тип документа");
        fieldNameColumnName.put("name","№");
        fieldNameColumnName.put("dateTime","Время");
        fieldNameColumnName.put("sum","Сумма");
        fieldNameColumnName.put("warehouse.name","Со склада");
        fieldNameColumnName.put("warehouse.sortNumber","На склад");
        fieldNameColumnName.put("company.name","Организация");
        fieldNameColumnName.put("contractor.name","Контрагент");
        fieldNameColumnName.put("status","Статус");
        fieldNameColumnName.put("project.name","Отправлено");
        fieldNameColumnName.put("project.description","Напечатоно");
        fieldNameColumnName.put("comment","Комментарий");

        return fieldNameColumnName;

    }

    // Здесь настройка панели инструментов
    private void configToolPanel() {

        Button helpButton = new Button(new Icon(VaadinIcon.QUESTION_CIRCLE));
        helpButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
        helpButton.addClickListener(e->{
            Notification.show("В разделе хранятся удаленные документы — их можно\n" +
                    "\n" +
                    "восстановить в течение 7 дней после удаления. По истечении этого\n" +
                    "\n" +
                    "срока документы окончательно стираются.\n" +
                    "\n" +
                    "Читать инструкцию: Корзина", 5000, Notification.Position.TOP_START);
        });

        Label textProducts = new Label();
        textProducts.setText("Корзина");

        Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH));
        refreshButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);

        Button addOrderButton = new Button("Очистить корзину", new Icon(VaadinIcon.PLUS));
        addOrderButton.addThemeVariants(ButtonVariant.LUMO_SMALL);

        Button addFilterButton = new Button("Фильтр");
        addFilterButton.addThemeVariants(ButtonVariant.LUMO_SMALL);

        TextField searchField = new TextField();
        searchField.setPlaceholder("Номер или комментарий");
        searchField.setMinWidth("170px");

        NumberField numberField = new NumberField();
        grid.addSelectionListener(event -> numberField.setValue((double) (grid.getSelectedItems().size())));
        numberField.setValue(0d);
        numberField.setWidth("40px");

        MenuBar menuBar = new MenuBar();

        MenuItem change = menuBar.addItem("Изменить");
        change.add(new Icon(VaadinIcon.CARET_DOWN));

        MenuItem print = menuBar.addItem(new Icon(VaadinIcon.PRINT));
        print.add("Печать");
        print.add(new Icon(VaadinIcon.CARET_DOWN));

        SubMenu changeSubMenu = change.getSubMenu();
        MenuItem delete = changeSubMenu.addItem("Удалить");
        delete.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });
        MenuItem recover = changeSubMenu.addItem("Востановить");
        recover.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });

        SubMenu printSubMenu = print.getSubMenu();
        MenuItem recycleBinList = printSubMenu.addItem("Список документов");
        recycleBinList.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });

        MenuItem configurePrint = printSubMenu.addItem("Настроить...");
        configurePrint.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });

        horizontalToolPanelLayout.add(helpButton,textProducts, refreshButton, addOrderButton, addFilterButton, searchField, numberField, menuBar);
    }
}