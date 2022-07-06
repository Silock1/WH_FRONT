package com.warehouse_accounting.components.payments;

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
import com.warehouse_accounting.components.util.ColumnToggleContextMenu;
import com.warehouse_accounting.models.dto.PaymentsDto;
import com.warehouse_accounting.services.interfaces.PaymentsService;
import org.springframework.stereotype.Component;

@Component
@UIScope
@Route(value = "payments", layout = AppView.class)
public class PaymentsView extends VerticalLayout {

    private HorizontalLayout horizontalToolPanelLayout = new HorizontalLayout();
    private Grid<PaymentsDto> grid = new Grid<>(PaymentsDto.class);

    private Button settingButton = new Button(new Icon(VaadinIcon.COG));
    private PaymentsService paymentsService;

    public PaymentsView(PaymentsService paymentsService) {
        this.paymentsService = paymentsService;
        horizontalToolPanelLayout.setAlignItems(Alignment.CENTER);

        configToolPanel();
        initGrid();
    }

    private void initGrid() {
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.setItems(paymentsService.getAll());

        Grid.Column<PaymentsDto> typeOfPayment = grid.getColumnByKey("typeOfPayment").setHeader("Тип документа");
        Grid.Column<PaymentsDto> id = grid.getColumnByKey("id").setHeader("№");
        Grid.Column<PaymentsDto> dateTime = grid.getColumnByKey("date").setHeader("Время");
        Grid.Column<PaymentsDto> org = grid.getColumnByKey("company").setHeader("Организация");
        Grid.Column<PaymentsDto> invoiceOrg = grid.getColumnByKey("contract").setHeader("Счет организации");
        Grid.Column<PaymentsDto> contrAgent = grid.getColumnByKey("contractor").setHeader("Контрагент");
        Grid.Column<PaymentsDto> invoiceContrAgent = grid.getColumnByKey("tax").setHeader("Счет контрагента");
        Grid.Column<PaymentsDto> amount = grid.getColumnByKey("amount").setHeader("Приход");
        Grid.Column<PaymentsDto> paymentExpenditure = grid.getColumnByKey("paymentExpenditure").setHeader("Расход");
        Grid.Column<PaymentsDto> target = grid.getColumnByKey("number").setHeader("Назначение платежа");
        Grid.Column<PaymentsDto> status = grid.getColumnByKey("isDone").setHeader("Статус");
        Grid.Column<PaymentsDto> send = grid.getColumnByKey("project").setHeader("Отправлено");
        Grid.Column<PaymentsDto> print = grid.getColumnByKey("purpose").setHeader("Напечатано");
        Grid.Column<PaymentsDto> comment = grid.getColumnByKey("comment").setHeader("Комментарий");
        grid.setColumnOrder(typeOfPayment, id, dateTime, org, invoiceOrg, contrAgent, invoiceContrAgent, amount, paymentExpenditure, target, status, send, print, comment);
        settingButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        ColumnToggleContextMenu<PaymentsDto> columnToggleContextMenu = new ColumnToggleContextMenu<>(settingButton);

        columnToggleContextMenu.addColumnToggleItem("Тип документа", typeOfPayment);
        columnToggleContextMenu.addColumnToggleItem("№", id);
        columnToggleContextMenu.addColumnToggleItem("Время", dateTime);
        columnToggleContextMenu.addColumnToggleItem("Организация", org);
        columnToggleContextMenu.addColumnToggleItem("Счет организации", invoiceOrg);
        columnToggleContextMenu.addColumnToggleItem("Контрагент", contrAgent);
        columnToggleContextMenu.addColumnToggleItem("Счет контрагента", invoiceContrAgent);
        columnToggleContextMenu.addColumnToggleItem("Приход", amount);
        columnToggleContextMenu.addColumnToggleItem("Расход", paymentExpenditure);
        columnToggleContextMenu.addColumnToggleItem("Назначение платежа", target);
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

    // Здесь настройка панели инструментов
    private void configToolPanel() {
        Button helpButton = new Button(new Icon(VaadinIcon.QUESTION_CIRCLE));
        helpButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
        helpButton.addClickListener(e -> {
            Notification.show("На основе платежей формируются взаиморасчеты с контрагентами.\n" +
                    "\n" +
                    "Входящий и исходящий платежи изменяют баланс на расчетных\n" +
                    "\n" +
                    "счетах организации, то есть используются для учета безналичных\n" +
                    "\n" +
                    "платежей. Приходный и расходный ордеры учитывают наличные\n" +
                    "\n" +
                    "платежи, то есть изменяют баланс в кассе организации. Также\n" +
                    "\n" +
                    "можно импортировать выписку из банка.\n" +
                    "\n" +
                    "Платежные документы можно привязать сразу к нескольким\n" +
                    "\n" +
                    "товарным документам.\n" +
                    "\n" +
                    "Читать инструкцию: Платежи", 5000, Notification.Position.TOP_START);
        });

        Span text = new Span("Платежи");
        text.setClassName("money");

        Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH), event -> {
            //TODO повод поработать этот функционал
        });

        Button addPaymentsButton = new Button("Приход", new Icon(VaadinIcon.PLUS), event -> {
            //TODO повод поработать этот функционал
        });

        Button addExpensesButton = new Button("Расходы", new Icon(VaadinIcon.PLUS), event -> {
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
        MenuItem create = changeSubMenu.addItem("Изменить");
        create.addClickListener(event -> {
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
        MenuItem printList = printSubMenu.addItem("Список всех платежей");
        printList.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });
        MenuItem incomingOrdersList = printSubMenu.addItem("Список приходных ордеров");
        incomingOrdersList.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });
        MenuItem receiptOrder = printSubMenu.addItem("Приходный ордер");
        receiptOrder.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });
        MenuItem spendingOrdersList = printSubMenu.addItem("Список расходных ордеров");
        spendingOrdersList.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });
        MenuItem expenseOrder = printSubMenu.addItem("Расходный ордер");
        expenseOrder.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });
        MenuItem incomingPaymentsList = printSubMenu.addItem("Список входящих платежей");
        incomingPaymentsList.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });
        MenuItem outgoingPaymentsList = printSubMenu.addItem("Список исходящих платежей ");
        outgoingPaymentsList.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });
        MenuItem paymentOrder = printSubMenu.addItem("Платежное поручение");
        paymentOrder.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });
        MenuItem toConfigure = printSubMenu.addItem("Настроить");
        toConfigure.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });

        MenuItem importList = menuBar.addItem("Импорт");
        importList.add(new Icon(VaadinIcon.CARET_DOWN));

        SubMenu importListSubMenu = importList.getSubMenu();
        MenuItem import1C = importListSubMenu.addItem("Банковская выписка(1С)");
        import1C.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });

        MenuItem export = menuBar.addItem("Экспорт");
        export.add(new Icon(VaadinIcon.CARET_DOWN));

        SubMenu exportListSubMenu = export.getSubMenu();
        MenuItem export1C = exportListSubMenu.addItem("Экспорт платежных поручений(1C)");
        toConfigure.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });


        Button settingsButton = new Button(new Icon(VaadinIcon.COG));
        settingsButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        settingsButton.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });

        horizontalToolPanelLayout.add(helpButton, importList, export, text, refreshButton, addExpensesButton,
                addPaymentsButton, addExpensesButton, filterButton, searchField, numberField, menuBar, settingsButton);
        add(horizontalToolPanelLayout);
    }
}