package com.warehouse_accounting.components;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Arrays;
import java.util.List;

import static com.warehouse_accounting.components.UtilView.subMenuTabs;

@Route(value = "indicators", layout = AppView.class)
@PageTitle("Показатели")

public class IndicatorsSubMenuView extends VerticalLayout {
    private final Div pageContent = new Div();

    public IndicatorsSubMenuView() {
        pageContent.setSizeFull();
        add(initSubMenu(), pageContent);
    }

    private Tabs initSubMenu() {
        List<String> indicatorsMenuItems = Arrays.asList("Показатели",
                "Документы",
                "Корзина",
                "Аудит",
                "Файлы");
//        Этот метод дублируется для восьми вкладок, я вынес его в отдельный
//        утилитный класс UtilView
       return subMenuTabs(indicatorsMenuItems);
    }
}
