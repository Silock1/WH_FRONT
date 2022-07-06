package com.warehouse_accounting.components.sales.forms.order.components;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;

// Это меню перемещает пользователя в нужный пункт главного меню.
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

public class DocumentMenu extends MenuBar {
    private final Icon caretDownIcon = new Icon(VaadinIcon.CARET_DOWN);

    public DocumentSubMenu addCommonSubMenu(String title) {
        return addSubMenu(null, title, null);
    }

    public DocumentSubMenu addCommonSubMenu(String title, ComponentEventListener<ClickEvent<MenuItem>> handler) {
        return addSubMenu(null, title, handler);
    }

    public DocumentSubMenu addCommonSubMenu(Component control, ComponentEventListener<ClickEvent<MenuItem>> handler) {
        return addSubMenu(control, null,  handler);
    }

    public DocumentSubMenu addCommonSubMenu(Component control, String title, ComponentEventListener<ClickEvent<MenuItem>> handler) {
        return addSubMenu(control, title, handler);
    }


    public DocumentSubMenu addPrintSubMenu() {
        return addSubMenu(new Icon(VaadinIcon.PRINT), "Печать", null);
    }
    public DocumentSubMenu addPrintSubMenu(ComponentEventListener<ClickEvent<MenuItem>> handler) {
        return addSubMenu(new Icon(VaadinIcon.PRINT), "Печать", handler);
    }

    public DocumentSubMenu addSendSubMenu() {
        return addSubMenu(new Icon(VaadinIcon.ENVELOPE_O), "Отправить", null);
    }

    public DocumentSubMenu addSendSubMenu(ComponentEventListener<ClickEvent<MenuItem>> handler) {
        return addSubMenu(new Icon(VaadinIcon.ENVELOPE_O), "Отправить", handler);
    }

    private DocumentSubMenu addSubMenu(Component prefix, String title,
                                       ComponentEventListener<ClickEvent<MenuItem>> handler) {
        MenuItem subMenu;
        if(prefix != null) {
            subMenu = addItem(prefix);
            if(title != null) { subMenu.add(title); }
        } else {
            subMenu = addItem(title);
        }
        subMenu.add(caretDownIcon);
        if(handler != null) { subMenu.addClickListener(handler); }
        return new DocumentSubMenu(subMenu);
    }
}
