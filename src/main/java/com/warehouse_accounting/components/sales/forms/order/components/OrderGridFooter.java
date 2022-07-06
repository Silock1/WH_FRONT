package com.warehouse_accounting.components.sales.forms.order.components;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.warehouse_accounting.components.sales.forms.order.types.GridSummaryReciver;
import com.warehouse_accounting.components.sales.forms.order.types.OrderSummary;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

// todo: заполнять поле comment в InvoiceDto

public class OrderGridFooter extends HorizontalLayout implements GridSummaryReciver {
    private boolean enableNds = true;
    private boolean priceIncludeNds = false;
    private BigDecimal weight = BigDecimal.ZERO;
    private BigDecimal volume = BigDecimal.ZERO;
    private int amount = 0;
    private BigDecimal price = BigDecimal.ZERO;
    private BigDecimal totalPrice = BigDecimal.ZERO;
    private Span weightField = new Span("Вес: 0.0");
    private Span volumeField = new Span("Объём: 0.0");
    private Span amountField = new Span("Количество: 0.0");
    private Span priceField = new Span("Промежуточный итог: 0.0");
    private Span totalPriceField = new Span("Итог: 0.0");
    private OrderGrid grid;

    public OrderGridFooter() {
        setWidthFull();
        TextArea comment = new TextArea();
        comment.setWidthFull();
        comment.setPlaceholder("Комментарий");

        TextField outerCode = new TextField();
        outerCode.setEnabled(false);
        outerCode.addValueChangeListener(x -> Notification.show("Not implemented (" + x.getValue() + ")"));

        Checkbox outerCodeSwitch = new Checkbox("Внешний код");
        outerCodeSwitch.addValueChangeListener(event -> { outerCode.setEnabled(event.getValue()); });

        Checkbox ndsIncluded = new Checkbox("Цена включает НДС");
        ndsIncluded.addValueChangeListener(event -> {
            priceIncludeNds = event.getValue();
            grid.includeNds(priceIncludeNds);
            refresh();
        });

        Checkbox nds = new Checkbox("НДС");
        nds.setValue(true);
        nds.addValueChangeListener(event -> {
            enableNds = event.getValue();
            ndsIncluded.setEnabled(enableNds);
            grid.includeNds(!enableNds || enableNds && priceIncludeNds);
            refresh();
        });

        add(new VerticalLayout(comment, new Div(outerCodeSwitch, outerCode)),
                new HorizontalLayout(
                        new VerticalLayout(priceField, nds, ndsIncluded),
                        new VerticalLayout(totalPriceField, weightField, volumeField, amountField)
                )
        );
    }

    public OrderGridFooter(OrderGrid grid) {
        this();
        this.grid = grid;
    }

    @Override
    public void allowSummary(OrderSummary summary) {
        weight = summary.getWeight();
        amount = summary.getAmount();
        volume = summary.getVolume();
        price = summary.getPriceWithoutNds();
        totalPrice = summary.getPriceWithNds();
        refresh();
    }

    private void refresh() {
        weightField.setText("Вес: " + weight.round(new MathContext(2, RoundingMode.HALF_UP)).floatValue());
        amountField.setText("Количество: " + amount);
        volumeField.setText("Объём: " + volume.round(new MathContext(2, RoundingMode.HALF_UP)).floatValue());
        priceField.setText("Промежуточный итог: " + price.round(new MathContext(2, RoundingMode.HALF_UP)).floatValue());
        totalPriceField.setText("Итог: " + (enableNds && !priceIncludeNds ? totalPrice : price)
                .round(new MathContext(2, RoundingMode.HALF_UP)).floatValue());
    }
}
