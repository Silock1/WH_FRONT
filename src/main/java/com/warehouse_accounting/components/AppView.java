package com.warehouse_accounting.components;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import lombok.extern.log4j.Log4j2;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Route
@Log4j2
@CssImport(value = "./css/my-app-layout.css", themeFor = "vaadin-app-layout")
@CssImport(value = "./css/my-app-layout.css")
public class AppView extends AppLayout {
    private final String LOGO_PNG = "logo_main.svg";

    public AppView() {
        prepareNavBarTabs();
    }

    private void prepareNavBarTabs() {
        Tabs navBarTabs = new Tabs();
        List<String> tabNames = Arrays.asList("Показатели", "Закупки", "Продажи", "Товары",
                "Контрагенты", "Деньги", "Розница", "Производство", "Задачи", "Приложения");
        List<StreamResource> streamResources = Stream.of(
                "indicators.svg",
                "procurement.svg",
                "selling.svg",
                "goods.svg",
                "contragents.svg",
                "money.svg",
                "retail.svg",
                "manufacture.svg",
                "tasks.svg",
                "embed-apps.svg"
        ).map(name -> new StreamResource(name, () -> getImageInputStream(name))).collect(Collectors.toList());

        for (int i = 0; i < tabNames.size(); i++) {
            VerticalLayout verticalLayout = new VerticalLayout();
            Image icon = new Image(streamResources.get(i), "tab_icon");
            Span tabName = new Span(tabNames.get(i));
            tabName.setId("navBarText");
            verticalLayout.add(icon, tabName);
            verticalLayout.setId("vertical_layout");
            verticalLayout.setAlignItems(FlexComponent.Alignment.CENTER);
            String name = tabNames.get(i);
            verticalLayout.addClickListener(event ->
                    verticalLayout.getUI().ifPresent(ui -> ui.navigate(resolveSubMenuView(name))));
            Tab tab = new Tab(verticalLayout);
            Tab separatorTab = new Tab();
            separatorTab.setId("separator_tab");
            navBarTabs.add(tab, separatorTab);
        }

        Div rightSideNavBar = new Div();
        rightSideNavBar.add(new Image(new StreamResource("help.svg", () -> getImageInputStream("help.svg")), "help"));
        rightSideNavBar.add(new Image(new StreamResource("bell.svg", () -> getImageInputStream("bell.svg")), "notification"));
        rightSideNavBar.setMinHeight("58");
        rightSideNavBar.setId("rightSideNavBar");
        rightSideNavBar.setWidthFull();

        StreamResource resource = new StreamResource("logo_main.svg", () -> getImageInputStream(LOGO_PNG)); // Если icons будут в виде svg файлов в static лежать
        Image logo = new Image(resource, "logo_main");
        logo.setId("logo_main");
        logo.setHeight("19px");
        logo.setWidth("58px");
        addToNavbar(logo, navBarTabs, rightSideNavBar);
    }

    public static InputStream getImageInputStream(String svgIconName) {
        InputStream imageInputStream = null;
        try {
            imageInputStream = new DataInputStream(new FileInputStream("src/main/resources/static/icons/" + svgIconName));
        } catch (IOException ex) {
            log.error("При чтении icon {} произошла ошибка", svgIconName);
        }
        return imageInputStream;
    }

    private String resolveSubMenuView(String name) {
        String subMenuView;
        switch (name) {
            case "Товары":
                subMenuView = "goods";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + name);
        }
        return subMenuView;
    }
}
