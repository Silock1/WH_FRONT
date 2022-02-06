package com.warehouse_accounting.components.user;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.user.settings.SettingsView;

import com.warehouse_accounting.models.dto.SalesChannelDto;
import com.warehouse_accounting.services.impl.SalesChannelsServiceImpl;
import com.warehouse_accounting.services.interfaces.SalesChannelsService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import java.awt.*;
import java.time.LocalDateTime;

@PageTitle("Добавить канал продаж")
@Route(value = "add_channel", layout = SettingsView.class)
public class SalesChannelsAddView extends VerticalLayout {

    private final SalesChannelsService salesChannelsService;
    private final Grid<SalesChannelDto> grid = new Grid<>();
    private Notification notification;

    private TextField nameField;
    private TextField typeField;
    private TextField descrField;
    private TextField accessField;
    private TextField ownerDepartmentField;
    private TextField ownerEmployeeField;
//    private LocalDateTime whenChangedField;
    private TextField whoChangedField;


    public SalesChannelsAddView(SalesChannelsService salesChannelsService) {
        this.salesChannelsService = salesChannelsService;
        AddForm();
    }

    private  void  AddForm(){

        nameField = new TextField("Наименование");
        typeField = new TextField("Тип");
        descrField = new TextField("Описание");
        accessField = new TextField("Общий доступ");
        ownerDepartmentField = new TextField("Владелец-отдел");
        ownerEmployeeField = new TextField("Владелец-сотрудник");
//        whenChangedField = new L("Когда изменён");
        whoChangedField = new TextField("Кто изменил");

        FormLayout formLayout = new FormLayout(
            nameField,
            typeField,
            descrField,
            accessField,
            ownerDepartmentField,
            ownerEmployeeField,
//            whenChangedField,
            whoChangedField
        );

        HorizontalLayout buttonLayout = new HorizontalLayout(
//            create,
//            cancel
            initButtons()
        );
        add(formLayout,buttonLayout);
    }

    private VerticalLayout initButtons(){
        VerticalLayout verticalLayout = new VerticalLayout();

        Button save = new Button("Сохранить", e -> {
            try {
                Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://localhost:4446")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

                SalesChannelsService channelsService = new SalesChannelsServiceImpl("/api/sales_channels", retrofit);
                SalesChannelDto channelDto = new SalesChannelDto();

                channelDto.setId(10L);
                channelDto.setName(nameField.getValue());
                channelDto.setType(typeField.getValue());
                channelDto.setDescription(descrField.getValue());
                channelDto.setGeneralAccessC(accessField.getValue());
                channelDto.setOwnerDepartment(ownerDepartmentField.getValue());
                channelDto.setOwnerEmployee(ownerEmployeeField.getValue());
                channelDto.setWhenChanged(LocalDateTime.now().toString());
                channelDto.setWhoChanged(whoChangedField.getValue());

                channelsService.create(channelDto);

                notification = Notification.show("Канал продаж сохранён");
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                notification.setPosition(Notification.Position.BOTTOM_STRETCH);
            } catch (Exception exception) {
                notification = Notification.show("Ошибка сохранения канала продаж");
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.setPosition(Notification.Position.BOTTOM_STRETCH);
            }

        });

        Button close = new Button("Отменить", e -> {

        });

        verticalLayout.add(save, close);

        return verticalLayout;
    }
}
















