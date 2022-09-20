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
import com.warehouse_accounting.models.dto.RemainsDto;
import com.warehouse_accounting.models.dto.WriteOffsDto;
import com.warehouse_accounting.services.interfaces.RemainsService;
import com.warehouse_accounting.services.interfaces.WriteOffsService;
import org.springframework.stereotype.Component;

@Component
@UIScope
@Route(value = "remains", layout = AppView.class)
public class Remains extends VerticalLayout {
    private HorizontalLayout horizontalToolPanelLayout = new HorizontalLayout();
    private Grid<RemainsDto> grid = new Grid<>(RemainsDto.class);

    private Button settingButton = new Button(new Icon(VaadinIcon.COG));
    private RemainsService remainsService;

    public Remains(RemainsService remainsService) {
        this.remainsService = remainsService;
        horizontalToolPanelLayout.setAlignItems(Alignment.CENTER);

        configToolPanel();
        initGrid();
    }

    private void initGrid() {
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.setItems(remainsService.getAll());

        Grid.Column<RemainsDto> id = grid.getColumnByKey("id").setHeader("№");
        Grid.Column<RemainsDto> name = grid.getColumnByKey("name").setHeader("Наименование");
        Grid.Column<RemainsDto> sum = grid.getColumnByKey("sum").setHeader("Код");
        Grid.Column<RemainsDto> article = grid.getColumnByKey("article").setHeader("Артикул");
        Grid.Column<RemainsDto> remainder = grid.getColumnByKey("remainder").setHeader("Остаток");
        Grid.Column<RemainsDto> nonRemainder = grid.getColumnByKey("nonRemainder").setHeader("Неснижаемый остаток");
        Grid.Column<RemainsDto> reserve = grid.getColumnByKey("reserve").setHeader("Резерв");
        Grid.Column<RemainsDto> wait = grid.getColumnByKey("wait").setHeader("Ожидание");
        Grid.Column<RemainsDto> aviable = grid.getColumnByKey("available").setHeader("Доступно");
        Grid.Column<RemainsDto> unit = grid.getColumnByKey("unit").setHeader("Ед.изм.");
        Grid.Column<RemainsDto> days = grid.getColumnByKey("days").setHeader("Дней на складе");
        Grid.Column<RemainsDto> price = grid.getColumnByKey("price").setHeader("Сумма себестоимости");
        Grid.Column<RemainsDto> salesPrice = grid.getColumnByKey("salesPrice").setHeader("Цена продажи");
        Grid.Column<RemainsDto> buyPrice = grid.getColumnByKey("buyPrice").setHeader("Сумма продажи");
        grid.setColumnOrder(id, name, sum, article, remainder, nonRemainder, reserve, wait, aviable,  unit, days,
                price, salesPrice, buyPrice);


        settingButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        ColumnToggleContextMenu<RemainsDto> columnToggleContextMenu = new ColumnToggleContextMenu<>(settingButton);

        columnToggleContextMenu.addColumnToggleItem("№", id);
        columnToggleContextMenu.addColumnToggleItem("Наименование", name);
        columnToggleContextMenu.addColumnToggleItem("Код", sum);
        columnToggleContextMenu.addColumnToggleItem("Артикул", article);
        columnToggleContextMenu.addColumnToggleItem("Остаток", remainder);
        columnToggleContextMenu.addColumnToggleItem("Неснижаемый остаток", nonRemainder);
        columnToggleContextMenu.addColumnToggleItem("Резерв", reserve);
        columnToggleContextMenu.addColumnToggleItem("Ожидание", wait);
        columnToggleContextMenu.addColumnToggleItem("Доступно", aviable);
        columnToggleContextMenu.addColumnToggleItem("Ед.изм", unit);
        columnToggleContextMenu.addColumnToggleItem("Сумма себестоимости", price);
        columnToggleContextMenu.addColumnToggleItem("Цена продажи", salesPrice);
        columnToggleContextMenu.addColumnToggleItem("Сумма продажи", buyPrice);


        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.add(grid, settingButton);
        grid.setHeightByRows(true);
        headerLayout.setWidthFull();

        add(headerLayout);
    }

    private void configToolPanel() {
        Button helpButton = new Button(new Icon(VaadinIcon.QUESTION_CIRCLE));
        Dialog dialog = new Dialog();
        dialog.add(new Text("В разделе представлены остатки товаров на складах. Для каждого товара показан его фактический остаток, зарезервированный объем и доступное для продажи количество.\n" +
                "\n"));
        helpButton.addClickListener(event -> dialog.open());

        dialog.setWidth("400px");
        dialog.setHeight("150px");

        Span text = new Span("Остатки");
        text.setClassName("pageTitle");

        Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH), event -> {
        });

        Button addWriteOffsButton = new Button("По товарам", event -> {
        });

        Button filterButton = new Button("По складам", event -> {
        });
        Button numberField = new Button("Фильтр", event -> {
        });

        MenuBar menuBar = new MenuBar();

        MenuItem change = menuBar.addItem("Печать");
        change.add(new Icon(VaadinIcon.PRINT));
        change.add(new Icon(VaadinIcon.CARET_DOWN));
        MenuItem reserves = menuBar.addItem("Пополнить резервы");

        MenuItem status = menuBar.addItem("Тележка");
        status.add(new Icon(VaadinIcon.CART));
        status.add(new Icon(VaadinIcon.CARET_DOWN));

        SubMenu changeSubMenu = change.getSubMenu();
        MenuItem delete = changeSubMenu.addItem("Остатки по товарам");
        delete.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });
        MenuItem copy = changeSubMenu.addItem("Остатки по сериям");
        copy.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });
        MenuItem massEdit = changeSubMenu.addItem("Ценник (70х49,5мм");
        massEdit.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });
        MenuItem operate = changeSubMenu.addItem("Термоэтикетка (58х40мм)");
        operate.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });
        MenuItem undoOperate = changeSubMenu.addItem("Настроить..");
        undoOperate.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });


        Button settingsButton = new Button(new Icon(VaadinIcon.COG));
        settingsButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        settingsButton.addClickListener(event -> {
        });

        horizontalToolPanelLayout.add(helpButton, text, refreshButton, reserves, addWriteOffsButton, filterButton, numberField, menuBar, settingsButton);
        add(horizontalToolPanelLayout);
    }
}
