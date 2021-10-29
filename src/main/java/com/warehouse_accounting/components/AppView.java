package com.warehouse_accounting.components;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.warehouse_accounting.components.help.HelpButton;
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
    private final String AVATAR_PNG = "avatar-placeholder.svg";

    public AppView() {
        prepareNavBarTabs();
    }

    private void prepareNavBarTabs() {
        Tabs navBarTabs = new Tabs();
        List<String> tabNames = Arrays.asList("Показатели", "Закупки", "Продажи", "Товары",
                "Контрагенты", "Деньги", "Розница", "Производство", "Задачи", "Приложения");
        List<VaadinIcon> streamResources = Stream.of(
                VaadinIcon.SPLINE_CHART,
                VaadinIcon.STOCK,
                VaadinIcon.SUITCASE,
                VaadinIcon.CART_O,
                VaadinIcon.USERS,
                VaadinIcon.WALLET,
                VaadinIcon.SHOP,
                VaadinIcon.FACTORY,
                VaadinIcon.TASKS,
                VaadinIcon.GRID_BIG_O).collect(Collectors.toList());

        for (int i = 0; i < tabNames.size(); i++) {
            VerticalLayout verticalLayout = new VerticalLayout();
            Icon icon = new Icon(streamResources.get(i));
            icon.setSize("18px");
            icon.setColor("white");
            icon.setId("navBarIcon");
            Span tabName = new Span(tabNames.get(i));
            tabName.setId("navBarText");
            verticalLayout.add(icon, tabName);
            verticalLayout.setId("vertical_layout");
            verticalLayout.setAlignItems(FlexComponent.Alignment.CENTER);
            String name = tabNames.get(i);
            verticalLayout.addClickListener(event ->
                    verticalLayout.getUI().ifPresent(ui -> ui.navigate(resolveSubMenuView(name))));
            Tab tab = new Tab(verticalLayout);
            tab.setId("vaadinTab");
            Tab separatorTab = new Tab();
            separatorTab.setId("separator_tab");
            navBarTabs.add(tab, separatorTab);
        }

        Div rightSideNavBar = new Div();
        Icon iconHelp = new Icon(VaadinIcon.QUESTION_CIRCLE_O);
        iconHelp.setColor("white");
        iconHelp.setSize("18px");
        iconHelp.setSize("22px");
        Div helpDiv = new Div();
        helpDiv.setVisible(false);
        HelpButton helpButton = new HelpButton(helpDiv);
        helpDiv.add(helpButton.helpDivInit());
        iconHelp.addClickListener(iconClickEvent -> {
            helpDiv.setVisible(helpButton.helpDivVisible());
        });
        Icon iconBell = new Icon(VaadinIcon.BELL_O);
        iconBell.setColor("white");
        iconBell.setSize("22px");


        rightSideNavBar.add(iconHelp);
        rightSideNavBar.add(iconBell);
        rightSideNavBar.setMinHeight("58");
        rightSideNavBar.setId("rightSideNavBar");
        rightSideNavBar.setWidthFull();

        StreamResource resource = new StreamResource("logo_main.svg", () -> getImageInputStream(LOGO_PNG)); // Если icons будут в виде svg файлов в static лежать
        Image logo = new Image(resource, "logo_main");
        logo.setId("logo_main");
        logo.setHeight("21px");
        logo.setWidth("58px");

        MenuBar userNavBar = new MenuBar();
        userNavBar.setOpenOnHover(true);
        MenuItem profile = userNavBar.addItem("Пользователь");
        SubMenu userSubMenu = profile.getSubMenu();
        userSubMenu.addItem("Настройки пользователя", event -> profile.getUI().ifPresent(ui -> ui.navigate("profile/settings")));
        userSubMenu.addItem("Настройки", event -> profile.getUI().ifPresent(ui -> ui.navigate("companysettings")));
        profile.getSubMenu().add(new Hr());
        userSubMenu.addItem("Новости", event -> profile.getUI().ifPresent(ui -> ui.navigate("feed")));
        userSubMenu.addItem("Спецпредожения", event -> profile.getUI().ifPresent(ui -> ui.navigate("specialOffers")));
        userSubMenu.addItem("Приложения", event -> profile.getUI().ifPresent(ui -> ui.navigate("applications")));
        userSubMenu.addItem("Подписка", event -> profile.getUI().ifPresent(ui -> ui.navigate("subscription")));
        profile.getSubMenu().add(new Hr());
        userSubMenu.addItem("Выход", event -> profile.getUI().ifPresent(ui -> ui.navigate("logout")));

        StreamResource res = new StreamResource("avatar-placeholder.svg", () -> getImageInputStream(AVATAR_PNG));
        Image image = new Image(res, "avatar-placeholder");
        image.setId("avatar-placeholder");
        image.setSizeFull();

        Tab userMenu = new Tab(userNavBar);
        userMenu.getStyle().set("width", "220px");
        userMenu.add(image);

        addToNavbar(logo, navBarTabs, rightSideNavBar, userMenu, helpDiv);
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
            case "Контрагенты":
                subMenuView = "contragents";
                break;
            case "Производство":
                subMenuView = "production";
                break;
            case "Продажи":
                subMenuView = "sales";
                break;
            case "Закупки":
                subMenuView = "purchases";
                break;
            case "Деньги":
                subMenuView = "money";
                break;
            case "Розница":
                subMenuView = "retail";
                break;
            case "Показатели":
                subMenuView = "indicators";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + name);
        }
        return subMenuView;
    }
}

