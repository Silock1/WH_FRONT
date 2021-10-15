package com.warehouse_accounting.components.user.settings;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
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
@Route(value = "profile")
@CssImport(value = "./css/settings.css", themeFor = "vaadin-app-layout")
@CssImport(value = "./css/settings.css")
public class SettingsView extends AppLayout {

    public SettingsView() {

        List<String> settingsList = Arrays.asList("Настройки компании", "Сценарии", "Скидки");
        Tabs settingsTabs = new Tabs();
        settingsTabs.setOrientation(Tabs.Orientation.VERTICAL);
        Span settingsTitle = new Span("НАСТРОЙКИ");
        settingsTitle.addClassName("settingsTitle");
        for (String name : settingsList) {
            HorizontalLayout layout = new HorizontalLayout();
            layout.addClassName("layout");
            Span span = new Span(name);
            span.addClassName("span");
            layout.add(span);
            Tab tab = new Tab(layout);
            tab.addClassName("tab");
            settingsTabs.add(tab);
        }

        List<String> imExList = Arrays.asList("Импорт", "Экспорт", "Интернет-магазины", "Токены");
        Tabs importExportTabs = new Tabs();
        importExportTabs.setOrientation(Tabs.Orientation.VERTICAL);
        Span importExportTitle = new Span("ОБМЕН ДАННЫМИ");
        importExportTitle.addClassName("importExportTitle");
        for (String name : imExList) {
            HorizontalLayout layout = new HorizontalLayout();
            layout.addClassName("layout");
            Span span = new Span(name);
            span.addClassName("span");
            layout.add(span);
            Tab tab = new Tab(layout);
            tab.addClassName("tab");
            importExportTabs.add(tab);
        }
        importExportTabs.setSelectedIndex(-1);

        List<String> catalogList = Arrays.asList("Юр. лица", "Сотрудники", "Склады", "Каналы продаж",
                                                 "Валюты", "Проекты", "Страны", "Единицы измерения");
        Tabs catalogTabs = new Tabs();
        catalogTabs.setOrientation(Tabs.Orientation.VERTICAL);
        Span catalogTitle= new Span("СПРАВОЧНИКИ");
        catalogTitle.addClassName("catalogTitle");
        for (String name : catalogList) {
            HorizontalLayout layout = new HorizontalLayout();
            layout.addClassName("layout");
            Span span = new Span(name);
            span.addClassName("span");
            layout.add(span);
            Tab tab = new Tab(layout);
            tab.addClassName("tab");
            catalogTabs.add(tab);
        }
        catalogTabs.setSelectedIndex(-1);

        addToNavbar(new AppView());
        addToDrawer(settingsTitle,
                    settingsTabs,
                    importExportTitle,
                    importExportTabs,
                    catalogTitle,
                    catalogTabs);
    }

}
