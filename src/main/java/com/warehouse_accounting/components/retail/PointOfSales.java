package com.warehouse_accounting.components.retail;



import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.warehouse_accounting.components.retail.forms.CreatePointOfSalesForm;
import com.warehouse_accounting.components.retail.grids.PointOfSalesGridLayout;



/*
Точки продаж
*/

public class PointOfSales extends VerticalLayout {

    private PointOfSalesGridLayout pointOfSalesGridLayout;
    private final TextField textFieldGridSelected = new TextField();
    private final Div parentLayer;

    public PointOfSales(Div parentLayer) {
        this.parentLayer = parentLayer;
        pointOfSalesGridLayout = new PointOfSalesGridLayout(textFieldGridSelected);
        Div pageContent = new Div();
        pageContent.add(pointOfSalesGridLayout);
        pageContent.setSizeFull();
        add(getGroupButtons(), pageContent);
    }

    private HorizontalLayout getGroupButtons() {
        HorizontalLayout groupControl = new HorizontalLayout();

        Button helpButton = new Button(new Icon(VaadinIcon.QUESTION_CIRCLE));
        helpButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
        helpButton.addClickListener(e->{
            Notification.show("Для каждой точки продаж можно назначить отдельный склад, тип цен и указать кассиров, " +
                    "которые получат к ней доступ. В карточке точки продаж отображаются данные о кассе," +
                    " фискальном накопителе и активности.", 5000, Notification.Position.TOP_START);
        });

        Label textProducts = new Label();
        textProducts.setText("Точки продаж");

        Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH));
        refreshButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);

        Button addOrderButton = new Button("Точка продаж", new Icon(VaadinIcon.PLUS));
        addOrderButton.addThemeVariants(ButtonVariant.LUMO_SMALL);

        addOrderButton.addClickListener(event -> {
            removeAll();
            add(getGroupButtons());
            CreatePointOfSalesForm pointForm = new CreatePointOfSalesForm(parentLayer, this);
            parentLayer.removeAll();
            parentLayer.add(pointForm);
        });

        Button addFilterButton = new Button("Фильтр", new Icon(VaadinIcon.PLUS));
        addFilterButton.addThemeVariants(ButtonVariant.LUMO_SMALL);

        Button addDownloadButton = new Button("Скачать приложение", new Icon(VaadinIcon.DOWNLOAD));
        addFilterButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);


        groupControl.add(helpButton, textProducts, refreshButton, addOrderButton,
                addFilterButton, addDownloadButton);
        setSizeFull();
        return groupControl;
    }

}
