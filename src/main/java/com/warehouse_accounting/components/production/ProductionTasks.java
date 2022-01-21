package com.warehouse_accounting.components.production;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.production.grids.ProductionTasksFilterLayout;

import javax.net.ssl.HostnameVerifier;
import java.awt.*;


@SpringComponent
@UIScope
public class ProductionTasks extends VerticalLayout {

    private final ProductionTasksFilterLayout productionTasksFilterLayout;

    public ProductionTasks(ProductionTasksFilterLayout productionTasksFilterLayout) {
        this.productionTasksFilterLayout = productionTasksFilterLayout;
        add(getGroupButton(), productionTasksFilterLayout);
    }

    private HorizontalLayout getGroupButton() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Button helpButton = new Button(new Image("icons/helpApp.svg", "helpApp"));
        helpButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_LARGE);
        helpButton.addClickListener(c -> Notification.show("Производственное задание позволяет выстроить процесс изготовления" +
                        "продукции по техкартам и рассчитать расходы. Тут же можно отмечать выполнение производственных этапов" +
                        "и контролировать результаты."+
                "\n" +
                "Читать инструкцию: Расширенный учет производственных операций" + "Видео: Расширенный способ", 3000, Notification.Position.MIDDLE));

        Text productionTasks = new Text("Производственные задания");
        Icon refresh = new Icon(VaadinIcon.REFRESH);
        refresh.setColor("silver");
        Button refreshButton = new Button(refresh);
        refreshButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        refreshButton.addClickListener(click -> {
            System.out.println("перезагрузка");
        });
        Image image = new Image("icons/plus.png", "Plus");
        image.setWidth("15px");
        Button exercise = new Button("Задание", image);
        exercise.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        Button filter = new Button("Фильтр");
        filter.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        filter.addClickListener(e -> {
            if (productionTasksFilterLayout.isVisible()) {
                productionTasksFilterLayout.setVisible(false);
            } else {
                productionTasksFilterLayout.setVisible(true);
            }
        });

        TextField textField = new TextField();
        textField.setPlaceholder("Номер и комментарий");
        textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        textField.setWidth("210px");

        MenuBar menuBar = new MenuBar(); // Создаем меню бар
        menuBar.addThemeVariants(MenuBarVariant.LUMO_ICON, MenuBarVariant.LUMO_CONTRAST);

        TextField count = new TextField();
        count.setValue("0");
        count.setWidth("20px");
        count.addThemeVariants(TextFieldVariant.LUMO_SMALL);

        menuBar.addItem(count); // Добавим поле с кол-вом

        Icon caretDownPrint = new Icon(VaadinIcon.CARET_DOWN);
        caretDownPrint.setSize("13px");
        Icon caretDownStatus = new Icon(VaadinIcon.CARET_DOWN);
        caretDownStatus.setSize("13px");
        Icon caretDownEdit = new Icon(VaadinIcon.CARET_DOWN);
        caretDownEdit.setSize("13px");

        HorizontalLayout editVision = new HorizontalLayout(new Text("Изменить"), caretDownEdit);
        editVision.setSpacing(false);

        MenuItem edit = menuBar.addItem(editVision);
        SubMenu editSubMenu = edit.getSubMenu();
        editSubMenu.addItem("Удалить").onEnabledStateChanged(false);
        editSubMenu.addItem("Копировать").onEnabledStateChanged(false);
        editSubMenu.addItem("Массовое редактирование");
        editSubMenu.addItem("Провести").onEnabledStateChanged(false);
        editSubMenu.addItem("Снять проведение").onEnabledStateChanged(false);

        HorizontalLayout statusVision = new HorizontalLayout(new Text("Статус"), caretDownStatus);
        statusVision.setSpacing(false);

        MenuItem status = menuBar.addItem(statusVision);
        SubMenu statusSubMenu = status.getSubMenu();
        statusSubMenu.addItem("Настроить");

        Icon printIcon = new Icon(VaadinIcon.PRINT);
        printIcon.setSize("13px");
        HorizontalLayout printVision = new HorizontalLayout(new Text("Печать"), printIcon, caretDownPrint);
        statusVision.setSpacing(false);
        MenuItem print = menuBar.addItem(printVision);
        SubMenu printSubMenu = print.getSubMenu();
        printSubMenu.addItem("Список производственных заданий");
        printSubMenu.addItem("Производственное задание").onEnabledStateChanged(false);
        printSubMenu.addItem("Комплект").onEnabledStateChanged(false);
        printSubMenu.addItem("Настроить");


        horizontalLayout.add(helpButton, productionTasks, refreshButton, exercise, filter, textField, menuBar
                );

        return horizontalLayout;
    }


}
