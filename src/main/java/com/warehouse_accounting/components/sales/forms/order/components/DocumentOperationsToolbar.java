package com.warehouse_accounting.components.sales.forms.order.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.menubar.MenuBar;
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
        MenuBar menu = new DocumentMenu();

        List.of(saveButton, closeButton, menu).forEach(x -> add(x));
    }

    public void setSaveHandler(DocumentSaveHandler saveHandler) {
        saveButton.addClickListener(event -> saveHandler.save());
    }

    public void setCloseHandler(DocumentCloseHandler closeHandler) {
        closeButton.addClickListener(event -> closeHandler.close());
    }
}