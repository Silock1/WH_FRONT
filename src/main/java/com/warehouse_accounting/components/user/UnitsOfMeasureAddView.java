package com.warehouse_accounting.components.user;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.user.settings.SettingsView;
import com.warehouse_accounting.models.dto.UnitsOfMeasureDto;
import com.warehouse_accounting.services.interfaces.UnitsOfMeasureService;

import java.awt.*;

@PageTitle("Добавить еденицу измерений")
@Route(value = "add", layout = SettingsView.class)
public class UnitsOfMeasureAddView extends VerticalLayout {

    private final UnitsOfMeasureService uomService;
    private final Grid<UnitsOfMeasureDto> grid = new Grid<>();



    public UnitsOfMeasureAddView(UnitsOfMeasureService uomService) {
        this.uomService = uomService;

        AddForm();
    }



    private  void  AddForm(){

        TextField typeField = new TextField("Тип", "Системный", "");
        TextField nameField = new TextField("Краткое наименование", "Блок", "");
        TextField fullNameField = new TextField("Полное наименование","Блок сигарет");
        TextField codeField = new TextField("Цифровой код", "322");
        FormLayout formLayout = new FormLayout(typeField,
                                                nameField,
                                                fullNameField,
                                                codeField);

        Button create = new Button("Сохранить");
        create.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button cancel = new Button("Закрыть");
        HorizontalLayout buttonLayout = new HorizontalLayout(create, cancel);
        add(formLayout,buttonLayout);
    }
}
