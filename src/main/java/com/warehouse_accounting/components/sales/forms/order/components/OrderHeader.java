package com.warehouse_accounting.components.sales.forms.order.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.warehouse_accounting.models.dto.CustomerOrderDto;
import com.warehouse_accounting.models.dto.InvoiceDto;

import java.time.LocalDateTime;

@CssImport("./css/order_header.css")
public class OrderHeader extends HorizontalLayout {
    private final CustomerOrderDto customerOrder;

    public OrderHeader(CustomerOrderDto customerOrder) {
        this.customerOrder = customerOrder;
        addClassName("dtb-baseline");

        Label invoiceLabel = new Label("Заказ покупателя №");

        TextField orderNumber = new TextField();
        orderNumber.addValueChangeListener(x -> customerOrder.setDocNumber(x.getValue()));

        Label fromLabel = new Label(" от ");

        DateTimePicker customerOrderDate = new DateTimePicker(LocalDateTime.now());
//        customerOrderDate.addValueChangeListener(event -> customerOrder.setDate(event.getValue()));

        Icon pendingIcon = new Icon(VaadinIcon.CLOCK);
        Span pending = new Span(pendingIcon, new Span("Не оплачено"));
        pending.addClassNames("dtb-pending");
        pendingIcon.setClassName("dtb-question");
        pending.getElement().getThemeList().add("badge");

        Button payRequest = new Button("Запросить оплату");
        payRequest.addClickListener(x -> Notification.show("Not implemented"));

        Marker marker = new Marker();

        CheckboxWithHelper isCompleted = new CheckboxWithHelper("Проведено", "Непроведённый документ - это черновик. Он не учитывается в отчетах.");
        isCompleted.setValue(true);
        isCompleted.addValueChangeListener(event -> customerOrder.setIsPosted(event.getValue()));

        CheckboxWithHelper isReserved = new CheckboxWithHelper("Резерв");
        isReserved.addValueChangeListener(event -> Notification.show("Not implemented"));

        add(invoiceLabel, orderNumber, fromLabel, customerOrderDate, pending, payRequest, marker, isCompleted, isReserved);
    }

    public CustomerOrderDto getInvoice() {
        return customerOrder;
    }
}
