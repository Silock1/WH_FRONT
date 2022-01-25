package com.warehouse_accounting.components.user.settings;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.History;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Настройки компании")
@Route(value = "companysettings", layout = SettingsView.class)
public class CompanySettingsView extends VerticalLayout {

    public CompanySettingsView() {

        VerticalLayout verticalLayout = new VerticalLayout();
        HorizontalLayout oneHorizontalLayout = new HorizontalLayout();
        VerticalLayout oneVerticalLayout = new VerticalLayout();

        Span title = new Span("Настройки компании");

        Button helpButton = new Button(new Image("icons/helpApp.svg", "helpApp"));
        helpButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_LARGE);
        helpButton.addClickListener(c -> Notification.show("Отправка документов по почте", 3000, Notification.Position.MIDDLE));

        Button buttonSave = new Button("Сохранить");
        Button editButton = new Button("Изменения:");
        editButton.addClickListener(buttonClickEvent -> {Notification.show(
                "Реализовать окно справа как будет готова история изменения", 3000, Notification.Position.MIDDLE);
        });

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


        RadioButtonGroup<String> radioButtonGroup = new RadioButtonGroup<>();
        radioButtonGroup.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        radioButtonGroup.setLabel("Адрес для ответа при отправке документов по почте");
        radioButtonGroup.setItems("Адрес сотрудника, который отправляет документ", "Другой, укажите в форме ниже");
        radioButtonGroup.setValue("Адрес сотрудника, который отправляет документ");



        RadioButtonGroup<String> radioButton = new RadioButtonGroup<>();
        radioButton.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        radioButton.setLabel("Нумеровать документы");
        radioButton.setItems("Внутри календарного года", "По порядку за всю историю");
        radioButton.setValue("Внутри календарного года");

        MultiSelectListBox<String> multiListSelector = new MultiSelectListBox<>();
        multiListSelector.setItems(
                "Запретить отгрузку товаров, которых нет на складе","Автоматически устанавливать минимальную цену",
                "Перемещать удаленные документы на 7 дней в корзину");
        multiListSelector.select("Перемещать удаленные документы на 7 дней в корзину");

        verticalLayout.add(title, radioButton);
        oneHorizontalLayout.add(radioButtonGroup, helpButton);
        oneVerticalLayout.add(oneHorizontalLayout, emailField, multiListSelector);

        add(verticalLayout, oneVerticalLayout);

    }
}
