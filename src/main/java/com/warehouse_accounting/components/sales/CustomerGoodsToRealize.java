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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.warehouse_accounting.components.contragents.grids.ContractsFilterLayout;
import com.warehouse_accounting.components.sales.grids.CustomerGoodsToRealizeFilter;
import com.warehouse_accounting.components.sales.grids.SalesGridGoodsToRealizeGet;
import com.warehouse_accounting.components.sales.grids.SalesGridGoodsToRealizeGive;
import com.warehouse_accounting.components.sales.grids.SalesGridLayout;


public class CustomerGoodsToRealize extends VerticalLayout {

    private HorizontalLayout salesGridLayout;
    private final CustomerGoodsToRealizeFilter filterLayout;
    private final TextField textFieldGridSelected = new TextField();
    private final Div pageContent;

    public CustomerGoodsToRealize(CustomerGoodsToRealizeFilter filterLayout) {
        this.filterLayout = filterLayout;
        salesGridLayout = new SalesGridGoodsToRealizeGet(textFieldGridSelected);
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
        textProducts.setText("Товары на реализации");

        Button filterButton = new Button("Фильтр");
        filterButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        filterButton.addClickListener(e -> filterLayout.setVisible(!filterLayout.isVisible()));


        HorizontalLayout getGridMenuGet = getGridMenuGet();
        HorizontalLayout getGridMenuGive = getGridMenuGive();
        HorizontalLayout printMenuBar = getPrintMenuBar();

        groupControl.add(helpButton, textProducts, getGridMenuGet, getGridMenuGive,
                filterButton, printMenuBar);
        groupControl.setAlignItems(FlexComponent.Alignment.CENTER);
        setSizeFull();
        return groupControl;
    }




    private HorizontalLayout getGridMenuGet(){
        HorizontalLayout groupControl = new HorizontalLayout();
        Button buttonGet = new Button("Принятые");
        buttonGet.addThemeVariants(ButtonVariant.LUMO_SMALL);
        buttonGet.addClickListener(clickEvent -> {
            salesGridLayout = new SalesGridGoodsToRealizeGet(textFieldGridSelected);
            pageContent.removeAll();
            pageContent.add(salesGridLayout);
            pageContent.setSizeFull();
            add(pageContent);
        });
        groupControl.add(buttonGet);
        setSizeFull();
        return groupControl;

    }

    private HorizontalLayout getGridMenuGive(){
        HorizontalLayout groupControl = new HorizontalLayout();
        Button buttonGive = new Button("Переданные");
        buttonGive.addThemeVariants(ButtonVariant.LUMO_SMALL);
        buttonGive.addClickListener(clickEvent -> {
            salesGridLayout = new SalesGridGoodsToRealizeGive(textFieldGridSelected);
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
        HorizontalLayout printItem = new HorizontalLayout(printIcon, new Text("  Печать"), caretDownIcon);
        printItem.setSpacing(false);
        printItem.setAlignItems(Alignment.CENTER);
        MenuItem print = printMenuBar.addItem(printItem);

        print.getSubMenu().addItem("Переданные товары на реализации", e -> {

        });
        print.getSubMenu().addItem("Принятые товары на реализации", e -> {

        });
        print.getSubMenu().addItem("Настроить...", e -> {

        });

        HorizontalLayout groupPrint = new HorizontalLayout();
        groupPrint.add(printMenuBar);
        groupPrint.setSpacing(false);
        groupPrint.setAlignItems(Alignment.CENTER);
        return groupPrint;
    }
}
