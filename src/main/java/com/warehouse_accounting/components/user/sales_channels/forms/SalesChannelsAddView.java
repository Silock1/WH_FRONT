package com.warehouse_accounting.components.user.sales_channels.forms;

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
import com.warehouse_accounting.components.user.sales_channels.SalesChannelsList;
import com.warehouse_accounting.components.user.settings.SettingsView;
import com.warehouse_accounting.models.dto.SalesChannelDto;
import com.warehouse_accounting.services.interfaces.SalesChannelsService;

@PageTitle("Добавить канал продаж")
@Route(value = "add_channel", layout = SettingsView.class)
public class SalesChannelsAddView extends VerticalLayout {

    private TextField nameField;
    private ComboBox<String> typeField;
    private TextField descrField;
    private SalesChannelsService salesChannelsService;


    public SalesChannelsAddView(SalesChannelsService salesChannelsService) {
        this.salesChannelsService = salesChannelsService;

        initForm();
    }

    private  void initForm() {
        VerticalLayout layout = new VerticalLayout();

        nameField = new TextField("Наименование");

        typeField = new ComboBox<>();
        typeField.setItems("Тип", "Выслано предложение","Переговоры", "Сделка заключена", "Сделка не заключена");
        typeField.setLabel("Тип");

        descrField = new TextField("Описание");

        FormLayout formLayout = new FormLayout(
                nameField,
                typeField,
                descrField
        );

        Button create = new Button("Сохранить", buttonClickEvent -> {
            salesChannelsService.create(
                    SalesChannelDto.builder()
                            .type(typeField.getValue())
                            .name(nameField.getValue())
                            .description(descrField.getValue())
                            .build()
            );
            UI.getCurrent().navigate(SalesChannelsList.class);

        });
        create.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button cancel = new Button("Закрыть", buttonClickEvent -> UI.getCurrent().navigate(SalesChannelsList.class));
        HorizontalLayout buttonLayout = new HorizontalLayout(create, cancel);

        HorizontalLayout groupForm = new HorizontalLayout();
        groupForm.add(formLayout);

        layout.add(buttonLayout, groupForm);
        add(layout);
    }
}
















