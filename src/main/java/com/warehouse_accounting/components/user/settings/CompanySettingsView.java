package com.warehouse_accounting.components.user.settings;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Настройки компании")
@Route(value = "companysettings", layout = SettingsView.class)
public class CompanySettingsView extends VerticalLayout {

    public CompanySettingsView() {

        VerticalLayout verticalLayout = new VerticalLayout();
        HorizontalLayout oneHorizontalLayout = new HorizontalLayout();

        Span title = new Span("Настройки компании");
        Span numDocument = new Span("Нумеровать документы");
        Span backResult = new Span("Адрес для ответа при отправке документов по почте");

        Button helpButton = new Button(new Image("icons/helpApp.svg", "helpApp"));
        helpButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_LARGE);
        helpButton.addClickListener(c -> Notification.show("Отправка документов по почте", 3000, Notification.Position.MIDDLE));

        Button buttonSave = new Button("Сохранить");
        buttonSave.addThemeVariants(ButtonVariant.LUMO_LARGE);
        Button editButton = new Button("Изменения:");

        oneHorizontalLayout.add(backResult, helpButton);
        add(oneHorizontalLayout);

        verticalLayout.add(title, numDocument);
        add(verticalLayout);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(buttonSave, editButton);
        add(horizontalLayout);

        EmailField emailField = new EmailField();
        emailField.setLabel("Email address");
        emailField.getElement().setAttribute("name", "email");
        emailField.setPlaceholder("username@example.com");
        emailField.setErrorMessage("Please enter a valid example.com email address");
        emailField.setClearButtonVisible(true);
        emailField.setPattern("^.+@example\\.com$");

        ListBox<String> listBox = new ListBox<>();
        listBox.setItems("Внутри календарного года", "По порядку за всю историю");
        listBox.setValue("Внутри календарного года");

        ListBox<String> listBackResult = new ListBox<>();
        listBackResult.setItems("Адрес сотрудника, который отправляет документ", "Другой, укажите в форме");
        listBackResult.setValue("Адрес сотрудника, который отправляет документ");

        MultiSelectListBox<String> multiListSelector = new MultiSelectListBox<>();
        multiListSelector.setItems(
                "Запретить отгрузку товаров, которых нет на складе","Автоматически устанавливать минимальную цену",
                "Перемещать удаленные документы на 7 дней в корзину");
        multiListSelector.select("Перемещать удаленные документы на 7 дней в корзину");

        add(verticalLayout, listBox, oneHorizontalLayout, listBackResult, emailField, multiListSelector);



    }
}
