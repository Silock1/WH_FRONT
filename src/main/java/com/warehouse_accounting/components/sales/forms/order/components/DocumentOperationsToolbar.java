package com.warehouse_accounting.components.sales.forms.order.components;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.warehouse_accounting.components.sales.forms.order.types.DocumentCloseHandler;
import com.warehouse_accounting.components.sales.forms.order.types.DocumentSaveHandler;

import java.util.List;

// todo: Это же меню появляется сверху страницы при скроле вниз
@CssImport("./css/document_operation_toolbar.css")
public class DocumentOperationsToolbar extends HorizontalLayout {
    Button closeButton = new Button("Закрыть");
    Button saveButton = new Button("Сохранить");

    public DocumentOperationsToolbar() {
        DocumentMenu menu = new DocumentMenu();

        menu.addCommonSubMenu("Изменить").addSubMenuItem("Пусто", (ComponentEventListener<ClickEvent<MenuItem>>)
                event -> { Notification.show("Not Implemented"); })
                .addSubMenuItem("Массовое редактирование", (ComponentEventListener<ClickEvent<MenuItem>>)
                        event -> { Notification.show("Not Implemented"); })
                .addSubMenuItem("Переместить", (ComponentEventListener<ClickEvent<MenuItem>>)
                        event -> { Notification.show("Not Implemented"); })
                .addSubMenuItem("Поместить в архив", (ComponentEventListener<ClickEvent<MenuItem>>)
                        event -> { Notification.show("Not Implemented"); })
                .addSubMenuItem("Извлечь из архива", (ComponentEventListener<ClickEvent<MenuItem>>)
                        event -> { Notification.show("Not Implemented"); })
                .addSubMenuItem("Цены...", (ComponentEventListener<ClickEvent<MenuItem>>)
                        event -> { Notification.show("Not Implemented"); });

        menu.addCommonSubMenu("Создать").addSubMenuItem("Создать документ",
                (ComponentEventListener<ClickEvent<MenuItem>>) event -> { Notification.show("Not Implemented"); });

        menu.addPrintSubMenu().addSubMenuItem("Ценник (70x49,5мм)",
                (ComponentEventListener<ClickEvent<MenuItem>>) event -> { Notification.show("Not Implemented"); })
                .addSubMenuItem("Термоэтикетка (58х40мм)",
                        (ComponentEventListener<ClickEvent<MenuItem>>) event -> { Notification.show("Not Implemented"); })
                .addSubMenuItem("Настроить...",
                        (ComponentEventListener<ClickEvent<MenuItem>>) event -> { Notification.show("Not Implemented"); });

        menu.addSendSubMenu().addSubMenuItem("Разбежался",
                (ComponentEventListener<ClickEvent<MenuItem>>) event -> { Notification.show("Сам донесёшь"); });

        List.of(saveButton, closeButton, menu).forEach(x -> add(x));
    }

    public DocumentOperationsToolbar setSaveHandler(DocumentSaveHandler saveHandler) {
        saveButton.addClickListener(event -> saveHandler.save());
        return this;
    }

    public DocumentOperationsToolbar setCloseHandler(DocumentCloseHandler closeHandler) {
        closeButton.addClickListener(event -> closeHandler.close());
        return this;
    }
}