package com.warehouse_accounting.components.user.currency.forms;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.user.currency.CurrencySettingsView;
import com.warehouse_accounting.components.user.settings.SettingsView;
import com.warehouse_accounting.models.dto.CurrencyDto;
import com.warehouse_accounting.services.interfaces.CurrencyService;

@PageTitle("Добавить валюту")
@Route(value = "add_currencies", layout = SettingsView.class)
public class CurrencySettingsAddView extends VerticalLayout {

    private TextField charcodeField;
    private ComboBox<String> nameField;
    private TextField numcodeField;
    private TextField nominalField;
    private TextField valueField;
    private CurrencyService currencyService;

    public CurrencySettingsAddView(CurrencyService currencyService) {
        this.currencyService = currencyService;
        initForm();
    }

    private void initForm() {
        VerticalLayout layout = new VerticalLayout();

        charcodeField = new TextField("Короткое название");
        nameField = new ComboBox<>();
        nameField.setItems("Азербайджанский манат (AZN)", "Армянский драм (AMD)", "Белорусский рубль (BYN)", "Доллар США (USD)", "Евро (EUR)", "Казахстанский тенге (KZT)", "Китайский юань (CNY)", "Другая...");
        nameField.setLabel("Выберите валюту");
        numcodeField = new TextField("Цифровой код");
        nominalField = new TextField("Номинал");
        valueField = new TextField("Курс");

        FormLayout formLayout = new FormLayout(
                charcodeField,
                nameField,
                numcodeField,
                nominalField,
                valueField
        );

        Button create = new Button("Сохранить", buttonClickEvent -> {
            currencyService.create(
                    CurrencyDto.builder()
                            .charcode(charcodeField.getValue())
                            .name(nameField.getValue())
                            .numcode(numcodeField.getValue())
                            .nominal(Integer.parseInt(nominalField.getValue()))
                            .value(Double.parseDouble(valueField.getValue()))
                            .build()
            );
            UI.getCurrent().navigate(CurrencySettingsView.class);

        });
        create.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button cancel = new Button("Закрыть", buttonClickEvent -> UI.getCurrent().navigate(CurrencySettingsView.class));
        HorizontalLayout buttonLayout = new HorizontalLayout(create, cancel);

        HorizontalLayout groupForm = new HorizontalLayout();
        groupForm.add(formLayout);

        layout.add(buttonLayout, groupForm);
        add(layout);
    }
}
