package com.warehouse_accounting.components.user;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.user.settings.SettingsView;
import com.warehouse_accounting.models.dto.ConnectorSettingsDto;

@PageTitle("Экспорт")
@Route(value = "export", layout = SettingsView.class)
public class ExportView extends VerticalLayout {

    public ExportView() {
        add(getGroupButtons(), createGrid());
    }

    Button select = new Button("Экспортировать");

    private HorizontalLayout getGroupButtons(){
        var horizontalLayout = new HorizontalLayout();

        Button helpButton = new Button(new Icon(VaadinIcon.QUESTION_CIRCLE));
        helpButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
        helpButton.addClickListener(buttonClickEvent -> Notification.show("" +
                "В разделе отображаются результаты всех операций по экспорту товаров, контрагентов и дополнительных справочников, а также результаты действий с ЭДО.\n" +
                "\n" +
                "Экспорт запускается из разделов Товары → Товары и услуги, Контрагенты → Контрагенты.\n" +
                "\n" +
                "Читать инструкции:\n" +
                "\n" +
                "Импорт и экспорт\n" +
                "Экспорт контрагентов\n" +
                "Импорт и экспорт справочников", 5000, Notification.Position.TOP_START));

        Span employee = new Span("Экспорт");
        employee.getElement().getThemeList().add("badge contrast");

        Button delete = new Button("Удалить завершенные задачи");
        delete.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        Span tasks = new Span("Задачи старше 10 дней удаляются автоматически");
        tasks.getElement().getThemeList().add("badge contrast");

        select.addClickListener(buttonClickEvent -> addExport());

        horizontalLayout.add(helpButton, employee, select, delete, tasks);
        return horizontalLayout;
    }
    private Grid<ConnectorSettingsDto> createGrid() {

        Grid<ConnectorSettingsDto> grid = new Grid<>(ConnectorSettingsDto.class, false);
        grid.addColumn(ConnectorSettingsDto::getShops).setHeader("Задача");
        grid.addColumn(ConnectorSettingsDto::getType).setHeader("Создана");
        grid.addColumn(ConnectorSettingsDto::getOrders).setHeader("Завершена");
        grid.addColumn(ConnectorSettingsDto::getLeftovers).setHeader("Статус");

        return grid;
    }

    private void addExport(){
        Dialog dialog = new Dialog();
        dialog.getElement().setAttribute("aria-label", "addExport");

        VerticalLayout dialogLayout = createDialogLayout(dialog);
        dialog.add(dialogLayout);
        select.addClickListener(buttonClickEvent -> dialog.open());
        add(dialog);
    }

    private static VerticalLayout createDialogLayout(Dialog dialog) {
        H2 headline = new H2("Экспортировать");
        headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0")
                .set("font-size", "1.5em").set("font-weight", "bold");

        Button cancelButton = new Button("Отменить", e -> dialog.close());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button a = new Button("Товары (Excel, старый)");
        a.addClickListener(buttonClickEvent -> Notification.show("" +
                "Тарифный план вашей компании не позволяет экспортировать данные", 5000, Notification.Position.MIDDLE));
        Button b = new Button("Товары (Excel)");
        b.addClickListener(buttonClickEvent -> Notification.show("" +
                "Тарифный план вашей компании не позволяет экспортировать данные", 5000, Notification.Position.MIDDLE));
        Button c = new Button("Контрагентов (Excel)");
        c.addClickListener(buttonClickEvent -> Notification.show("" +
                "Тарифный план вашей компании не позволяет экспортировать данные", 5000, Notification.Position.MIDDLE));
        Button d = new Button("1C: Бухгалтерия");
        d.addClickListener(buttonClickEvent -> Notification.show("" +
                "Тарифный план вашей компании не позволяет экспортировать данные", 5000, Notification.Position.MIDDLE));

        VerticalLayout buttonLayout = new VerticalLayout(a, b, c, d, cancelButton);
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        VerticalLayout dialogLayout = new VerticalLayout(headline, buttonLayout);
        dialogLayout.setPadding(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "300px").set("max-width", "100%");

        return dialogLayout;
    }

}
