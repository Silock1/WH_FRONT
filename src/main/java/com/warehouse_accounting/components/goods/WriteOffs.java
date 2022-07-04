package com.warehouse_accounting.components.goods;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
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
import com.warehouse_accounting.components.util.ColumnToggleContextMenu;
import com.warehouse_accounting.models.dto.WriteOffsDto;
import com.warehouse_accounting.services.interfaces.WriteOffsService;
import org.springframework.stereotype.Component;

@Component
@UIScope
@Route(value = "writeOffs", layout = AppView.class)
public class WriteOffs extends VerticalLayout {
    private HorizontalLayout horizontalToolPanelLayout = new HorizontalLayout();
    private Grid<WriteOffsDto> grid = new Grid<>(WriteOffsDto.class);

    private Button settingButton = new Button(new Icon(VaadinIcon.COG));
    private WriteOffsService writeOffsService;

    public WriteOffs(WriteOffsService writeOffsService) {
        this.writeOffsService = writeOffsService;
        horizontalToolPanelLayout.setAlignItems(Alignment.CENTER);

        configToolPanel();
        initGrid();
    }

    private void initGrid() {
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.setItems(writeOffsService.getAll());

        Grid.Column<WriteOffsDto> id = grid.getColumnByKey("id").setHeader("№");
        Grid.Column<WriteOffsDto> dateTime = grid.getColumnByKey("dateTime").setHeader("Время");
        Grid.Column<WriteOffsDto> warehouseName = grid.getColumnByKey("warehouseName").setHeader("Со склада");
        Grid.Column<WriteOffsDto> org = grid.getColumnByKey("company").setHeader("Организация");
        Grid.Column<WriteOffsDto> sum = grid.getColumnByKey("sum").setHeader("Сумма");
        Grid.Column<WriteOffsDto> status = grid.getColumnByKey("status").setHeader("Статус");
        Grid.Column<WriteOffsDto> send = grid.getColumnByKey("moved").setHeader("Отправлено");
        Grid.Column<WriteOffsDto> print = grid.getColumnByKey("printed").setHeader("Напечатано");
        Grid.Column<WriteOffsDto> comment = grid.getColumnByKey("comment").setHeader("Комментарий");
        grid.setColumnOrder(id, dateTime, warehouseName, org, sum, status, send, print, comment);

        settingButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        ColumnToggleContextMenu<WriteOffsDto> columnToggleContextMenu = new ColumnToggleContextMenu<>(settingButton);

        columnToggleContextMenu.addColumnToggleItem("№", id);
        columnToggleContextMenu.addColumnToggleItem("Время", dateTime);
        columnToggleContextMenu.addColumnToggleItem("Со склада", warehouseName);
        columnToggleContextMenu.addColumnToggleItem("Организация", org);
        columnToggleContextMenu.addColumnToggleItem("Сумма", sum);
        columnToggleContextMenu.addColumnToggleItem("Статус", status);
        columnToggleContextMenu.addColumnToggleItem("Отправлено", send);
        columnToggleContextMenu.addColumnToggleItem("Напечатано", print);
        columnToggleContextMenu.addColumnToggleItem("Комментарий", comment);


        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.add(grid, settingButton);
        grid.setHeightByRows(true);
        headerLayout.setWidthFull();

        add(headerLayout);
    }

    private void configToolPanel() {
        Button helpButton = new Button(new Icon(VaadinIcon.QUESTION_CIRCLE));
        Dialog dialog = new Dialog();
        dialog.add(new Text("Списание проводится в результате инвентаризации. При списании со склада списываются недостающие или испорченные товары. Для каждого склада создается отдельный документ списания. Списание можно создать вручную или из документа инвентаризации."));
        helpButton.addClickListener(event -> dialog.open());

        dialog.setWidth("400px");
        dialog.setHeight("150px");

        Span text = new Span("Списания");
        text.setClassName("pageTitle");

        Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH), event -> {
        });

        Button addWriteOffsButton = new Button("Списания", new Icon(VaadinIcon.PLUS), event -> {
        });

        Button filterButton = new Button("Фильтр", event -> {
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
        });
        MenuItem copy = changeSubMenu.addItem("Копировать");
        copy.addClickListener(event -> {
        });
        MenuItem massEdit = changeSubMenu.addItem("Массовое редактирование");
        massEdit.addClickListener(event -> {
        });
        MenuItem operate = changeSubMenu.addItem("Провести");
        operate.addClickListener(event -> {
        });
        MenuItem undoOperate = changeSubMenu.addItem("Снять проведение");
        undoOperate.addClickListener(event -> {
        });

        SubMenu statusSubMenu = status.getSubMenu();
        MenuItem configureStatus = statusSubMenu.addItem("Настроить...");
        configureStatus.addClickListener(event -> {
        });

        SubMenu printSubMenu = print.getSubMenu();
        MenuItem priceListList = printSubMenu.addItem("Список прайс-листов");
        priceListList.addClickListener(event -> {
        });

        MenuItem priceTags2x3 = printSubMenu.addItem("Ценники 2x3");
        priceTags2x3.addClickListener(event -> {
        });
        MenuItem priceTags = printSubMenu.addItem("Ценники");
        priceTags.addClickListener(event -> {
        });
        MenuItem priceList = printSubMenu.addItem("Прайс-лист");
        priceList.addClickListener(event -> {
        });

        MenuItem kit = printSubMenu.addItem("Комплект...");
        kit.addClickListener(event -> {
        });
        MenuItem configurePrint = printSubMenu.addItem("Настроить...");
        configurePrint.addClickListener(event -> {
        });

        Button settingsButton = new Button(new Icon(VaadinIcon.COG));
        settingsButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        settingsButton.addClickListener(event -> {
        });

        horizontalToolPanelLayout.add(helpButton, text, refreshButton, addWriteOffsButton, filterButton, searchField, numberField, menuBar, settingsButton);
        add(horizontalToolPanelLayout);
    }
}
