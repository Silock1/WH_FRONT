package com.warehouse_accounting.components.goods;

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
import com.warehouse_accounting.components.goods.grids.InternalOrderGridLayout;
import org.springframework.stereotype.Component;

@UIScope
@Component
@Route(value = "internalOrderView", layout = AppView.class)
public class InternalOrderView extends VerticalLayout {

    private final InternalOrderGridLayout internalOrderGridLayout;

    private HorizontalLayout buttons;

    public InternalOrderView(InternalOrderGridLayout internalOrderGridLayout) {
        this.internalOrderGridLayout = internalOrderGridLayout;
        this.buttons = getGroupButton();
        this.internalOrderGridLayout.setParent(this);

        add(buttons, internalOrderGridLayout);
    }

    private HorizontalLayout getGroupButton() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setAlignItems(Alignment.CENTER);

        Button helpButton = new Button(new Image("icons/helpApp.svg", "helpApp"));
        helpButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_LARGE);
        helpButton.addClickListener(c -> Notification.show("Внутренние заказы позволяют планировать закупки " +
                "у поставщиков и перемещения товаров по складам внутри организации.\n" +
                "С их помощью можно пополнять резервы при достижении неснижаемого остатка.\n\nЧитать инструкции:" +
                "\n\nПополнение до снижаемого остатка" +
                "\n\nРезервы и ожидания", 5000, Notification.Position.TOP_START));

        Span text = new Span("Внутренние заказы");
        text.setClassName("pageTitle");

        Icon refresh = new Icon(VaadinIcon.REFRESH);
        refresh.setColor("silver");
        Button refreshButton = new Button(refresh);
        refreshButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        refreshButton.addClickListener(click -> {
            internalOrderGridLayout.refreshDate();
        });

        Image image = new Image("icons/plus.png", "Plus");
        image.setWidth("15px");
        Button internalOrder = new Button("Заказ", image);
        internalOrder.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

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

        NumberField numberField = new NumberField();
        internalOrderGridLayout.getInternalOrderDtoGrid().addSelectionListener(event ->
                numberField.setValue((double) (internalOrderGridLayout.getInternalOrderDtoGrid().getSelectedItems().size())));
        numberField.setValue(0d);
        numberField.setWidth("40px");

        menuBar.addItem(numberField);

        MenuItem change = menuBar.addItem("Изменить");
        change.add(new Icon(VaadinIcon.CARET_DOWN));
        MenuItem status = menuBar.addItem("Статус");
        status.add(new Icon(VaadinIcon.CARET_DOWN));
        MenuItem create = menuBar.addItem("Создать");
        create.add(new Icon(VaadinIcon.CARET_DOWN));
        MenuItem print = menuBar.addItem(new Icon(VaadinIcon.PRINT));
        print.add("Печать");
        print.add(new Icon(VaadinIcon.CARET_DOWN));

        SubMenu editSubMenu = change.getSubMenu();
        MenuItem delete = editSubMenu.addItem("Удалить");
        delete.addClickListener(event -> {

        });
        MenuItem copy = editSubMenu.addItem("Копировать");
        copy.addClickListener(event -> {

        });
        MenuItem massEdit = editSubMenu.addItem("Массовое редактирование");
        massEdit.addClickListener(event -> {

        });
        ;
        MenuItem operate = editSubMenu.addItem("Провести");
        operate.addClickListener(event -> {

        });
        MenuItem undoOperate = editSubMenu.addItem("Снять проведение");
        undoOperate.addClickListener(event -> {

        });
        MenuItem join = editSubMenu.addItem("Объединить");
        join.addClickListener(event -> {

        });

        SubMenu statusSubMenu = status.getSubMenu();
        MenuItem configureStatus = statusSubMenu.addItem("Настроить...");
        configureStatus.addClickListener(event -> {

        });

        SubMenu createSubMenu = create.getSubMenu();
        MenuItem orderToSuppliers = createSubMenu.addItem("Заказ поставщикам");
        orderToSuppliers.addClickListener(event -> {

        });
        MenuItem orderToSuppliersAvailable = createSubMenu.addItem("Заказ поставщикам (с учетом «доступно»)");
        orderToSuppliersAvailable.addClickListener(event -> {

        });

        SubMenu printSubMenu = print.getSubMenu();
        MenuItem ordersList = printSubMenu.addItem("Список заказов");
        ordersList.addClickListener(event -> {

        });
        MenuItem internalOrd = printSubMenu.addItem("Внутренний заказ");
        internalOrd.addClickListener(event -> {

        });
        MenuItem kit = printSubMenu.addItem("Комплект...");
        kit.addClickListener(event -> {

        });
        MenuItem configurePrint = printSubMenu.addItem("Настроить...");
        configurePrint.addClickListener(event -> {

        });

        Button setting = new Button(new Icon(VaadinIcon.COG));
        setting.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        setting.addClickListener(event -> {

        });

        horizontalLayout.add(helpButton, text, refreshButton, internalOrder, filter, textField, menuBar, setting);

        return horizontalLayout;
    }
}
