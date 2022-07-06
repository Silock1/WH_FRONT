package com.warehouse_accounting.components.goods.grids.priceList;

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
import com.warehouse_accounting.models.dto.PriceListDto;
import com.warehouse_accounting.services.interfaces.PriceListService;
import org.springframework.stereotype.Component;

@Component
@UIScope
@Route(value = "priceList", layout = AppView.class)
public class PriceList extends VerticalLayout {

    private HorizontalLayout horizontalToolPanelLayout = new HorizontalLayout();
    private Grid<PriceListDto> grid = new Grid<>(PriceListDto.class);

    private Button settingButton = new Button(new Icon(VaadinIcon.COG));
    private PriceListService priceListService;

    public PriceList(PriceListService priceListService) {
        this.priceListService = priceListService;
        horizontalToolPanelLayout.setAlignItems(Alignment.CENTER);

        configToolPanel();
        initGrid();

    }

    private void initGrid() {
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.setItems(priceListService.getAll());
        Grid.Column<PriceListDto> id = grid.getColumnByKey("id").setHeader("№");
        Grid.Column<PriceListDto> dateTime = grid.getColumnByKey("dateTime").setHeader("Время");
        Grid.Column<PriceListDto> org = grid.getColumnByKey("company").setHeader("Организация");
        Grid.Column<PriceListDto> send = grid.getColumnByKey("moved").setHeader("Отправлено");
        Grid.Column<PriceListDto> print = grid.getColumnByKey("printed").setHeader("Напечатано");
        Grid.Column<PriceListDto> comment = grid.getColumnByKey("comment").setHeader("Комментарий");
        grid.setColumnOrder(id, dateTime, org, send, print, comment);

        settingButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        ColumnToggleContextMenu<PriceListDto> columnToggleContextMenu = new ColumnToggleContextMenu<>(settingButton);


        columnToggleContextMenu.addColumnToggleItem("№", id);
        columnToggleContextMenu.addColumnToggleItem("Время", dateTime);
        columnToggleContextMenu.addColumnToggleItem("Организация", org);
        columnToggleContextMenu.addColumnToggleItem("Отправлено", send);
        columnToggleContextMenu.addColumnToggleItem("Напечатано", print);
        columnToggleContextMenu.addColumnToggleItem("Комментарий", comment);

        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.add(grid, settingButton);
        grid.setHeightByRows(true);
        headerLayout.setWidthFull();

        add(headerLayout);
    }

    // Здесь настройка панели инструментов
    private void configToolPanel() {
        Button helpButton = new Button(new Icon(VaadinIcon.QUESTION_CIRCLE));
        Dialog dialog = new Dialog();
        dialog.add(new Text("Перемещения фиксируют движение товаров между складами внутри вашей организации."));
        helpButton.addClickListener(event -> dialog.open());

        dialog.setWidth("400px");
        dialog.setHeight("150px");

        Span text = new Span("Прайс-листы");
        text.setClassName("pageTitle");

        Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH), event -> {
            //TODO повод поработать этот функционал
        });

        Button addPriceListButton = new Button("Прайс-лист", new Icon(VaadinIcon.PLUS), event -> {
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
        MenuItem copy = changeSubMenu.addItem("Копировать");
        copy.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });
        MenuItem massEdit = changeSubMenu.addItem("Массовое редактирование");
        massEdit.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });
        MenuItem operate = changeSubMenu.addItem("Провести");
        operate.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });
        MenuItem undoOperate = changeSubMenu.addItem("Снять проведение");
        undoOperate.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });

        SubMenu statusSubMenu = status.getSubMenu();
        MenuItem configureStatus = statusSubMenu.addItem("Настроить...");
        configureStatus.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });

        SubMenu printSubMenu = print.getSubMenu();
        MenuItem priceListList = printSubMenu.addItem("Список прайс-листов");
        priceListList.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });

        MenuItem priceTags2x3 = printSubMenu.addItem("Ценники 2x3");
        priceTags2x3.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });
        MenuItem priceTags = printSubMenu.addItem("Ценники");
        priceTags.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });
        MenuItem priceList = printSubMenu.addItem("Прайс-лист");
        priceList.addClickListener(event -> {
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
        settingsButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        settingsButton.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });

        horizontalToolPanelLayout.add(helpButton, text, refreshButton, addPriceListButton, filterButton, searchField, numberField, menuBar, settingsButton);
        add(horizontalToolPanelLayout);
    }
}
