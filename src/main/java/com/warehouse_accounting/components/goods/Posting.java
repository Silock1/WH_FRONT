package com.warehouse_accounting.components.goods;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
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
import com.warehouse_accounting.models.dto.PostingDto;
import com.warehouse_accounting.services.interfaces.PostingService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@UIScope
@Route(value = "posting", layout = AppView.class)
public class Posting extends VerticalLayout {
    private HorizontalLayout horizontalToolPanelLayout = new HorizontalLayout();
    private Grid<PostingDto> grid = new Grid<>(PostingDto.class);
    private PostingService postingService;

    @Getter
    @Setter
    private Div pageContent;

    public Posting(PostingService postingService) {
        this.postingService = postingService;
        horizontalToolPanelLayout.setAlignItems(Alignment.CENTER);


        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.setItems(postingService.getAll());
        grid.setColumns("id", "dateOfCreation", "warehouseTo.name", "company.name", "sum", "moved", "printed", "comment");
        grid.setColumnOrder(grid.getColumnByKey("id").setHeader("Id"),
                grid.getColumnByKey("dateOfCreation").setHeader("Время"),
                grid.getColumnByKey("warehouseTo.name").setHeader("Склад"),
                grid.getColumnByKey("company.name").setHeader("Организация"),
                grid.getColumnByKey("sum").setHeader("Сумма"),
                grid.getColumnByKey("moved").setHeader("Отправлено"),
                grid.getColumnByKey("printed").setHeader("Напечатано"),
                grid.getColumnByKey("comment").setHeader("Комментарий")
        );


//        grid.setSelectionMode(Grid.SelectionMode.MULTI);
//        grid.setItems(postingService.getAll());
//        grid.setColumnOrder(grid.getColumnByKey("Id").setHeader("Id"),
//                grid.getColumnByKey("Наименование").setHeader("Наименование"),
//                grid.getColumnByKey("Масса").setHeader("Масса"),
//                grid.getColumnByKey("Объем").setHeader("Объем"),
//                grid.getColumnByKey("Описание").setHeader("Описание"),
//                grid.getColumnByKey("Единица измерения").setHeader("Единица измерения"),
//                grid.getColumnByKey("В архиве").setHeader("В архиве"),
//                grid.getColumnByKey("Подрядчик").setHeader("Подрядчик"),
//                grid.getColumnByKey("Цена").setHeader("Цена"),
//                grid.getColumnByKey("Группа").setHeader("Группа"),
//                grid.getColumnByKey("Объект рассчетов").setHeader("Объект рассчетов"));
        configToolPanel();


        add(horizontalToolPanelLayout);
        add(grid);
    }

    private void configToolPanel() {
        Button helpButton = new Button(new Icon(VaadinIcon.QUESTION_CIRCLE));
        Dialog dialog = new Dialog();
        dialog.add(new Text("\"Оприходование позволяет учитывать на складе излишки товаров,\\n\" +\n" +
                "                        \"обнаруженные при инвентаризации, и пересортицу, а также\\n\" +\n" +
                "                        \"вносить на склад начальные остатки. Для каждого склада\\n\" +\n" +
                "                        \"создается отдельный документ оприходования.\\n\" +\n" +
                "                        \"\\n\" +\n" +
                "                        \"Оприходование можно создать вручную или из документа\\n\" +\n" +
                "                        \"инвентаризации.\\n\" +\n" +
                "                        \"\\n\" +\n" +
                "                        \"Читать инструкцию\\n\""));
        helpButton.addClickListener(event -> dialog.open());

        dialog.setWidth("400px");
        dialog.setHeight("150px");

        Span text = new Span("Оприходования");
        text.setClassName("pageTitle");

        Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH), event -> {
        });

        Button addWriteOffsButton = new Button("Оприходования", new Icon(VaadinIcon.PLUS), event -> {
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
        settingsButton.addClickListener(event -> {
        });

        horizontalToolPanelLayout.add(helpButton, text, refreshButton, addWriteOffsButton, filterButton, searchField, numberField, menuBar, settingsButton);
    }
}