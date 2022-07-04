
package com.warehouse_accounting.components.purchases;

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
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.AppView;
import com.warehouse_accounting.components.util.ColumnToggleContextMenu;
import com.warehouse_accounting.models.dto.InvoicesReceivedDto;
import com.warehouse_accounting.services.interfaces.InvoicesReceivedService;
import org.springframework.stereotype.Component;

@Component
@UIScope
@Route(value = "invoicesReceived", layout = AppView.class)
public class InvoicesReceived extends VerticalLayout {

    private HorizontalLayout horizontalToolPanelLayout = new HorizontalLayout();
    private HorizontalLayout horizontalToolPanelLayout2 = new HorizontalLayout();
    private Grid<InvoicesReceivedDto> grid = new Grid<>(InvoicesReceivedDto.class);

    private Button settingButton = new Button(new Icon(VaadinIcon.COG));
    private InvoicesReceivedService invoicesReceivedService;

    public InvoicesReceived(InvoicesReceivedService invoicesReceivedService) {
        this.invoicesReceivedService = invoicesReceivedService;
        horizontalToolPanelLayout.setAlignItems(Alignment.CENTER);

        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.setItems(invoicesReceivedService.getAll());
        Grid.Column<InvoicesReceivedDto> id = grid.getColumnByKey("id").setHeader("№").setAutoWidth(true);
        Grid.Column<InvoicesReceivedDto> dateTime = grid.getColumnByKey("data").setHeader("Время").setAutoWidth(true);
        Grid.Column<InvoicesReceivedDto> contrAgent = grid.getColumnByKey("contractor").setHeader("Контрагент").setAutoWidth(true);
        Grid.Column<InvoicesReceivedDto> org = grid.getColumnByKey("company").setHeader("Организация").setAutoWidth(true);
        Grid.Column<InvoicesReceivedDto> sum = grid.getColumnByKey("sum").setHeader("Сумма").setAutoWidth(true);
        Grid.Column<InvoicesReceivedDto> number = grid.getColumnByKey("incomingNumber").setHeader("Входящий номер").setAutoWidth(true);
        Grid.Column<InvoicesReceivedDto> incomeDate = grid.getColumnByKey("dateIncomingNumber").setHeader("Входящая дата").setAutoWidth(true);
        Grid.Column<InvoicesReceivedDto> sent = grid.getColumnByKey("sent").setHeader("Отправлено").setAutoWidth(true);
        Grid.Column<InvoicesReceivedDto> print = grid.getColumnByKey("printed").setHeader("Напечатано").setAutoWidth(true);
        Grid.Column<InvoicesReceivedDto> comment = grid.getColumnByKey("comment").setHeader("Комментарий").setAutoWidth(true);
        grid.setColumnOrder(id, dateTime, contrAgent, org, sum, number, incomeDate, sent, print, comment);

        settingButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        ColumnToggleContextMenu<InvoicesReceivedDto> columnToggleContextMenu = new ColumnToggleContextMenu<>(settingButton);

        columnToggleContextMenu.addColumnToggleItem("№", id);
        columnToggleContextMenu.addColumnToggleItem("Время", dateTime);
        columnToggleContextMenu.addColumnToggleItem("Контрагент", contrAgent);
        columnToggleContextMenu.addColumnToggleItem("Организация", org);
        columnToggleContextMenu.addColumnToggleItem("Сумма", sum);
        columnToggleContextMenu.addColumnToggleItem("Входящий номер", number);
        columnToggleContextMenu.addColumnToggleItem("Входящая дата", incomeDate);
        columnToggleContextMenu.addColumnToggleItem("Отправлено", sent);
        columnToggleContextMenu.addColumnToggleItem("Напечатано", print);
        columnToggleContextMenu.addColumnToggleItem("Комментарий", comment);

        horizontalToolPanelLayout2.setAlignItems(FlexComponent.Alignment.BASELINE);
        horizontalToolPanelLayout2.setFlexGrow(1);
        grid.setHeightByRows(true);
        horizontalToolPanelLayout2.setWidthFull();
        horizontalToolPanelLayout2.add(grid, settingButton);

        configToolPanel();

        add(horizontalToolPanelLayout, horizontalToolPanelLayout2);


    }

    private void configToolPanel() {
        Button helpButton = new Button(new Icon(VaadinIcon.QUESTION_CIRCLE));
        Dialog dialog = new Dialog();
        dialog.add(new Text("Счета-фактуры создаются на основе приёмки."));
        helpButton.addClickListener(event -> dialog.open());

        dialog.setWidth("400px");
        dialog.setHeight("150px");

        Span text = new Span("Счета-фактуры полученные");
        text.setClassName("pageTitle");

        Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH), event -> {

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
        MenuItem invoicesReceivedList = printSubMenu.addItem("Список счетов-фактур");
        invoicesReceivedList.addClickListener(event -> {

        });

        MenuItem invoiceTraceability = printSubMenu.addItem("Счет-фактура (с прослеживаемостью)");
        invoiceTraceability.addClickListener(event -> {

        });
        MenuItem invoiceNotTraceability = printSubMenu.addItem("Счет-фактура (без прослеживаемости)");
        invoiceNotTraceability.addClickListener(event -> {

        });
        MenuItem correctiveTraceability = printSubMenu.addItem("Корректировочный счет-фактура (с прослеживаемостью)");
        correctiveTraceability.addClickListener(event -> {

        });

        MenuItem correctiveNotTraceability = printSubMenu.addItem("Корректировочный счет-фактура (без прослеживаемости)");
        correctiveNotTraceability.addClickListener(event -> {

        });
        MenuItem set = printSubMenu.addItem("Комплект...");
        set.addClickListener(event -> {

        });

        MenuItem configurePrint = printSubMenu.addItem("Настроить...");
        configurePrint.addClickListener(event -> {

        });

        Button settingsButton = new Button(new Icon(VaadinIcon.COG));
        settingsButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        settingsButton.addClickListener(event -> {

        });

        horizontalToolPanelLayout.add(helpButton, text, refreshButton, filterButton, searchField, numberField, menuBar, settingsButton);
    }
}

