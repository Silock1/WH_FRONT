package com.warehouse_accounting.components.sales;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.AppView;
import com.warehouse_accounting.components.sales.forms.InvoiceForm;
import com.warehouse_accounting.components.sales.grids.ComissionerReportsGridLayout;
import org.springframework.stereotype.Component;

@Component
@UIScope
@Route(value = "comissioner_reports", layout = AppView.class)
public class ComissionerReports extends VerticalLayout {

    private final ComissionerReportsGridLayout reportsGridLayout;
//    private final Div parentLayer;
//    private ComissionerReportsService comissionerReportsService;


    public ComissionerReports(/*Div parentLayer*/) {
//        this.parentLayer = parentLayer;
        reportsGridLayout = new ComissionerReportsGridLayout();
        Div pageContent = new Div();
        pageContent.add(reportsGridLayout);
        pageContent.setSizeFull();
        add(buttonsGroup(), pageContent);
    }

    private HorizontalLayout buttonsGroup() {
        HorizontalLayout groupControl = new HorizontalLayout();

// "?" — информация о странице **************************************************************************
        Icon questionIcon = new Icon(VaadinIcon.QUESTION_CIRCLE_O);
        questionIcon.setSize("22px");
        Button helpButton = new Button(questionIcon);
        helpButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
        Dialog dialog = new Dialog();
        dialog.add(new Text("В разделе представлены выданные и полученные отчеты комиссионера. " +
            "В отчетах указываются проданные товары, сумма продажи, вознаграждение комиссионера. " +
            "На основе отчетов формируется долг комиссионера перед комитентом." +
            "Выданные отчеты создает комиссионер. Полученные — комитент."));
        helpButton.addClickListener(event -> dialog.open());
        dialog.setHeight("150px");
        dialog.setWidth("400px");

// Название страницы
        Span pageName = new Span("Отчеты комиссионера");
        pageName.setClassName("pageTitle");
        setAlignItems(Alignment.AUTO);

// Кнопка обновления
        Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH),
            event -> {

            });

// Кнопка "Фильтр"
        Button filterButton = new Button("Фильтр", event -> {

        });

// Поле ввода
        TextField searchField = new TextField();
        searchField.setPlaceholder("Номер или комментарий");
        searchField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        searchField.setWidth("160px");
        searchField.addInputListener(inputEvent -> {

        });

// Вся группа:
        groupControl.add(
            helpButton,
            pageName,
            refreshButton,
            createReportTypeBar(),
            filterButton,
            searchField,
            createNumberField(),
            createChangeBar(),
            createCog()
        );
        groupControl.setAlignItems(Alignment.BASELINE);
        setSizeFull();
        return groupControl;
    }

    private HorizontalLayout createReportTypeBar() {

        Icon plusIcon = new Icon(VaadinIcon.PLUS_CIRCLE);
        plusIcon.setSize("12px");
        Icon caretDownIcon = new Icon(VaadinIcon.CARET_DOWN);
        caretDownIcon.setSize("12px");

        TextField textField = new TextField();
        textField.setReadOnly(true);
        textField.setWidth("30px");
        textField.setValue("0");

        MenuBar statusMenuBar = new MenuBar();
        HorizontalLayout horizontalLayout = new HorizontalLayout(plusIcon, new Text("_Отчёт комиссионера"), caretDownIcon);
        horizontalLayout.setSpacing(false);
        horizontalLayout.setAlignItems(Alignment.CENTER);

        MenuItem statusItem = statusMenuBar.addItem(horizontalLayout);

        statusItem.getSubMenu().addItem("Полученный отчет комиссионера", e -> {

        });

        statusItem.getSubMenu().addItem("Выданный отчет комиссионера", e -> {

        });

        HorizontalLayout groupStatus = new HorizontalLayout();
        groupStatus.add(statusMenuBar);
        groupStatus.setSpacing(false);
        groupStatus.setAlignItems(Alignment.CENTER);
        return groupStatus;
    }

    // Поле вывода числа
    private TextField createNumberField() {
        TextField textField = new TextField();
        textField.setReadOnly(true);
        textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        textField.setWidth("30px");
        textField.setValue("0");
//        textField.addSelectionListener(event -> textField.setValue(     // Добавить функционал в грид
//            (double) (reportsGridLayout.getSelectedItems().size())
//        ));
        return textField;
    }

    private MenuBar createChangeBar() {
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
        MenuItem allReportsList = printSubMenu.addItem("Список отчетов комиссионера");
        allReportsList.addClickListener(event -> {

        });
        MenuItem receivedReportsList = printSubMenu.addItem("Список полученных отчетов");
        receivedReportsList.addClickListener(event -> {

        });
        MenuItem receivedReport = printSubMenu.addItem("Полученный отчет комиссионера");
        receivedReport.addClickListener(event -> {

        });
        MenuItem givenReportsList = printSubMenu.addItem("Список выданных отчетов");
        givenReportsList.addClickListener(event -> {

        });
        MenuItem givenReport = printSubMenu.addItem("Выданный отчет комиссионера");
        givenReport.addClickListener(event -> {

        });
        MenuItem configurePrint = printSubMenu.addItem("Настроить...");
        configurePrint.addClickListener(event -> {

        });

        return menuBar;
    }

    private Button createCog() {

        Icon settingsIcon = new Icon(VaadinIcon.COG);
        settingsIcon.setSize("22px");
        Button settingsButton = new Button(settingsIcon);

        settingsButton.addClickListener(event -> {

        });

        return settingsButton;
    }
}


