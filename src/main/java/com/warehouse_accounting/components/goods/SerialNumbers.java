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
import com.warehouse_accounting.models.dto.SerialNumbersDto;
import com.warehouse_accounting.models.dto.TurnsDto;
import com.warehouse_accounting.models.dto.WriteOffsDto;
import com.warehouse_accounting.services.interfaces.RemainsService;
import com.warehouse_accounting.services.interfaces.SerialNumbersService;
import com.warehouse_accounting.services.interfaces.TurnsService;
import com.warehouse_accounting.services.interfaces.WriteOffsService;
import org.springframework.stereotype.Component;

@Component
@UIScope
@Route(value = "serialNumbers", layout = AppView.class)
public class SerialNumbers extends VerticalLayout {
    private HorizontalLayout horizontalToolPanelLayout = new HorizontalLayout();
    private Grid<SerialNumbersDto> grid = new Grid<>(SerialNumbersDto.class);

    private Button settingButton = new Button(new Icon(VaadinIcon.COG));
    private SerialNumbersService serialNumbersService;

    public SerialNumbers(SerialNumbersService serialNumbersService) {
        this.serialNumbersService = serialNumbersService;
        horizontalToolPanelLayout.setAlignItems(Alignment.CENTER);

        configToolPanel();
        initGrid();
    }

    private void initGrid() {
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.setItems(serialNumbersService.getAll());

        Grid.Column<SerialNumbersDto> sn = grid.getColumnByKey("sn").setHeader("Серийный номер");
        Grid.Column<SerialNumbersDto> code = grid.getColumnByKey("code").setHeader("Код");
        Grid.Column<SerialNumbersDto> article = grid.getColumnByKey("article").setHeader("Артикул");
        Grid.Column<SerialNumbersDto> product = grid.getColumnByKey("product").setHeader("Товар");
        Grid.Column<SerialNumbersDto> fabric = grid.getColumnByKey("fabric").setHeader("Склад");
        Grid.Column<SerialNumbersDto> typeOfDoc = grid.getColumnByKey("typeOfDoc").setHeader("Тип документа");
        Grid.Column<SerialNumbersDto> number = grid.getColumnByKey("number").setHeader("Номер");
        Grid.Column<SerialNumbersDto> description = grid.getColumnByKey("description").setHeader("Описание");


        grid.setColumnOrder(sn, code, article, product, fabric, typeOfDoc, number, description);


        settingButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        ColumnToggleContextMenu<SerialNumbersDto> columnToggleContextMenu = new ColumnToggleContextMenu<>(settingButton);

        columnToggleContextMenu.addColumnToggleItem("Серийный номер", sn);
        columnToggleContextMenu.addColumnToggleItem("Код", code);
        columnToggleContextMenu.addColumnToggleItem("Артикул", article);
        columnToggleContextMenu.addColumnToggleItem("Товар", product);
        columnToggleContextMenu.addColumnToggleItem("Склад", fabric);
        columnToggleContextMenu.addColumnToggleItem("Тип документа", typeOfDoc);
        columnToggleContextMenu.addColumnToggleItem("Номер", number);
        columnToggleContextMenu.addColumnToggleItem("Описание", description);

        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.add(grid, settingButton);
        grid.setHeightByRows(true);
        headerLayout.setWidthFull();

        add(headerLayout);
    }

    private void configToolPanel() {
        Button helpButton = new Button(new Icon(VaadinIcon.QUESTION_CIRCLE));
        Dialog dialog = new Dialog();
        dialog.add(new Text("Для продажи электроники и другой продукции, уникальные экземпляры которой нужно контролировать, в МоемСкладе есть учет товаров по серийным номерам — он позволяет отслеживать движение каждой единицы товара. В разделе представлены все товары с серийными номерами, которые сейчас есть на ваших складах.\n" +
                "\n" +
                "Учет по серийным номерам нужно включить в карточке товара."));
        helpButton.addClickListener(event -> dialog.open());

        dialog.setWidth("400px");
        dialog.setHeight("150px");

        Span text = new Span("Сер. номера");
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
        MenuItem delete = changeSubMenu.addItem("Серийные номера");
        delete.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });
        MenuItem copy = changeSubMenu.addItem("Движения");
        copy.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });
        MenuItem massEdit = changeSubMenu.addItem("Ценник (70х49,5мм)");
        massEdit.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });
        MenuItem undoOperate = changeSubMenu.addItem("Термоэтикетка (58х40мм)");
        undoOperate.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });
        MenuItem undoOperate1 = changeSubMenu.addItem("Настроить...");
        undoOperate1.addClickListener(event -> {
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
