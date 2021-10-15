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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        createSubPanel(settingsList, settingsTabs);

        List<String> imExList = Arrays.asList("Импорт", "Экспорт", "Интернет-магазины", "Токены");
        Tabs importExportTabs = new Tabs();
        importExportTabs.setOrientation(Tabs.Orientation.VERTICAL);
        Span importExportTitle = new Span("ОБМЕН ДАННЫМИ");
        importExportTitle.addClassName("importExportTitle");
        createSubPanel(imExList, importExportTabs);

        List<String> catalogList = Arrays.asList("Юр. лица", "Сотрудники", "Склады", "Каналы продаж",
                "Валюты", "Проекты", "Страны", "Единицы измерения");
        Tabs catalogTabs = new Tabs();
        catalogTabs.setOrientation(Tabs.Orientation.VERTICAL);
        Span catalogTitle = new Span("СПРАВОЧНИКИ");
        catalogTitle.addClassName("catalogTitle");
        createSubPanel(catalogList, catalogTabs);

        addToNavbar(new AppView());
        addToDrawer(settingsTitle,
                settingsTabs,
                importExportTitle,
                importExportTabs,
                catalogTitle,
                catalogTabs);
    }


    void createSubPanel(List<String> list, Tabs tabs) {
        for (String name : list) {
            HorizontalLayout layout = new HorizontalLayout();
            layout.addClassName("layout");
            layout.addClickListener(event ->
                    layout.getUI().ifPresent(ui -> ui.navigate(getUrlByName(name))));
            Span span = new Span(name);
            span.addClassName("span");
            layout.add(span);
            Tab tab = new Tab(layout);
            tab.addClassName("tab");
            tabs.add(tab);
        }
    }

    String getUrlByName(String name) {
        if (tabsList.containsKey(name)) {
            return tabsList.get(name);
        } else {
            throw new IllegalStateException("Unexpected value: " + name);
        }
    }
    Map<String, String> tabsList = new HashMap<>();
    {
        tabsList.put("Настройки компании", "companysettings");
        tabsList.put("Сценарии", "scripttemplate");
        tabsList.put("Скидки", "discount");
        tabsList.put("Импорт", "import");
        tabsList.put("Экспорт", "export");
        tabsList.put("Интернет-магазины", "connectorsettings");
        tabsList.put("Токены", "token");
        tabsList.put("Юр. лица", "mycompany");
        tabsList.put("Сотрудники", "employee");
        tabsList.put("Склады", "warehouse");
        tabsList.put("Каналы продаж", "saleschannel");
        tabsList.put("Валюты", "currency");
        tabsList.put("Проекты", "project");
        tabsList.put("Страны", "country");
        tabsList.put("Единицы измерения", "uom");
    }

}
