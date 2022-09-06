package com.warehouse_accounting.components;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.indiCators.DocumentsView;
import com.warehouse_accounting.components.indiCators.grids.IndicatorsGridLayout;
import com.warehouse_accounting.components.indiCators.grids.RecycleBinGridLayout;

import java.util.Arrays;
import java.util.List;

import static com.warehouse_accounting.components.UtilView.subMenuTabs;

@Route(value = "indicators", layout = AppView.class)
@PageTitle("Показатели")
public class IndicatorsSubMenuView extends VerticalLayout {

    private final Div pageContent = new Div();
    private final RecycleBinGridLayout recycleBinGridLayout;
   // private final IndicatorsGridLayout indicatorsGridLayout;

    private final DocumentsView documentsView;

    public IndicatorsSubMenuView(RecycleBinGridLayout recycleBinGridLayout,DocumentsView documentsView) {
          //  IndicatorsGridLayout indicatorsGridLayout
        this.recycleBinGridLayout = recycleBinGridLayout;
       // this.indicatorsGridLayout = indicatorsGridLayout;
        this.documentsView = documentsView;
        this.pageContent.removeAll();
        pageContent.setSizeFull();
        add(initSubMenu(), pageContent);
    }

    private Tabs initSubMenu() {
        List<String> indicatorsSubMenuItems = Arrays.asList("Показатели",
                "Документы",
                "Корзина",
                "Аудит",
                "Файлы");
        Tabs subMenuTabs = subMenuTabs(indicatorsSubMenuItems);

        subMenuTabs.addSelectedChangeListener(event -> {
            switch (event.getSelectedTab().getLabel()) {
                case "Показатели":
                    pageContent.removeAll();
                   // pageContent.add(indicatorsGridLayout);
                    break;
                case "Документы":
                    pageContent.removeAll();
                    pageContent.add(documentsView);
                    break;
                case "Корзина":
                    pageContent.removeAll();
                    pageContent.add(recycleBinGridLayout);
                    break;
                case "Аудит":
                    pageContent.removeAll();
                    pageContent.add(new Span("Аудит"));
                    break;
                case "Файлы":
                    pageContent.removeAll();
                    pageContent.add(new Span("Файлы"));
                    break;
            }
        });
        return subMenuTabs;
    }
}