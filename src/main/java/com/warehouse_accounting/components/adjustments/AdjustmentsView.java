package com.warehouse_accounting.components.adjustments;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.html.Div;
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
import com.warehouse_accounting.components.adjustments.adjustmentButton.AccountBalances;
import com.warehouse_accounting.components.adjustments.adjustmentButton.CashBalance;
import com.warehouse_accounting.components.adjustments.grids.AdjustmentsGridsLayout;
import com.warehouse_accounting.services.interfaces.AdjustmentsService;
import org.springframework.stereotype.Component;

@Component
@UIScope
@Route(value = "adjustments", layout = AppView.class)
public class AdjustmentsView extends VerticalLayout{

    private HorizontalLayout horizontalToolPanelLayout = new HorizontalLayout();
    private AdjustmentsGridsLayout adjustmentsGridsLayout;
    private AdjustmentsService adjustmentsService;
    private Div mainDiv = new Div();

    public AdjustmentsView(AdjustmentsService adjustmentsService) {
        adjustmentsGridsLayout = new AdjustmentsGridsLayout(adjustmentsService, this);
        mainDiv.setSizeFull();
        mainDiv.add(adjustmentsGridsLayout);
        add(configToolPanel(), mainDiv);
    }

    // Здесь настройка панели инструментов
    private HorizontalLayout configToolPanel() {

        HorizontalLayout groupControl = new HorizontalLayout();
        horizontalToolPanelLayout.setAlignItems(Alignment.CENTER);
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

        MenuBar adjustments = new MenuBar();

        MenuItem addAdjustmentsButton = adjustments.addItem(new Icon(VaadinIcon.PLUS));
        addAdjustmentsButton.add("Корректировки");
        addAdjustmentsButton.add(new Icon(VaadinIcon.CARET_DOWN));

        SubMenu addAdjustmentsButtonList = addAdjustmentsButton.getSubMenu();
        MenuItem accountBalance = addAdjustmentsButtonList.addItem("Остаток на счете");
        accountBalance.addClickListener(event -> {
            AccountBalances accountBalances = new AccountBalances(mainDiv, this);
            mainDiv.removeAll();
            mainDiv.add(accountBalances);
        });

        MenuItem cashBalance = addAdjustmentsButtonList.addItem("Остаток в кассе");
        cashBalance.addClickListener(event -> {
            CashBalance cashBalance1 = new CashBalance(mainDiv,this);
            mainDiv.removeAll();
            mainDiv.add(cashBalance1);
        });

        MenuItem contragentsBalance = addAdjustmentsButtonList.addItem("Баланс контрагента");
        contragentsBalance.addClickListener(event -> {
            //TODO повод поработать этот функционал
        });

        Button filterButton = new Button("Фильтр", event -> {
            //TODO повод поработать этот функционал
        });

        TextField searchField = new TextField();
        searchField.setPlaceholder("Номер или комментарий");
        searchField.setMinWidth("170px");

        NumberField numberField = new NumberField();
        //adjustmentsGridsLayout.addClickListener(event -> numberField.setValue((double) (adjustmentsGridsLayout.getElement().getThemeList()));
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

        horizontalToolPanelLayout.add(helpButton, text, refreshButton, addAdjustmentsButton, adjustments, filterButton, searchField, numberField, menuBar, settingsButton);
        add(horizontalToolPanelLayout);
        return groupControl;
    }
}
