package com.warehouse_accounting.components.goods;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
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
import com.warehouse_accounting.models.dto.TurnsDto;
import com.warehouse_accounting.models.dto.WriteOffsDto;
import com.warehouse_accounting.services.interfaces.RemainsService;
import com.warehouse_accounting.services.interfaces.TurnsService;
import com.warehouse_accounting.services.interfaces.WriteOffsService;
import org.springframework.stereotype.Component;

@Component
@UIScope
@Route(value = "turns", layout = AppView.class)
public class Turns extends VerticalLayout {
    private HorizontalLayout horizontalToolPanelLayout = new HorizontalLayout();
    private Grid<TurnsDto> grid = new Grid<>(TurnsDto.class);

    private Button settingButton = new Button(new Icon(VaadinIcon.COG));
    private TurnsService turnsService;

    public Turns(TurnsService turnsService) {
        this.turnsService = turnsService;
        horizontalToolPanelLayout.setAlignItems(Alignment.CENTER);

        configToolPanel();
        initGrid();
    }

    private void initGrid() {
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.setItems(turnsService.getAll());

        Grid.Column<TurnsDto> id = grid.getColumnByKey("id").setHeader("№");
        Grid.Column<TurnsDto> name = grid.getColumnByKey("name").setHeader("Наименование");
        Grid.Column<TurnsDto> sum = grid.getColumnByKey("sum").setHeader("Код");
        Grid.Column<TurnsDto> article = grid.getColumnByKey("article").setHeader("Артикул");
        Grid.Column<TurnsDto> unit = grid.getColumnByKey("unit").setHeader("Ед.изм.");
        Grid.Column<TurnsDto> amountBegin = grid.getColumnByKey("amountBegin").setHeader("Кол-во");
        Grid.Column<TurnsDto> sumBegin = grid.getColumnByKey("sumBegin").setHeader("Сумма");
        Grid.Column<TurnsDto> amountComing = grid.getColumnByKey("amountComing").setHeader("Кол-во");
        Grid.Column<TurnsDto> sumComing = grid.getColumnByKey("sumComing").setHeader("Сумма");
        Grid.Column<TurnsDto> amountConsumption = grid.getColumnByKey("amountConsumption").setHeader("Кол-во");
        Grid.Column<TurnsDto> sumConsumption = grid.getColumnByKey("sumConsumption").setHeader("Сумма");
        Grid.Column<TurnsDto> amountEnd = grid.getColumnByKey("amountEnd").setHeader("Кол-во");
        Grid.Column<TurnsDto> sumEnd = grid.getColumnByKey("sumEnd").setHeader("Сумма");


        grid.setColumnOrder(id, name, sum, article, unit, amountBegin, sumBegin, amountComing, sumComing,
                amountConsumption, sumConsumption, amountEnd, sumEnd);


        settingButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        ColumnToggleContextMenu<TurnsDto> columnToggleContextMenu = new ColumnToggleContextMenu<>(settingButton);

        columnToggleContextMenu.addColumnToggleItem("№", id);
        columnToggleContextMenu.addColumnToggleItem("Наименование", name);
        columnToggleContextMenu.addColumnToggleItem("Код", sum);
        columnToggleContextMenu.addColumnToggleItem("Артикул", article);
        columnToggleContextMenu.addColumnToggleItem("Ед.изм.", unit);
        columnToggleContextMenu.addColumnToggleItem("Кол-во", amountBegin);
        columnToggleContextMenu.addColumnToggleItem("Сумма", sumBegin);
        columnToggleContextMenu.addColumnToggleItem("Кол-во", amountComing);
        columnToggleContextMenu.addColumnToggleItem("Сумма", sumComing);
        columnToggleContextMenu.addColumnToggleItem("Кол-во", amountConsumption);
        columnToggleContextMenu.addColumnToggleItem("Сумма", sumConsumption);
        columnToggleContextMenu.addColumnToggleItem("Кол-во", amountEnd);
        columnToggleContextMenu.addColumnToggleItem("Сумма", sumEnd);

        HeaderRow second = grid.prependHeaderRow();
        second.join(amountBegin, sumBegin).setText("     Начало периода");
        second.join(amountComing, sumComing).setText("    Приход");
        second.join(amountConsumption, sumConsumption).setText("   Расход");
        second.join(amountEnd, sumEnd).setText("    Конец периода");

        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.add(grid, settingButton);
        grid.setHeightByRows(true);
        headerLayout.setWidthFull();

        add(headerLayout);
    }

    private void configToolPanel() {
        Button helpButton = new Button(new Icon(VaadinIcon.QUESTION_CIRCLE));
        Dialog dialog = new Dialog();
        dialog.add(new Text(" разделе представлены приход и расход товаров за определенный временной промежуток. Можно посмотреть статистику по складу, поставщику, проекту и так далее.\n" +
                "\n" +
                "Нажмите на строку с товаром — откроется список всех документов, которые повлияли на оборот по данному товару."));
        helpButton.addClickListener(event -> dialog.open());

        dialog.setWidth("400px");
        dialog.setHeight("150px");

        Span text = new Span("Обороты");
        text.setClassName("pageTitle");

        Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH), event -> {
        });

        Button addWriteOffsButton = new Button("Фильтр", event -> {
        });



//        Button filterButton = new Button("По складам", event -> {
//        });
//        Button numberField = new Button("Фильтр", event -> {
//        });

        MenuBar menuBar = new MenuBar();

        MenuItem change = menuBar.addItem("Печать");
        change.add(new Icon(VaadinIcon.PRINT));
        change.add(new Icon(VaadinIcon.CARET_DOWN));
//        MenuItem reserves = menuBar.addItem("Пополнить резервы");
//
//        MenuItem status = menuBar.addItem("Тележка");
//        status.add(new Icon(VaadinIcon.CART));
//        status.add(new Icon(VaadinIcon.CARET_DOWN));

        SubMenu changeSubMenu = change.getSubMenu();
        MenuItem delete = changeSubMenu.addItem("Обороты");
        delete.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });
        MenuItem copy = changeSubMenu.addItem("Обороты по сериям");
        copy.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });
        MenuItem massEdit = changeSubMenu.addItem("Обороты по товарам");
        massEdit.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });
        MenuItem undoOperate = changeSubMenu.addItem("Настроить..");
        undoOperate.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });

//        Button settingsButton = new Button(new Icon(VaadinIcon.COG));
//        settingsButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
//        settingsButton.addClickListener(event -> {
//        });

        horizontalToolPanelLayout.add(helpButton, text, refreshButton, addWriteOffsButton, menuBar);
        add(horizontalToolPanelLayout);
    }
}
