package com.warehouse_accounting.components.indiCators;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.AppView;
import com.warehouse_accounting.components.indiCators.grids.DocumentsGrid;
import com.warehouse_accounting.models.dto.DocumentDto;
import com.warehouse_accounting.services.interfaces.DocumentService;
import org.springframework.stereotype.Component;

@UIScope
@Component
@Route(value = "documentsView", layout = AppView.class)
public class DocumentsView extends VerticalLayout {

    private final DocumentsGrid documentsGrid;

    private final DocumentService<DocumentDto> documentService;
    private HorizontalLayout buttons;

    public DocumentsView(DocumentsGrid documentsGrid, DocumentService<DocumentDto> documentService) {
        this.documentsGrid = documentsGrid;
        this.documentService = documentService;
        this.buttons = getGroupButton();
        this.documentsGrid.setParent(this);

        add(buttons, documentsGrid);
    }

    private HorizontalLayout getGroupButton() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setAlignItems(Alignment.CENTER);

        String notification = "В разделе представлены все документы, созданные за всё время.\n" +
                "Здесь удобно искать конкретные документы с помощью фильтров,\n" +
                "можно создавать и удалять документы, печатать списки\n" +
                "документов.\n\n\n\n" +
                "Читать инструкцию: Документы";
        Button helpButton = new Button(new Image("icons/helpApp.svg", "helpApp"));
        helpButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_LARGE);
        helpButton.addClickListener(buttonClickEvent -> Notification.show(notification, 5000, Notification.Position.TOP_START));

        Span text = new Span("Документы");
        text.setClassName("pageTitle");

        Icon refresh = new Icon(VaadinIcon.REFRESH);
        refresh.setColor("silver");
        Button refreshButton = new Button(refresh);
        refreshButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        refreshButton.addClickListener(click -> {
            documentsGrid.refreshDate();
        });

        MenuBar documents = this.createNewDocumentMenu(); //не реализовна логика
        Button filter = new Button("Фильтр");
        filter.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        filter.addClickListener(e -> {
            //фильтр пока не реализован
        });

        TextField textField = new TextField();
        textField.setPlaceholder("Номер или комментарий");
        textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        textField.setWidth("170px");

        MenuBar menuBar = new MenuBar();

        MenuItem change = menuBar.addItem("Изменить");
        change.add(new Icon(VaadinIcon.CARET_DOWN));
        change.setEnabled(false);

        NumberField numberField = new NumberField();
        documentsGrid.getDocumentDtoGrid().addSelectionListener(event -> {
            double val = documentsGrid.getDocumentDtoGrid().getSelectedItems().size();
            numberField.setValue(val);
            if (val == 0) {
                change.setEnabled(false);
            } else {
                change.setEnabled(true);
            }
        });
        numberField.setValue(0d);
        numberField.setWidth("40px");
        menuBar.addItem(numberField);

        MenuItem print = menuBar.addItem(new Icon(VaadinIcon.PRINT));
        print.add("Печать");
        print.add(new Icon(VaadinIcon.CARET_DOWN));

        SubMenu editSubMenu = change.getSubMenu();
        MenuItem delete = editSubMenu.addItem("Удалить");
        delete.addClickListener(event -> {
            documentsGrid.getDocumentDtoGrid().getSelectedItems().forEach(selected -> documentService.deleteById(selected.getId()));
            documentsGrid.getDocumentDtoGrid().setItems(documentService.getAll());
        });

        SubMenu printSubMenu = print.getSubMenu();
        MenuItem ordersList = printSubMenu.addItem("Список документов");
        ordersList.addClickListener(event -> {

        });

        MenuItem configurePrint = printSubMenu.addItem("Настроить...");
        configurePrint.addClickListener(event -> {

        });


        horizontalLayout.add(helpButton, text, refreshButton, documents, filter, textField, menuBar);
        return horizontalLayout;
    }

    private MenuBar createNewDocumentMenu() {

        Image image = new Image("icons/plus.png", "Plus");
        image.setWidth("15px");
        image.getStyle().set("width", "var(--lumo-icon-size-s)")
                .set("height", "var(--lumo-icon-size-s)")
                .set("marginRight", "var(--lumo-space-s)");
        MenuBar documents = new MenuBar();
        MenuItem item = documents.addItem(image);
        item.add("Документ");
        SubMenu docs = item.getSubMenu();

        MenuItem headerSales = docs.addItem("Продажи");
        this.styleForCategory(headerSales);
        MenuItem customerOrder = docs.addItem("Заказы покупателей", event -> {});
        MenuItem customerInvoices = docs.addItem("Счета покупателям");
        MenuItem shipments = docs.addItem("Отгрузка");

        MenuItem headerPurchases = docs.addItem("Закупки");
        this.styleForCategory(headerPurchases);
        MenuItem purchasesOrders = docs.addItem("Заказы поставщикам");
        MenuItem accountsPayable = docs.addItem("Счета поставщиков");
        MenuItem acceptances = docs.addItem("Приемки");

        MenuItem headerMoney = docs.addItem("Деньги");
        this.styleForCategory(headerMoney);
        MenuItem paymentsIn = docs.addItem("Входящие платежи");
        MenuItem paymentsInOrders = docs.addItem("Приходные ордеры");
        MenuItem paymentsOut = docs.addItem("Исходящие платежи");
        MenuItem paymentsOutOrders = docs.addItem("Расходные ордеры");

        MenuItem headerGoods = docs.addItem("Склад");
        this.styleForCategory(headerGoods);
        MenuItem internalOrders = docs.addItem("Внутренние заказы");
        MenuItem movements = docs.addItem("Перемещения");
        MenuItem inventory = docs.addItem("Инвентаризация");
        MenuItem posting = docs.addItem("Оприходования");
        MenuItem writeOffs = docs.addItem("Списания");

        MenuItem headerProduction = docs.addItem("Производство");
        this.styleForCategory(headerProduction);
        MenuItem technologicalOperation = docs.addItem("Технологическая операция");
        MenuItem productionOrder = docs.addItem("Заказ на производство");
        MenuItem productionTask = docs.addItem("Производственное задание");

        return documents;
    }

    private void styleForCategory(MenuItem header) {
        header.getElement()
                .getStyle()
                .set("background-color", "lightgray")
                .set("font-weight", "bold")
                .set("font-size", "12px")
                .set("margin", "0px")
                .set("padding", "0px");
    }
}
