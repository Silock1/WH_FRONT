package com.warehouse_accounting.components.goods;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.AppView;
import com.warehouse_accounting.models.dto.InventoryDto;
import com.warehouse_accounting.services.interfaces.InventoryService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@UIScope
@Route(value = "inventory", layout = AppView.class)
public class Inventory extends VerticalLayout {

    private HorizontalLayout horizontalToolPanelLayout = new HorizontalLayout();
    private Grid<InventoryDto> grid = new Grid<>(InventoryDto.class);
    private InventoryService inventoryService;

    public Inventory(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
        horizontalToolPanelLayout.setAlignItems(Alignment.CENTER);

        // Здесь настройка грида
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.setItems(inventoryService.getAll());
        grid.setColumnOrder(grid.getColumnByKey("id").setHeader("№"),
                grid.getColumnByKey("dateTime").setHeader("Время"),
                grid.getColumnByKey("warehouseFrom").setHeader("Со склада"),
                grid.getColumnByKey("company").setHeader("Организация"),
                grid.getColumnByKey("moved").setHeader("Отправлено"),
                grid.getColumnByKey("printed").setHeader("Напечатано"),
                grid.getColumnByKey("comment").setHeader("Комментарий"));
        configToolPanel();

        add(horizontalToolPanelLayout);
        add(grid);
    }

    // Здесь настройка панели инструментов
    private void configToolPanel() {
        Button helpButton = new Button(new Icon(VaadinIcon.QUESTION_CIRCLE));
        Dialog dialog = new Dialog();
        dialog.add(new Text("Инвентаризация позволяет выявить несоответствие фактических и учетных остатков. " +
                "Проводится по каждому складу отдельно. " +
                "Для изменения расчетных остатков вам необходимо создать документ оприходования или " +
                "списания."));
        helpButton.addClickListener(event -> dialog.open());

        dialog.setWidth("400px");
        dialog.setHeight("150px");

        Span text = new Span("Инвентаризации");
        text.setClassName("pageTitle");

        Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH), event -> {
            //TODO повод поработать этот функционал
        });

        Button addInventoryButton = new Button("Инвентаризация", new Icon(VaadinIcon.PLUS), event -> {
            //TODO повод поработать этот функционал
        });

        Button filterButton = new Button("Фильтр", event -> {
            //TODO повод поработать этот функционал
        });

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
        MenuItem status = menuBar.addItem("Статус");
        status.add(new Icon(VaadinIcon.CARET_DOWN));
        MenuItem print = menuBar.addItem(new Icon(VaadinIcon.PRINT));
        print.add("Печать");
        print.add(new Icon(VaadinIcon.CARET_DOWN));

        SubMenu changeSubMenu = change.getSubMenu();
        MenuItem delete = changeSubMenu.addItem("Удалить");
        delete.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });

        MenuItem massEdit = changeSubMenu.addItem("Массовое редактирование");
        massEdit.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });



        SubMenu statusSubMenu = status.getSubMenu();
        MenuItem configureStatus = statusSubMenu.addItem("Настроить...");
        configureStatus.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });

        SubMenu printSubMenu = print.getSubMenu();
        MenuItem inventoryList = printSubMenu.addItem("Список инвентаризаций");
        inventoryList.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });
        MenuItem inv3 = printSubMenu.addItem("ИНВ-3 (инвентаризационная опись)");
        inv3.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });
        MenuItem inv = printSubMenu.addItem("Инвентаризация");
        inv.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });
        MenuItem kit = printSubMenu.addItem("Комплект...");
        kit.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });
        MenuItem configurePrint = printSubMenu.addItem("Настроить...");
        configurePrint.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });

        Button settingsButton = new Button(new Icon(VaadinIcon.COG));
        settingsButton.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });



        horizontalToolPanelLayout.add(helpButton, text, refreshButton, addInventoryButton, filterButton, searchField, numberField, menuBar, settingsButton);
    }
}

