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

import com.warehouse_accounting.models.dto.SalesChannelDto;
import com.warehouse_accounting.services.interfaces.SalesChannelsService;


import java.awt.*;

@PageTitle("Добавить еденицу измерений")
@Route(value = "add_channel", layout = SettingsView.class)
public class SalesChannelsAddView extends VerticalLayout {

    private final SalesChannelsService salesChannelsService;
    private final Grid<SalesChannelDto> grid = new Grid<>();



    public SalesChannelsAddView(SalesChannelsService salesChannelsService) {
        this.salesChannelsService = salesChannelsService;
        AddForm();
    }

    private  void  AddForm(){

        TextField nameField = new TextField("Наименование");
        TextField typeField = new TextField("Тип");
        TextField descrField = new TextField("Описание");
        TextField accessField = new TextField("Общий доступ");
        TextField ownerDepartmentField = new TextField("Владелец-отдел");
        TextField ownerEmployeeField = new TextField("Владелец-сотрудник");
        TextField whenChangedField = new TextField("Когда изменён");
        TextField whoChangedField = new TextField("Кто изменил");
        FormLayout formLayout = new FormLayout(
            nameField,
            typeField,
            descrField,
            accessField,
            ownerDepartmentField,
            ownerEmployeeField,
            whenChangedField,
            whoChangedField
        );

        Button create = new Button("Сохранить");
        create.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button cancel = new Button("Закрыть");

        HorizontalLayout buttonLayout = new HorizontalLayout(
            create,
            cancel
        );
        add(formLayout,buttonLayout);
    }
}
