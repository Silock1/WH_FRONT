package com.warehouse_accounting.components;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Arrays;
import java.util.List;

import static com.warehouse_accounting.components.UtilView.subMenuTabs;

@Route(value = "contragents", layout = AppView.class)
@PageTitle("Контрагенты")
public class ContragentsSubMenuView extends VerticalLayout {

    private final Div pageContent = new Div();

    public ContragentsSubMenuView() {
        pageContent.setSizeFull();
        add(initSubMenu(), pageContent);
    }

    private Tabs initSubMenu() {
        List<String> goodsMenuItems = Arrays.asList("Контрагенты",
                "Договоры",
                "Звонки");
        Tabs subMenuTabs = subMenuTabs(goodsMenuItems);

        subMenuTabs.addSelectedChangeListener(event -> {
            switch (event.getSelectedTab().getLabel()) {
                case "Контрагенты":
                    pageContent.removeAll();
                    pageContent.add(new Span("Контрагенты"));
                    break;
                case "Договоры":
                    pageContent.removeAll();
                    pageContent.add(new Span("Договоры"));
                    break;
                case "Звонки":
                    pageContent.removeAll();
                    pageContent.add(new Span("Звонки"));
                    break;
            }
        });
        return subMenuTabs;
    }

}
