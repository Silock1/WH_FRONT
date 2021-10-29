package com.warehouse_accounting.components.user;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.user.settings.SettingsView;
import com.warehouse_accounting.models.dto.CountryDto;
import com.warehouse_accounting.services.interfaces.CountryService;

import java.util.Optional;

@PageTitle("Добавить страну")
@Route(value = "addCountry", layout = SettingsView.class)
public class SettingCountryAddView extends VerticalLayout {

    private final CountryService countryService;
    private final Grid<CountryDto> grid = new Grid<>();

    public SettingCountryAddView(CountryService countryService) {
        this.countryService = countryService;
        AddForm();

    }

    private  void  AddForm(){

        TextField nameField = new TextField("Краткое наименование");
        TextField fullNameField = new TextField("Полное наименование");
        TextField codeField = new TextField("Цифровой код");
        TextField codeOneField = new TextField("Буквенный код(2)" );
        TextField codeTwoField = new TextField("Буквенный код(3)");
        FormLayout formLayout = new FormLayout(
                nameField,
                fullNameField,
                codeField,
                codeOneField,
                codeTwoField);

        Button create = new Button("Сохранить");
        create.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button cancel = new Button("Закрыть");
        cancel.addClickListener(buttonClickEvent -> getUI().ifPresent(ui -> ui.navigate("country")));
        HorizontalLayout buttonLayout = new HorizontalLayout(create, cancel);
        add(formLayout,buttonLayout);
    }
}
