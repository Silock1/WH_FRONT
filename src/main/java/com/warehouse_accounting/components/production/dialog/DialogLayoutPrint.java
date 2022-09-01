package com.warehouse_accounting.components.production.dialog;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.spring.annotation.SpringComponent;

@SpringComponent
public class DialogLayoutPrint {

    public VerticalLayout createDialogLayout(Dialog dialog) {
        H2 headline = new H2("Создание печатной формы");
        headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0")
                .set("font-size", "1.5em").set("font-weight", "bold");
        Select<String> selectForm = new Select<>("Открыть в браузере",
                "Скачать в формате EXEL", "Скачать в формате PDF");
        VerticalLayout fieldLayout = new VerticalLayout(selectForm);
        fieldLayout.setSpacing(false);
        fieldLayout.setPadding(false);
        fieldLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        Button cancelButton = new Button("Закрыть", e -> dialog.close());
        HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton);
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        VerticalLayout dialogLayout = new VerticalLayout(headline, fieldLayout, buttonLayout);
        dialogLayout.setPadding(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "350px").set("max-width", "100%");

        return dialogLayout;
    }
}
