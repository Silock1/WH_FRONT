package com.warehouse_accounting.components.sales;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.sales.filter.GoodsToRealizeFilter;
import com.warehouse_accounting.components.sales.grids.GoodsToRealizeGetSalesGrid;
import com.warehouse_accounting.components.sales.grids.GoodsToRealizeGiveSalesGrid;
import com.warehouse_accounting.services.interfaces.GoodsToRealizeGetService;
import com.warehouse_accounting.services.interfaces.GoodsToRealizeGiveService;

@SpringComponent
@UIScope
public class CustomerGoodsToRealize extends VerticalLayout {

    private HorizontalLayout salesGridLayout;
    private final GoodsToRealizeGetService goodsToRealizeGetService;
    private final GoodsToRealizeGiveService goodsToRealizeGiveService;
    private final GoodsToRealizeFilter filterLayout;

    private final Div pageContent;

    public CustomerGoodsToRealize(GoodsToRealizeFilter filterLayout, GoodsToRealizeGetService goodsToRealizeGetService, GoodsToRealizeGiveService goodsToRealizeGiveService) {
        this.filterLayout = filterLayout;
        this.goodsToRealizeGetService = goodsToRealizeGetService;
        this.goodsToRealizeGiveService = goodsToRealizeGiveService;
        salesGridLayout = new GoodsToRealizeGetSalesGrid(goodsToRealizeGetService);
        Div pageContent = new Div();
        pageContent.add(salesGridLayout);
        pageContent.setSizeFull();
        this.pageContent = pageContent;
        add(getGroupButtons(), filterLayout, pageContent);
    }

    private HorizontalLayout getGroupButtons() {
        HorizontalLayout groupControl = new HorizontalLayout();

        Button helpButton = new Button(new Icon(VaadinIcon.QUESTION_CIRCLE));
        helpButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);

        Label textProducts = new Label();
        textProducts.setText("???????????? ???? ????????????????????");

        Button filterButton = new Button("????????????");
        filterButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        filterButton.addClickListener(e -> filterLayout.setVisible(!filterLayout.isVisible()));


        HorizontalLayout getGridMenuGet = getGridMenuGet();
        HorizontalLayout getGridMenuGive = getGridMenuGive();
        HorizontalLayout printMenuBar = getPrintMenuBar();

        Button setting = new Button(new Icon(VaadinIcon.COG));
        setting.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        setting.addClickListener(event -> {

        });

        groupControl.add(helpButton, textProducts, getGridMenuGet, getGridMenuGive,
                filterButton, printMenuBar, setting);
        groupControl.setAlignItems(FlexComponent.Alignment.CENTER);
        setSizeFull();
        return groupControl;
    }


    private HorizontalLayout getGridMenuGet() {
        HorizontalLayout groupControl = new HorizontalLayout();


        Button buttonGet = new Button("????????????????");
        buttonGet.addThemeVariants(ButtonVariant.LUMO_SMALL);
        buttonGet.addClickListener(clickEvent -> {
            salesGridLayout = new GoodsToRealizeGetSalesGrid(goodsToRealizeGetService);
            pageContent.removeAll();
            pageContent.add(salesGridLayout);
            pageContent.setSizeFull();
            add(pageContent);
        });
        groupControl.add(buttonGet);
        setSizeFull();
        return groupControl;

    }

    private HorizontalLayout getGridMenuGive() {
        HorizontalLayout groupControl = new HorizontalLayout();
        Button buttonGive = new Button("????????????????????");
        buttonGive.addThemeVariants(ButtonVariant.LUMO_SMALL);
        buttonGive.addClickListener(clickEvent -> {
            salesGridLayout = new GoodsToRealizeGiveSalesGrid(goodsToRealizeGiveService);
            pageContent.removeAll();
            pageContent.add(salesGridLayout);
            pageContent.setSizeFull();
            add(pageContent);
        });
        groupControl.add(buttonGive);
        setSizeFull();
        return groupControl;

    }


    private HorizontalLayout getPrintMenuBar() {
        MenuBar printMenuBar = new MenuBar();
        printMenuBar.addThemeVariants(MenuBarVariant.LUMO_SMALL);
        Icon printIcon = new Icon(VaadinIcon.PRINT);
        printIcon.setSize("12px");
        Icon caretDownIcon = new Icon(VaadinIcon.CARET_DOWN);
        caretDownIcon.setSize("12px");
        HorizontalLayout printItem = new HorizontalLayout(printIcon, new Text("  ????????????"), caretDownIcon);
        printItem.setSpacing(false);
        printItem.setAlignItems(Alignment.CENTER);
        MenuItem print = printMenuBar.addItem(printItem);

        print.getSubMenu().addItem("???????????????????? ???????????? ???? ????????????????????", e -> {

        });
        print.getSubMenu().addItem("???????????????? ???????????? ???? ????????????????????", e -> {

        });
        print.getSubMenu().addItem("??????????????????...", e -> {

        });

        HorizontalLayout groupPrint = new HorizontalLayout();
        groupPrint.add(printMenuBar);
        groupPrint.setSpacing(false);
        groupPrint.setAlignItems(Alignment.CENTER);
        return groupPrint;
    }
}
