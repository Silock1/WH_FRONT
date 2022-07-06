package com.warehouse_accounting.components.goods.grids.movements;

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
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.AppView;
import com.warehouse_accounting.components.util.ColumnToggleContextMenu;
import com.warehouse_accounting.models.dto.MovementDto;
import com.warehouse_accounting.services.interfaces.MovementService;
import org.springframework.stereotype.Component;
import org.vaadin.olli.FileDownloadWrapper;

import java.time.LocalDate;

@Component
@UIScope
@Route(value = "movement", layout = AppView.class)
public class MovementView extends VerticalLayout {

    private HorizontalLayout horizontalToolPanelLayout = new HorizontalLayout();
    private Grid<MovementDto> grid = new Grid<>(MovementDto.class);

    private Button settingButton = new Button(new Icon(VaadinIcon.COG));
    private MovementService movementService;

    public MovementView(MovementService movementService) {
        this.movementService = movementService;
        horizontalToolPanelLayout.setAlignItems(Alignment.CENTER);

        configToolPanel();
        initGrid();
    }

    private void initGrid() {
        // Здесь настройка грида
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.setItems(movementService.getAll());
        Grid.Column<MovementDto> id = grid.getColumnByKey("id").setHeader("№");
        Grid.Column<MovementDto> dateTime = grid.getColumnByKey("dateTime").setHeader("Время");
        Grid.Column<MovementDto> warehouseNameFrom = grid.getColumnByKey("warehouseFrom").setHeader("Со склада");
        Grid.Column<MovementDto> warehouseNameTo = grid.getColumnByKey("warehouseTo").setHeader("На склад");
        Grid.Column<MovementDto> org = grid.getColumnByKey("company").setHeader("Организация");
        Grid.Column<MovementDto> sum = grid.getColumnByKey("sum").setHeader("Сумма");
        Grid.Column<MovementDto> send = grid.getColumnByKey("moved").setHeader("Отправлено");
        Grid.Column<MovementDto> print = grid.getColumnByKey("printed").setHeader("Напечатано");
        Grid.Column<MovementDto> comment = grid.getColumnByKey("comment").setHeader("Комментарий");
        grid.setColumnOrder(id,
                dateTime,
                warehouseNameFrom,
                warehouseNameTo,
                org,
                sum,
                send,
                print,
                comment);

        settingButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        ColumnToggleContextMenu<MovementDto> columnToggleContextMenu = new ColumnToggleContextMenu<>(settingButton);


        columnToggleContextMenu.addColumnToggleItem("№", id);
        columnToggleContextMenu.addColumnToggleItem("Время", dateTime);
        columnToggleContextMenu.addColumnToggleItem("Со склада", warehouseNameFrom);
        columnToggleContextMenu.addColumnToggleItem("На склад", warehouseNameTo);
        columnToggleContextMenu.addColumnToggleItem("Организация", org);
        columnToggleContextMenu.addColumnToggleItem("Сумма", sum);
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

        Span text = new Span("Перемещения");
        text.setClassName("pageTitle");

        Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH), event -> {
            //TODO повод поработать этот функционал
        });

        Button addMovementButton = new Button("Перемещение", new Icon(VaadinIcon.PLUS), event -> {
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
        MenuItem combine = changeSubMenu.addItem("Снять проведение");
        combine.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });

        SubMenu statusSubMenu = status.getSubMenu();
        MenuItem configureStatus = statusSubMenu.addItem("Настроить...");
        configureStatus.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });

        SubMenu printSubMenu = print.getSubMenu();
        MenuItem movementList = printSubMenu.addItem("Список перемещений");
        movementList.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });
        MenuItem torg13 = printSubMenu.addItem("ТОРГ-13");
        torg13.addClickListener(event -> {
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

        FileDownloadWrapper buttonWrapper = new FileDownloadWrapper(
                new StreamResource(LocalDate.now().toString() + " someSheet.xlsx",
                        () -> movementService.getExcel().byteStream()));
        buttonWrapper.wrapComponent(new Button("Скачать XLSX", new Icon(VaadinIcon.DOWNLOAD)));

        horizontalToolPanelLayout.add(helpButton, text, refreshButton, addMovementButton, filterButton, searchField, numberField, menuBar, settingsButton, buttonWrapper);
        add(horizontalToolPanelLayout);
    }
}
