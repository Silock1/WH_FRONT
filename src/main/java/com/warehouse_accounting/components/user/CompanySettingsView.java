package com.warehouse_accounting.components.user;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.AppView;

@PageTitle("Настройки")
@Route(value = "profile", layout = AppView.class)
public class CompanySettingsView extends AppLayout {



    public CompanySettingsView() {
        VerticalLayout leftColumn = new VerticalLayout();
        leftColumn.addClassName("leftColumn");


        VerticalLayout rightColumn = new VerticalLayout();

    }

}
