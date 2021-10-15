package com.warehouse_accounting.components.user.settings;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Настройки компании")
@Route(value = "companysettings", layout = SettingsView.class)
public class CompanySettingsView extends VerticalLayout {

    public CompanySettingsView() {
        VerticalLayout verticalLayout = new VerticalLayout();
        Span title = new Span("Настройки компании!");
        verticalLayout.add(title);
        add(verticalLayout);
    }
}
