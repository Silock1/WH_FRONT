package com.warehouse_accounting.components.adjustments;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
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
import com.warehouse_accounting.models.dto.AdjustmentsDto;
import com.warehouse_accounting.services.interfaces.AdjustmentsService;
import org.springframework.stereotype.Component;

@Component
@UIScope
@Route(value = "adjustments", layout = AppView.class)
public class AdjustmentsView extends VerticalLayout{

    private HorizontalLayout horizontalToolPanelLayout = new HorizontalLayout();
    private Grid<AdjustmentsDto> adjustmentsDtoGrid = new Grid<>(AdjustmentsDto.class);
    private AdjustmentsService adjustmentsService;

    public AdjustmentsView(AdjustmentsService adjustmentsService ) {
        this.adjustmentsService = adjustmentsService;
        horizontalToolPanelLayout.setAlignItems(Alignment.CENTER);

        adjustmentsDtoGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        adjustmentsDtoGrid.setItems(adjustmentsService.getAll());
        adjustmentsDtoGrid.setColumnOrder(adjustmentsDtoGrid.getColumnByKey("number").setHeader("№"),
                adjustmentsDtoGrid.getColumnByKey("dateTimeAdjustment").setHeader("Время"),
                adjustmentsDtoGrid.getColumnByKey("company").setHeader("Организация"),
                adjustmentsDtoGrid.getColumnByKey("contractor").setHeader("Контрагент"),
                adjustmentsDtoGrid.getColumnByKey("currentBalance").setHeader("Счет"),
                adjustmentsDtoGrid.getColumnByKey("totalBalance").setHeader("Касса"),
                adjustmentsDtoGrid.getColumnByKey("adjustmentAmount").setHeader("Сумма корректировки"),
                adjustmentsDtoGrid.getColumnByKey("comment").setHeader("Комментарий"),
                adjustmentsDtoGrid.getColumnByKey("whenChanged").setHeader("Когда изменен"),
                adjustmentsDtoGrid.getColumnByKey("type").setHeader("Кто изменил"));
        configToolPanel();

        add(horizontalToolPanelLayout);
        add(adjustmentsDtoGrid);
    }

    // Здесь настройка панели инструментов
    private void configToolPanel() {
        Button helpButton = new Button(new Icon(VaadinIcon.QUESTION_CIRCLE));
        helpButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
        helpButton.addClickListener(e->{
            Notification.show("Корректировка баланса — изменение остатка денежных средств\n" +
                    "\n" +
                    "на счетах организации и балансах контрагентов без привязки\n" +
                    "\n" +
                    "к документам товарного учета. Корректировка помогает установить\n" +
                    "\n" +
                    "начальный баланс и наладить учет, если реальное количество\n" +
                    "\n" +
                    "денег не сходится с суммами в МоемСкладе.\n" +
                    "\n" +
                    "Читать инструкцию: Корректировка остатков по счетам и кассе", 5000, Notification.Position.TOP_START);
        });

        Span text = new Span("Корректировки");
        text.setClassName("adjustments");

        Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH), event -> {
            //TODO повод поработать этот функционал
        });

        Button addPaymentsButton = new Button("Корректировки", new Icon(VaadinIcon.PLUS), event -> {
            //TODO повод поработать этот функционал
        });

        Button filterButton = new Button("Фильтр", event -> {
            //TODO повод поработать этот функционал
        });

        TextField searchField = new TextField();
        searchField.setPlaceholder("Номер или комментарий");
        searchField.setMinWidth("170px");

        NumberField numberField = new NumberField();
        adjustmentsDtoGrid.addSelectionListener(event -> numberField.setValue((double) (adjustmentsDtoGrid.getSelectedItems().size())));
        numberField.setValue(0d);
        numberField.setWidth("40px");

        MenuBar menuBar = new MenuBar();

        MenuItem change = menuBar.addItem("Изменить");
        change.add(new Icon(VaadinIcon.CARET_DOWN));

        MenuItem print = menuBar.addItem(new Icon(VaadinIcon.PRINT));
        print.add("Печать");
        print.add(new Icon(VaadinIcon.CARET_DOWN));

        SubMenu changeSubMenu = change.getSubMenu();
        MenuItem removeList = changeSubMenu.addItem("Удалить");
        removeList.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });

        MenuItem massEdit = changeSubMenu.addItem("Массовое редактирование");
        massEdit.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });


        SubMenu printSubMenu = print.getSubMenu();
        MenuItem adjustmentsList = printSubMenu.addItem("Список корректировок");
        adjustmentsList.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });
        MenuItem adjustmentsToCashBalancesList = printSubMenu.addItem("Список корректировок остатков в кассе");
        adjustmentsToCashBalancesList.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });
        MenuItem adjustmentsToAccountBalancesList = printSubMenu.addItem("Список корректировок остатков на счете");
        adjustmentsToAccountBalancesList.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });
        MenuItem adjustmentsBalancesContractorList = printSubMenu.addItem("Список корректировок баланса контрагента");
        adjustmentsBalancesContractorList.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });
        MenuItem toConfigure = printSubMenu.addItem("Настроить");
        toConfigure.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });


        Button settingsButton = new Button(new Icon(VaadinIcon.COG));
        settingsButton.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });

        horizontalToolPanelLayout.add(helpButton, text, refreshButton, addPaymentsButton, filterButton, searchField, numberField, menuBar, settingsButton);
    }
}
