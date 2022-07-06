package com.warehouse_accounting.components.sales.forms.order.components;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;

import java.util.List;
import java.util.Optional;

public class DocumentMenu extends MenuBar {
    // Это меню перемещает пользователя по в нужный пункт главного меню.
    // Т.е. это вторичное главное менню.
    // Создать документ:
    // Если выбрать пункт этогго меню, то перемещаемся на новую страницу, но прямо в форму заполнения.
    // Если закрыть там новую форму, то возвращаемся на старую (старая просто скрывается?)
    // начальная точка Продажи: Заказы покупателей: Заказ:
    // "Счёт покупателю" -- ( Продажи -- Счета покупателям -- Заказ )
    // "Перемещение" -- ( Товары -- Перемещение )
    // "Счёт покупателю" -- ( Продажи -- Счета покупателям -- Счёт )
    // "Отгрузка" -- ( Продажи -- Отгрузки -- Отгрузка )
    // "Входящий платёж" -- ( Деньги -- Платежи -- Приход -- Входящий платёж )
    // "Приходный ордер" -- ( Деньги -- Платежи -- Приход -- Приходный ордер )
    // "Предоплата" - неактивен, возможно временно
    // "Заказ поставщику" -- ( Закупки -- Заказы поставщикам -- Заказ )
    // "Заказ поставщику (с учётом доступно)" -- ( Закупки -- Заказы поставщикам -- Заказ )
    // "Розничная продажа" -- ( Розница -- Продажи -- ? )
    // "Сбор заказа" - неактивен, возможно временно

    DocumentMenu() {
        super();
        Icon iconDown = new Icon(VaadinIcon.CARET_DOWN);
        Icon iconPrint = new Icon(VaadinIcon.PRINT);
        Icon iconMail = new Icon(VaadinIcon.ENVELOPE_O);
        List<List<Object>> operations = List.of(
                List.of(Optional.<Icon>empty(), "Изменить", (ComponentEventListener<ClickEvent<MenuItem>>) event -> {
                    Notification.show("Not Implemented");
                }),
                List.of(Optional.<Icon>empty(), "Создать документ", (ComponentEventListener<ClickEvent<MenuItem>>) event -> {
                    Notification.show("Not Implemented");
                }),
                List.of(Optional.of(iconPrint), "Печать", (ComponentEventListener<ClickEvent<MenuItem>>) event -> {
                    Notification.show("Not Implemented");
                }),
                List.of(Optional.of(iconMail), "Отправить", (ComponentEventListener<ClickEvent<MenuItem>>) event -> {
                    Notification.show("Not Implemented");
                })
        );

        operations.forEach(op -> {
            MenuItem element;
            if(((Optional)op.get(0)).isPresent()) {
                element = addItem(((Optional<Icon>) op.get(0)).get());
                element.add((String) op.get(1));
            } else {
                element = addItem((String)op.get(1));
            }
            element.add(iconDown);
            element.addClickListener((ComponentEventListener<ClickEvent<MenuItem>>) op.get(2));
        });
    }
}
