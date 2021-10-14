package com.warehouse_accounting.components.user.settings;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.warehouse_accounting.components.AppView;

import java.util.Arrays;
import java.util.List;

import static com.warehouse_accounting.components.UtilView.subMenuTabs;

@PageTitle("Настройки")
@Route(value = "profile", layout = AppView.class)
public class CompanySettingsView extends HorizontalLayout {

    public CompanySettingsView() {
        List<String> settingsTabs = Arrays.asList(
                "Настройки компании",
                "Сценарии",
                "Скидки");
        Tabs subMenuTabs = subMenuTabs(settingsTabs);
        subMenuTabs.setOrientation(Tabs.Orientation.VERTICAL);
        subMenuTabs.setHeight("100%");
        add(subMenuTabs);
    }

}
