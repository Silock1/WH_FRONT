package com.warehouse_accounting.components.userSubMenu;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.models.dto.ApplicationDto;

@SpringComponent
@UIScope
public class ApplicationsList extends VerticalLayout {
    private final HorizontalLayout buttons;

//    private final ApplicationsFilterLayout applicationsFilterLayout;

    private final ApplicationsListGridLayout applicationsListGridLayout;

    private final FormForApplication formForApplication;

    public ApplicationsList(ApplicationsListGridLayout applicationsListGridLayout,
//                            ApplicationsFilterLayout applicationsFilterLayout,
                            FormForApplication formForApplication) {
        this.applicationsListGridLayout = applicationsListGridLayout;
//        this.applicationsFilterLayout = applicationsFilterLayout;
        this.formForApplication = formForApplication;
        this.applicationsListGridLayout.setParent(this);
        this.formForApplication.setParent(this);
        buttons = getGroupButtons();
        add(buttons, applicationsListGridLayout); //здесь был фильтр
    }

    private HorizontalLayout getGroupButtons() {

        HorizontalLayout controlButton = new HorizontalLayout();

        Button helpButton = new Button(new Image("icons/helpApp.svg", "helpApp"));
        helpButton.addThemeVariants(ButtonVariant.LUMO_LARGE, ButtonVariant.LUMO_TERTIARY);
        helpButton.addClickListener(c -> Notification.show("К МоемуСкладу можно подключить интернет-магазин и настроить\n автоматическую синхронизацию каталога товаров, списка заказов\n и остатков.",
                4000, Notification.Position.TOP_START));

        Label textLabel = new Label("Онлайн-торговля");

        Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH));
        refreshButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
        refreshButton.addClickListener(e -> applicationsListGridLayout.refreshData());
        Image buttonIcon = new Image("icons/plus.png", "Plus");
        buttonIcon.setWidth("14px");
        Button addContragent = new Button("Магазин", buttonIcon);
        addContragent.addClickListener(e -> {
            hideButtonEndGrid();
            formForApplication.build();
            add(formForApplication);
        });

//        Button filter = new Button("Фильтр");
//        filter.addThemeVariants(ButtonVariant.LUMO_SMALL);
//        filter.addClickListener(e -> applicationsFilterLayout.setVisible(!applicationsFilterLayout.isVisible()));

        controlButton.add(helpButton, textLabel, refreshButton, addContragent); //и здесь был фильтр
        setSizeFull();
        return controlButton;
    }

    public void hideButtonEndGrid() {
        buttons.setVisible(false);
        applicationsListGridLayout.setVisible(false);
//        applicationsFilterLayout.setVisible(false);
    }

    public void editFormActivate(ApplicationDto applicationDto) {
        formForApplication.build(applicationDto);
        hideButtonEndGrid();
        add(formForApplication);
    }

    public void showButtonEndGrid(Boolean refreshGrid) {
        buttons.setVisible(true);
        if (refreshGrid) applicationsListGridLayout.refreshData();
        applicationsListGridLayout.setVisible(true);
    }

}
